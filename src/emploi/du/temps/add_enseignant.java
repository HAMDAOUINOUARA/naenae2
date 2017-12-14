/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.Nom_enseignant;
import static emploi.du.temps.theMain.ajout_enseignant;
import static emploi.du.temps.theMain.connection;
import static emploi.du.temps.theMain.matiere_selection;
import static emploi.du.temps.theMain.prenom_enseignant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIDOU
 */
public class add_enseignant {
    public static void ajouter(){
        String Nom_ens = Nom_enseignant.getText();
        String Prenom_ens = prenom_enseignant.getText();
        String matiere = matiere_selection.getSelectedItem().toString();
        String idmatiere = null;
        String rqt = "SELECT * FROM matiere WHERE `nom_matiere` = '"+matiere+"'";
        try {
             Statement statement = connection.createStatement();
            ResultSet résultatsM = statement.executeQuery(rqt);
             while(résultatsM.next()){
             idmatiere=résultatsM.getString("idmatiere");
             }
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
        //String id_matiere = 
        String requete = "INSERT INTO emploi_du_temp.enseignant (nom_enseignat, `prenom _enseignant`, matiere_idmatiere,`nombre_heurs`) \n" +
"	VALUES ('"+Nom_ens+"', '"+Prenom_ens+"',"+idmatiere+",23 )";
         try {
             Statement statement2 = connection.createStatement();
             statement2.execute(requete);
             ajout_enseignant.setVisible(false);
             liste_enseignant.afficher();
             
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
          
        
    
    }
}
