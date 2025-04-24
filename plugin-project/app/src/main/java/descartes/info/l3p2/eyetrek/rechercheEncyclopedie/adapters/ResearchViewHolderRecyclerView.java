package descartes.info.l3p2.eyetrek.rechercheEncyclopedie.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import descartes.info.l3p2.eyetrek.rechercheEncyclopedie.services.interfaces.ItemClickListener;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.services.UtilitaireResultat;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.R;

/**
 * Permet de garder les références vers les vues de chaque cellule du RecyclerView.
 *
 * @author Dorian on 27/02/2018.
 */

public class ResearchViewHolderRecyclerView extends RecyclerView.ViewHolder {

    /**
     * Image contenue dans l'item.
     */
    private final ImageView imageItem;

    /**
     * Nom contenu dans l'item.
     */
    private final TextView nomItem;

    /**
     * Clic long.
     */
    private ItemClickListener itemClickListener;

    /**
     * Constructeur.
     * @param itemView la liste des éléments.
     */
    public ResearchViewHolderRecyclerView(View itemView) {
        super(itemView);

        this.imageItem = itemView.findViewById(R.id.item_image);
        this.nomItem = itemView.findViewById(R.id.item_name);

        itemView.setOnClickListener((v)-> {
            itemClickListener.onItemClick(v,getLayoutPosition());
        });
    }

    /**
     * Insérer un clic long.
     * @param listener le listener du clic long.
     */
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    /**
     * Obtenir l'image de l'élément.
     * @return l'image de l'élément.
     */
    public ImageView getImageItem() {
        return imageItem;
    }

    /**
     * Insérer le nom du l'élément.
     * @param nomItem le nom de l'élément.
     */
    public void setNomItem(String nomItem) {
        this.nomItem.setText(nomItem);
    }
}
