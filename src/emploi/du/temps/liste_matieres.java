/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emploi.du.temps;

import static emploi.du.temps.theMain.connection;
import static emploi.du.temps.theMain.jButton6;
import static emploi.du.temps.theMain.jButton7;
import static emploi.du.temps.theMain.jButton8;
import static emploi.du.temps.theMain.table_matiere;
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
public class liste_matieres {
    public static void afficher(){
      try {
             ResultSet résultats = null;
             ResultSet résultatsM = null;
             
             String requete = "SELECT * FROM matiere";
             
             Statement statement = connection.createStatement();
             
             résultats = statement.executeQuery(requete);
             
             ResultSetMetaData metaData = résultats.getMetaData();
             
             // names of columns
             Vector<String> columnNames = new Vector<String>();
             int columnCount = metaData.getColumnCount();
             for (int column = 1; column <= columnCount; column++) {
                 
                     columnNames.add(metaData.getColumnName(column));
             }
             
             Vector<Vector<Object>> data = new Vector<Vector<Object>>();
             while (résultats.next()) {
                 Vector<Object> vector = new Vector<Object>();
                 for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                         vector.add(résultats.getObject(columnIndex));
                 }
                 data.add(vector);
             }
             table_matiere.setModel( new DefaultTableModel(data,columnNames){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                 return false;
                   }
                });
         } catch (SQLException ex) {
             Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
         }
         CardLayout card = (CardLayout)theCard.getLayout();
         card.show(theCard, "card_matiere");
         jButton6.setEnabled(true);
         jButton7.setEnabled(false);
         jButton8.setEnabled(false);
    
    }
}
