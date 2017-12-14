/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.Num_salle;
import static emploi.du.temps.theMain.ajout_salle;
import static emploi.du.temps.theMain.connection;
import static emploi.du.temps.theMain.designation_salle;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIDOU
 */
public class add_salle {
    public static void ajouter(){
       String num_sall = Num_salle.getText();
        String des_salle = designation_salle.getText();
        
        //String id_matiere = 
        String requete = "INSERT INTO emploi_du_temp.salle (num_salle, designation)VALUES ('"+num_sall+"', '"+des_salle+"' )";
         try {
             Statement statement2 = connection.createStatement();
             statement2.execute(requete);
             ajout_salle.setVisible(false);
             add_salle.ajouter();
             
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
       
    }
}
