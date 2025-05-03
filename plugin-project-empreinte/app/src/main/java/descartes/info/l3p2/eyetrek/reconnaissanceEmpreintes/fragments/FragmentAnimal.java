package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments;

//import static descartes.info.l3p2.eyetrek.modularite.data.ModuleDaoImpl.getInstalledModuleVersion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.InputStream;

//import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.BuildConfig;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.AlertDialogFactory;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.classes.MySQLiteHelper;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl;


/**
 * La classe s'occupe du fragment du module Analyse d'empreintes.
 * Basé sur le fragment FragmentAnimal développé pendant les années précédentes.
 *
 * @author Huihui Huang - 2021
 */
public class FragmentAnimal extends Fragment {

    /**
     * Le bouton radio.
     */
    private RadioGroup radioGroup;

    /**
     * Le bouton suivant.
     */
    private Button next;

    /**
     * Le bouton annuler.
     */
    private ImageButton cancel;

    /**
     * La gestion des fragments.
     */
    private FragmentManager fragmentManager;

    /**
     * La base de données.
     */
    private MySQLiteHelper helper;

    /**
     * La gestion de la table animal.
     */
    private AnimalDaoImpl animalDaoImpl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_animal, container, false);

        helper= MySQLiteHelper.getInstance(getContext());
        animalDaoImpl = new AnimalDaoImpl(getContext());

        // Trouver les identifiants.
        radioGroup = view.findViewById(R.id.groupe);
        next = view.findViewById(R.id.next);
        //cancel = view.findViewById(R.id.cancel);

        // Affichage version du module
        TextView versionTextView=view.findViewById(R.id.version_empreintes);

        //versionTextView.setText("Version:"+ getInstalledModuleVersion(getActivity(), "reconnaissanceEmpreintes"));
        //Log.d("FragmentAnimal", String.valueOf(versionTextView));




        // Téléchargements des données pour les animaux.
        /* Cause dédoublement des animaux dans la base de données
        if (helper.emptyTable("animal")) {
            Log.e("Database", "Adding data...");
            ProgressDialog boiteChargement;
            boiteChargement = new ProgressDialog(getContext());
            boiteChargement.setMessage("Téléchargemment des données en cours... \n Veuillez patienter.");
            boiteChargement.setTitle("Téléchargement");
            boiteChargement.setCancelable(false);
            boiteChargement.show();
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    //Ajout des animaux
                    InputStream csvAnimal = getContext().getResources().openRawResource(R.raw.animal);
                    animalDaoImpl.addAnimalFromCsv(csvAnimal, getContext());
                    Log.e("Database", "Data added !");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boiteChargement.dismiss();
            }).start();
        }
        */

        // Redirection vers la page suivante.
        next.setOnClickListener((v) -> {
            Log.e("TESt","OK");
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                createAlertBox("Erreur", "Veuillez sélectionner un choix !");
            } else {
                // Id du bouton radio sélectionné.
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // On sélectionne le bouton à partir de l'id.
                RadioButton radioButton = view.findViewById(selectedId);
                Log.e("NAME", radioButton.getText().toString());
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Empreintes en formes de Mains.
                if (radioButton.getText().toString().equals("Mains")) {
                    fragmentTransaction.replace(R.id.fragment_animal, new FragmentHands());
                    fragmentTransaction.addToBackStack("fragment_hands");
                    fragmentTransaction.commit();
                } else if (radioButton.getText().toString().equals("Coussinets")) {
                    fragmentTransaction.replace(R.id.fragment_animal, new FragmentPads());
                    fragmentTransaction.addToBackStack("fragment_pads");
                    fragmentTransaction.commit();
                } else {
                    fragmentTransaction.replace(R.id.fragment_animal, new FragmentClogs());
                    fragmentTransaction.addToBackStack("fragment_clogs");
                    fragmentTransaction.commit();
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
