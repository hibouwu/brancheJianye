package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.FragmentRechercheEmpreintes;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments.FragmentAnimal;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments.FragmentRechercheExitModule;


/**
 * La classe s'occupe de l'activité du module Analyse d'empreintes.
 *
 * @author  Huihui Huang - 2021
 */
public class Activity extends AppCompatActivity {

    private final String TAG = "AnimalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_module);


        AnimalDaoImpl animalDaoImpl= new AnimalDaoImpl(this);

        // Retrouver le fichier csv.
        int ressource= descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R.raw.animal;

        // Téléchargements des données pour les empreintes depuis le CSV.
        animalDaoImpl.downloadDataFromCsv(getBaseContext(), ressource);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_main, new FragmentAnimal());
        transaction.add(R.id.settings, FragmentRechercheExitModule
                .newInstance(new FragmentRechercheEmpreintes(), R.id.settings));
        transaction.commit();
    }

    /**
     * Méthode pour extraire une image du dossier drawable.
     * @param name nom du drawable.
     * @return un drawable.
     */
    public Drawable getDrawable(String name) {
        Context context = this.getBaseContext();
        String packageName= getApplicationContext().getPackageName()+"."+getString(R.string.title_reconnaissanceEmpreintes);
        int resourceId = context.getResources().getIdentifier(name, "drawable", packageName);
        return context.getResources().getDrawable(resourceId);
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

}