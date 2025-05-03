package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.adapters.ResearchAdapterRecyclerView;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.interfaces.IDataHolder;

/**
 * La classe affiche l'encyclopédie des empreintes.
 * Basé sur le fragment FragmentRecherche développé pendant les années précédentes.
 *
 * @author Huihui HUANG et modifié par Tristan JEANNE - 2021
 */
public class FragmentRechercheEmpreintes extends Fragment {

    /**
     * La barre de recherche.
     */
    private SearchView barreRecherche;

    /**
     * Le RecyclerView.
     */
    private RecyclerView recycler;

    /**
     * L'Adapter.
     */
    private ResearchAdapterRecyclerView adapter;

    /**
     * La liste des animaux.
     */
    private List<IDataHolder> listAnimaux;

    /**
     * La gestion de la base de données animal.
     */
    private descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl animalDaoImpl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_recherche, container, false);

        // On trouve tous les animaux de la base de données.
        animalDaoImpl= new descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDaoImpl(getContext());
        listAnimaux = animalDaoImpl.getAllAnimals();
        //TODO suppr test
        Log.e("ANIMAUX", listAnimaux.toString());

        // Retrouver les identifiants du layout.
        barreRecherche = view.findViewById(R.id.searchbarre);
        recycler = view.findViewById(R.id.recycler_feuille);

        // Initialisation du texte.
        barreRecherche.setQueryHint("Recherche d'empreintes");

        // On instancie les propriétés de la RecyclerView :

        // Le type de remplissage des données (en grille avec 3 cellules par ligne).
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // Le type d'animation de remplissage par default.
        recycler.setItemAnimator(new DefaultItemAnimator());

        // Fournit la liste d'animaux dans l'adapter.
        adapter = new ResearchAdapterRecyclerView(getContext(), animalDaoImpl.getAllAnimals(),
                R.string.title_reconnaissanceEmpreintes);

        recycler.setAdapter(adapter);

        // On définit un listener pour la saisie de la recherche.
        barreRecherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String search) {
                return false;
            }

            /**
             * On filtre la search à chaque saisie de caractères (à chaque fois le text change)
             * @param search
             * @return
             */
            @Override
            public boolean onQueryTextChange(String search) {
                // Recherche suivant les animaux.
                adapter.getFilter().filter(search, (count) -> {
                    List<IDataHolder> animals = new ArrayList<>();
                    for (IDataHolder iDataHolder: animalDaoImpl.getAllAnimals()) {
                        if (iDataHolder.getName().toLowerCase().startsWith(search)) {
                            animals.add(iDataHolder);
                        }
                    }
                    if (animals.isEmpty()) {
                        Toast.makeText(getContext(), "Aucun résultat pour cette search",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new ResearchAdapterRecyclerView(getContext(), animals,
                                R.string.title_reconnaissanceEmpreintes);
                        recycler.setAdapter(adapter);
                    }
                });
                return false;
            }
        });

        // Fermer la barre de recherche.
        barreRecherche.setOnCloseListener(() -> {
            return false;
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}