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
public class afficher_emploi {
    public static void afficher(){
    
        try {
            String requete_get_classe = "select * from classe";
            Statement statement_get_classe = connection.createStatement();
            ResultSet resultat_get_classe = statement_get_classe.executeQuery(requete_get_classe);
            
            while(resultat_get_classe.next()){
                int id_classe = resultat_get_classe.getInt("idclasse");
                int id_niveau_classe = resultat_get_classe.getInt("niveau_classe_idniveau_classe");
                String jour =null;
                System.out.println(resultat_get_classe.getString("nom_classe")+" id : "+id_classe);
                for(int i = 1;i<=5;i++){
                String requete_get_seance = "select * from seance where emploi_idemploi = 1 and classe_idclasse = "+id_classe+" and heur_jour_idjour = "+i+" ORDER BY heur_idheur";
                Statement statement_get_seance = connection.createStatement();
                ResultSet resultat_get_seance = statement_get_seance.executeQuery(requete_get_seance);
                
                    String requete_get_jour = "select * from jour where idjour = "+i;
                    Statement statement_get_jour = connection.createStatement();
                    ResultSet resultat_get_jour = statement_get_jour.executeQuery(requete_get_jour);
                    while(resultat_get_jour.next()){
                        jour = resultat_get_jour.getString("nom");
                    }
                    System.out.print(jour+" | ");
                while(resultat_get_seance.next()){
                    int id_salle =  resultat_get_seance.getInt("salle_idsalle");
                    int id_heur = resultat_get_seance.getInt("heur_idheur");
                    int id_jour = resultat_get_seance.getInt("heur_jour_idjour");
                    int id_enseignant = resultat_get_seance.getInt("enseignant_idenseignant");
                    int id_matiere = resultat_get_seance.getInt("enseignant_matiere_idmatiere");
                    int num_salle=0;
                    String heur = null;
                    
                    String nom_enseignant=null;
                    String matiere = null;
                    
                    String requete_get_salle = "select * from salle where idsalle = "+id_salle;
                    Statement statement_get_salle = connection.createStatement();
                    ResultSet resultat_get_salle = statement_get_salle.executeQuery(requete_get_salle);
                    while(resultat_get_salle.next()){
                        num_salle = resultat_get_salle.getInt("num_salle");
                    }
                    String requete_get_heur = "select * from heur where idheur = "+id_heur;
                    Statement statement_get_heur = connection.createStatement();
                    ResultSet resultat_get_heur = statement_get_heur.executeQuery(requete_get_heur);
                    while(resultat_get_heur.next()){
                        heur = resultat_get_heur.getString("heur");
                    }
                    
                    
                    String requete_get_enseignant = "select * from enseignant where idenseignant = "+id_enseignant;
                    Statement statement_get_enseignant = connection.createStatement();
                    ResultSet resultat_get_enseignant = statement_get_enseignant.executeQuery(requete_get_enseignant);
                    while(resultat_get_enseignant.next()){
                        nom_enseignant = resultat_get_enseignant.getString("nom_enseignat");
                    }
                    String requete_get_matiere = "select * from matiere where idmatiere = "+id_matiere;
                    Statement statement_get_matiere = connection.createStatement();
                    ResultSet resultat_get_matiere = statement_get_matiere.executeQuery(requete_get_matiere);
                    while(resultat_get_matiere.next()){
                        matiere = resultat_get_matiere.getString("nom_matiere");
                    }
                   System.out.print(heur+" "+matiere+" "+nom_enseignant+" "+num_salle+" | "); 
                    
                }
               System.out.println();
            }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(afficher_emploi.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
}
