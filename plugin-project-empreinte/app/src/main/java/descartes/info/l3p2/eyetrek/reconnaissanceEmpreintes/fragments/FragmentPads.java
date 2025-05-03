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

import java.util.ArrayList;
import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.AlertDialogFactory;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.Animal;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl;

/**
 * Ce fragment permet la gestion de la page qui permet de faire le choix entre les
 * différents coussinets des animaux.
 * Debug by Yaêl Temam on 03/2023
 */
public class FragmentPads extends Fragment {


    public FragmentPads() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AnimalDaoImpl animalDaoImpl= new AnimalDaoImpl(getContext());
        View view = inflater.inflate(R.layout.fragment_pads, container, false);
        RadioGroup groupeCoussinet = view.findViewById(R.id.groupeNbCoussinet);
        RadioButton coussinet4 = view.findViewById(R.id.radio_4Coussinets);
        RadioButton coussinet5 = view.findViewById(R.id.radio_5Coussinets);
        RadioGroup groupeGriffe = view.findViewById(R.id.groupeGriffe);
        RadioButton avecGriffe = view.findViewById(R.id.radio_Oui);
        RadioButton sansGriffe = view.findViewById(R.id.radio_Non);
        Button valider = view.findViewById(R.id.valider);
        Button reset = view.findViewById(R.id.reset);

        ImageButton cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setClassName("descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes",
                    "descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes.activity.AnimalActivity");
            startActivity(intent);
        });
        cancel.setVisibility(View.INVISIBLE);

        //Click pour reset tous les boutons
        reset.setOnClickListener((v) -> {
            groupeCoussinet.clearCheck();
            groupeGriffe.clearCheck();
            for (int i = 0; i < groupeCoussinet.getChildCount(); i++) {
                groupeCoussinet.getChildAt(i).setEnabled(true);
            }
            for (int i = 0; i < groupeGriffe.getChildCount(); i++) {
                groupeGriffe.getChildAt(i).setEnabled(true);
            }
        });
        valider.setOnClickListener((v) -> {
            if (coussinet4.isChecked() || coussinet5.isChecked()) {
                if (avecGriffe.isChecked() || sansGriffe.isChecked()) {
                    List<Animal> animalList = new ArrayList<>();
                    int nbCoussinetID = groupeCoussinet.getCheckedRadioButtonId();
                    RadioButton radioCoussinet = view.findViewById(nbCoussinetID);
                    String nbCoussinet = radioCoussinet.getText().toString();
                    int nbGriffeID = groupeGriffe.getCheckedRadioButtonId();
                    RadioButton radioGriffe = view.findViewById(nbGriffeID);
                    String nbGriffe = radioGriffe.getText().toString();
                    if (nbCoussinet.equals("4 Coussinets")) {
                        if (nbGriffe.equals("Avec Griffes")) {
                            animalList = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbCoussinet=4 AND griffe=1");
                        } else {
                            animalList = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbCoussinet=4 AND griffe=0");
                        }
                    } else if (nbCoussinet.equals("5 Coussinets")) {
                        if (nbGriffe.equals("Avec Griffes")) {
                            animalList = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbCoussinet=5 AND griffe=1");
                        } else {
                            animalList = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbCoussinet=5 AND griffe=0");
                        }
                    }
                    StringBuilder res = new StringBuilder();
                    for (Animal animal : animalList) {
                        Log.e("NOM", animal.getNom());
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
        coussinet4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeGriffe.getChildCount(); i++) {
                    groupeGriffe.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < groupeGriffe.getChildCount(); i++) {
                    groupeGriffe.getChildAt(i).setEnabled(true);
                }
            }
        });
        coussinet5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupeGriffe.getChildCount(); i++) {
                    groupeGriffe.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < groupeGriffe.getChildCount(); i++) {
                    groupeGriffe.getChildAt(i).setEnabled(true);
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
