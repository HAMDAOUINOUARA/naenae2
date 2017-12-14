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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIDOU
 */
public class affecter_profs_classes {
     public static void affecter_prof_classes (){
               
         try {
             
            
             
             Vector<Vector<Object>> classes = new Vector<Vector<Object>>();
             for(int i=3;i>0 ;i--){
                 ResultSet résultats = null;
                 
             String requete = "SELECT * FROM classe where `niveau` = "+i;
             
             Statement statement;
             statement = connection.createStatement();
              résultats = statement.executeQuery(requete);
              while(résultats.next()){
                  
              String requete_niveau = "SELECT * FROM niveau_classe WHERE `idniveau_classe` = '"+résultats.getString("niveau_classe_idniveau_classe")+"'";
              Statement statement_niveau = connection.createStatement();
              ResultSet résultats_niveau = statement_niveau.executeQuery(requete_niveau);
              
              while(résultats_niveau.next()){
                  
                  System.out.println(résultats_niveau.getString("niveau")+résultats_niveau.getString("specialité"));
                  String requete_niveau_HM = "SELECT * FROM niveau_classe_has_matiere WHERE `niveau_classe_idniveau_classe` = '"+résultats_niveau.getString("idniveau_classe")+"'";
                  Statement statement_niveau_HM = connection.createStatement();
                  ResultSet résultats_niveau_HM = statement_niveau_HM.executeQuery(requete_niveau_HM);
                  while(résultats_niveau_HM.next()){
                      
                      String requete_matiere = "SELECT * FROM matiere WHERE `idmatiere` = '"+résultats_niveau_HM.getString("matiere_idmatiere")+"'";
                  Statement statement_matiere = connection.createStatement();
                  ResultSet résultats_matiere = statement_matiere.executeQuery(requete_matiere);
                  int profindex = 0;
                  if(résultats_matiere.next()){
                  profindex = Integer.valueOf(résultats_matiere.getString("index_matiere"));
                  }
                  //tester si la classe a deja un prof pour la maitere
                    String requete_classe_Has_ens = "SELECT * FROM classe_has_enseignant WHERE `enseignant_matiere_idmatiere` = '"+résultats_niveau_HM.getString("matiere_idmatiere")+"' AND `classe_idclasse`="+résultats.getString("idclasse");
                  Statement statement_classe_Has_ens = connection.createStatement();
                  ResultSet résultats_classe_Has_ens = statement_classe_Has_ens.executeQuery(requete_classe_Has_ens);
                  if(!résultats_classe_Has_ens.next()){
                  System.out.println("id matiere"+résultats_niveau_HM.getString("matiere_idmatiere"));
                        String requete_enseignant = "SELECT * FROM enseignant WHERE nombre_heurs = (SELECT MAX(nombre_heurs) FROM enseignant WHERE `matiere_idmatiere`= '"+résultats_niveau_HM.getString("matiere_idmatiere")+"') AND`matiere_idmatiere`= '"+résultats_niveau_HM.getString("matiere_idmatiere")+"'";
                        Statement statement_enseignant = connection.createStatement();
                         ResultSet résultats_enseignant = statement_enseignant.executeQuery(requete_enseignant);
                         
                         
                        while (résultats_enseignant.next()) {
                            System.out.println("id enseignant = "+résultats_enseignant.getString("idenseignant"));
                            int heurs_classe = Integer.valueOf(résultats_niveau_HM.getString("volumePS"));
                            int heurs_ens = Integer.valueOf(résultats_enseignant.getString("nombre_heurs"));
                            if(heurs_ens >= heurs_classe){
                                   //créer une classe has enseignant
                                 int rest_ens =  heurs_ens-heurs_classe;
                                 if((heurs_ens>profindex)||(résultats_enseignant.isLast()) ){
                                     if(rest_ens<=profindex){
                                         profindex=rest_ens;
                                         }
                                         Statement statement_index = connection.createStatement();  
                                         String requete_index = "UPDATE emploi_du_temp.matiere SET `index_matiere` = "+profindex+" WHERE idmatiere = "+ résultats_enseignant.getString("matiere_idmatiere");
                                         statement_index.execute(requete_index);
                                         Statement statement_classe_HE = connection.createStatement();
                                         String requete_classe_HE = "INSERT INTO emploi_du_temp.classe_has_enseignant (classe_idclasse, enseignant_idenseignant, enseignant_matiere_idmatiere) VALUES ("+résultats.getString("idclasse")+", "+résultats_enseignant.getString("idenseignant")+", "+résultats_enseignant.getString("matiere_idmatiere")+")";
                                         statement_classe_HE.execute(requete_classe_HE);
                                         Statement statement_ens_affect = connection.createStatement(); 
                                         
                                         //affecter le prof a la classe
                                     String requete_ens_affect = "UPDATE emploi_du_temp.enseignant SET `nombre_heurs` = "+rest_ens+" WHERE idenseignant = "+ résultats_enseignant.getString("idenseignant");
                                     statement_ens_affect.execute(requete_ens_affect);
                                     System.out.println("affecter prof"+résultats_enseignant.getString("nom_enseignat")+" a la classe "+résultats.getString("nom_classe"));
                                     
                                     
                                break;
                                 }}
                            
                           /* Vector<Object> vector = new Vector<Object>();
                            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                                    vector.add(résultats_enseignant.getObject(columnIndex));
                                   System.out.println(résultats_enseignant.getString(columnIndex)); 
                            }
                            profs.add(vector);*/
                        }
                  }else{
                  System.out.println("classe already has prof in this matiere");
                  }
                     System.out.println("passe a une autre matiere");  
                        /* while(résultats_enseignant.next()){
                              System.out.println("prof : "+résultats_enseignant.getString("nom_enseignat"));
                         }*/
                  }
              }   
              
              System.out.println("passe a une autre classe");
              }
             }    
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
             
            
     
    
     }
}
