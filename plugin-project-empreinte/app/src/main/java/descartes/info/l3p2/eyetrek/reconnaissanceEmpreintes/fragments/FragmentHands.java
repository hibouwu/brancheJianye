package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.AlertDialogFactory;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.Animal;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl;

/**
 * Ce fragment permet la gestion de la page qui permet de faire le choix entre
 * les différents pattes des animaux.
 * Debug by Yaël Temam on 03/2023
 */
public class FragmentHands extends Fragment {


    public FragmentHands() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AnimalDaoImpl animalDaoImpl= new AnimalDaoImpl(getContext());
        View view = inflater.inflate(R.layout.fragment_hands, container, false);
        // Nombre de doigts.
        RadioGroup groupeDoigt = view.findViewById(R.id.groupeNbSabot);
        RadioButton doigt5 = view.findViewById(R.id.radio_5);
        RadioButton doigt5a = view.findViewById(R.id.radio_5a);
        RadioButton doigt4 = view.findViewById(R.id.radio_4);
        // Palme ou non.
        RadioGroup groupePalme = view.findViewById(R.id.groupePalme);
        RadioButton palme = view.findViewById(R.id.radio_Oui);
        RadioButton nonpalme = view.findViewById(R.id.radio_Non);
        // Même taille ou non.
        RadioGroup groupeTaille = view.findViewById(R.id.groupeForme);
        RadioButton egale = view.findViewById(R.id.radio_egale);
        RadioButton nonegale = view.findViewById(R.id.radio_nonegale);
        // BoutonS.
        Button reset = view.findViewById(R.id.reset);
        Button valider = view.findViewById(R.id.valider);
        ImageButton cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setClassName("descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes",
                    "descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes.activity.AnimalActivity");
            startActivity(intent);
        });
        cancel.setVisibility(View.INVISIBLE);

        for (int i = 0; i < groupePalme.getChildCount(); i++) {
            groupePalme.getChildAt(i).setEnabled(false);
        }
        for (int i = 0; i < groupePalme.getChildCount(); i++) {
            groupeTaille.getChildAt(i).setEnabled(false);
        }
        valider.setOnClickListener((v) -> {
            if (doigt5.isChecked() || doigt5a.isChecked() || doigt4.isChecked()) {
                if (palme.isChecked() || nonpalme.isChecked() || egale.isChecked() ||
                        nonegale.isChecked() || doigt4.isChecked()) {
                    List<Animal> animalList;
                    int nbDoigtID = groupeDoigt.getCheckedRadioButtonId();
                    RadioButton radioDoigt = view.findViewById(nbDoigtID);
                    String nbDoigt = radioDoigt.getText().toString();
                    switch (nbDoigt) {
                        case "4 doigts":
                            //Celui la c'est bon
                            animalList = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbDoigt=4");
                            break;
                        case "5 doigts":
                            int nbPalmeID = groupePalme.getCheckedRadioButtonId();
                            RadioButton radioPalme = view.findViewById(nbPalmeID);
                            String nbPalme = radioPalme.getText().toString();
                            Log.e("Palme", nbPalme);
                            if (nbPalme.equals("Palmes")) {
                                animalList = animalDaoImpl
                                        .getAnimalsFromRequest("SELECT * FROM animal WHERE nbDoigt=5 AND palme=1 AND doigtA=0");
                            } else {
                                animalList = animalDaoImpl
                                        .getAnimalsFromRequest("SELECT * FROM animal WHERE nbDoigt=5 AND palme=0 AND doigtA=0");
                            }
                            break;
                        default:
                            int nbTailleID = groupeTaille.getCheckedRadioButtonId();
                            RadioButton radioTaille = view.findViewById(nbTailleID);
                            String nbTaille = radioTaille.getText().toString();
                            if (nbTaille.equals("Même taille")) {
                                animalList = animalDaoImpl
                                        .getAnimalsFromRequest("SELECT * FROM animal WHERE nbDoigt=5 AND doigtA=1 AND memeTaille=1 AND palme=0");
                            } else {
                                animalList = animalDaoImpl
                                        .getAnimalsFromRequest("SELECT * FROM animal WHERE nbDoigt=5 AND doigtA=1 AND memeTaille=0 AND palme=0");
                            }
                            break;
                    }
                    StringBuilder res = new StringBuilder();
                    for (Animal animal : animalList) {
                        res.append(animal.getNom()).append("\n");
                    }
                    createAlertBox("Résultats", res.toString());
                } else {
                    createAlertBox("Erreur", "Veuillez remplir tous les champs");
                }
            } else {
                createAlertBox("Erreur", "Veuillez remplir tous les champs");
            }
        });

        // Clic pour reset tous les boutons.
        reset.setOnClickListener((v) -> {
            groupeDoigt.clearCheck();
            groupePalme.clearCheck();
            groupeTaille.clearCheck();
            for (int i = 0; i < groupeDoigt.getChildCount(); i++) {
                groupeDoigt.getChildAt(i).setEnabled(true);
            }
            for (int i = 0; i < groupePalme.getChildCount(); i++) {
                groupePalme.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                groupeTaille.getChildAt(i).setEnabled(false);
            }
        });

        // Si l'utilisateur clique sur 4 doigts.
        doigt4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupePalme.getChildCount(); i++) {
                    groupePalme.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                    groupeTaille.getChildAt(i).setEnabled(false);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts.
        doigt5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                    groupeTaille.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupePalme.getChildCount(); i++) {
                    groupePalme.getChildAt(i).setEnabled(true);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts arrière.
        doigt5a.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupePalme.getChildCount(); i++) {
                    groupePalme.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                    groupeTaille.getChildAt(i).setEnabled(true);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts arrière.
        palme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeDoigt.getChildCount(); i++) {
                    groupeDoigt.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                    groupeTaille.getChildAt(i).setEnabled(false);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts arrière.
        nonpalme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeDoigt.getChildCount(); i++) {
                    groupeDoigt.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeTaille.getChildCount(); i++) {
                    groupeTaille.getChildAt(i).setEnabled(false);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts arrière.
        egale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeDoigt.getChildCount(); i++) {
                    groupeDoigt.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupePalme.getChildCount(); i++) {
                    groupePalme.getChildAt(i).setEnabled(false);
                }
            }
        });
        // Si l'utilisateur clique sur 5 doigts arrière.
        nonegale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeDoigt.getChildCount(); i++) {
                    groupeDoigt.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupePalme.getChildCount(); i++) {
                    groupePalme.getChildAt(i).setEnabled(false);
                }
            }
        });

        return view;
    }

    /**
     * Fonction permettant de créer une AlertBox.
     * @param title le titre.
     * @param msg le message.
     */
    private void createAlertBox(String title, String msg) {
        AlertDialogFactory.createInfoDialog(getContext(), title, msg).show();
    }
}
