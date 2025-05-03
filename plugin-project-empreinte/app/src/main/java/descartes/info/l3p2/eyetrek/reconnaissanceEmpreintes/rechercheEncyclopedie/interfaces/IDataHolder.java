package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.rechercheEncyclopedie.interfaces;

/**
 * Interface contenant les méthodes utilisées par un conteneur d'informations.
 * Typiquement le module de recherche.
 *
 * @author Tristan Jeanne - 2021
 */
public interface IDataHolder {

    /**
     * Retourne l'id du conteneur.
     * @return un int représentant l'id.
     */
    int getId();

    /**
     * Retourne le nom de la ressource contenue.
     * @return un String contenant le nom.
     */
    String getName();

    /**
     * Retourne le nom du drawable associé à ce conteneur.
     * @return un String contenant le nom du drawable.
     */
    String getPicture();

}
