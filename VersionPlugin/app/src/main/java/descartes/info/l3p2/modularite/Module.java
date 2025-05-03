package descartes.info.l3p2.modularite;

import android.view.View;
/**
 * Module de l'application.
 * Cette classe représente un module de l'application et contient les informations nécessaires pour l'affichage et l'interaction avec l'utilisateur.
 * 
 * @author Shi Jianye
 */
public class Module {
    private final int imageResource;
    private final String title;
    private final View.OnClickListener onClickListener;
    
    public Module(int imageResource, String title, View.OnClickListener onClickListener) {
        this.imageResource = imageResource;
        this.title = title;
        this.onClickListener = onClickListener;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
} 