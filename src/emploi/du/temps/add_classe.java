/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.Niveau;
import static emploi.du.temps.theMain.Nom_classe;
import static emploi.du.temps.theMain.ajout_classe;
import static emploi.du.temps.theMain.connection;
import static emploi.du.temps.theMain.specialité;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIDOU
 */
public class add_classe {
    public static void ajouter(){
      String Nom_cls = Nom_classe.getText();
        String niveau = Niveau.getSelectedItem().toString();
        String special = specialité.getSelectedItem().toString();
        String idniveau=null;
        try {//String id_matiere = *
        String requete_recherche_niveau = "SELECT * FROM niveau_classe WHERE `niveau` ="+niveau+" AND `specialité`='"+special+"'";
             Statement statement_niveau = connection.createStatement();
             ResultSet resultat_niveau = statement_niveau.executeQuery(requete_recherche_niveau);
             if(resultat_niveau.next()){
             idniveau= resultat_niveau.getString("idniveau_classe");
             }
        String requete_ajout_classe = "INSERT INTO emploi_du_temp.classe (nom_classe, niveau, niveau_classe_idniveau_classe) \n" +
"	VALUES ('"+Nom_cls+"',"+niveau+" ,"+idniveau+")";
         
             Statement statement2 = connection.createStatement();
             statement2.execute(requete_ajout_classe);
             ajout_classe.setVisible(false);
             liste_classe.afficher();
             
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
          
    }
}
