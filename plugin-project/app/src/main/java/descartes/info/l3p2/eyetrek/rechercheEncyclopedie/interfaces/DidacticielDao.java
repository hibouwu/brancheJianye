package descartes.info.l3p2.eyetrek.rechercheEncyclopedie.interfaces;

import android.app.Activity;
import android.view.View;

/**
 * L'interface DidacticielDao.
 *
 * @author Huihui HUANG - 2021
 */
public interface DidacticielDao {

    /**
     * Met à jour les préférences liée au didacticiel.
     * @param value le choix d'une préférence.
     * @param entry boolean d'entrée.
     * @return la mise à jour des préférences.
     */
    int updateDidacticiel(String value, Boolean entry);

    /**
     * Méthode servant à retourner le booléen correspondant à la préférence.
     * @param value le choix d'une préférence.
     * @return
     */
    boolean getDidacticiel(String value);

    /**
     * Mise à jour de la table entière.
     * @param activity une activité.
     */
    void updateAllDidacticiel(Activity activity);

    /**
     * Mise à jour du didacticiel du menu principal.
     * @param activity une activité.
     */
    void updateMainActivityDidacticiel(Activity activity);

    /**
     * Mise à jour du didacticiel de la barre de recherche.
     * @param view le view.
     * @param activity l'activité.
     */
    void updateSearchDidacticiel(View view, Activity activity);
}
