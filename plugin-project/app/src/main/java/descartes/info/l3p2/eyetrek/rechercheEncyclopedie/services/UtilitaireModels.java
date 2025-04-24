package descartes.info.l3p2.eyetrek.rechercheEncyclopedie.services;

import android.os.Environment;

/**
 * Cette classe permet de télécharger les modèles d'un module: feuilles, champignons et oiseaux.
 *
 * @author Enzo DUTRA le 15/04/2019 et mis à jour par Tristan JEANNE - 2021.
 */
public class UtilitaireModels {
    private final static String URL_SERVEUR = "http://www.ens.math-info.univ-paris5.fr/~ij00084/";
    private final static String FICHIER_VERSIONS = Environment.getExternalStorageDirectory() + "/EyeTrek/Models/models_version.txt";
    public static boolean running_download = false;

    public static int VERSION_MODELE_FEUILLES = 0;
    public final static String VERSION_MODELE_FEUILLES_URL = URL_SERVEUR + "modele.php?type=feuilles&demande=last_version";
    public final static String FICHIER_MODELE_FEUILLES_URL = URL_SERVEUR + "modele.php?type=feuilles&demande=modele";
    public final static String FICHIER_LABELS_MODELE_FEUILLES_URL = URL_SERVEUR + "modele.php?type=feuilles&demande=label";
    public final static String MODELE_FEUILLES_PATH = "/modele_feuilles.tflite";
    public final static String LABELS_MODELE_FEUILLES_PATH = "/labels_modele_feuilles.txt";

    public static int VERSION_MODELE_CHAMPIGNIONS = 0;
    public final static String VERSION_MODELE_CHAMPIGNIONS_URL = URL_SERVEUR + "modele.php?type=champignons&demande=last_version";
    public final static String FICHIER_MODELE_CHAMPIGNIONS_URL = URL_SERVEUR + "modele.php?type=champignons&demande=modele";
    public final static String FICHIER_LABELS_MODELE_CHAMPIGNIONS_URL = URL_SERVEUR + "modele.php?type=champignons&demande=label";
    public final static String MODELE_CHAMPIGNIONS_PATH = "/modele_champignons.tflite";
    public final static String LABELS_MODELE_CHAMPIGNIONS_PATH = "/labels_modele_champignons.txt";

    public static int VERSION_MODELE_OISEAUX = 0;
    public final static String VERSION_MODELE_OISEAUX_URL = URL_SERVEUR + "modele.php?type=oiseaux&demande=last_version";
    public final static String FICHIER_MODELE_OISEAUX_URL = URL_SERVEUR + "modele.php?type=oiseaux&demande=modele";
    public final static String FICHIER_LABELS_MODELE_OISEAUX_URL = URL_SERVEUR + "modele.php?type=oiseaux&demande=label";
    public final static String MODELE_OISEAUX_PATH = "/modele_oiseaux.tflite";
    public final static String LABELS_MODELE_OISEAUX_PATH = "/labels_modele_oiseaux.txt";

    private UtilitaireModels(){}
}
