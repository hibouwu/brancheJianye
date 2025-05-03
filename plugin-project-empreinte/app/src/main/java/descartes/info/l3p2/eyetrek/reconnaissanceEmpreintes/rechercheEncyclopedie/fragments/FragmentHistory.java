package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;

/**
 * Ce fragment permet d'afficher l'historique des analyses d'un utilisateur.
 *
 * @author Quaboul le 19/04/2018.
 * @author Evelyne TRAN (débogage) - 12/03/2023
 */
public class FragmentHistory extends Fragment {

    private Button leaf, mushroom, bird;
    SharedPreferences sharedPreferences;
    private static final String PREFS = "app_prefs";

    public FragmentHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.history, container, false);
        leaf = view.findViewById(R.id.leaf);
        bird = view.findViewById(R.id.button7);
        mushroom = view.findViewById(R.id.mushroom);
        if (getActivity()==null) throw new AssertionError("Activity cannot be null");
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String prefs_lastname = sharedPreferences.getString("id", null);
        Toast.makeText(getActivity(),prefs_lastname , Toast.LENGTH_SHORT).show();

//        leaf.setOnClickListener((v) -> {
//            getParentFragmentManager().beginTransaction().add(R.id.contenu_fragment, new LeafHistoric()).commit();
//        });
//        bird.setOnClickListener((v) -> {
//            getParentFragmentManager().beginTransaction().add(R.id.contenu_fragment, new BirdHistoric()).commit();
//        });
//        mushroom.setOnClickListener((v) -> {
//            getParentFragmentManager().beginTransaction().add(R.id.contenu_fragment, new MushroomHistoric()).commit();
//        });
//
//        getParentFragmentManager().beginTransaction().add(R.id.contenu_fragment, new LeafHistoric()).commit();

        return view;
    }
}
