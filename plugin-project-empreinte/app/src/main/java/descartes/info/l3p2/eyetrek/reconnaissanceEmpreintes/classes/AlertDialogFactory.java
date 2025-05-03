package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes;

import android.app.AlertDialog;
import android.content.Context;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.R;

/**
 * Contient des méthodes pour créer des fenêtres modales afin d'informer l'utilisateur.
 * Les fenêtres n'ont qu'un bouton. Elles ne permettent pas d'interaction spécifique.
 *
 * @author Tristan JEANNE - 22/03/2021
 */
public class AlertDialogFactory {

    /**
     * Crée un AlertDialog ayant pour titre et texte les valeurs passées en paramètre.
     * @param context le context dans lequel la fenêtre sera affiché.
     * @param titleResId l'ID de ressource du titre de la fenêtre.
     * @param textResId l'ID de ressource du texte de la fenêtre.
     * @return un AlertDialog utilisant les paramètres ci-dessus.
     */
    public static AlertDialog createInfoDialog(Context context, int titleResId, int textResId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Ajout du texte et du titre.
        builder.setMessage(textResId)
                .setTitle(titleResId);
        // Ajoute le bouton
        builder.setPositiveButton(R.string.background_dl_dialog_ok_button,
                (dialog, id2) -> dialog.dismiss());
        return builder.create();
    }

    /**
     * Crée un AlertDialog ayant pour titre et texte les valeurs passées en paramètre.
     * @param context le context dans lequel la fenêtre sera affiché.
     * @param title une String contenant le titre de la fenêtre.
     * @param text une String contenant le texte de la fenêtre.
     * @return un AlertDialog utilisant les paramètres ci-dessus.
     */
    public static AlertDialog createInfoDialog(Context context, String title, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Ajout du texte et du titre.
        builder.setMessage(text)
                .setTitle(title);
        // Ajoute les boutons
        builder.setPositiveButton(R.string.background_dl_dialog_ok_button,
                (dialog, id2) -> dialog.dismiss());
        return builder.create();
    }
}
