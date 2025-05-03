package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.data;


import android.content.Context;

import java.io.InputStream;
import java.util.List;

import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.interfaces.IDataHolder;
import descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.classes.Animal;

/**
 * L'interface du DAO Animal.
 *
 * @author Huihui HUANG - 2021
 *
 * Inspiré de  https://openclassrooms.com/fr/courses/4568746-gerez-vos-donnees-localement-pour-avoir-une-application-100-hors-ligne/5106566-manipulez-vos-donnees-grace-aux-dao
 */
public interface AnimalDao {

    /**
     * Méthode permettant l'ajout d'une empreinte animal.
     *
     * @param animal un animal.
     */
    void addAnimal(Animal animal);

    /**
     * Retourne une liste d'animaux correspondant à la requête.
     *
     * @param query une requête SQL.
     * @return une liste des animaux.
     */
    List<Animal> getAnimalsFromRequest(String query);

    /**
     * Ajout d'empreinte animal depuis un CSV.
     *
     * @param inputStream le fichier CSV.
     * @param context
     */
    void addAnimalFromCsv(InputStream inputStream, Context context);

    /**
     * Retourne tous les animaux de la base de données.
     *
     * @return la liste de tous les animaux de la base de données.
     */
    List<IDataHolder> getAllAnimals();

    /**
     * Téléchargements des données pour les animaux depuis le CSV.
     *
     * @param context le contexte.
     * @param ressource le fichier.
     */
    void downloadDataFromCsv(Context context, int ressource);
}
