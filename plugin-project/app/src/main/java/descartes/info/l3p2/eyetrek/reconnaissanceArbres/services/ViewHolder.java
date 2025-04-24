package descartes.info.l3p2.eyetrek.reconnaissanceArbres.services;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceArbres.R;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.classes.Departement;

/**
 * Cette classe permet de fournir les Views correspondant à la liste des items à afficher.
 *
 * @author Antony on 29/03/2019 et mis à jour par Huihui Huang - 2021. Debug by Yaël Temam on 03/2023
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView textViewView;

    private final ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public ViewHolder(View itemView, Context context, List<Departement> list) {
        super(itemView);

        textViewView = itemView.findViewById(R.id.text);
        imageView = itemView.findViewById(R.id.image);

        itemView.setOnClickListener(v -> {
            int position=getAdapterPosition();
            Toast.makeText(context, list.get(position).getText(), Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Obtenir le texte.
     * @return le texte.
     */
    public TextView getTextViewView() {
        return textViewView;
    }

    /**
     * Insérer un texte.
     * @param textViewView un texte.
     */
    public void setTextViewView(String textViewView) {
        this.textViewView.setText(textViewView);
    }

    /**
     * Obtenir une image.
     * @return l'image.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Insérer une image.
     * @param drawable une image.
     */
    public void setImageView(Drawable drawable) {
        this.imageView.setImageDrawable(drawable);
    }
}
