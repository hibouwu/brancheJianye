package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.classes.MySQLiteHelper;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.interfaces.IDataHolder;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.Animal;

/**
 * La classe qui implémente AnimalDao et manipule la table animal dans la base de données.
 *
 * @author Huihui HUANG - 2021
 *
 * Inspiré de  https://openclassrooms.com/fr/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne/5106566-manipulez-vos-donnees-grace-aux-dao
 */
public class AnimalDaoImpl implements descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data.AnimalDao {

    /**
     * Les informations de la table animal.
     */
    private static final String TABLE_ANIMAL = "animal";
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

    /**
     * MySQLiteHelper.
     */
    private MySQLiteHelper helper= null;

    /**
     * Constructeur.
     * @param context
     */
    public AnimalDaoImpl(Context context) {
        helper= MySQLiteHelper.getInstance(context);
    }

    @Override
    public void addAnimal(Animal animal) {
        // Base de donnée en mode écriture.
        SQLiteDatabase db = helper.getWritableDatabase();

        // L'ajout des informations concernant un animal.
        ContentValues values = new ContentValues();
        values.put(ANIMAL_NOM, animal.getNom());
        values.put(ANIMAL_IMAGE, animal.getImage());
        values.put(ANIMAL_NBDOIGT, animal.getNbDoigt());
        values.put(ANIMAL_DOIGTA, animal.getDoigtA());
        values.put(ANIMAL_PALME, animal.getPalme());
        values.put(ANIMAL_MEMETAILLE, animal.getMemeTaille());
        values.put(ANIMAL_NBCOUSSINET, animal.getNbCoussinet());
        values.put(ANIMAL_GRIFFES, animal.getGriffe());
        values.put(ANIMAL_NBSABOT, animal.getNbSabot());
        values.put(ANIMAL_CONCAVE, animal.getConcave());
        values.put(ANIMAL_CONVEXE, animal.getConvexe());
        values.put(ANIMAL_CIRCULAIRE, animal.getCirculaire());

        // Insérer les informations dans la base de données.
        db.insert(TABLE_ANIMAL, null, values);

        // Fermeture de la base de données.
        db.close();
    }

    @Override
    public List<Animal> getAnimalsFromRequest(String query) {
        List<Animal> animalList = new ArrayList<Animal>();
        // Base de données en mode lecture.
        SQLiteDatabase db = helper.getReadableDatabase();

        // Résultat de la requête.
        Cursor cursor = db.rawQuery(query, null);

        // Si la première ligne du résultat de la requête existe.
        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(Integer.parseInt(cursor.getString(0)));
                animal.setNom(cursor.getString(1));
                animal.setImage(cursor.getString(2));
                animal.setNbDoigt(Integer.parseInt(cursor.getString(3)));
                animal.setDoigtA(Integer.parseInt(cursor.getString(4)));
                animal.setPalme(Integer.parseInt(cursor.getString(5)));
                animal.setMemeTaille(Integer.parseInt(cursor.getString(6)));
                animal.setNbCoussinet(Integer.parseInt(cursor.getString(7)));
                animal.setGriffe(Integer.parseInt(cursor.getString(8)));
                animal.setNbSabot(Integer.parseInt(cursor.getString(9)));
                animal.setConcave(Integer.parseInt(cursor.getString(10)));
                animal.setConvexe(Integer.parseInt(cursor.getString(11)));
                animal.setCirculaire(Integer.parseInt(cursor.getString(12)));
                animalList.add(animal);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return animalList;
    }

    @Override
    public void addAnimalFromCsv(InputStream inputStream, Context context) {
        AnimalDaoImpl animalDaoImpl= new AnimalDaoImpl(context);
        Scanner scanner = new Scanner(inputStream);

        // Ouvrir le fichier CSV.
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] str = line.split(";");
            animalDaoImpl.addAnimal(new Animal(Integer.parseInt(str[0]), str[1], str[2], Integer.parseInt(str[3]), Integer.parseInt(str[4]), Integer.parseInt(str[5])
                    , Integer.parseInt(str[6]), Integer.parseInt(str[7]), Integer.parseInt(str[8]), Integer.parseInt(str[9]), Integer.parseInt(str[10]),
                    Integer.parseInt(str[11]), Integer.parseInt(str[12])));
        }

        // Fermeture du fichier CSV.
        scanner.close();
    }

    @Override
    public List<IDataHolder> getAllAnimals() {
        List<IDataHolder> animalList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ANIMAL;
        // Base de données en mode lecture
        SQLiteDatabase db = helper.getReadableDatabase();

        // Résultat de la requête.
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Si la première ligne du résultat de la requête existe.
        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(Integer.parseInt(cursor.getString(0)));
                animal.setNom(cursor.getString(1));
                animal.setImage(cursor.getString(2));
                animal.setNbDoigt(Integer.parseInt(cursor.getString(3)));
                animal.setDoigtA(Integer.parseInt(cursor.getString(4)));
                animal.setPalme(Integer.parseInt(cursor.getString(5)));
                animal.setMemeTaille(Integer.parseInt(cursor.getString(6)));
                animal.setNbCoussinet(Integer.parseInt(cursor.getString(7)));
                animal.setGriffe(Integer.parseInt(cursor.getString(8)));
                animal.setNbSabot(Integer.parseInt(cursor.getString(9)));
                animal.setConcave(Integer.parseInt(cursor.getString(10)));
                animal.setConvexe(Integer.parseInt(cursor.getString(11)));
                animal.setCirculaire(Integer.parseInt(cursor.getString(12)));
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        return animalList;
    }

    @Override
    public void downloadDataFromCsv(Context context, int ressource) {
        if (helper.emptyTable("animal")) {
            Log.e("Database", "Adding data...");
            ProgressDialog boiteChargement;
            boiteChargement = new ProgressDialog(context);
            boiteChargement.setMessage("Téléchargemment des données en cours... \n Veuillez patienter.");
            boiteChargement.setTitle("Téléchargement");
            boiteChargement.setCancelable(false);
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    //On récupère toutes les feuilles depuis le csv
                    InputStream csvAnimal = context.getResources().openRawResource(ressource);
                    addAnimalFromCsv(csvAnimal, context);
                    Log.e("Database", "Data added !");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boiteChargement.dismiss();
            }).start();
        }
    }
}
