/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIDOU
 */
public class add_matiere {
    public static void ajouter(){
           ajout_matiere.setVisible(true);
        ResultSet résultatsM = null;
             
             String requete = "SELECT * FROM matiere";
             
         try {
             Statement statement = connection.createStatement();
             résultatsM=statement.executeQuery(requete);
             while(résultatsM.next()){
             matiere_selection.addItem(résultatsM.getString("nom_matiere"));
             
             }
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
