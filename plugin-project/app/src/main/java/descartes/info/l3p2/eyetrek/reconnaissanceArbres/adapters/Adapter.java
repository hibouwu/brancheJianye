package descartes.info.l3p2.eyetrek.reconnaissanceArbres.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceArbres.R;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.classes.Departement;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.services.ViewHolder;

/**
 * Cette classe permet d'utiliser les views de ViewHolder dans notre Adapter.
 *
 * @author Antony on 29/03/2019. Debug by Yaël Temam on 03/2023
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    /**
     * La liste des départements.
     */
    private final List<Departement> list;

    /**
     * Le contexte.
     */
    private final Activity activity;

    /**
     * Le nom du package.
     */
    private final String packageName;

    /**
     * Constructeur prenant en entrée une liste de département.
     * @param list la liste de département.
     * @param activity l'activité mère.
     * @param packageNameResID le nom du package où se situe les ressources.
     */
    public Adapter(List<Departement> list, Activity activity, int packageNameResID) {
        this.list = list;
        this.activity= activity;
        this.packageName= activity.getPackageName()+"."+activity.getString(packageNameResID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.departement,
                viewGroup,false);
        return new ViewHolder(view, activity, list);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // C'est ici que nous allons remplir notre cellule avec le texte/image de chaque Département.
        try{
            Departement departement = list.get(position);
            setInfo(viewHolder, departement);
        } catch(Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Méthode ajoutant au viewHolder les informations correspondantes au département.
     * @param viewHolder le viewHolder.
     * @param departement le département.
     *
     * @author Lin Zhanwang -10/04/2025
     * En raison de la transformation du module dynamique en application indépendante,
     * un problème de nom de package est apparu.
     * Ajout de ces deux lignes pour récupérer le bon nom de package.
     * 'Context context = viewHolder.getImageView().getContext();'
     * 'String packageName = context.getPackageName();'
     */
    public void setInfo(ViewHolder viewHolder, Departement departement){
        try{
            viewHolder.setTextViewView(departement.getText());
            String uri = departement.getImageUrl();
            Context context = viewHolder.getImageView().getContext();
            String packageName = context.getPackageName();
            int imageResource = activity.getResources().getIdentifier(uri, "drawable", packageName);
            Drawable res = resizeImage(imageResource);
            Glide.with(viewHolder.getImageView().getContext()).load(res)
                    .into(viewHolder.getImageView());
        } catch (Throwable t){
            t.printStackTrace();
        }

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable resizeImage(int imageResource) {// R.drawable.large_image
        // Get device dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        /*Display display = activity.getWindowManager().getDefaultDisplay();*/
        double deviceWidth = displayMetrics.widthPixels;

        BitmapDrawable bd;
        bd = (BitmapDrawable) activity.getDrawable(imageResource);
        assert bd != null;
        double imageHeight = bd.getBitmap().getHeight();
        double imageWidth = bd.getBitmap().getWidth();

        double ratio = deviceWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);

        Bitmap bMap = BitmapFactory.decodeResource(activity.getResources(), imageResource);

        return new BitmapDrawable(activity.getResources(),
                getResizedBitmap(bMap, newImageHeight, (int) deviceWidth));
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap

        return Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
    }
}
