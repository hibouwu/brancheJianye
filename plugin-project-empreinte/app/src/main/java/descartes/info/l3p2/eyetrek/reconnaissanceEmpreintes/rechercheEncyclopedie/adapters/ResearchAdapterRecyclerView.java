package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.services.UtilitaireResultat;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.interfaces.IDataHolder;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.services.FilterSearch;

/**
 * Cette classe permet de fournir les Views correspondant à la liste des items à afficher.
 *
 * @author Dorian 27/02/2018 et modifié par Tristan 12/04/2021
 * @author Evelyne TRAN (débogage) - 12/03/2023
 */
public class ResearchAdapterRecyclerView extends RecyclerView.Adapter<ResearchViewHolderRecyclerView> implements Filterable {

    /**
     * Le contexte.
     */
    private final Context context;

    /**
     * Liste des items à afficher.
     */
    private List<IDataHolder> listItem;

    /**
     * Liste des résultats de la recherche.
     */
    private final List<IDataHolder> listResult;

    /**
     * Le filtre de recherche.
     */
    private FilterSearch filterSearch;

    /**
     * Le nom du package d'un module.
     */
    private final String packageName;

    /**
     * Le nom d'un module.
     */
    private final String moduleName;

    /**
     * Le constructeur.
     * @param context le contexte.
     * @param listItem la liste des éléments.
     * @param packageNameResID l'identifiant du package d'un module.
     */
    public ResearchAdapterRecyclerView(Context context, List<IDataHolder> listItem,
                                       int packageNameResID) {
        this.context = context;
        this.listItem = listItem;
        this.listResult = listItem;
        this.packageName= context.getPackageName()+"."+context.getString(packageNameResID);
        this.moduleName = context.getString(packageNameResID);
    }

    @NonNull
    @Override
    public ResearchViewHolderRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        //Permet de déterminer la vue correspondant au squelette d'une cellule
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encyclopedie,
                null);

        return(new ResearchViewHolderRecyclerView(item));
    }

    @Override
    public void onBindViewHolder(@NonNull ResearchViewHolderRecyclerView holder, int position) {
        IDataHolder item = listItem.get(position);
        if (context.getResources()==null) throw new AssertionError("Resource cannot be null");
        int idImageCell = context.getResources().getIdentifier(item.getPicture(),"drawable",
                packageName);
        String textCell = (item.getName());

        Glide.with(context).load(idImageCell).into(holder.getImageItem());
        holder.setNomItem(textCell);

        //Il faudra checker dans la bdd locale, si l'utilisateur a déjà analysé l'élément.

        holder.setItemClickListener((v,pos)->{
            //On affiche l'image de la cellule dans une boîte de dialogue
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater adbInflater = LayoutInflater.from(context);
            View layout = adbInflater.inflate(R.layout.dialog_encyclopedie, null);

            ImageView imgDialog = layout.findViewById(R.id.dialog_image_encyclopedie);
            ImageButton flagDialog = layout.findViewById(R.id.dialog_flag_encyclopedie);

            TextView titreDialog = layout.findViewById(R.id.dialog_titre_encyclopedie);

            TextView description = layout.findViewById(R.id.description_espece);

            CardView bouton_wikipedia = layout.findViewById(R.id.bouton_wikipedia);

            if (context.getResources()==null) throw new AssertionError("Resource cannot be null");
            int idImageCell2 = context.getResources().getIdentifier(item.getPicture(),
                    "drawable",packageName);
            titreDialog.setText(item.getName());
            UtilitaireResultat utilitaireResultat= new UtilitaireResultat(context, packageName);
            description.setText(utilitaireResultat.getDescription(item.getName()
                    .replaceAll(" ", "_").toLowerCase(), moduleName.toLowerCase()));
            //imgDialog.setImageDrawable(context.getDrawable(idImageCell2));
            Glide.with(context).load(idImageCell).into(imgDialog);
            bouton_wikipedia.setOnClickListener((event) -> {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://fr.wikipedia.org/wiki/" +
                                (item.getName().replaceAll(" ", "_")
                                        .toLowerCase()))));
            });

            //on place l'image dans la boîte de dialogue
            builder.setView(layout)
                    .setCancelable(true)
                    .create().show();
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public Filter getFilter() {
        if(filterSearch == null) {
            filterSearch = new FilterSearch(listResult,this);
        }
        return filterSearch;
    }

    /**
     * Insérer la liste des éléments.
     * @param listItem la liste des éléments.
     */
    public void setListItem(List<IDataHolder> listItem) {
        this.listItem = listItem;
    }
}
