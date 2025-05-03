package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
//import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.classes.DidacticielDaoImpl;

/**
 * La classe s'occupe du fragment qui permet de faire une recherche encyclopédique.
 *
 * @author Huihui HUANG - 2021
 *
 * débogage
 * @author Evelyne TRAN - 09/03/2023
 */
public class FragmentRechercheExitModule extends Fragment {

    /**
     * Le bouton pour faire une recherche encyclopédique.
     */
    private ImageButton search;

    /**
     * Constructeur par défaut.
     */
    public FragmentRechercheExitModule() {

    }

    /**
     * Constructeur construit à partir du fragment à insérer.
     * @param fragment le fragment.
     */
    public static FragmentRechercheExitModule newInstance(Fragment fragment, int id) {
        FragmentRechercheExitModule fragmentRechercheExitModule= new FragmentRechercheExitModule();
        Bundle bundle = new Bundle();
        bundle.putString("fragment", fragment.getClass().getName());
        bundle.putInt("id", id);
        fragmentRechercheExitModule.setArguments(bundle);
        return fragmentRechercheExitModule;
    }

    /**
     * @see #loadExitFragment(int)
     * @see #search(int, Fragment) 
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        assert getActivity()!=null;


        // Trouver les identifiants des éléments en questions
        search = view.findViewById(R.id.search);

        assert getArguments() != null;
        String fragmentName= getArguments().getString("fragment");
        int id= getArguments().getInt("id");

        // Quitter le module
        search.setOnClickListener((listener) -> {
            Fragment fragment= null;
            try {
                fragment = (Fragment) Class.forName(fragmentName).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException |
                    ClassNotFoundException e) {
                e.printStackTrace();
            }
            search(id, fragment);
        });

        loadExitFragment(id);

        return view;
    }

    /**
     * Quitter le module et revenir au menu principal.
     */
    private void search(int id, Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack("fragment_recherche");
        fragmentTransaction.commit();
    }

    /**
     * Quitter le module.
     * @param id l'id du fragment du module.
     */
    private void loadExitFragment(int id) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(id, new FragmentExitModule());
        fragmentTransaction.commit();
    }

}
