/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

import com.github.lgooddatepicker.components.DatePicker;
import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sts
 */
public class uiController {

    void paintModifClient(Client modClient, JTextField clientNameField, JTextField clientFNameField, JTextField clientPhoneField, JTextField clientAdressField, JTextField ageField, DatePicker clientBD, JTextArea clientTextArea, JButton modClientBtn, JButton viderBtn, JButton sauvegarderBtn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void resetBackgroundColor(JPanel mainContainer) {
        for (Component child : mainContainer.getComponents()) {
            // Set the background color for each child component
            child.setBackground(new Color(247,247,247));
        }
    }

    void emptyTeethpanel(JPanel TeethPanel) {
        for (Component component : TeethPanel.getComponents()) {
            if (component instanceof JPanel && !component.getName().equals("9999999")) {
                component.setVisible(false);
            }
        }
    }

    void paintDetailedVisit(JTable visitsTable1, String[] searchResult, JPanel searchTypeContainer, JPanel searchTypePanel ) {
     try {

                DefaultTableModel d = (DefaultTableModel) visitsTable1.getModel();
                d.setRowCount(0);
                String[] searchResultRow = null;
                
                for (int i = 0; i<searchResult.length ; i++) {
                    searchResultRow = searchResult[i].split(":::");
                    Vector v2 = new Vector();

                    v2.add(searchResultRow[0]);
                    v2.add(searchResultRow[1]);
                    v2.add(searchResultRow[2]);
                    v2.add(searchResultRow[3]);
                    v2.add(searchResultRow[4]);
                    v2.add(searchResultRow[5]);
                    v2.add(searchResultRow[6]);
                    v2.add(searchResultRow[7]);
                    
                    
                    
                    d.addRow(v2);
                }
                
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("when painting detailed visit ...");
            }
     
            searchTypeContainer.removeAll();
            searchTypeContainer.add(searchTypePanel);
            searchTypeContainer.repaint();
            searchTypeContainer.revalidate();
            
        }

    void paintPatientsTable(JTable clientsTable, String[] searchResult) {
        
         try {

                DefaultTableModel d = (DefaultTableModel) clientsTable.getModel();
                d.setRowCount(0);
                String[] searchResultRow = null;
                
                for (int i = 0; i<searchResult.length ; i++) {
                    searchResultRow = searchResult[i].split(":::");
                    Vector v2 = new Vector();

                    v2.add(searchResultRow[0]);
                    v2.add(searchResultRow[1]);
                    v2.add(searchResultRow[2]);
                    v2.add(searchResultRow[3]);
                    v2.add(searchResultRow[4]);
                    v2.add(searchResultRow[5]);
                    
                    
                    
                    d.addRow(v2);
                }
                
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error in mouse clicked ...");
            }
            
        
    }

    void paintSeances(JTable seancesTable, String[] searchResult) {
        try {

                DefaultTableModel d = (DefaultTableModel) seancesTable.getModel();
                d.setRowCount(0);
                String[] searchResultRow = null;
                
                for (int i = 0; i<searchResult.length ; i++) {
                    searchResultRow = searchResult[i].split(":::");
                    Vector v2 = new Vector();

                    v2.add(searchResultRow[0]);
                    v2.add(searchResultRow[1]);
                    v2.add(searchResultRow[2]);
                    v2.add(searchResultRow[3]);
                    v2.add(searchResultRow[4]);
                    
                    
                    d.addRow(v2);
                }
                
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error when painting seances ");
            }
    }
    
    
}
