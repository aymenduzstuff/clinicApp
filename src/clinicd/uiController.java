/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clinicd;

import com.github.lgooddatepicker.components.DatePicker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sts
 */
public class uiController {



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
    void paintVistsTable(JTable visitsTable , String[] searchResult ){
        DefaultTableModel d = (DefaultTableModel) visitsTable.getModel();
        d.setRowCount(0);
        String[] searchResultRow = null;

        for (int i = 0; i < searchResult.length; i++) {
            searchResultRow = searchResult[i].split(":::");
            Vector v2 = new Vector();

            v2.add(searchResultRow[0]);
            v2.add(searchResultRow[1]);
            v2.add(searchResultRow[2]);
            v2.add(searchResultRow[3]);
            v2.add(searchResultRow[4]);
            v2.add(searchResultRow[5]);
            v2.add(searchResultRow[6]);

            d.addRow(v2);
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
    void paintActsTable(JTable clientsTable, String[] searchResult) {
        
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
                    
                    
                    
                    d.addRow(v2);
                }
                
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error in uicontroller actstable paint ...");
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
                e.printStackTrace();
                System.err.println("error when painting seances ");
            }
    }
    void paintModifClient(Client modClient, JTextField clientNameField, JTextField clientFNameField, JTextField clientPhoneField, JTextField clientAdressField, JTextField ageField, DatePicker clientBD, JTextArea clientTextArea, JButton modClientBtn, JButton viderBtn, JButton sauvegarderBtn) {
        clientNameField.setText(modClient.nom);
        clientFNameField.setText(modClient.prenom);
        clientPhoneField.setText(modClient.numero_tel);
        clientAdressField.setText(modClient.adresse);
        ageField.setText(String.valueOf(modClient.age));
        clientBD.setDate(modClient.date_nais);
        clientTextArea.setText(modClient.notes);
        
        modClientBtn.setVisible(true);
        viderBtn.setVisible(false);
        sauvegarderBtn.setVisible(false);
        
        
        
    }
    void paintModifAct(act thisAct  , JTextField operationNameField, JSpinner operationPriceSpinner, JTextArea operationDescription, JButton saveActBtn, JButton updateActBtn) {
        operationNameField.setText(thisAct.nom_act);
        operationPriceSpinner.setValue(thisAct.cout);
        operationDescription.setText(thisAct.description);
                
        saveActBtn.setVisible(false);
        updateActBtn.setVisible(true);
        
                        
    }
void paintRightsTable(JTable table  , String[] searchResult ){
    
        try {

                DefaultTableModel d = (DefaultTableModel) table.getModel();
                d.setRowCount(0);
                String[] searchResultRow = null;
                
                for (int i = 0; i<searchResult.length ; i++) {
                    searchResultRow = searchResult[i].split(":::");
                    Vector v2 = new Vector();

                    v2.add(searchResultRow[0]);
                    v2.add(searchResultRow[1]);
                    
                    String[] rightsString = searchResultRow[2].split("-");
                    
                    for (int j = 0; j < 5; j++) {
                        
                        v2.add(rightsString[j]);
                       /*
                        if (rightsString[j].equals("0")) {
                            v2.add(new ImageIcon("no.png"));
                            

                        } else {
                            v2.add(new ImageIcon("yes.png"));
                            
                        }
`                       */
                    }
                    
                    
                    
                    
                    d.addRow(v2);
                }
                table.setRowHeight(50);
                
            } catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("error admins table creation ...");
            }
        
       
        
    }

    void paintModifAdmin(Admin modAdmin, JTextField usernameField, JTextField passwordField, JRadioButton gestionAdminsRight, JRadioButton visitsRight, JRadioButton gestionActesRight, JRadioButton gestionPatientsRight, JRadioButton voirApercusRight, JButton adminMajBtn, JButton adminSaveBtn, JButton adminDiscardBtn) {
    
        boolean[] rights = new boolean[5] ;
        for(int i = 0 ; i < modAdmin.authorities.length ; i++){
            rights[i] = modAdmin.authorities[i] == 1;
        }
        
        usernameField.setText(modAdmin.name);
        passwordField.setText(modAdmin.pass);
        
        
        gestionAdminsRight.setSelected(rights[0]); 
        gestionActesRight.setSelected(rights[1]); 
        gestionPatientsRight.setSelected(rights[2]); 
        visitsRight.setSelected(rights[3]); 
        voirApercusRight.setSelected(rights[4]); 
        
                
        adminSaveBtn.setVisible(false);
        adminDiscardBtn.setVisible(false);
        adminMajBtn.setVisible(true);
        
               
    
    }

    void paintApercus(String[] results, JLabel apercu1, JLabel apercu2, JLabel apercu3, JLabel apercu4, JLabel apercu5, JLabel apercu6, JLabel apercu7) {
        apercu1.setText(results[0]);
        apercu2.setText(results[1]);
        apercu3.setText(results[2]);
        apercu4.setText(results[3]);
        double termine =  Integer.parseInt(results[4]);
        double total   = Integer.parseInt(results[3]) ;
                
        double percentage =  100 *(termine / total);
        
        
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        
        apercu5.setText(String.valueOf(decimalFormat.format(percentage))+"%");
        apercu6.setText(results[5]);
        int avgSpend = Integer.parseInt(results[5])/ Integer.parseInt(results[6]);
        
        apercu7.setText(String.valueOf(avgSpend));
        
       
    }

    void paintUsersTable(JTable usersTable, String[] searchResult) {
    
        DefaultTableModel d = (DefaultTableModel) usersTable.getModel();
        d.setRowCount(0);
        String[] searchResultRow = null;

        for (int i = 0; i < searchResult.length; i++) {
            searchResultRow = searchResult[i].split(":::");
            Vector v2 = new Vector();

            v2.add(Integer.parseInt(searchResultRow[0]));
            v2.add(searchResultRow[1]);
            v2.add(searchResultRow[2]);

            d.addRow(v2);
        }

    }

    void resetTeethPanel(JPanel teethpanel, JPanel babyTeethPanel) {
        
        Component[] components = teethpanel.getComponents();
        Component[] otherComponents = babyTeethPanel.getComponents();

        // make one big array to contain all components 
        int totalSize = components.length + otherComponents.length;
        Component[] allComponents = new Component[totalSize];
        System.arraycopy(components, 0, allComponents, 0, components.length);
        System.arraycopy(otherComponents, 0, allComponents, components.length, otherComponents.length);

        
        for (Component component : allComponents) {
            if ("9999999".equals(component.getName())) {
                continue;
            }
            component.setVisible(false);
            System.out.println("all components :" + component.getName());
        }
    }
    
    void resetVisitPanel( JSpinner priceTF, JCheckBox termineRadio, JTextArea notesTextArea, JPanel teethpanel, JPanel babyTeethPanel, JPanel allTeethPanel){
        priceTF.setValue(0);
        termineRadio.setSelected(false);
        notesTextArea.setText("                                \n" +
"                                   laisser une remarque");
        Font font = new Font("Segoe UI", Font.BOLD, 14);
        notesTextArea.setFont(font);
        notesTextArea.setForeground(new Color(204,204,204));
        resetTeethPanel(teethpanel , babyTeethPanel);
        
        allTeethPanel.removeAll();
        allTeethPanel.add(teethpanel);
        allTeethPanel.repaint();
        allTeethPanel.revalidate();
        
    }

}
