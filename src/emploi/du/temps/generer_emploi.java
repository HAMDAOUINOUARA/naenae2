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
public class generer_emploi {
    
   public static void generer(){
    
         try {
             for(int i = 3 ; i>0;i--){
            String requete_get_classe = "SELECT * FROM classe WHERE `niveau` ="+i;
            Statement statement_get_classe = connection.createStatement();
            ResultSet résultats_get_classe = statement_get_classe.executeQuery(requete_get_classe);
            
            while(résultats_get_classe.next()){
                //classe has enseignant
                
                int coef= 0;
                String id_niveau =résultats_get_classe.getString("niveau_classe_idniveau_classe");
                
                
                
                
                String requete_classe_Has_ens = "SELECT * FROM classe_has_enseignant WHERE `classe_idclasse` = "+résultats_get_classe.getString("idclasse");
                Statement statement_classe_Has_ens = connection.createStatement();
                ResultSet résultats_classe_Has_ens = statement_classe_Has_ens.executeQuery(requete_classe_Has_ens);
                while(résultats_classe_Has_ens.next()){
                    
                    String id_salle = null;
                    String id_ens = null;
                    String id_matiere= null;
                    
                    String requete_heurs =null;
                    id_ens= résultats_classe_Has_ens.getString("enseignant_idenseignant");
                    id_matiere =   résultats_classe_Has_ens.getString("enseignant_matiere_idmatiere");
                   
                    //recherche salle de la classe
                    for(int j = 7; j>0 ;j--){
                        String requete_coef ="SELECT * FROM niveau_classe_has_matiere WHERE `niveau_classe_idniveau_classe` = "+id_niveau+" AND `matiere_idmatiere` = "+ id_matiere+" AND coefficiant ="+j;
                        Statement statement_coef = connection.createStatement();
                        ResultSet restultats_coef= statement_coef.executeQuery(requete_coef);
                        //chercher l'heur libre du prof
                        while (restultats_coef.next() ){
                             coef = Integer.valueOf( restultats_coef.getString("coefficiant"));
                             if(coef >=4){
                                requete_heurs ="Select * from heur where not exists (select 1 from seance where seance.heur_idheur = idheur AND enseignant_idenseignant ="+id_ens+" ) AND heur.heur < '12:00'";
                                }else{
                                requete_heurs ="Select * from heur where not exists (select 1 from seance where seance.heur_idheur = idheur AND enseignant_idenseignant ="+id_ens+" )";
                                }
                            //chercher le temps libre du prof
                            
                             
                             
                             
                            String requete_salle_already = "SELECT * FROM salle WHERE `idsalle`=(SELECT salle_idsalle from seance WHERE classe_idclasse ="+résultats_get_classe.getString("idclasse")+" )";
                            Statement statement_salle_already = connection.createStatement();
                            ResultSet résultats_salle_already = statement_salle_already.executeQuery(requete_salle_already);

                            if(résultats_salle_already.next()){
                            //elle existe une salle deja pour cette classe
                               id_salle = résultats_salle_already.getString("idsalle");
                               System.out.println("find salle"+ id_salle+" enseignant  : "+id_ens+"matiere : "+ id_matiere);  

                            }else{
                                String requete_salle_libre="SELECT * FROM salle WHERE NOT EXISTS(SELECT 1 FROM seance WHERE `salle_idsalle`= salle.idsalle)"; 
                                Statement statement_salle_libre = connection.createStatement();
                                ResultSet résultats_salle_libre = statement_salle_libre.executeQuery(requete_salle_libre);
                                if(résultats_salle_libre.next()){
                                id_salle = résultats_salle_libre.getString("idsalle");

                                }else{
                                   // cas d'une classe qui ce deplace
                                   


                                }

                            }
                        
                        }
                    
                    }
                    



                     //recherche les heurs libre du prof
                    
                 
                    
                    
                }
                
                
                
                String requete_matiere ="SELECT * FROM matiere WHERE `idmatiere` ="+id_matiere;
                Statement statement_matiere = connection.createStatement();
                ResultSet restultats_matiere= statement_matiere.executeQuery(requete_matiere);
                
                    
                 String requete_salle_already = "SELECT * FROM salle WHERE `idsalle`=(SELECT salle_idsalle from seance WHERE classe_idclasse ="+résultats_get_classe.getString("idclasse")+" )";
                    Statement statement_salle_already = connection.createStatement();
                    ResultSet résultats_salle_already = statement_salle_already.executeQuery(requete_salle_already);
                    
                    if(résultats_salle_already.next()){
                    //elle existe une salle deja pour cette classe
                       id_salle = résultats_salle_already.getString("idsalle");
                       System.out.println("find salle"+ id_salle+" enseignant  : "+id_ens+"matiere : "+ id_matiere);  
                       
                    }else{
                        String requete_salle_libre="SELECT * FROM salle WHERE NOT EXISTS(SELECT 1 FROM seance WHERE `salle_idsalle`= salle.idsalle)"; 
                        Statement statement_salle_libre = connection.createStatement();
                        ResultSet résultats_salle_libre = statement_salle_libre.executeQuery(requete_salle_libre);
                        if(résultats_salle_libre.next()){
                        id_salle = résultats_salle_libre.getString("idsalle");
                        
                        }else{
                        
                        }
                        
                    }
            }
             }
            String requete_get_seance = "SELECT * FROM seance";
            Statement statement_get_seance = connection.createStatement();
            ResultSet résultats_get_seance = statement_get_seance.executeQuery(requete_get_seance);
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        
        
   
   }
}
