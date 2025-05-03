package descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.services;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Classe qui permet l'affichage d'informations à l'analyse d'une feuille.
 *
 * @author Antony Torbey le 06/03/2018 et mis à jour par Huihui Huang et Tristan JEANNE - 2021
 */

public class UtilitaireResultat {

    /**
     * Nom du package d'accès aux ressources.
     */
    private final String packageName;

    /**
     * Le contexte
     */
    private final Context context;

    public final static char SEPARATOR=';';

    /**
     * Constructeur.
     * @param context le contexte.
     * @param packageName le nom du package.
     */
    public UtilitaireResultat(Context context, String packageName) {
        this.context= context;
        this.packageName= packageName;
    }

    /**
     * Lire un fichier.
     * @param file le fichier.
     * @return le contenu d'un fichier.
     */
    public static ArrayList<String> readFile(InputStream file){
        ArrayList<String> res = new ArrayList<>();

        InputStreamReader r = new InputStreamReader(file);
        BufferedReader br = new BufferedReader(r);

        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                res.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ArrayList<String> getMushInfo(String id, String moduleName){
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> lines;
        ArrayList<String[]> data;
        String sep = Character.valueOf(SEPARATOR).toString();

        res.add(ajouterMajuscules(id.replaceAll("_", " ")));

        int mushroomsId= context.getResources().getIdentifier("liste_"+ moduleName +"_details", "raw", packageName);

        lines = readFile(context.getResources().openRawResource(mushroomsId));

        // On reinitialise la variable data
        data = new ArrayList<>();

        for(String line : lines){
            String[] oneData = line.split(sep);
            data.add(oneData);
        }

        boolean trouve = false;
        for(int i=0 ; i<data.size() ; i++){
            String[] tabStr = data.get(i);
            if (tabStr[1].equals(id)) {
                res.add(tabStr[2]);
                trouve = true;
                break;
            } else {
                Log.w("getMushInfo()", "" + tabStr[1] + " --- " + id);
            }
        }
        if(!trouve){
            Log.e("getInfo()", "Aucun nom de plante trouvé pour cette entrée");
            res.add("Aucune description disponible pour le moment.\n\nCliquez sur cette case pour acceder à la page Wikipedia correspondante");
        }

        res.add("https://fr.wikipedia.org/wiki/"+id);

        res.add("drawable/"+id+".*");

        return res;
    }

    public ArrayList<String> getInfo(String id, String moduleName){

        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> lines;
        ArrayList<String[]> data = new ArrayList<>();
        String sep = Character.valueOf(SEPARATOR).toString();

        int liste_leaf1= context.getResources().getIdentifier("liste_leaf1", "raw", packageName);
        lines = readFile(context.getResources().openRawResource(liste_leaf1));

        for(String line : lines){
            String[] oneData = line.split(sep);
            data.add(oneData);
        }

        boolean trouve = false;
        for(int i=0 ; i<data.size() ; i++){
            String[] tabStr = data.get(i);
            if (tabStr[2].equals(id)) {
                res.add(tabStr[1]);
                trouve = true;
                break;
            }
        }
        if(!trouve){
            Log.e("getInfo()", "Aucun nom de plante trouvé pour cette entrée");
            res.add(ajouterMajuscules(id.replaceAll("_", " ")));
        }

        int liste_leaf_details= context.getResources().getIdentifier("liste_"+ moduleName +"_details", "raw", packageName);

        lines = readFile(context.getResources().openRawResource(liste_leaf_details));

        // On reinitialise la variable data
        data = new ArrayList<>();

        for(String line : lines){
            String[] oneData = line.split(sep);
            data.add(oneData);
        }

        trouve = false;
        for(int i=0 ; i<data.size() ; i++){
            String[] tabStr = data.get(i);
            if (tabStr[2].equals(id)) {
                res.add(tabStr[1]);
                trouve = true;
                break;
            }
        }
        if(!trouve){
            Log.e("getInfo()", "Aucun nom de plante trouvé pour cette entrée");
            res.add("Aucune description disponible pour le moment.\n\nCliquez sur cette case pour acceder à la page Wikipedia correspondante");
        }

        res.add("https://fr.wikipedia.org/wiki/"+id);
        res.add("drawable/"+id+".*");
        return res;
    }

    /**
     * Retourne la description associée à la donnée.
     * @param idData l'identifiant de la donnée.
     * @param moduleName le nom du module où est situé ladite donnée.
     * @return un String contenant la description.
     */
    public String getDescription(String idData, String moduleName){
        ArrayList<String> lines;
        ArrayList<String[]> data;
        String sep = Character.valueOf(SEPARATOR).toString();
        boolean trouve = false;
        String resultat = "";

        int liste_leaf_details= context.getResources().getIdentifier("liste_"+ moduleName +
                "_details", "raw", packageName);
        if (liste_leaf_details == 0) {
        } else {
            lines = readFile(context.getResources().openRawResource(liste_leaf_details));
            // On reinitialise la variable data
            data = new ArrayList<>();

            for (String line : lines) {
                String[] oneData = line.split(sep);
                data.add(oneData);
            }

            for (int i = 0; i < data.size(); i++) {
                String[] tabStr = data.get(i);
                if (tabStr[2].equals(idData)) {
                    resultat = tabStr[1];
                    trouve = true;
                    break;
                }
            }
        }

        if(!trouve){
            Log.e("get_description()", "Aucun nom trouvé pour cette entrée");
            resultat = "Aucune description disponible pour le moment.";
        }
        return resultat;
    }

    /**
     * Ajoute des majuscules au phrases dans la String passée en paramètre.
     * @param str la String à modifier.
     * @return la String modifiée.
     */
    public static String ajouterMajuscules(String str) {

        // Create a char array of given String
        char[] ch = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char)(ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        return new String(ch);
    }
}