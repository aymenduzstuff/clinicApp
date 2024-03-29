/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicd;

import static com.github.lgooddatepicker.durationpicker_underconstruction.DurationUnit.Day;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aymen
 */
public class PopUp extends javax.swing.JFrame {

    private DBconnection dBConnection;
    static  PopupListener listener ;
    
    UserQueryManager UQM = new UserQueryManager();
    uiController UIC = new uiController();
    
    Admin loggedAdmin = new Admin(); 
    
    Client modClient = new Client();
    act modAct = new act();
    Admin modAdmin = new Admin();
    Facture modFacture = new Facture();
    /**
     * Creates new form PopUp
     */
    public PopUp(String btnName , DBconnection dBConnection, Object currentObj , Admin loggedAdmin) throws SQLException {
        initComponents();
        this.dBConnection = dBConnection;
        this.loggedAdmin = loggedAdmin ;
        
        
        if(btnName == "admin"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newAdminPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modAdmin = (Admin) currentObj ;
                UIC.paintModifAdmin( modAdmin , usernameField, passwordField, gestionAdminsRight ,visitsRight , gestionActesRight , gestionPatientsRight ,voirApercusRight , adminMajBtn , adminSaveBtn , adminDiscardBtn );
            }
        }else if(btnName == "operation"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newOpPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modAct = (act) currentObj ;
                UIC.paintModifAct( modAct , operationNameField, operationPriceSpinner, operationDescription, saveActBtn , updateActBtn );
            }
        }else if(btnName == "client"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newClientPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modClient = (Client) currentObj ;
                UIC.paintModifClient(modClient, clientNameField, clientFNameField, clientPhoneField, clientAdressField, ageField , clientBD, clientTextArea , modClientBtn , viderBtn , sauvegarderBtn);
            }
            
        }else if(btnName == "ordonnance"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newAdminPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
        }else if(btnName == "facture"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newFacturePanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modFacture = (Facture) currentObj ;
                UIC.paintModifFacture(modFacture , operationNameField1, operationPriceSpinner1, operationDescription1, saveActBtn1 , updateActBtn1 );
            }
        }
        
    }

    private PopUp() {
        initComponents();
    }
    
    public PopUp(String btnName , DBconnection dBConnection, Object currentObj , Admin loggedAdmin , PopupListener main) throws SQLException {
        initComponents();
        this.dBConnection = dBConnection;
        this.loggedAdmin = loggedAdmin ;
        this.listener = main ;
        
        
        if(btnName == "admin"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newAdminPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modAdmin = (Admin) currentObj ;
                UIC.paintModifAdmin( modAdmin , usernameField, passwordField, gestionAdminsRight ,visitsRight , gestionActesRight , gestionPatientsRight ,voirApercusRight , adminMajBtn , adminSaveBtn , adminDiscardBtn );
            }
        }else if(btnName == "operation"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newOpPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modAct = (act) currentObj ;
                UIC.paintModifAct( modAct , operationNameField, operationPriceSpinner, operationDescription, saveActBtn , updateActBtn );
            }
        }else if(btnName == "client"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newClientPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modClient = (Client) currentObj ;
                UIC.paintModifClient(modClient, clientNameField, clientFNameField, clientPhoneField, clientAdressField, ageField , clientBD, clientTextArea , modClientBtn , viderBtn , sauvegarderBtn);
            }
            
        }else if(btnName == "ordonnance"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newAdminPanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
        }else if(btnName == "facture"){
            popUpParentPanel.removeAll();
            popUpParentPanel.add(newFacturePanel);
            popUpParentPanel.repaint();
            popUpParentPanel.revalidate();
            
            if(currentObj!=null){
                modFacture = (Facture) currentObj ;
                UIC.paintModifFacture(modFacture , operationNameField1, operationPriceSpinner1, operationDescription1, saveActBtn1 , updateActBtn1 );
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUpParentPanel = new javax.swing.JPanel();
        newAdminPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        gestionAdminsRight = new javax.swing.JRadioButton();
        gestionActesRight = new javax.swing.JRadioButton();
        gestionPatientsRight = new javax.swing.JRadioButton();
        voirApercusRight = new javax.swing.JRadioButton();
        adminDiscardBtn = new javax.swing.JButton();
        adminSaveBtn = new javax.swing.JButton();
        visitsRight = new javax.swing.JRadioButton();
        adminMajBtn = new javax.swing.JButton();
        checkUser = new javax.swing.JLabel();
        checkPass = new javax.swing.JLabel();
        newOpPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        operationNameField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        SpinnerModel model5 = new SpinnerNumberModel(0, 0, 100000, 500);
        operationPriceSpinner = new javax.swing.JSpinner(model5) ;
        saveActBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        operationDescription = new javax.swing.JTextArea();
        updateActBtn = new javax.swing.JButton();
        checkAct = new javax.swing.JLabel();
        checkPrice = new javax.swing.JLabel();
        newClientPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        clientNameField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        clientFNameField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        clientPhoneField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        clientAdressField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientTextArea = new javax.swing.JTextArea();
        viderBtn = new javax.swing.JButton();
        sauvegarderBtn = new javax.swing.JButton();
        clientBD = new com.github.lgooddatepicker.components.DatePicker();
        ageField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        checkNom = new javax.swing.JLabel();
        checkPrenom = new javax.swing.JLabel();
        checkAge = new javax.swing.JLabel();
        modClientBtn = new javax.swing.JButton();
        ordonnancePanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        ordonnanceSearch = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        medsList = new javax.swing.JList<>();
        med1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        newFacturePanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        operationNameField1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        SpinnerModel model55 = new SpinnerNumberModel(0, 0, 100000, 500);
        operationPriceSpinner1 = new javax.swing.JSpinner(model55) ;
        saveActBtn1 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        operationDescription1 = new javax.swing.JTextArea();
        updateActBtn1 = new javax.swing.JButton();
        checkAct1 = new javax.swing.JLabel();
        checkPrice1 = new javax.swing.JLabel();
        multipleInsertBox = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(325, 425));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        popUpParentPanel.setLayout(new java.awt.CardLayout());

        newAdminPanel.setName("adminP"); // NOI18N

        jLabel2.setText("nouvel admin");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setText("utilisateur");

        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameFieldKeyReleased(evt);
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordFieldKeyReleased(evt);
            }
        });

        jLabel5.setText("mot de pass");

        jLabel6.setText("responsabilites :");

        gestionAdminsRight.setText("gestion des admins");
        gestionAdminsRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionAdminsRightActionPerformed(evt);
            }
        });

        gestionActesRight.setText("gestion des actes dentaires");

        gestionPatientsRight.setText("gestion des patients ");

        voirApercusRight.setText("consultation des appercues statistiques ");

        adminDiscardBtn.setText("réinitialiser");
        adminDiscardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDiscardBtnActionPerformed(evt);
            }
        });

        adminSaveBtn.setText("sauvegarder");
        adminSaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminSaveBtnActionPerformed(evt);
            }
        });

        visitsRight.setText("suppression / modification des visites ");
        visitsRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visitsRightActionPerformed(evt);
            }
        });

        adminMajBtn.setText("mettre a jour");
        adminMajBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminMajBtnMouseClicked(evt);
            }
        });

        checkUser.setText("*");
        checkUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkUser.setForeground(new java.awt.Color(255, 51, 51));

        checkPass.setText("*");
        checkPass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkPass.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout newAdminPanelLayout = new javax.swing.GroupLayout(newAdminPanel);
        newAdminPanel.setLayout(newAdminPanelLayout);
        newAdminPanelLayout.setHorizontalGroup(
            newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newAdminPanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newAdminPanelLayout.createSequentialGroup()
                        .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordField)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkUser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(voirApercusRight)
                    .addComponent(gestionPatientsRight)
                    .addComponent(gestionActesRight)
                    .addComponent(gestionAdminsRight)
                    .addComponent(visitsRight))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newAdminPanelLayout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newAdminPanelLayout.createSequentialGroup()
                        .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newAdminPanelLayout.createSequentialGroup()
                                .addComponent(adminDiscardBtn)
                                .addGap(75, 75, 75)
                                .addComponent(adminSaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(adminMajBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newAdminPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164))))
        );
        newAdminPanelLayout.setVerticalGroup(
            newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newAdminPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(52, 52, 52)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(checkUser))
                .addGap(18, 18, 18)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkPass)))
                .addGap(45, 45, 45)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(newAdminPanelLayout.createSequentialGroup()
                        .addComponent(gestionAdminsRight)
                        .addGap(3, 3, 3)
                        .addComponent(gestionActesRight)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gestionPatientsRight)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(visitsRight, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(voirApercusRight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(adminMajBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminDiscardBtn)
                    .addComponent(adminSaveBtn))
                .addGap(25, 25, 25))
        );

        gestionActesRight.setSelected(true);
        gestionPatientsRight.setSelected(true);
        voirApercusRight.setSelected(true);
        visitsRight.setSelected(true);
        adminMajBtn.setVisible(false) ;

        popUpParentPanel.add(newAdminPanel, "card3");

        newOpPanel.setName("operationP"); // NOI18N

        jLabel1.setText("                                     nouvel acte dentaire");
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel13.setText("designation :");

        operationNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationNameFieldActionPerformed(evt);
            }
        });
        operationNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                operationNameFieldKeyReleased(evt);
            }
        });

        jLabel14.setText("montant a charger");

        operationPriceSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                operationPriceSpinnerStateChanged(evt);
            }
        });
        operationPriceSpinner.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                operationPriceSpinnerFocusGained(evt);
            }
        });

        saveActBtn.setText("sauvegarder");
        saveActBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActBtnActionPerformed(evt);
            }
        });

        jLabel18.setText("description");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        operationDescription.setColumns(20);
        operationDescription.setLineWrap(true);
        operationDescription.setRows(5);
        jScrollPane2.setViewportView(operationDescription);

        updateActBtn.setText("mettre a jour");
        updateActBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateActBtnMouseClicked(evt);
            }
        });

        checkAct.setText("*");
        checkAct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAct.setForeground(new java.awt.Color(255, 0, 51));

        checkPrice.setText("*");
        checkPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkPrice.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout newOpPanelLayout = new javax.swing.GroupLayout(newOpPanel);
        newOpPanel.setLayout(newOpPanelLayout);
        newOpPanelLayout.setHorizontalGroup(
            newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newOpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newOpPanelLayout.createSequentialGroup()
                        .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addGroup(newOpPanelLayout.createSequentialGroup()
                                .addComponent(operationPriceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(checkPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newOpPanelLayout.createSequentialGroup()
                                .addComponent(operationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkAct, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(newOpPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updateActBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(saveActBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(64, 64, 64))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        newOpPanelLayout.setVerticalGroup(
            newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newOpPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(operationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkAct)))
                .addGap(41, 41, 41)
                .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(operationPriceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPrice))
                .addGap(47, 47, 47)
                .addGroup(newOpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(saveActBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateActBtn)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        updateActBtn.setVisible(false);

        popUpParentPanel.add(newOpPanel, "card2");

        newClientPanel.setName("clientP"); // NOI18N

        jLabel3.setText("Nouveau patient");
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setText("nom");

        clientNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientNameFieldKeyReleased(evt);
            }
        });

        jLabel8.setText("prenom");

        clientFNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientFNameFieldKeyReleased(evt);
            }
        });

        jLabel9.setText("numero");

        jLabel10.setText("adresse");

        jLabel11.setText("date de naissance :");

        jLabel12.setText("remarques :");

        clientTextArea.setColumns(20);
        clientTextArea.setRows(5);
        clientTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(clientTextArea);

        viderBtn.setText("vider");
        viderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viderBtnActionPerformed(evt);
            }
        });

        sauvegarderBtn.setText("sauvegarder");
        sauvegarderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sauvegarderBtnActionPerformed(evt);
            }
        });

        clientBD.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clientBDPropertyChange(evt);
            }
        });

        ageField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ageFieldKeyReleased(evt);
            }
        });

        jLabel15.setText("age");

        checkNom.setText("*");
        checkNom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkNom.setForeground(new java.awt.Color(255, 51, 51));

        checkPrenom.setText("*");
        checkPrenom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkPrenom.setForeground(new java.awt.Color(255, 51, 51));

        checkAge.setText("*");
        checkAge.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAge.setForeground(new java.awt.Color(255, 51, 51));

        modClientBtn.setText("mettre a jour");
        modClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modClientBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout newClientPanelLayout = new javax.swing.GroupLayout(newClientPanel);
        newClientPanel.setLayout(newClientPanelLayout);
        newClientPanelLayout.setHorizontalGroup(
            newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newClientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(newClientPanelLayout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newClientPanelLayout.createSequentialGroup()
                            .addComponent(viderBtn)
                            .addGap(88, 88, 88)
                            .addComponent(sauvegarderBtn))
                        .addGroup(newClientPanelLayout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(33, 33, 33)
                            .addComponent(clientBD, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newClientPanelLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(clientAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newClientPanelLayout.createSequentialGroup()
                            .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(clientNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                .addComponent(clientFNameField)
                                .addComponent(clientPhoneField)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(modClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkNom, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAge, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        newClientPanelLayout.setVerticalGroup(
            newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newClientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(clientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkNom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(clientFNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPrenom))
                .addGap(11, 11, 11)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(clientPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(clientAdressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(clientBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(checkAge))
                .addGap(2, 2, 2)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newClientPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newClientPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(modClientBtn)
                .addGap(18, 18, 18)
                .addGroup(newClientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viderBtn)
                    .addComponent(sauvegarderBtn))
                .addGap(35, 35, 35))
        );

        modClientBtn.setVisible(false) ;

        popUpParentPanel.add(newClientPanel, "card3");

        ordonnancePanel.setBackground(new java.awt.Color(255, 255, 255));
        ordonnancePanel.setName("facture    "); // NOI18N

        jLabel16.setText("ordonnance");
        jLabel16.setFont(new java.awt.Font("Tempus Sans ITC", 0, 24)); // NOI18N

        ordonnanceSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ordonnanceSearchKeyReleased(evt);
            }
        });

        jLabel17.setText("medicament");

        jLabel21.setText("medicament");
        jLabel21.setBackground(new java.awt.Color(51, 255, 51));

        jLabel22.setText("dose");

        jScrollPane3.setHorizontalScrollBar(null);

        medsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        medsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                medsListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(medsList);

        med1.setText("med1");

        jButton6.setText("imprimer");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("sauvegarder");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ordonnancePanelLayout = new javax.swing.GroupLayout(ordonnancePanel);
        ordonnancePanel.setLayout(ordonnancePanelLayout);
        ordonnancePanelLayout.setHorizontalGroup(
            ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ordonnancePanelLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ordonnancePanelLayout.createSequentialGroup()
                .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ordonnancePanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addGroup(ordonnancePanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(ordonnanceSearch)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ordonnancePanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ordonnancePanelLayout.createSequentialGroup()
                                .addComponent(med1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(ordonnancePanelLayout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                                .addComponent(jLabel22)))))
                .addGap(63, 63, 63))
        );
        ordonnancePanelLayout.setVerticalGroup(
            ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ordonnancePanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel16)
                .addGap(45, 45, 45)
                .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ordonnanceSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(med1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addGroup(ordonnancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(17, 17, 17))
        );

        popUpParentPanel.add(ordonnancePanel, "card5");

        newFacturePanel.setName("factureP"); // NOI18N

        jLabel19.setText("                                       nouvelle facture");
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel20.setText("designation");

        operationNameField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationNameField1ActionPerformed(evt);
            }
        });
        operationNameField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                operationNameField1KeyReleased(evt);
            }
        });

        jLabel23.setText("montant déposée");

        operationPriceSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                operationPriceSpinner1StateChanged(evt);
            }
        });
        operationPriceSpinner1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                operationPriceSpinner1FocusGained(evt);
            }
        });

        saveActBtn1.setText("sauvegarder");
        saveActBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActBtn1ActionPerformed(evt);
            }
        });

        jLabel24.setText("description");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        operationDescription1.setColumns(20);
        operationDescription1.setLineWrap(true);
        operationDescription1.setRows(5);
        jScrollPane4.setViewportView(operationDescription1);

        updateActBtn1.setText("mettre a jour");
        updateActBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateActBtn1MouseClicked(evt);
            }
        });

        checkAct1.setText("*");
        checkAct1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAct1.setForeground(new java.awt.Color(255, 0, 51));

        checkPrice1.setText("*");
        checkPrice1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkPrice1.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout newFacturePanelLayout = new javax.swing.GroupLayout(newFacturePanel);
        newFacturePanel.setLayout(newFacturePanelLayout);
        newFacturePanelLayout.setHorizontalGroup(
            newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newFacturePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newFacturePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(newFacturePanelLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(updateActBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(saveActBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newFacturePanelLayout.createSequentialGroup()
                        .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newFacturePanelLayout.createSequentialGroup()
                                .addComponent(operationPriceSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(newFacturePanelLayout.createSequentialGroup()
                                .addComponent(operationNameField1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(checkAct1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(64, 64, 64))
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        newFacturePanelLayout.setVerticalGroup(
            newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newFacturePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(operationNameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAct1)
                    .addComponent(jLabel20))
                .addGap(31, 31, 31)
                .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(operationPriceSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkPrice1))
                .addGroup(newFacturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newFacturePanelLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newFacturePanelLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel24)))
                .addGap(52, 52, 52)
                .addComponent(saveActBtn1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateActBtn1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        updateActBtn1.setVisible(false);

        popUpParentPanel.add(newFacturePanel, "card2");

        jLabel25.setText("mode saisi multiple");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(popUpParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(multipleInsertBox, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(multipleInsertBox, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(popUpParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminSaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminSaveBtnActionPerformed
        if(usernameField.getText().length() <4){
            JOptionPane.showMessageDialog(rootPane, "le nom d'utilisateur doit contenir au minimum 4 lettres");
        }
        else if (passwordField.getText().length() < 4) {
            JOptionPane.showMessageDialog(rootPane, "le mot de pass doit contenir au minimum 4 lettres");
        } else {
            try {
                int adminsRight = 0, actesRight = 0, patientsright = 0, visitsright = 0, voirapercusright = 0;
                
                if (gestionAdminsRight.isSelected()) {
                    adminsRight = 1;
                }
                if (gestionActesRight.isSelected()) {
                    actesRight = 1;
                }
                if (gestionPatientsRight.isSelected()) {
                    patientsright = 1;
                }
                if (visitsRight.isSelected()) {
                    visitsright = 1;
                }
                if (voirApercusRight.isSelected()) {
                    voirapercusright = 1;
                }
                
                UQM.insertAdmin(dBConnection, usernameField.getText(), passwordField.getText(), adminsRight, actesRight, patientsright, visitsright, voirapercusright);
            
            } catch (SQLException ex) {
                Logger.getLogger(PopUp.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if(!multipleInsertBox.isSelected()){
            dispose();
        }

        
        
    }//GEN-LAST:event_adminSaveBtnActionPerformed

    private void operationNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operationNameFieldActionPerformed

    private void sauvegarderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sauvegarderBtnActionPerformed
        boolean ffb = UQM.insertClient(dBConnection, clientNameField.getText(), clientFNameField.getText() ,clientPhoneField.getText(),clientAdressField.getText() , clientBD.getDate() , clientTextArea.getText());
        
        clientNameField.setText("") ;
        clientFNameField.setText("") ;
        clientPhoneField.setText("") ;
        clientAdressField.setText("") ;
        ageField.setText("");
        clientBD.clear(); 
        
        if(!multipleInsertBox.isSelected() && ffb){
            dispose();
        }
        

    }//GEN-LAST:event_sauvegarderBtnActionPerformed

    private void saveActBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActBtnActionPerformed
        boolean ffb = UQM.insertOperation(dBConnection,  operationNameField.getText() , operationPriceSpinner.getValue().toString() , operationDescription.getText());
        operationPriceSpinner.setValue(0);
        operationNameField.setText("");
        if(!multipleInsertBox.isSelected() && ffb){
            dispose();
        }
    }//GEN-LAST:event_saveActBtnActionPerformed

    private void gestionAdminsRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionAdminsRightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gestionAdminsRightActionPerformed

    private void viderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viderBtnActionPerformed
        clientNameField.setText("");
        clientFNameField.setText("");
        clientPhoneField.setText("");
        clientAdressField.setText("");
        clientBD.setText("");
        clientTextArea.setText("");
        ageField.setText("");
    }//GEN-LAST:event_viderBtnActionPerformed

    private void ageFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ageFieldKeyReleased
        if(!"".equals(ageField.getText())){
            checkAge.setVisible(false);
        }else{
            checkAge.setVisible(true);
        }
        LocalDate localDate = LocalDate.now();
        
        int year = Year.now().getValue();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        
        int age = Integer.parseInt(ageField.getText());
        int realYear = year - age ;
        
        LocalDate date = LocalDate.of(realYear, month, day);
        clientBD.setDate(date);
    }//GEN-LAST:event_ageFieldKeyReleased

    private void clientBDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clientBDPropertyChange
        if(clientBD.getDate()!=null){
            int birthYear = clientBD.getDate().getYear();
            int currYear = LocalDate.now().getYear();
            int diff = currYear-birthYear ;
            ageField.setText(Integer.toString(diff));
        }
    }//GEN-LAST:event_clientBDPropertyChange

    private void ordonnanceSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ordonnanceSearchKeyReleased
        String[] searchResult = new String[3];
        try {
            searchResult=UQM.getMedsResults(dBConnection, ordonnanceSearch.getText());
            
        } catch (SQLException ex) {
            Logger.getLogger(PopUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // creating a model from scratch ..
            ListModel<String> model = new AbstractListModel<String>() {
                String[] items = {"wahed", "zouj 2", "tlata 3"};

                public int getSize() {
                    return items.length;
                }

                public String getElementAt(int index) {
                    return items[index];
                }
            };
            
            // adding items to that model 
            DefaultListModel<String> newModel = new DefaultListModel<>();
            
            for (int i = 0; i < searchResult.length; i++) {
                newModel.addElement(searchResult[i]);
            }
            medsList.setModel(newModel);
        } catch (Exception e) {
            System.err.println(e.toString());
            System.out.println("error in ordonnace...");
        }
        
        
    }//GEN-LAST:event_ordonnanceSearchKeyReleased

    private void medsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_medsListMouseClicked
        
        med1.setText(medsList.getSelectedValue());
    }//GEN-LAST:event_medsListMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void clientNameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientNameFieldKeyReleased
        if(!"".equals(clientNameField.getText())){
            checkNom.setVisible(false);
        }else{
            checkNom.setVisible(true);
        }
    }//GEN-LAST:event_clientNameFieldKeyReleased

    private void clientFNameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientFNameFieldKeyReleased
        if(!"".equals(clientFNameField.getText())){
            checkPrenom.setVisible(false);
        }else{
            checkPrenom.setVisible(true);
        }
    }//GEN-LAST:event_clientFNameFieldKeyReleased

    private void modClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modClientBtnActionPerformed
        boolean ffb = UQM.modifyClient(dBConnection , modClient.id , clientNameField.getText() , clientFNameField.getText() , clientPhoneField.getText() , clientAdressField.getText() , clientBD.getDate() , clientTextArea.getText());
        if(ffb){
            dispose();
        }
    }//GEN-LAST:event_modClientBtnActionPerformed

    private void updateActBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateActBtnMouseClicked
        boolean ffb = UQM.modifyAct(dBConnection, modAct.id, operationNameField.getText(), (int) operationPriceSpinner.getValue(), operationDescription.getText()); // TODO add your handling code here:
        if(ffb){
            dispose();
        }
    }//GEN-LAST:event_updateActBtnMouseClicked

    private void adminMajBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminMajBtnMouseClicked
       
        if (usernameField.getText().length() < 4) {
            JOptionPane.showMessageDialog(rootPane, "le nom d'utilisateur doit contenir au minimum 4 lettres");
        } else if (passwordField.getText().length() < 4) {
            JOptionPane.showMessageDialog(rootPane, "le mot de pass doit contenir au minimum 4 lettres");
        } else {

            try {
                int adminsRight = 0, actesRight = 0, patientsright = 0, visitsright = 0, voirapercusright = 0;

                if (gestionAdminsRight.isSelected()) {
                    adminsRight = 1;
                }
                if (gestionActesRight.isSelected()) {
                    actesRight = 1;
                }
                if (gestionPatientsRight.isSelected()) {
                    patientsright = 1;
                }
                if (visitsRight.isSelected()) {
                    visitsright = 1;
                }
                if (voirApercusRight.isSelected()) {
                    voirapercusright = 1;
                }

                UQM.modifyAdmin(dBConnection, modAdmin.id, usernameField.getText() , passwordField.getText() , adminsRight, actesRight, patientsright, visitsright, voirapercusright);
                
                dispose();
            } catch (SQLException ex) {
                Logger.getLogger(PopUp.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }
    }//GEN-LAST:event_adminMajBtnMouseClicked

    private void visitsRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visitsRightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_visitsRightActionPerformed

    private void adminDiscardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDiscardBtnActionPerformed
        usernameField.setText("");
        passwordField.setText("");
    }//GEN-LAST:event_adminDiscardBtnActionPerformed

    private void usernameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameFieldKeyReleased
        if (!"".equals(usernameField.getText())) {
            checkUser.setVisible(false);
        } else {
            checkUser.setVisible(true);
        } 
        
    }//GEN-LAST:event_usernameFieldKeyReleased

    private void passwordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyReleased
        if(!"".equals(passwordField.getText())){
            checkPass.setVisible(false);
        }else{
            checkPass.setVisible(true);
        }
    }//GEN-LAST:event_passwordFieldKeyReleased

    private void operationNameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_operationNameFieldKeyReleased
        if(!"".equals(operationNameField.getText())){
            checkAct.setVisible(false);
        }else{
            checkAct.setVisible(true);
        }
    }//GEN-LAST:event_operationNameFieldKeyReleased

    private void operationPriceSpinnerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_operationPriceSpinnerFocusGained
        checkPrice.setVisible(false);
    }//GEN-LAST:event_operationPriceSpinnerFocusGained

    private void operationPriceSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_operationPriceSpinnerStateChanged
        checkPrice.setVisible(false);
    }//GEN-LAST:event_operationPriceSpinnerStateChanged

    private void operationNameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationNameField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operationNameField1ActionPerformed

    private void operationNameField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_operationNameField1KeyReleased
        if(!"".equals(operationNameField1.getText())){
            checkAct1.setVisible(false);
        }else{
            checkAct1.setVisible(true);
        }
    }//GEN-LAST:event_operationNameField1KeyReleased

    private void operationPriceSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_operationPriceSpinner1StateChanged
        checkPrice1.setVisible(false);
    }//GEN-LAST:event_operationPriceSpinner1StateChanged

    private void operationPriceSpinner1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_operationPriceSpinner1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_operationPriceSpinner1FocusGained

    private void saveActBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActBtn1ActionPerformed
        boolean ffb = UQM.insertFacture(dBConnection,  operationNameField1.getText() , Integer.parseInt(operationPriceSpinner1.getValue().toString()) , operationDescription1.getText() , loggedAdmin.id );
        operationNameField1.setText("");
        if(!multipleInsertBox.isSelected() && ffb){
            dispose();
        }
    }//GEN-LAST:event_saveActBtn1ActionPerformed

    private void updateActBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateActBtn1MouseClicked
        boolean ffb = UQM.modifyFacture(dBConnection, modFacture.id, operationNameField1.getText(), (int) operationPriceSpinner1.getValue(), operationDescription1.getText()); 
        if(ffb){
            dispose();
        }
    }//GEN-LAST:event_updateActBtn1MouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        String Active_card = null ;
        
        
        
        if(newOpPanel.isVisible()){
            Active_card = newOpPanel.getName() ;
            
        }else if(newClientPanel.isVisible()){
            Active_card = newClientPanel.getName() ;
            
        }else if(newFacturePanel.isVisible()){
            Active_card = newFacturePanel.getName() ;
            
        }else if(newAdminPanel.isVisible()){
            Active_card = newAdminPanel.getName() ;
        }
        
        
        if(listener!=null){
            listener.onPopupClosed(Active_card);
        }
        
        
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PopUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminDiscardBtn;
    private javax.swing.JButton adminMajBtn;
    private javax.swing.JButton adminSaveBtn;
    private javax.swing.JTextField ageField;
    private javax.swing.JLabel checkAct;
    private javax.swing.JLabel checkAct1;
    private javax.swing.JLabel checkAge;
    private javax.swing.JLabel checkNom;
    private javax.swing.JLabel checkPass;
    private javax.swing.JLabel checkPrenom;
    private javax.swing.JLabel checkPrice;
    private javax.swing.JLabel checkPrice1;
    private javax.swing.JLabel checkUser;
    private javax.swing.JTextField clientAdressField;
    private com.github.lgooddatepicker.components.DatePicker clientBD;
    private javax.swing.JTextField clientFNameField;
    private javax.swing.JTextField clientNameField;
    private javax.swing.JTextField clientPhoneField;
    private javax.swing.JTextArea clientTextArea;
    private javax.swing.JRadioButton gestionActesRight;
    private javax.swing.JRadioButton gestionAdminsRight;
    private javax.swing.JRadioButton gestionPatientsRight;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel med1;
    private javax.swing.JList<String> medsList;
    private javax.swing.JButton modClientBtn;
    private javax.swing.JCheckBox multipleInsertBox;
    private javax.swing.JPanel newAdminPanel;
    private javax.swing.JPanel newClientPanel;
    private javax.swing.JPanel newFacturePanel;
    private javax.swing.JPanel newOpPanel;
    private javax.swing.JTextArea operationDescription;
    private javax.swing.JTextArea operationDescription1;
    private javax.swing.JTextField operationNameField;
    private javax.swing.JTextField operationNameField1;
    private javax.swing.JSpinner operationPriceSpinner;
    private javax.swing.JSpinner operationPriceSpinner1;
    private javax.swing.JPanel ordonnancePanel;
    private javax.swing.JTextField ordonnanceSearch;
    private javax.swing.JTextField passwordField;
    private javax.swing.JPanel popUpParentPanel;
    private javax.swing.JButton sauvegarderBtn;
    private javax.swing.JButton saveActBtn;
    private javax.swing.JButton saveActBtn1;
    private javax.swing.JButton updateActBtn;
    private javax.swing.JButton updateActBtn1;
    private javax.swing.JTextField usernameField;
    private javax.swing.JButton viderBtn;
    private javax.swing.JRadioButton visitsRight;
    private javax.swing.JRadioButton voirApercusRight;
    // End of variables declaration//GEN-END:variables
}
