package descartes.info.l3p2.eyetrek.rechercheEncyclopedie.services;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import descartes.info.l3p2.eyetrek.rechercheEncyclopedie.adapters.ResearchAdapterRecyclerView;
import descartes.info.l3p2.eyetrek.rechercheEncyclopedie.interfaces.IDataHolder;

/**
 * Cette classe va permettre de filtrer la liste de résultats concernant la recherche.
 * Elle met à jour la liste des items à afficher selon la recherche.
 *
 * @author Dorian le 27/02/2018.
 */

public class FilterSearch extends Filter {

    private final List<IDataHolder> listResult;
    private final ResearchAdapterRecyclerView adapter;

    public FilterSearch(List<IDataHolder> listItem, ResearchAdapterRecyclerView adapter) {
        this.listResult = listItem;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence recherche) {
        //On créé une variable qui va contenir les résultats ainsi que le nombre de résultats
        FilterResults results = new FilterResults();
        //On vérifie que la recherche n'est pas vide
        if(recherche != null && recherche.length() > 0) {
            //On transforme la recherche actuelle en minuscule
            String rechercheMin = recherche.toString().toLowerCase();

            List<IDataHolder> resultatsItem = new ArrayList<>();
            //On parcours chaque élément de l'encyclopédie
            for (int i = 0; i< listResult.size(); i++) {
                //On vérifie s'il contient la recherche actuelle
                if(listResult.get(i).getName().toLowerCase().contains(rechercheMin)) {
                    //S'il contient la chaine de caractère de la recherche, on l'ajoute à la liste des résultats
                    resultatsItem.add(listResult.get(i));
                }
            }

            results.count = resultatsItem.size();
            results.values = resultatsItem;
        }
        //Si la recherche est vide, on affiche la liste actuelle
        else {
            results.count = listResult.size();
            results.values = listResult;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //On met à jour la liste d'items affichés
        adapter.setListItem((List<IDataHolder>)results.values);
        //On notifie le changement de données à l'adapter
        adapter.notifyDataSetChanged();
    }
}
