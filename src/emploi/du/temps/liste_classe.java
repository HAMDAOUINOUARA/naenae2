/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.connection;
import static emploi.du.temps.theMain.jButton10;
import static emploi.du.temps.theMain.jButton11;
import static emploi.du.temps.theMain.jButton9;
import static emploi.du.temps.theMain.table_classe;
import static emploi.du.temps.theMain.theCard;
import java.awt.CardLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MIDOU
 */
public class liste_classe {
    public static void afficher() {
         try {
             ResultSet résultats = null;
             
             
             String requete = "SELECT * FROM classe";
             
             Statement statement = connection.createStatement();
             
             résultats = statement.executeQuery(requete);
             
             
             
             ResultSetMetaData metaData = résultats.getMetaData();
             
             // names of columns
             Vector<String> columnNames = new Vector<String>();
             int columnCount = metaData.getColumnCount();
             for (int column = 1; column <= columnCount; column++) {
                    if(metaData.getColumnName(column).equals("niveau_classe_idniveau_classe")){
                      columnNames.add("niveau"); 
                      columnNames.add("specialité"); }
                    else
                     columnNames.add(metaData.getColumnName(column));
             }
             
             Vector<Vector<Object>> data = new Vector<Vector<Object>>();
             while (résultats.next()) {
                 Vector<Object> vector = new Vector<Object>();
                 for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        if(metaData.getColumnName(columnIndex).equals("niveau_classe_idniveau_classe")){
                            
                         String requeteM = "SELECT * FROM niveau_classe WHERE `idniveau_classe` = '"+résultats.getObject(columnIndex)+"'";
                         
                           Statement statementM = connection.createStatement();
                         ResultSet résultatsM  = statementM.executeQuery(requeteM);
                         while(résultatsM.next()){
                        vector.add(résultatsM.getString("niveau"));
                        vector.add(résultatsM.getString("specialité"));
                         }
                        }else
                         vector.add(résultats.getObject(columnIndex));
                 }
                 data.add(vector);
             }
             table_classe.setModel( new DefaultTableModel(data,columnNames){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                 return false;
                   }
                });
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
         CardLayout card = (CardLayout)theCard.getLayout();
         card.show(theCard, "card_classe");
         jButton9.setEnabled(true);
         jButton10.setEnabled(false);
         jButton11.setEnabled(false);
    }
}
