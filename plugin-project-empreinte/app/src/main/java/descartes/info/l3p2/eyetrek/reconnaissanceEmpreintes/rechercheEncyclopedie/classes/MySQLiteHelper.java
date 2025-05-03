package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * La base d'entrée de la base de données.
 *
 * @author Huihui HUANG - 2021
 * Inspiré de <a href="https://openclassrooms.com/fr/courses/2023346-creez-des-applications-pour-android/2027250-les-bases-de-donnees">...</a>
 *
 * @author Evelyne TRAN - 28/03/2023
 * (pour les produits et les listes)
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private final String TAG="MySQLiteHelper";

    /**
     * La version de la base de données.
     */
    private static final int DATABASE_VERSION = 12;

    /**
     * L'entrée dans la base de données.
     */
    private static MySQLiteHelper sInstance;

    /**
     * Le nom de la base de donnée.
     */
    private static final String DATABASE_NAME = "eyetrek.db";

    /**
     * Les tables de la base de données.
     */
    private static final String TABLE_LEAFS = "leafs";
    private static final String LEAF_ID = "id";
    private static final String LEAF_NAME = "name";
    private static final String LEAF_PICTURE = "picture";

    private static final String TABLE_BIRD = "bird";
    private static final String BIRD_ID = "id";
    private static final String BIRD_NAME = "name";
    private static final String BIRD_PICTURE = "picture";

    private static final String TABLE_DIDACTICIEL = "didacticiel";
    private static final String DIDACTICIEL_ID = "id";
    private static final String DIDACTICIEL_MENU = "menu";
    private static final String DIDACTICIEL_ANALYSE = "analyse";
    private static final String DIDACTICIEL_SETTINGS = "settings";
    private static final String DIDACTICIEL_PROFIL = "profil";
    private static final String DIDACTICIEL_SEARCH = "search";

    private static final String TABLE_COLORS = "colors";
    private static final String COLORS_ID = "id";
    private static final String COLORS_COLOR = "color";

    private static final String TABLE_ANIMAL = "animal";
    private static final String ANIMAL_ID = "id";
    private static final String ANIMAL_NOM = "nom";
    private static final String ANIMAL_IMAGE = "image";
    private static final String ANIMAL_NBDOIGT = "nbDoigt";
    private static final String ANIMAL_DOIGTA = "doigtA";
    private static final String ANIMAL_PALME = "palme";
    private static final String ANIMAL_MEMETAILLE = "memeTaille";
    private static final String ANIMAL_NBCOUSSINET = "nbCoussinet";
    private static final String ANIMAL_GRIFFES = "griffe";
    private static final String ANIMAL_NBSABOT = "nbSabot";
    private static final String ANIMAL_CONCAVE = "concave";
    private static final String ANIMAL_CONVEXE = "convexe";
    private static final String ANIMAL_CIRCULAIRE = "circulaire";

    private static final String TABLE_MODULE= "module";
    private static final String MODULE_ID= "id";
    private static final String MODULE_IMAGE= "image";
    private static final String MODULE_NAME= "nom";
    private static final String MODULE_PACKAGENAME= "nomPackage";
    private static final String MODULE_ACTIVITY= "activity";
    private static final String MODULE_INSTALLED = "installed";
    private static final String MODULE_ASSETNAME= "assetName";


    private static final String TABLE_LISTE = "liste";
    private static final String LISTE_NAME = "nom";

    private static final String TABLE_PRODUIT = "produit";
    private static final String PRODUIT_ID = "id";
    private static final String PRODUIT_NAME = "nom";
    private static final String PRODUIT_QUANTITY = "quantite";
    private static final String PRODUIT_LISTE = "id_liste";
    private static final String PRODUIT_IMAGE = "image";

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make call to static method "getInstance()" instead.
     */
    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Constructeur.
     * @param context le contexte.
     */
    public static synchronized MySQLiteHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Création de la table didacticiel
        String CREATE_DIDACTICIEL_TABLE = "create table if not exists " + TABLE_DIDACTICIEL + " ("
                + DIDACTICIEL_ID + " INTEGER PRIMARY KEY," + DIDACTICIEL_MENU + " BOOLEAN," +
                DIDACTICIEL_ANALYSE + " BOOLEAN,"
                + DIDACTICIEL_SETTINGS + " BOOLEAN, " + DIDACTICIEL_PROFIL + " BOOLEAN, " +
                DIDACTICIEL_SEARCH + " BOOLEAN " + " )";
        //Création de la table feuille
        String CREATE_LEAF_TABLE = "create table if not exists " + TABLE_LEAFS + " ("
                + LEAF_ID + " INTEGER PRIMARY KEY," + LEAF_NAME + " VARCHAR(60)," + LEAF_PICTURE +
                " VARCHAR(80) " + " )";
        //Création de la table oiseau
        String CREATE_BIRD_TABLE = "create table if not exists " + TABLE_BIRD + " ("
                + BIRD_ID + " INTEGER PRIMARY KEY," + BIRD_NAME + " VARCHAR(60)," + BIRD_PICTURE +
                " VARCHAR(80) " + " )";
        //Création de la table couleurs
        String CREATE_COLORS_TABLE = "create table if not exists " + TABLE_COLORS + " ("
                + COLORS_ID + " INTEGER PRIMARY KEY," + COLORS_COLOR + " VARCHAR(100)  )";
        //Création de la table animal
        String CREATE_ANIMAL_TABLE = "create table if not exists " + TABLE_ANIMAL + " (" +
                ANIMAL_ID + " INTEGER PRIMARY KEY," + ANIMAL_NOM + " VARCHAR(60), "
                + ANIMAL_IMAGE + " VARCHAR(60), " + ANIMAL_NBDOIGT + " INTEGER(1), " +
                ANIMAL_DOIGTA + " INTEGER(1), " + ANIMAL_PALME + " INTEGER(1), " +
                ANIMAL_MEMETAILLE + " INTEGER(1), " +
                ANIMAL_NBCOUSSINET + " INTEGER(1), " + ANIMAL_GRIFFES + " INTEGER(1), " +
                ANIMAL_NBSABOT + " INTEGER(1), " + ANIMAL_CONCAVE + " INTEGER(1), " +
                ANIMAL_CONVEXE + " INTEGER(1), " + ANIMAL_CIRCULAIRE + " INTEGER(1) ) ";

        // Création de la table module
        String CREATE_MODULE_TABLE = "create table if not exists " + TABLE_MODULE +
                " (" + MODULE_ID + " INTEGER PRIMARY KEY," + MODULE_NAME + " VARCHAR(60), "
                + MODULE_IMAGE + " VARCHAR(60), "+ MODULE_PACKAGENAME+ " VARCHAR(60), "+
                MODULE_ACTIVITY+ " VARCHAR(60)," + MODULE_INSTALLED+ " BOOLEAN,"+
                MODULE_ASSETNAME+" VARCHAR(60) )";

        //Création de la table liste
        String CREATE_LISTE_TABLE = "create table if not exists "+ TABLE_LISTE +
                " (" + LISTE_NAME + " VARCHAR(60) NOT NULL PRIMARY KEY )" ;

        //Création de la table produit
        String CREATE_PRODUIT_TABLE = "create table if not exists " + TABLE_PRODUIT +
                " (" + PRODUIT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                PRODUIT_NAME + " VARCHAR(60), " +
                PRODUIT_QUANTITY + " INTEGER, "+
                PRODUIT_IMAGE + " VARCHAR(60), " +
                PRODUIT_LISTE + " VARCHAR(60), " +
                "FOREIGN KEY (" + PRODUIT_LISTE + ") REFERENCES " + TABLE_LISTE+ "(" + LISTE_NAME + ") )";

        sqLiteDatabase.execSQL(CREATE_ANIMAL_TABLE);
        sqLiteDatabase.execSQL(CREATE_DIDACTICIEL_TABLE);
        sqLiteDatabase.execSQL(CREATE_LEAF_TABLE);
        sqLiteDatabase.execSQL(CREATE_BIRD_TABLE);
        sqLiteDatabase.execSQL(CREATE_COLORS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MODULE_TABLE);
        sqLiteDatabase.execSQL(CREATE_LISTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRODUIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.e(TAG, "Mise à jour des tables Version " + oldVersion + " à Version " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DIDACTICIEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMAL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAFS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT);

        onCreate(sqLiteDatabase);
    }

    /**
     * Méthode permettant de voir si une table est vide.
     * (modifiée par Evelyne TRAN)
     *
     * @param table la table de la base de données.
     * @return vrai si la table est vide.
     */
    public boolean emptyTable(String table) {
        String query = "SELECT count(*) FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count <= 0;
    }
}