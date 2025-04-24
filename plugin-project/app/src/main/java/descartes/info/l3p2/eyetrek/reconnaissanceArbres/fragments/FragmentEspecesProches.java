package descartes.info.l3p2.eyetrek.reconnaissanceArbres.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import descartes.info.l3p2.eyetrek.reconnaissanceArbres.services.UtilitaireResultat;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.R;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.adapters.Adapter;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.classes.Departement;

/**
 * Ce fragment permet d'afficher la liste des différents départements.
 *
 * @author Antony on 29/03/2019. Debug by Yaël Temam
 */

public class FragmentEspecesProches extends Fragment {

    //séparateur du fichier csv contenant les départements de France
    /*public final static char SEPARATOR=',';*/

    //ArrayList qui contiendra la liste des départements de France
    private final List<Departement> departementsList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Chargement du noeud parent du fichier layout fragment_analyse.xml
        View view = inflater.inflate(R.layout.fragment_especes_proches, container, false);

        //remplir la ville
        ajouterDepartements();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHistory);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        //puis créer un Adapter, lui fournir notre liste de départements.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new Adapter(departementsList, requireActivity(), descartes.info.l3p2.eyetrek.reconnaissanceArbres.R.string.title_reconnaissanceArbres));

        return view;
    }

    private void ajouterDepartements() {
        /*String sep = new Character(SEPARATOR).toString();*/

        ArrayList<String> lines = UtilitaireResultat.readFile(requireActivity().getResources().openRawResource(R.raw.departement));

        for(String line : lines){
            Log.i("ajouterDepartements()", line);
            try{
                StringTokenizer st = new StringTokenizer(line, ",");
                ArrayList<String> liste = new ArrayList<>();

                while(st.hasMoreTokens()){
                    liste.add(st.nextToken());
                }

                departementsList.add( new Departement( liste.get(1) +" - "+ liste.get(2) ,"@drawable/"+ liste.get(4)));
            } catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}
