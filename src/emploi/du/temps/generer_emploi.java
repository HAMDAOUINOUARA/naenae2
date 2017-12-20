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
                         //insertion des seance tp
                            String requete_seancestp_predef ="select * from seance_a_fair where `type` = 'tp'";
                            Statement statement_seancestp_predef = connection.createStatement();
                            ResultSet resultat_seancestp_predef = statement_seancestp_predef.executeQuery(requete_seancestp_predef);
                            String id_labo = null;
                            while(resultat_seancestp_predef.next()){
                                String id_matier = resultat_seancestp_predef.getString("matiere_idmatiere");
                                String id_niveau = resultat_seancestp_predef.getString("niveau_classe_idniveau_classe");
                                String id_classe = null;
                                String id_enseign = null;
                                int id_heur = 0;
                                int id_jour = 0;
                                int volume = resultat_seancestp_predef.getInt("volume");
                                        String requete_get_classe = "SELECT * FROM classe WHERE `niveau_classe_idniveau_classe` ="+id_niveau;
                                        Statement statement_get_classe = connection.createStatement();
                                        ResultSet résultats_get_classe = statement_get_classe.executeQuery(requete_get_classe);

                                        while(résultats_get_classe.next()){
                                            id_classe = résultats_get_classe.getString("idclasse");
                                            
                                            String requete_classe_Has_ens = "SELECT * FROM classe_has_enseignant WHERE `classe_idclasse` = "+id_classe+" and enseignant_matiere_idmatiere = "+id_matier;
                                            Statement statement_classe_Has_ens = connection.createStatement();
                                            ResultSet résultats_classe_Has_ens = statement_classe_Has_ens.executeQuery(requete_classe_Has_ens);
                                            if(résultats_classe_Has_ens.next()){
                                                id_enseign = résultats_classe_Has_ens.getString("enseignant_idenseignant");
                                            }
                                                        
                                                                   String requete_recherche_labo_libre_h = "SELECT * FROM salle WHERE designation ='laboratoire'";
                                                                   Statement statement_recherche_labo_libre_h = connection.createStatement();
                                                                   ResultSet résultats_recherche_labo_libre_h = statement_recherche_labo_libre_h.executeQuery(requete_recherche_labo_libre_h);

                                                                   if(résultats_recherche_labo_libre_h.next()){
                                                                       id_labo = résultats_recherche_labo_libre_h.getString("idsalle");
                                                                          //recherches un jour libre pour un labor
                                                                          String requete_jour_libre_labo = "SELECT * FROM jour WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idjour = heur_jour_idjour AND salle_idsalle= "+id_labo+" AND classe_idclasse != "+id_classe+" )";
                                                                          Statement statement_jour_libre_labo = connection.createStatement();
                                                                          ResultSet resultat_jour_libre_labo = statement_jour_libre_labo.executeQuery(requete_jour_libre_labo);
                                                                          if(resultat_jour_libre_labo.next()){
                                                                          id_jour = resultat_jour_libre_labo.getInt("idjour");
                                                                              //recherche les heurs libre de l'enseignant et la classe et labo
                                                                                String requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") ) AND( heur = '10:00' OR heur ='08:00') AND jour_idjour = "+id_jour;
                                                                                Statement statement_heur_libre_ens = connection.createStatement();
                                                                                ResultSet resultat_heur_libre_ens = statement_heur_libre_ens.executeQuery(requete_heur_libre_ens);
                                                                                if(resultat_heur_libre_ens.next()){
                                                                                //recherche laboratoir libre
                                                                                             id_heur = resultat_heur_libre_ens.getInt("idheur");
                                                                                             

                                                                                  } 
                                                                          }
                                                                         
                                                                   } 

                                                    String verif_si_existe = "SELECT * from seance where salle_idsalle = "+id_labo+"  and emploi_idemploi = 1 and classe_idclasse = "+id_classe+" and classe_niveau_classe_idniveau_classe ="+id_niveau+" and enseignant_idenseignant ="+id_enseign+" and enseignant_matiere_idmatiere ="+id_matier;
                                                                   
                                                    Statement statement_verif_si_existe = connection.createStatement();
                                                    ResultSet résultats_verif_si_existe = statement_verif_si_existe.executeQuery(verif_si_existe);
                                                    if(!résultats_verif_si_existe.next()){
                                                        for(int i = 0;i<volume;i++){
                                                    id_heur = id_heur+i;
                                                      String requete_créer_seance = "INSERT INTO emploi_du_temp.seance (salle_idsalle, heur_idheur, heur_jour_idjour, emploi_idemploi, classe_idclasse, classe_niveau_classe_idniveau_classe, enseignant_idenseignant, enseignant_matiere_idmatiere, `type`) \n" +
                                "                       VALUES ("+id_labo+", "+id_heur+", "+id_jour+", 1, "+id_classe+", "+id_niveau+", "+id_enseign+", "+id_matier+" , 'tp');";


                                                    Statement statement_créer_seance = connection.createStatement();
                                                    statement_créer_seance.execute(requete_créer_seance);              
                                                    System.out.println(requete_créer_seance); 
                                                    
                                                    }


                                               }
                                        }     
                                        
                                        }
                   //insertion des seance Sport
                            String requete_seancesprt_predef ="select * from seance_a_fair where `type` = 'sport'";
                            Statement statement_seancesprt_predef = connection.createStatement();
                            ResultSet resultat_seancesprt_predef = statement_seancesprt_predef.executeQuery(requete_seancesprt_predef);
                            String id_stade = null;
                            while(resultat_seancesprt_predef.next()){
                                String id_matier = resultat_seancesprt_predef.getString("matiere_idmatiere");
                                String id_niveau = resultat_seancesprt_predef.getString("niveau_classe_idniveau_classe");
                                String id_classe = null;
                                String id_enseign = null;
                                int id_heur = 0;
                                int id_jour = 0;
                                int volume = resultat_seancesprt_predef.getInt("volume");
                                        String requete_get_classe = "SELECT * FROM classe WHERE `niveau_classe_idniveau_classe` ="+id_niveau;
                                        Statement statement_get_classe = connection.createStatement();
                                        ResultSet résultats_get_classe = statement_get_classe.executeQuery(requete_get_classe);

                                        while(résultats_get_classe.next()){
                                            id_classe = résultats_get_classe.getString("idclasse");
                                            
                                            String requete_classe_Has_ens = "SELECT * FROM classe_has_enseignant WHERE `classe_idclasse` = "+id_classe+" and enseignant_matiere_idmatiere = "+id_matier;
                                            Statement statement_classe_Has_ens = connection.createStatement();
                                            ResultSet résultats_classe_Has_ens = statement_classe_Has_ens.executeQuery(requete_classe_Has_ens);
                                            if(résultats_classe_Has_ens.next()){
                                                id_enseign = résultats_classe_Has_ens.getString("enseignant_idenseignant");
                                            }
                                                        
                                                                   
                                                                       
                                                                          
                                                                          
                                                                              //recherche les heurs libre de l'enseignant et la classe et stade
                                                                                String requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") ) AND NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur+1 AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") ) AND( heur = '10:00' OR heur ='08:00' OR heur ='13:00' or heur = '14:00') ORDER BY RAND()";
                                                                                Statement statement_heur_libre_ens = connection.createStatement();
                                                                                ResultSet resultat_heur_libre_ens = statement_heur_libre_ens.executeQuery(requete_heur_libre_ens);
                                                                                while(resultat_heur_libre_ens.next()){
                                                                                //recherche stade libre
                                                                                             id_heur = resultat_heur_libre_ens.getInt("idheur");
                                                                                             id_jour = resultat_heur_libre_ens.getInt("jour_idjour");
                                                                                             String requete_recherche_stade_libre_h = "SELECT * FROM salle WHERE designation ='stade' and not exists (select 1 from seance where idsalle = salle_idsalle and heur_idheur = "+id_heur+")";
                                                                                                Statement statement_recherche_stade_libre_h = connection.createStatement();
                                                                                                ResultSet résultats_recherche_stade_libre_h = statement_recherche_stade_libre_h.executeQuery(requete_recherche_stade_libre_h);

                                                                                            if(résultats_recherche_stade_libre_h.next()){
                                                                                            id_stade = résultats_recherche_stade_libre_h.getString("idsalle");
                                                                                            break;
                                                                                            } 

                                                                         
                                                                                   } 

                                                    String verif_si_existe = "SELECT * from seance where salle_idsalle = "+id_stade+"  and emploi_idemploi = 1 and classe_idclasse = "+id_classe+" and classe_niveau_classe_idniveau_classe ="+id_niveau+" and enseignant_idenseignant ="+id_enseign+" and enseignant_matiere_idmatiere ="+id_matier;
                                                                   
                                                    Statement statement_verif_si_existe = connection.createStatement();
                                                    ResultSet résultats_verif_si_existe = statement_verif_si_existe.executeQuery(verif_si_existe);
                                                    if(!résultats_verif_si_existe.next()){
                                                        for(int i = 0;i<volume;i++){
                                                    id_heur = id_heur+i;
                                                      String requete_créer_seance = "INSERT INTO emploi_du_temp.seance (salle_idsalle, heur_idheur, heur_jour_idjour, emploi_idemploi, classe_idclasse, classe_niveau_classe_idniveau_classe, enseignant_idenseignant, enseignant_matiere_idmatiere, `type`) \n" +
                                "                       VALUES ("+id_stade+", "+id_heur+", "+id_jour+", 1, "+id_classe+", "+id_niveau+", "+id_enseign+", "+id_matier+" , 'sport');";


                                                    Statement statement_créer_seance = connection.createStatement();
                                                    statement_créer_seance.execute(requete_créer_seance);              
                                                    System.out.println(requete_créer_seance); 
                                                    
                                                    }


                                               }
                                        }     
                                        
                                        }
                            
                            
             for(int i = 3 ; i>0;i--){
                 
                 
                 
                //insertion des seance cours
                String requete_get_niveau = "SELECT * FROM niveau_classe WHERE `niveau` ="+i;
                Statement statement_get_niveau = connection.createStatement();
                ResultSet résultats_get_niveau = statement_get_niveau.executeQuery(requete_get_niveau);
            
                while(résultats_get_niveau.next()){
                    String id_niveau = résultats_get_niveau.getString("idniveau_classe");
                               String requete_seancescr_predef ="select * from seance_a_fair where `type` = 'cours' and niveau_classe_idniveau_classe="+id_niveau;
                            Statement statement_seancescr_predef = connection.createStatement();
                            ResultSet resultat_seancescr_predef = statement_seancescr_predef.executeQuery(requete_seancescr_predef);
                            String id_salle = null;
                            while(resultat_seancescr_predef.next()){
                                String id_matier = resultat_seancescr_predef.getString("matiere_idmatiere");
                                String id_seance_a_f = resultat_seancescr_predef.getString("idseance_a_fair");
                                String heur_pref = resultat_seancescr_predef.getString("heur_pref");
                                String id_classe = null;
                                String id_enseign = null;
                                int id_heur = 0;
                                int id_jour = 0;
                                int volume = resultat_seancescr_predef.getInt("volume");
                                        String requete_get_classe = "SELECT * FROM classe WHERE `niveau_classe_idniveau_classe` ="+id_niveau;
                                        Statement statement_get_classe = connection.createStatement();
                                        ResultSet résultats_get_classe = statement_get_classe.executeQuery(requete_get_classe);

                                        while(résultats_get_classe.next()){
                                            id_classe = résultats_get_classe.getString("idclasse");
                                            
                                            String requete_classe_Has_ens = "SELECT * FROM classe_has_enseignant WHERE `classe_idclasse` = "+id_classe+" and enseignant_matiere_idmatiere = "+id_matier;
                                            Statement statement_classe_Has_ens = connection.createStatement();
                                            ResultSet résultats_classe_Has_ens = statement_classe_Has_ens.executeQuery(requete_classe_Has_ens);
                                            if(résultats_classe_Has_ens.next()){
                                                id_enseign = résultats_classe_Has_ens.getString("enseignant_idenseignant");
                                            }
                                                                    //recherche salle libre
                                                                   String requete_recherche_cours_libre_h = "SELECT * FROM salle WHERE not exists(select 1 from seance where idsalle = salle_idsalle and classe_idclasse != "+id_classe+") and designation ='cours'";
                                                                   Statement statement_recherche_cours_libre_h = connection.createStatement();
                                                                   ResultSet résultats_recherche_cours_libre_h = statement_recherche_cours_libre_h.executeQuery(requete_recherche_cours_libre_h);

                                                                   if(résultats_recherche_cours_libre_h.next()){
                                                                       id_labo = résultats_recherche_cours_libre_h.getString("idsalle");
                                                                         
                                                                               //recherche les heurs libre de l'enseignant et la classe et labo en fesan l'espace entre les matieres
                                                                               String requete_heur_libre_ens =null;
                                                                                if(heur_pref.equals("any")){
                                                                                
                                                                                requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") OR (jour_idjour = heur_jour_idjour AND enseignant_matiere_idmatiere = "+id_matier+" AND classe_idclasse = "+id_classe+")  ) and not exists(select 1 from seance where ((jour_idjour+1) % 5 = heur_jour_idjour AND jour_idjour != 5) AND enseignant_matiere_idmatiere = "+id_matier+") AND not exists(select 1 from seance where ((jour_idjour+4) % 5 = heur_jour_idjour AND jour_idjour != 1) AND enseignant_matiere_idmatiere = "+id_matier+") ORDER BY RAND()";
                                                                                
                                                                                }else{
                                                                                requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") OR (jour_idjour = heur_jour_idjour AND enseignant_matiere_idmatiere = "+id_matier+" AND classe_idclasse = "+id_classe+")  ) AND( heur = '10:00' OR heur ='08:00') and not exists(select 1 from seance where ((jour_idjour+1) % 5 = heur_jour_idjour AND jour_idjour != 5) AND enseignant_matiere_idmatiere = "+id_matier+") AND not exists(select 1 from seance where ((jour_idjour+4) % 5 = heur_jour_idjour AND jour_idjour != 1) AND enseignant_matiere_idmatiere = "+id_matier+")ORDER BY RAND()";
                                                                                  }
                                                                                
                                                                                Statement statement_heur_libre_ens1 = connection.createStatement();
                                                                                ResultSet resultat_heur_libre_ens1 = statement_heur_libre_ens1.executeQuery(requete_heur_libre_ens);
                                                                                if(resultat_heur_libre_ens1.next()){
                                                                                //recherche laboratoir libre
                                                                                             id_heur = resultat_heur_libre_ens1.getInt("idheur");
                                                                                             id_jour = resultat_heur_libre_ens1.getInt("jour_idjour");

                                                                                  }else{ 
                                                                              //recherche les heurs libre de l'enseignant et la classe et labo
                                                                             
                                                                                if(heur_pref.equals("any")){
                                                                                
                                                                                requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") OR (jour_idjour = heur_jour_idjour AND enseignant_matiere_idmatiere = "+id_matier+" AND classe_idclasse = "+id_classe+")  ) ORDER BY RAND()";
                                                                                
                                                                                }else{
                                                                                requete_heur_libre_ens ="SELECT * FROM heur WHERE NOT EXISTS(SELECT 1 FROM seance WHERE idheur = heur_idheur AND ( enseignant_idenseignant = "+id_enseign+" OR classe_idclasse = "+id_classe+") OR (jour_idjour = heur_jour_idjour AND enseignant_matiere_idmatiere = "+id_matier+" AND classe_idclasse = "+id_classe+")  ) AND( heur = '10:00' OR heur ='08:00') ORDER BY RAND()";
                                                                                  }
                                                                                
                                                                                Statement statement_heur_libre_ens = connection.createStatement();
                                                                                ResultSet resultat_heur_libre_ens = statement_heur_libre_ens.executeQuery(requete_heur_libre_ens);
                                                                                if(resultat_heur_libre_ens.next()){
                                                                                //recherche laboratoir libre
                                                                                             id_heur = resultat_heur_libre_ens.getInt("idheur");
                                                                                             id_jour = resultat_heur_libre_ens.getInt("jour_idjour");

                                                                                  } 
                                                                          
                                                                                }
                                                                         
                                                                   }else{
                                                                   System.out.println("no salle found");
                                                                   } 

                                               String verif_si_existe = "SELECT * from seance where emploi_idemploi = 1 and classe_idclasse = "+id_classe+" and enseignant_matiere_idmatiere ="+id_matier+" AND heur_idheur = "+id_heur;
                                                                 
                                                                   
                                                    Statement statement_verif_si_existe = connection.createStatement();
                                                    ResultSet résultats_verif_si_existe = statement_verif_si_existe.executeQuery(verif_si_existe);
                                                    if(!résultats_verif_si_existe.next()){        
                                               for(int l = 0;l<volume;l++){
                                               id_heur = id_heur+l;
                                                       String requete_créer_seance = "INSERT INTO emploi_du_temp.seance (salle_idsalle, heur_idheur, heur_jour_idjour, emploi_idemploi, classe_idclasse, classe_niveau_classe_idniveau_classe, enseignant_idenseignant, enseignant_matiere_idmatiere, `type`) \n" +
                                "                       VALUES ("+id_labo+", "+id_heur+", "+id_jour+", 1, "+id_classe+", "+id_niveau+", "+id_enseign+", "+id_matier+" , 'cours');";


                                                    Statement statement_créer_seance = connection.createStatement();
                                                    statement_créer_seance.execute(requete_créer_seance);              
                                                    System.out.println(requete_créer_seance); 

                                               }
                                        }     
                                        }
                                        }  
                 
                }
         }
             

                            
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        
        
   
   }
}
