package descartes.info.l3p2.eyetrek.modularite.fragments;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

//import descartes.info.l3p2.eyetrek.R;

/**
 * La classe s'occupe du fragment qui permet de quitter le module.
 *
 * @author Huihui HUANG - 2021
 */
public class FragmentExitModule extends Fragment {

    /**
     * Le bouton qui permet de quitter le module et de revenir au menu principal.
     */
    private ImageButton exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R, container, false);

        // Trouver les identifiants des éléments en questions
        exit = view.findViewById(R);

        // Quitter le module
        exit.setOnClickListener((listener) -> {
            exitActivity();
        });

        return view;
    }

    /**
     * Quitter le module et revenir au menu principal.
     */
    private void exitActivity() {
        if (getActivity()!=null)
            getActivity().finish();
    }
}