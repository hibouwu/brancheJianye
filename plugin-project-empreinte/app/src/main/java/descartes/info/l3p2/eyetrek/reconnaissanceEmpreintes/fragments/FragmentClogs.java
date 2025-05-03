package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments;


import android.content.Intent;
import android.os.Bundle;
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
 * La classe qui affiche le fragment le choix des sabots.
 */
public class FragmentClogs extends Fragment {

    /**
     * La gestion de la table animal.
     */
    private AnimalDaoImpl animalDaoImpl;

    /**
     * Le radio pour faire le choix du nombre de sabots.
     */
    private RadioGroup groupeNbSabot;

    /**
     * Le radio pour le choix si nombre paire de sabots.
     */
    private RadioButton paire;

    /**
     * Le radio pour le choix si nombre impaire de sabots.
     */
    private RadioButton impaire;

    /**
     * Le radio pour le choix si les sabots sont en nombre paires.
     */
    private  RadioGroup groupePaire;

    /**
     * Le radio pour le choix si les sabots sont en nombre de 4.
     */
    private RadioButton paire4;

    /**
     * Le radio pour le choix si les sabots sont en nombre de 2.
     */
    private RadioButton paire2;

    /**
     * Le radio pour le choix de la forme des sabots.
     */
    private RadioGroup groupeForme;

    /**
     * Le radio si les sabots sont de la forme concave.
     */
    private RadioButton concave;

    /**
     * Le radio si les sabots sont de la forme convexe.
     */
    private RadioButton convexe;

    /**
     * Le radio si les sabots sont de la forme circulaire.
     */
    private RadioButton circulaire;

    /**
     * Le buton pour refaire les choix.
     */
    private Button reset;

    /**
     * Le bouton pour valider les choix.
     */
    private Button valider;

    /**
     * Le bouton annuler.
     */
    private ImageButton cancel;

    /**
     * Constructeurs.
     */
    public FragmentClogs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clogs, container, false);

        animalDaoImpl= new AnimalDaoImpl(getContext());
        groupeNbSabot = view.findViewById(R.id.groupeNbSabot);
        paire = view.findViewById(R.id.radio_Paire);
        impaire = view.findViewById(R.id.radio_Impaire);
        groupePaire = view.findViewById(R.id.groupeDoigt);
        paire4 = view.findViewById(R.id.radio_4);
        paire2 = view.findViewById(R.id.radio_2);
        groupeForme = view.findViewById(R.id.groupeForme);
        concave = view.findViewById(R.id.radio_concave);
        convexe = view.findViewById(R.id.radio_convexe);
        circulaire = view.findViewById(R.id.radio_circulaire);
        reset = view.findViewById(R.id.reset);
        valider = view.findViewById(R.id.valider);
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setClassName("descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes",
                    "descartes.info.l3a1.eyetrek2.reconnaissanceEmpreintes.activity.AnimalActivity");
            startActivity(intent);
        });
        cancel.setVisibility(View.INVISIBLE);

        for (int i = 0; i < groupePaire.getChildCount(); i++) {
            groupePaire.getChildAt(i).setEnabled(false);
        }
        for (int i = 0; i < groupeForme.getChildCount(); i++) {
            groupeForme.getChildAt(i).setEnabled(false);
        }

        // Clique sur le bouton valider
        valider.setOnClickListener((v) -> {
            if (paire.isChecked() || impaire.isChecked()) {
                if (paire4.isChecked() || paire2.isChecked() &&(concave.isChecked() ||
                        convexe.isChecked() || circulaire.isChecked()) || impaire.isChecked()) {
                    List<Animal> animals;
                    int nbSabotID = groupeNbSabot.getCheckedRadioButtonId();
                    RadioButton radioSabot = view.findViewById(nbSabotID);
                    String nbSabot = radioSabot.getText().toString();
                    if ((nbSabot.equals("Mono"))) {
                        animals = animalDaoImpl
                                .getAnimalsFromRequest("SELECT * FROM animal WHERE nbSabot=1");
                    } else {
                        int nbPaireID = groupePaire.getCheckedRadioButtonId();
                        RadioButton radioPaire = view.findViewById(nbPaireID);
                        String nbPaire = radioPaire.getText().toString();
                        if (nbPaire.equals("4")) {
                            animals = animalDaoImpl
                                    .getAnimalsFromRequest("SELECT * FROM animal WHERE nbSabot=4");
                        } else {
                            int formeID = groupeForme.getCheckedRadioButtonId();
                            RadioButton radioForme = view.findViewById(formeID);
                            String forme = radioForme.getText().toString();
                            switch (forme) {
                                case "Circulaire":
                                    animals = animalDaoImpl
                                            .getAnimalsFromRequest("SELECT * FROM animal WHERE nbSabot=2 AND circulaire=1");
                                    break;
                                case "Convexe":
                                    animals = animalDaoImpl
                                            .getAnimalsFromRequest("SELECT * FROM animal WHERE nbSabot=2 AND convexe=1");
                                    break;
                                default:
                                    animals = animalDaoImpl
                                            .getAnimalsFromRequest("SELECT * FROM animal WHERE nbSabot=2 AND concave=1");
                                    break;
                            }
                        }
                    }
                    String res = "";
                    for (Animal animal : animals) {
                        res = res + animal.getNom() + "\n";
                    }
                    createAlertBox("Résultats", res);
                } else {
                    createAlertBox("Erreur", "Veuillez remplir tous les champs");
                }
            } else {
                createAlertBox("Erreur", "Veuillez remplir tous les champs");
            }
        });

        // Click pour reset tous les boutons.
        reset.setOnClickListener((v) -> {
            groupeNbSabot.clearCheck();
            groupeForme.clearCheck();
            groupePaire.clearCheck();
            for (int i = 0; i < groupePaire.getChildCount(); i++) {
                groupePaire.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                groupeNbSabot.getChildAt(i).setEnabled(true);
            }
            for (int i = 0; i < groupeForme.getChildCount(); i++) {
                groupeForme.getChildAt(i).setEnabled(false);
            }
        });

        // Si l'utilisateur clique sur impaire.
        impaire.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupePaire.getChildCount(); i++) {
                    groupePaire.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeForme.getChildCount(); i++) {
                    groupeForme.getChildAt(i).setEnabled(false);
                }
            }
        });
        // Si l'utilisateur clique sur impaire.
        paire.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //On désactive tous les boutons
                for (int i = 0; i < groupePaire.getChildCount(); i++) {
                    groupePaire.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < groupeForme.getChildCount(); i++) {
                    groupeForme.getChildAt(i).setEnabled(false);
                }
            }
        });

        //Si l'utilisateur clique sur nombre de doigts = 4.
        paire4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupeForme.getChildCount(); i++) {
                    groupeForme.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                    groupeNbSabot.getChildAt(i).setEnabled(false);
                }
            }
        });

        //Si l'utilisateur clique sur nombre de doigts = 2.
        paire2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupeForme.getChildCount(); i++) {
                    groupeForme.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                    groupeNbSabot.getChildAt(i).setEnabled(false);
                }
            }
        });

        concave.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupePaire.getChildCount(); i++) {
                    groupePaire.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                    groupeNbSabot.getChildAt(i).setEnabled(false);
                }
            }
        });

        convexe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupePaire.getChildCount(); i++) {
                    groupePaire.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                    groupeNbSabot.getChildAt(i).setEnabled(false);
                }
            }
        });

        circulaire.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < groupePaire.getChildCount(); i++) {
                    groupePaire.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < groupeNbSabot.getChildCount(); i++) {
                    groupeNbSabot.getChildAt(i).setEnabled(false);
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
