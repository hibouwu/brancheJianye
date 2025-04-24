package descartes.info.l3p2.eyetrek.rechercheEncyclopedie.services;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Classe regroupant des méthodes permettant des traitements en rapport avec les images.
 *
 * @author Tristan JEANNE - 16/04/2021
 */
public class ImageUtils {

    private static int pixelScreenWidth = -1;
    private static int pixelScreenHeight = -1;

    /**
     * Cette méthode convertie les "dp" en pixels en fonction de la densité de l'écran de l'appareil.
     *
     * @param dp la valeur en dp (density independent pixels) à convertir en pixel.
     * @param context le context pour récupérer des informations sur l'appareil.
     * @return un float représentant l'équivalent en pixel de la valeur en dp passé en paramètre.
     */
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * Méthode retournant la largeur de l'écran en pixel.
     * @param context le context pour récupérer les informations sur l'écran.
     * @return un entier contenant la largeur de l'écran en pixel.
     */
    public static int getScreenPixelWidth(Context context){
        if(pixelScreenWidth < 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            pixelScreenWidth = p.x;
        }
        return pixelScreenWidth;
    }

    /**
     * Méthode retournant la longueur de l'écran en pixel.
     * @param context le context pour récupérer les informations sur l'écran.
     * @return un entier contenant la longueur de l'écran en pixel.
     */
    public static int getScreenPixelHeight(Context context){
        if(pixelScreenHeight < 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            pixelScreenHeight = p.y;
        }
        return pixelScreenHeight;
    }
}
