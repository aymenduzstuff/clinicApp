
package clinicd;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.Timer;

/**
 *
 * @author aymen
 */
public class UserQueryManager {
    //is client in database 
    
    //
    public boolean isValidUser(DBconnection dBConnection, String name, String password) throws SQLException {

        boolean isValidUser = false;
        try (Connection con = dBConnection.getConnection()) {

            try (PreparedStatement stmt = con.prepareStatement("select count(*) from UserATable where name=? and password=?")) {

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {

                        int count = rs.getInt(1);
                        isValidUser = count > 0; // Maybe count == 1 would be more valid

                    }

                }

            }

        }

        return isValidUser;

    }
    
    public boolean checkPassword(DBconnection dBConnection, String name, String password) throws SQLException{
        Connection con = dBConnection.getConnection() ;
        try {
                String mysql_query = "SELECT password FROM clinicdatabase.admins WHERE username ='"+name+"';";
                
                PreparedStatement pst = con.prepareStatement(mysql_query);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    System.out.println("returned something");
                }else{
                    
                    System.out.println("returned nothing");
                }
                String correctPass;
                correctPass = rs.getString("password");
                System.out.println("the password retruned : "+correctPass);
                
                if(password.equals(correctPass)){
                    System.out.println("password correct");
                    pst.close();
                    return true ;
                    
                }else{
                    System.out.println("password or username incorrect");
                    
                    pst.close();
                    return false;
                }
                
            } catch (SQLException e) {
                System.out.println("error when checking password");
                System.err.println(e.getMessage());
            }
            return false;
        
    }
    public String getUsersCombo(DBconnection dBConnection) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String result = "";
        String sql = "SELECT * FROM admins ORDER BY ID  LIMIT 1";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                result += rs.getString("username");
                
            }
            return result ;
           
            
        }catch(SQLException ex){
            System.out.println("error when");
            System.out.println(ex);
        }

        return "no result" ;
}

    void insertAdmin(DBconnection dBConnection, String username, String password, int adminsRight, int actsRight, int patientsright, int visitsright, int voirapercusright) throws SQLException {
        
        Connection con =  dBConnection.getConnection() ;
        
                    
        
        String sqlQuery = "SELECT COUNT(*) as adminCount FROM admins WHERE gestionadminsright = 1 ";

            // Create a statement to execute the query
        Statement statement = con.createStatement();

            // Execute the query and get the result
        ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Retrieve the admin count from the result set
        int nbrAdminPrev = 0;
            if (resultSet.next()) {
                nbrAdminPrev = resultSet.getInt("adminCount");
            }        
        
        System.out.println("nombre des admins " + nbrAdminPrev);
        if(nbrAdminPrev == 1 && adminsRight != 1){
            String msg = "il devrait y avoir au moins un administrateur avec le privilège de gestion des admins  , cochez la case gestion admins" ;
            sqlAlert(1 , msg , 7);
        }else {
            String sql = "insert into admins(username , password ,gestionadminsright , gestionactsright , gestionpatientsright , gestionvisitsright , voirapercuright ) values(?,? , ? ,? , ? , ?, ?)";
            PreparedStatement ptst = con.prepareStatement(sql);

            ptst.setString(1, username);
            ptst.setString(2, password);
            ptst.setInt(3, adminsRight);
            ptst.setInt(4, actsRight);
            ptst.setInt(5, patientsright);
            ptst.setInt(6, visitsright);
            ptst.setInt(7, voirapercusright);

            ptst.executeUpdate();
            sqlAlert(0 , "nouveau admin ajoute" , 3);
        }
        
        con.close();
    }

    boolean insertClient(DBconnection dBConnection,  String nom, String prenom, String adress, String phone, LocalDate date , String note)  {
        
        
        try {
            if (date == null) {
                throw new SQLException() ;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = date.format(formatter) ;
            
            if(nom.equals("")){
                nom = null ;
            }
            if(prenom.equals("")){
                prenom = null ;
            }
            
            Connection con =  dBConnection.getConnection() ;
            String sql = "INSERT INTO clinicdatabase.clients ( nom, prenom, adresse, num_tel, date_naiss , notes , registration_date) VALUES (?, ?, ?, ?, ? , ? , CURRENT_DATE()) " ;
            PreparedStatement ptst = con.prepareStatement(sql);
            ptst.setString(1 , nom);
            ptst.setString(2 , prenom);
            ptst.setString(3 , phone);
            ptst.setString(4 , adress);
            ptst.setString(5 , dateString);
            ptst.setString(6 , note);
            
            ptst.executeUpdate();
            
            //sqlAlert(0 , "patient ajoute " ,1 );
            
            con.close();
            return true ;
        } catch (SQLException ex) {
            sqlAlert(1 , "\tpatient non ajoutée , \n assurez-vous d'avoir bien inséré les informations" , 6);
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            return false ;
        }
    }
    
void deleteClient(DBconnection dBConnection ,int id) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "DELETE FROM clients WHERE id = "+id+""; 
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.executeUpdate();
        con.close();
        
        sqlAlert(0 , "patient supprime " , 3);
    }

void deleteAdmin(DBconnection dBConnection ,int id) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "DELETE FROM admins WHERE id = "+id+""; 
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.executeUpdate();
        sqlAlert(0, "administrateur supprimé " , 3);
        con.close();
        
    }
    void insertVisit(DBconnection dbc, String time, String client_id, String operation_id, String admin_id, String prix, String termine, int[] dents , String note , String coutFinal ) throws SQLException {
        
        
        
        
        Connection con =  dbc.getConnection() ;

        String sql = "INSERT INTO clinicdatabase.consultations ( client_id  , termine , cout) VALUES (?, ?, ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
        
        ptst.setString(1 ,client_id );
        ptst.setString(2 ,termine );
        ptst.setString(3 , coutFinal);
        
            
        
        int affectedRows = ptst.executeUpdate();
        int lastInsertID = 0 ;
        if (affectedRows > 0) {
                ResultSet generatedKeys = ptst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    lastInsertID = generatedKeys.getInt(1);
                    
                }
        }
        System.out.println("inserted consultation");
        
        String sql2 = "INSERT INTO clinicdatabase.seances ( id_consultation , nbr_seance , temps , admin_id , montant , notes , acte_dentaire) VALUES (?, 1, ?,  ?, ? , ?, ?)" ;
        PreparedStatement ptst2 = con.prepareStatement(sql2);
        
        if(note != null){
            if( note.replaceAll("\\s", "").length() < 1 ){
            note = null ;
            }
        }
        ptst2.setString(1 , String.valueOf(lastInsertID));
        ptst2.setString(2 ,time );
        ptst2.setString(3 ,admin_id );
        ptst2.setString(4 ,prix );
        ptst2.setString(5 ,note );
        ptst2.setString(6 ,operation_id);
        
        System.out.println("inserted seances ");
        
        ptst2.executeUpdate();
        
        
        
        
        for(int i = 0 ; i < dents.length ; i++){
            String sql3 = "insert into dents_for_consultation values(? , ?) ;" ;
            PreparedStatement ptst3 = con.prepareStatement(sql3);
            System.out.println("dent a ajouter : " + dents[i]);
            ptst3.setInt(1, lastInsertID);
            ptst3.setInt(2 , dents[i]);
            ptst3.executeUpdate();
        }
        System.out.println("insert dents ");
        
        
        System.out.println("new visit added !");
        
        con.close();
    
    }
    void insertSeance(DBconnection dbc, String visit_id , int seance_nbr , String time , String admin_id, String prix , String note ,String act_id) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        
        if(note != null){
            if( note.replaceAll("\\s", "").length() < 1 ){
            note = null ;
            }
        }
                                                 
        String sql = "INSERT INTO clinicdatabase.seances (id_consultation ,nbr_seance, temps , admin_id , montant , notes , acte_dentaire) VALUES (?,?,?, ?, ?, ? , ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql);
        
        
        ptst.setString(1 ,visit_id );
        ptst.setInt(2 , seance_nbr + 1 );
        ptst.setString(3 ,time );
        ptst.setString(4 ,admin_id );
        ptst.setString(5 , prix);
        ptst.setString(6 , note);
        ptst.setString(7 , act_id);
        
        
        
        ptst.executeUpdate();
        
        System.out.println(" seance added !");
        
        con.close();
    
    }

    boolean insertOperation(DBconnection dBConnection,  String name, String price , String desc)  {
        
        try {
            if(name.equals("")){
                name = null ;
            }
            Connection con =  dBConnection.getConnection() ;
            String sql = "INSERT INTO clinicdatabase.acts_dentaire(nom_act, cout, description) values(?,?,?)";
            PreparedStatement ptst = con.prepareStatement(sql);
            ptst.setString(1, name);
            ptst.setString(2, price);
            ptst.setString(3, desc);
            
            
            ptst.executeUpdate();
            
            sqlAlert(0 , "nouvelle acte dentaire ajoutée" , 2);
            con.close();
            return true ;
        } catch (SQLException ex) {
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            sqlAlert(1 , "acte dentaire non ajoutée ,  assurez-vous d'avoir bien inséré les informations"  , 4);
            return false ;
        }
    }


    String[] getClientsResults(DBconnection dbc , String string) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT * FROM clients WHERE nom LIKE '%"+string+"%' OR prenom LIKE '%"+string+"%' order by id desc ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("id")+":::"+rs.getString("nom")+":::"+rs.getString("prenom");
                    i++;
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM searchUsersCatalogueResult...");
            }
        return result;
    }
    String[] getFacturesResults(DBconnection dbc , String string , int type) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = null ;
        if(type == 1 ){
            mysql_query = "SELECT idfacture , designation , date_creation , depense , ifnull(descri , ' ') as descr , username FROM clinicdatabase.factures join admins on admins.id = factures.admin_id where designation like'%"+string+"%' and MONTH(date_creation) = MONTH(CURRENT_DATE()) AND YEAR(date_creation) = YEAR(CURRENT_DATE()) order by date_creation desc" ;
            
        }else{
            mysql_query = "SELECT idfacture , designation , date_creation , depense , ifnull(descri , ' ') as descr, username FROM clinicdatabase.factures join admins on admins.id = factures.admin_id where designation LIKE '%"+string+"%' order by date_creation desc";
            
        }
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("idfacture")+":::"+rs.getString("designation")+":::"+rs.getString("date_creation")+":::"+rs.getString("depense")+":::"+rs.getString("descr")+":::"+rs.getString("username");
                    i++;
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM get factures results...");
            }
        return result;
    }
    
    String[] getApercusResults(DBconnection dbc) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = new String[7];
        String client_nbr = "SELECT COUNT(id) AS result FROM clients ";
        String operation_nbr = "SELECT count(distinct id_consultation) AS result FROM clinicdatabase.seances ";
        
        String clients_this_month = "SELECT COUNT(*) AS result FROM clients WHERE MONTH(registration_date) = MONTH(CURRENT_DATE()) AND YEAR(registration_date) = YEAR(CURRENT_DATE()) ";
        String total_operations_this_month = "SELECT count(distinct id_consultation) AS result FROM clinicdatabase.seances WHERE MONTH(temps) = MONTH(CURRENT_DATE())  AND YEAR(temps) = YEAR(CURRENT_DATE()) ";
        String termine_operations_this_month = "SELECT count(distinct id_consultation) AS result FROM clinicdatabase.seances join consultations on consultations.id = seances.id_consultation WHERE MONTH(temps) = MONTH(CURRENT_DATE())  AND YEAR(temps) = YEAR(CURRENT_DATE()) AND termine = 1";
        
        String gains = "select sum(cons_sum) AS result from ( select sum(montant) as cons_sum FROM clinicdatabase.seances WHERE MONTH(temps) = MONTH(CURRENT_DATE())  AND YEAR(temps) = YEAR(CURRENT_DATE()) group by  id_consultation) as subquery  ";
        String client_nbr_this_month = "SELECT  count(distinct client_id) AS result  FROM clinicdatabase.consultations join seances on consultations.id = seances.id_consultation WHERE MONTH(temps) = MONTH(CURRENT_DATE())  AND YEAR(temps) = YEAR(CURRENT_DATE())";
        
        String[] query_array = {client_nbr , operation_nbr , clients_this_month , total_operations_this_month , termine_operations_this_month , gains , client_nbr_this_month} ;
        try {
                
                int i = 0 ; 
                Statement st = con.createStatement();
                for(String query : query_array){
                    
                    ResultSet rs = st.executeQuery(query);
                    
                    if(rs.next()){
                        if(rs.getString("result") == null){
                            result[i] = "0" ;
                        }else{
                            result[i] = rs.getString("result") ;
                        }
                        
                    }else{
                        result[i] = "0" ;
                    
                    }
                    i++ ;
                    
                }
                
                
                 
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM get apercus results ...");
            }
        return result;
    }
    
    int getDepenseMois(DBconnection dbc) throws SQLException{
        Connection con =  dbc.getConnection() ;
        int sum = 0 ;
        String sqlQuery = "SELECT sum(depense) as somme FROM clinicdatabase.factures where  MONTH(date_creation) = MONTH(CURRENT_DATE()) AND YEAR(date_creation) = YEAR(CURRENT_DATE()) " ;
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sqlQuery);
                
                while(rs.next()) {
                    sum = rs.getInt("somme") ;
                }  
                st.close();
            } catch (SQLException e) {
                e.getNextException();
                System.out.println("error in UQM get depense mois ");
            }
        return sum ;
    }
    
    String[] getMedsResults(DBconnection dbc , String string) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT * FROM meds WHERE  designation like '%"+string+"%' ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("designation");
                    i++;
                }  
                st.close();
            } catch (SQLException e) {
                e.getNextException();
                System.out.println("error in UQM get meds result");
            }
        return result;
    }
    String[] getactsResults(DBconnection dbc , String string) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT id , nom_act , cout , IFNULL(description, '---') as descr FROM clinicdatabase.acts_dentaire WHERE nom_act LIKE '%"+string+"%' AND nom_act NOT LIKE '%acte suppri%'  ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("id")+":::"+rs.getString("nom_act")+":::"+rs.getString("cout")+":::"+rs.getString("descr")+"-";
                    i++;
                    System.out.println(Arrays.toString(result));
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM getacts results...");
            }
        return result;
    }
    void updateCombo(JComboBox combo, DBconnection dBConnection ) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "";
        String col = "" ;
        
        
        combo.setModel(new DefaultComboBoxModel<>());
        
        if ("usersComboBox".equals(combo.getName())) {

            sql = "select * from admins ";
            col = "username";
        } else if ("operationsComboBox".equals(combo.getName())) {
            sql = "select * from acts_dentaire where nom_act not LIKE '%acte sup%' order by id desc ";
            col = "nom_act";
        } else if ("teethComboBox".equals(combo.getName())) {
            for (int j = 0; j < 32; j++) {
                combo.addItem(j);
            }
        }
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                combo.addItem(rs.getString(col));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        con.close();
    }

    String[] getVisitsResults(DBconnection dbc, String code , int queryIndex) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String newDbQuery = null ;
        String newquery = "select visit_id, max(seance_nbr) as dern_seance, max(time) as dern_date, client_id,  nom_operation , admin_id , sum(prix) as total, tooth_name, max(terminee) as statu  from visits  inner join operations on operations.id_operation = visits.operation_id join tooth on tooth.tooth_id = visits.tooth where client_id='"+code+"' group by visit_id " ;
        if(queryIndex == 1){
            newDbQuery = "SELECT consultations.id as id , DATE_FORMAT(temps,  ' %W , %Y/%m/%d %H:%i ') as temps  , nom_act ,  IFNULL( t.dents , 'non spécifiées') AS  dents , montant , total , consultations.cout as cout , termine FROM   consultations \n" +
"join seances on consultations.id = seances.id_consultation  \n" +
" left join(\n" +
"                     SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n" +
"                   ) as t on t.id_consultation = consultations.id\n" +
"JOIN (\n" +
"								SELECT id_consultation, sum(montant) as total\n" +
"								FROM seances\n" +
"								GROUP BY id_consultation\n" +
"							) t2 ON seances.id_consultation = t2.id_consultation\n" +
"join acts_dentaire on acts_dentaire.id = seances.acte_dentaire\n" +
"                            \n" +
"where client_id = '"+code+"' order by id desc" ;

        }else if(queryIndex == 2){
            newDbQuery = "select consultations.id as id , max(seances.nbr_seance) as dern_seance , nom_act , total , max(seances.temps) as last_date , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%i ') as dern_date  , termine ,  IFNULL(GROUP_CONCAT(distinct t.dents), 'non spécifiées') AS dents , consultations.cout   from consultations \n" +
"                    join seances on consultations.id = seances.id_consultation  \n" +
"                     join acts_dentaire on acts_dentaire.id = seances.acte_dentaire\n" +
"                     JOIN (\n" +
"								SELECT id_consultation, MAX(temps) AS latest_seance_date, sum(montant) as total\n" +
"								FROM seances\n" +
"								GROUP BY id_consultation\n" +
"							) t2 ON seances.id_consultation = t2.id_consultation AND seances.temps = t2.latest_seance_date\n" +
"                     left join(\n" +
"                      SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n" +
"                    ) as t on t.id_consultation = consultations.id\n" +
"                     where client_id='"+code+"' group by consultations.id ORDER BY last_date desc ";

        }
       
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(newDbQuery);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(newDbQuery);
                
                int numberOfUsers = 0;
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                System.out.println("there are this many rows : "+ k);
                while(rs.next() && i<k) {
                    numberOfUsers++;
                    //result[i] = rs.getString("visit_id")+":::"+rs.getString("time")+":::"+rs.getString("Operation_id")+":::"+rs.getString("tooth")+":::"+rs.getString("seance_nbr")+":::"+rs.getString("prix")+":::"+rs.getString("terminee");
                   
                    result[i] = rs.getString("id")+":::"+rs.getString("temps")+":::"+rs.getString("nom_act")+":::"+rs.getString("dents")+":::"+rs.getString("montant")+":::"+rs.getString("total")+":::"+rs.getString("termine")+":::"+rs.getString("cout")+":::"+rs.getString("cout");
                    
                    System.out.println("\n");
                    System.out.println("content : "+result[i]);
                    i++;
                    
                }  
                
                st.close();
            } catch (SQLException e) {
                System.out.println("error when retrieving visit data...");
                System.out.println(e.getMessage());
            }
        return result;
    }

    String[] getDetailedVisitsResults(DBconnection dbc, String nom , String prenom , String actDent  , String minPrice  ,String maxPrice , LocalDate minDate , LocalDate maxDate , int termine , int id_cons ) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String minDateString ;
        String maxDateString ;
        String Termine = "";
        String id_consString = "" ;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        if(termine == 1){
            Termine = "and termine = '1'";
        }else if(termine == 2){
            Termine = "and termine = '0'";
        }
        if(minDate == null || maxDate == null){
            minDateString = "'2000-01-01'";
            maxDateString = "CURDATE() + INTERVAL 1 DAY";
        }else{
            minDateString = "'"+minDate.format(formatter)+"'" ;
            maxDateString = "'"+maxDate.format(formatter)+"' + INTERVAL 1 DAY ";
        
        }
        if(id_cons == -1){
            id_consString = "" ;
        }else{
            id_consString = " and consultations.id='"+id_cons+"'" ;
        }
        
        
        
        
        
        
        String condTime = "seances.temps  BETWEEN "+minDateString+" AND "+maxDateString+"" ;
        String condNomPrenom = " nom LIKE '%"+nom+"%' and prenom LIKE '%"+prenom+"%' " ;
        String condAct =  "nom_act like '%"+actDent+"%'" ;
        String condPrice = "total between '"+minPrice+"' and '"+maxPrice+"' " ;
        
        //not implemnted yet 
        String condAdmin = "username like '%"+actDent+"%'" ;
        
        
        String newDbQuery1 = "select consultations.id , concat(nom , ' ' , prenom) as nomprenom ,  max(seances.nbr_seance) as dern_seance , nom_act , max(seances.temps) as last_date , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%i ') as dern_date  , termine , sum(montant) as total,  IFNULL(GROUP_CONCAT( distinct t.dents), 'non spécifiées') AS dents   from consultations \n"
                + " join seances on consultations.id = seances.id_consultation  \n"
                + " join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  \n"
                + " join clients on consultations.client_id = clients.id \n"
                + " left join(\n"
                + "  SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n"
                + ") as t on t.id_consultation = consultations.id\n"
                + " where  "+condNomPrenom+" and "+condAct+" and "+condTime+" "+Termine+" group by consultations.id having  "+condPrice+" ORDER BY last_date desc ";

        String newDbQuery = "select consultations.id , concat(nom , ' ' , prenom) as nomprenom ,  max(seances.nbr_seance) as dern_seance , nom_act , max(seances.temps) as last_date , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%i ') as dern_date  , termine , total ,  IFNULL(GROUP_CONCAT( distinct t.dents), 'non spécifiées') AS dents  , consultations.cout as finalcost  from consultations \n" +
"                    join seances on consultations.id = seances.id_consultation  \n" +
"                     join acts_dentaire on acts_dentaire.id = seances.acte_dentaire\n" +
                     " join clients on consultations.client_id = clients.id \n" +
"                     JOIN (\n" +
"								SELECT id_consultation, MAX(temps) AS latest_seance_date , sum(montant) as total\n" +
"								FROM seances\n" +
"								GROUP BY id_consultation\n" +
"							) t2 ON seances.id_consultation = t2.id_consultation AND seances.temps = t2.latest_seance_date\n" +
"                     left join(\n" +
"                      SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n" +
"                    ) as t on t.id_consultation = consultations.id\n" +
"                      where  "+condNomPrenom+" and "+condAct+" and "+condTime+" "+Termine+" "+id_consString+" group by consultations.id having  "+condPrice+" ORDER BY last_date desc " ;
                

        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(newDbQuery);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(newDbQuery);
                
                int numberOfUsers = 0;
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                while(rs.next() && i<k) {
                    numberOfUsers++;
                    //result[i] = rs.getString("visit_id")+":::"+rs.getString("time")+":::"+rs.getString("Operation_id")+":::"+rs.getString("tooth")+":::"+rs.getString("seance_nbr")+":::"+rs.getString("prix")+":::"+rs.getString("terminee");
                    
                    result[i] = rs.getString("id")+":::"+rs.getString("nomprenom")+":::"+rs.getString("dern_date")+":::"+rs.getString("nom_act")+":::"+rs.getString("dents")+":::"+rs.getString("dern_seance")+":::"+rs.getString("total")+":::"+rs.getString("termine")+":::"+rs.getString("finalcost");
                    
                    
                    i++;
                    
                }  
                
                st.close();
            } catch (SQLException e) {
                System.out.println("error when retrieving detailed visit data...");
                System.out.println(e.getMessage());
            }
        return result;
    }
    
String[] getAdminsResults(DBconnection dbc ) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT id , username , concat(gestionadminsright,'-',gestionactsright,'-',gestionpatientsright,'-',gestionvisitsright,'-',voirapercuright) as rights FROM clinicdatabase.admins where username not like '%admin%' ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("id")+":::"+rs.getString("username")+":::"+rs.getString("rights");
                    i++;
                    System.out.println(Arrays.toString(result));
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM admins results...");
            }
        return result;
    }


    int getOperationIdByName(DBconnection dbc, String operation) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String sql = "select id from acts_dentaire where nom_act='"+operation+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            System.out.println("returned from if");
            return rs.getInt(1);
        }
        
        
        con.close();
        System.out.println("returned from function body");
        return  rs.getInt("id_operation");
        
        }
    
     int getOperationCostByName(DBconnection dbc, String operation) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String sql = "select cout from acts_dentaire where nom_act='"+operation+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            System.out.println("returned from if");
            return Integer.parseInt(rs.getString(1));
        }
        
        
        con.close();
        System.out.println("returned from function body");
        return Integer.parseInt(rs.getString("id_operation"));
        }
    boolean notesFlag(DBconnection dbc, String code) throws SQLException{
        Connection con =  dbc.getConnection() ;
        Statement st = con.createStatement();
        String query = "SELECT COUNT(*) AS count_of_rows from seances join clinicdatabase.admins on seances.admin_id = admins.id WHERE id_consultation = '"+code+"' and notes IS NOT NULL " ;
        ResultSet rs = st.executeQuery(query);
        int count = 0 ;
        if(rs.next()){
            count = rs.getInt("count_of_rows") ;
            return count != 0 ;
        }
        return false ;
        
    }
     
    String[] getseancesResults(DBconnection dbc, String code) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "select nbr_seance , temps, concat('- ' , IFNULL(notes, '') , ' -' )as note , montant, nom_act , notes ,  username from seances \n" +
"join clinicdatabase.admins on seances.admin_id = admins.id  \n" +
"join clinicdatabase.acts_dentaire on acts_dentaire.id = seances.acte_dentaire\n" +
"where id_consultation='"+code+"' ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int numberOfUsers = 0;
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                System.out.println("there are this many rows : "+ k);
                while(rs.next() && i<k) {
                    numberOfUsers++;
                    //result[i] = rs.getString("visit_id")+":::"+rs.getString("time")+":::"+rs.getString("Operation_id")+":::"+rs.getString("tooth")+":::"+rs.getString("seance_nbr")+":::"+rs.getString("prix")+":::"+rs.getString("terminee");
                    result[i] = rs.getString("nbr_seance")+":::"+rs.getString("temps")+":::"+rs.getString("montant")+":::"+rs.getString("username")+":::"+rs.getString("note")+":::"+rs.getString("nom_act");
                    
                    System.out.println("\n");
                    System.out.println("content : "+result[i]);
                    i++;
                    
                }  
                
                st.close();
            } catch (SQLException e) {
                System.out.println("error when retrieving seances data...");
                System.out.println(e.getMessage());
            }
        return result;
    }

    void setVisitTermine(DBconnection dbc, String visit_id, Consultation currCons , int addedSum) throws SQLException {
    
        Connection con =  dbc.getConnection() ;
        String sql   ;
        System.out.println("p1");
        int compensation =  currCons.payed +addedSum  ;
        if(currCons.rest < 0){
            
            System.out.println("p2");
            
            sql = "UPDATE `clinicdatabase`.`consultations` SET `termine` = '1', `cout` = '"+ compensation +"' WHERE (`id` = "+visit_id+");" ;
            System.out.println("p3");
            
        }else {
            sql = "UPDATE consultations SET termine = 1 WHERE id = "+visit_id+" ";
        }
        
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.executeUpdate();
        sqlAlert(0 , "visite définie comme terminée" , 2 );
        con.close();
    }

    String[] getDetailedClientsResults(DBconnection dbc, String string) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT id , nom , prenom , adresse , num_tel , YEAR(curdate())-YEAR(date_naiss) as age FROM clients WHERE nom LIKE '%"+string+"%' OR prenom LIKE '%"+string+"%' ";
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(mysql_query);
                
                int k =0;
                int i = 0;
                while(rs2.next()){
                    k++;
                }
                st2.close();
                result = new String[k];
                
                while(rs.next() && i<k) {
                    result[i] = rs.getString("id")+":::"+rs.getString("nom")+":::"+rs.getString("prenom")+"::: "+rs.getString("adresse")+":::"+rs.getString("num_tel")+":::"+rs.getString("age");
                    System.out.println("\n");
                    System.out.println("content : "+result[i]);
                    i++;
                }  
                st.close();
            } catch (SQLException e) {
                System.out.println("error in UQM searchUsersCatalogueResult...");
            }
        return result;
    }
Client currentClient(DBconnection dbc, int id) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT id , nom , prenom , adresse , num_tel , DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), date_naiss )), '%Y') + 0 AS age  , date_naiss ,notes   FROM clients WHERE id="+id+" ";
        
        Client client = new Client() ;
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    client = new Client(rs.getInt("id"), rs.getString("nom") , rs.getString("prenom"), rs.getString("adresse"), rs.getString("num_tel"), rs.getInt("age"), LocalDate.parse(rs.getString("date_naiss")), rs.getString("notes")) ;
                    st.close();
                    System.out.println("client object successfully created ");
                    
                }else{
                    System.out.println("client object wasn't created");
                    //Client client = new Client(0, "" , "", "", "", 0, "") ;
                    client = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when craeting client object ");
                
            }
        return client;
    }
act currentAct(DBconnection dbc, int id) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT id , nom_act , cout , description  FROM acts_dentaire WHERE id="+id+" ";
        
        act thisAct = new act();
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    thisAct = new act(rs.getInt("id") , rs.getString("nom_act"), rs.getInt("cout") , rs.getString("description"));
                    st.close();
                    System.out.println("act object successfully created ");
                    
                }else{
                    System.out.println("act object wasn't created");
                    //Client client = new Client(0, "" , "", "", "", 0, "") ;
                    thisAct = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when craeting acte object ");
                
            }
        return thisAct ;
    }
Admin currentAdmin(DBconnection dbc, String username) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT id , username ,concat(gestionadminsright,'-',gestionactsright,'-',gestionpatientsright,'-',gestionvisitsright,'-',voirapercuright) as rights FROM clinicdatabase.admins where username='"+username+"' ";
        int[] rights = new int[5] ;
        String[] rightString = new String[5] ;
        Admin currAdmin = new Admin();
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    rightString = rs.getString("rights").split("-") ;
                    
                    for (int i = 0; i < 5; i++) {
                        rights[i] = Integer.parseInt(rightString[i]);
                    }
                    
                    currAdmin = new Admin(rs.getInt("id") , rs.getString("username"),rights );
                    st.close();
                    System.out.println("admin created : " + currAdmin.name);
                    
                }else{
                    System.out.println("admin wasnt created ");
                    currAdmin = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when creating admin object");
                
            }
        return currAdmin ;
    }

Admin currentAdminWpass(DBconnection dbc, String username) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT id , username ,concat(gestionadminsright,'-',gestionactsright,'-',gestionpatientsright,'-',gestionvisitsright,'-',voirapercuright) as rights , password FROM clinicdatabase.admins where username='"+username+"' ";
        int[] rights = new int[5] ;
        String[] rightString = new String[5] ;
        Admin currAdmin = new Admin();
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    rightString = rs.getString("rights").split("-") ;
                    
                    for (int i = 0; i < 5; i++) {
                        rights[i] = Integer.parseInt(rightString[i]);
                    }
                    
                    currAdmin = new Admin(rs.getInt("id") , rs.getString("username"),rights , rs.getString("password") );
                    st.close();
                    System.out.println("admin with pass created : " + currAdmin.name);
                    
                }else{
                    System.out.println("admin with pass wasnt created ");
                    currAdmin = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when creating admin object");
                
            }
        return currAdmin ;
    }

Seance currentSeance(DBconnection dbc, int id_cons , int id) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT  * FROM clinicdatabase.seances where id_consultation="+id_cons+" and nbr_seance = "+id+" ";
        
        Seance seance = new Seance(); 
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    seance = new Seance(rs.getInt("id_consultation") , rs.getInt("nbr_seance"), rs.getString("notes") , rs.getInt("montant"));
                    st.close();
                    System.out.println("seance object successfully created ");
                    
                }else{
                    System.out.println("seance object wasn't created");
                    //Client client = new Client(0, "" , "", "", "", 0, "") ;
                    seance = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when craeting seance object ");
                
            }
        return seance ;
    }
    void modifySeance(DBconnection dBConnection, int cons_id, int id, String notes, int montant, int operation_id) throws SQLException {
        
        String idString = String.valueOf(id) ;
                
        Connection con =  dBConnection.getConnection() ;
        String col = null ;
        
        if("".equals(notes.replaceAll("\\s", ""))){
            col = "notes = NULL " ;
        }else {
             col = "notes = '"+notes+"'" ;
        }
        
        String sqlstmt = "UPDATE clinicdatabase.seances SET "+col+", montant='"+montant+"' , acte_dentaire='"+operation_id+"'  WHERE (`id_consultation` ='"+cons_id+"') and (`nbr_seance` = '"+id+"')" ;
        
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("seance modified !");
        
        con.close();
        
        sqlAlert(0  , "seance modifie" , 3);
    }
    
    void deleteSeance(DBconnection dbc, int cons_id, int id) throws SQLException {
        
        String idString = String.valueOf(id) ;
                
        Connection con =  dbc.getConnection() ;
        String sqlstmt = "DELETE FROM `clinicdatabase`.`seances` WHERE (`id_consultation` ='"+cons_id+"') and (`nbr_seance` ='"+id+"')" ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("seance supprimé !");
        
        con.close();
        
        sqlAlert(0 ,"seance supprimé " , 3);
    }
    
        void deleteConsultation(DBconnection dbc, int cons_id) throws SQLException {
        
                
        Connection con =  dbc.getConnection() ;
        String sqlstmt = "DELETE FROM `clinicdatabase`.`consultations` WHERE `id` ='"+cons_id+"'" ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("consultation supprimé !");
        
        con.close();
        
        sqlAlert(0 , "operation supprimé !" , 3);
    }
Consultation currentConsultation(DBconnection dbc, int id_cons ) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT consultations.id , client_id , termine , consultations.cout as cout , nbrseances , total  , dents  FROM clinicdatabase.consultations\n" +
"JOIN (\n" +
"								SELECT id_consultation , sum(montant) as total , count(nbr_seance) as nbrseances \n" +
"								FROM seances \n" +
"								GROUP BY id_consultation\n" +
"							) t2 ON consultations.id = t2.id_consultation \n" +
"left join(\n" +
"	SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n" +
")as t on t.id_consultation = consultations.id\n" +
"where consultations.id = "+id_cons+"";
        
        Consultation cons = new Consultation(); 
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    System.err.print(1) ;
                    int rest = rs.getInt("cout") - rs.getInt("total") ;
                    System.err.print(2) ;

                    cons = new Consultation(rs.getInt("id") , rs.getInt("client_id") ,rs.getInt("termine") == 1  , rest , rs.getString("dents") , rs.getInt("cout") , rs.getInt("nbrseances") , rs.getInt("total"));
                    st.close();
                    System.out.println("consultation object successfully created ");
                    
                }else{
                    System.out.println("consultation object wasn't created");
                    //Client client = new Client(0, "" , "", "", "", 0, "") ;
                    cons = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when craeting consulatoin object ");
                
            }
        return cons ;
    }
boolean modifyClient(DBconnection dBConnection,int id ,  String nom, String prenom , String phone, String adress, LocalDate date , String note)  {
        
        try {
            if (date == null) {
                throw new SQLException();
            }
            
            if (nom.equals("")) {
                nom = null;
            }else{
                nom = "'"+nom+"'";
            }
            if (prenom.equals("")) {
                prenom = null;
            }else{
                prenom = "'"+prenom+"'";
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = date.format(formatter) ;
            String idString = String.valueOf(id) ;
            String noteSAFE = note.replace("'", "''");
            
            Connection con =  dBConnection.getConnection() ;
            String sqlstmt = "UPDATE clinicdatabase.clients SET nom="+nom+", prenom="+prenom+", adresse='"+adress+"' , num_tel='"+phone+"', date_naiss='"+dateString+"', notes='"+noteSAFE+"' WHERE id='"+id+"'" ;
            PreparedStatement ptst = con.prepareStatement(sqlstmt);
            
            ptst.executeUpdate();
            
            System.out.println("client modified !");
            
            con.close();
            
            sqlAlert(0 , "patient modifie ! " , 2);
            return true ;
        } catch (SQLException ex) {
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            sqlAlert(1 , "\tpatient non ajoutée , \n assurez-vous d'avoir bien inséré les informations" , 6);
            return false ;
        }
    }

    boolean modifyAct(DBconnection dBConnection, int id, String nom_act, int cout, String description)  {
        
        try {
            String idString = String.valueOf(id) ;
            
            if(nom_act.equals("")){
                nom_act = null ;
            }else{
                nom_act = "'"+nom_act+"'";
            }
            
            String sqlstmt = "UPDATE clinicdatabase.acts_dentaire SET nom_act="+nom_act+", cout='"+cout+"', description='"+description+"'  WHERE id='"+id+"'" ;
            Connection con =  dBConnection.getConnection() ;
            PreparedStatement ptst = con.prepareStatement(sqlstmt);
            
            ptst.executeUpdate();
            
            System.out.println("act modified !");
            
            con.close();
            
            sqlAlert(0 , "acte dentiare modifie !" , 3);
            return true ;
        } catch (SQLException ex) {
            
            
            sqlAlert(1 , "\tt act non modifie , \n assurez-vous d'avoir bien inséré les informations" , 6);
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            return false ;
        }
    }

    void deleteAct(DBconnection dbc, int id) throws SQLException {
        
        String idString = String.valueOf(id) ;
                
        Connection con =  dbc.getConnection() ;
        String sqlstmt = "UPDATE clinicdatabase.acts_dentaire SET nom_act='acte supprimé'  WHERE id='"+id+"'" ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("acte supprimé !");
        
        con.close();
        
        sqlAlert(0 , "acte supprimé",3);
    }

    void modifyConsultation(DBconnection dBConnection, int cons_id, int client_id, int[] dents, int termine, String coutF) throws SQLException {
        
                
        Connection con =  dBConnection.getConnection() ;
        String sqlstmt ;
        if(client_id == 0){
            sqlstmt = "UPDATE clinicdatabase.consultations SET  termine='"+termine+"' , cout='"+coutF+"' WHERE `id` ='"+cons_id+"'" ;
       
        }else{
            sqlstmt = "UPDATE clinicdatabase.consultations SET client_id='"+client_id+"' , termine='"+termine+"' , cout='"+coutF+"'  WHERE `id` ='"+cons_id+"'" ;
       
        }
        
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        ptst.executeUpdate();
        
        //should be the delete statement 
        String sql = "DELETE FROM dents_for_consultation WHERE id_consultation = '" + cons_id + "' ";
        if (dents != null) {
            // Execute the DELETE statement
            ptst = con.prepareStatement(sql);
            ptst.executeUpdate();
            System.out.println("deleted teeth ");

            //should be the insert statement 
            for (int dent : dents) {
                // SQL DELETE statement
                sql = "INSERT INTO `clinicdatabase`.`dents_for_consultation` (`id_consultation`, `tooth_id`) VALUES ('" + cons_id + "', '" + dent + "')";
                
                // Execute the DELETE statement
                ptst = con.prepareStatement(sql);
                ptst.executeUpdate();
                System.out.println("tooth added");

            }
        }
        
        
        
        System.out.println("consultation modified !");
        
        con.close();
        
        sqlAlert(0 , "consultation modifie " , 3);
    }
    void modifyConsultation(DBconnection dBConnection, Consultation cons , int termine) throws SQLException {
        
                
        Connection con =  dBConnection.getConnection() ;
        String sqlstmt ;
        int new_cout  = cons.payed + 1 ; 
        
        if(cons.cout > cons.payed){
            sqlstmt = "UPDATE clinicdatabase.consultations SET  termine='"+termine+"'    WHERE `id` ='"+cons.id+"'" ;
        }else{
            sqlstmt = "UPDATE clinicdatabase.consultations SET  termine='"+termine+"' , cout = '"+new_cout+"'   WHERE `id` ='"+cons.id+"'" ;
        }
        
        
       
        
        
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        ptst.executeUpdate();
        

        System.out.println("consultation set to not termine !");
        
        con.close();
        
        sqlAlert(0 , "consultation modifie " , 3);
    }
    void modifyAdmin(DBconnection dBConnection, int id, String name, String pass, int adminsRight, int actesRight, int patientsright, int visitsright, int voirapercusright) throws SQLException {
        String sqlstmt = "UPDATE `clinicdatabase`.`admins` SET `username` = '"+name+"', `password` = '"+pass+"', `gestionadminsright` = "+adminsRight+", `gestionactsright` = "+actesRight+", `gestionpatientsright` = "+patientsright+", `gestionvisitsright` = "+visitsright+", `voirapercuright` = "+voirapercusright+" WHERE (`id` = "+id+");" ;
        
        Connection con =  dBConnection.getConnection() ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("admin modified !");
        
        con.close();
        
        sqlAlert(0 , "admin modifie !" , 3);
    }
    
    void sqlAlert(int type ,String msg , int time){
        JOptionPane optionPane = new JOptionPane(msg,JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = optionPane.createDialog("Message");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        if(type == 0){
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/images/done.png"));
            optionPane.setIcon(icon);
        }else if(type == 1){
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/images/warning.png"));
            optionPane.setIcon(icon);
        }
        
        
        Timer timer = new Timer(time*1000, e -> {
            dialog.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

    boolean insertFacture(DBconnection dBConnection, String designation, int depense ,  String descri, int admin_id)  {
        try {
            Connection con =  dBConnection.getConnection() ;
            if(designation.equals("")){
                designation = null ;
            }else{
                designation = "'"+designation+"'";
            }
            String sqlQuery = "INSERT INTO `clinicdatabase`.`factures` (`designation`, `date_creation`, `depense`, `descri`, `admin_id`) VALUES ("+designation+", CURDATE(), '"+depense+"', '"+descri+"', '"+admin_id+"')";
            PreparedStatement ptst = con.prepareStatement(sqlQuery);
            
            ptst.executeUpdate();
            
            sqlAlert(0 , "facture ajoutée" , 3);
            con.close();
            return true ;
        } catch (SQLException ex) {
            sqlAlert(1 , "facture non ajoutée , assurez-vous d'avoir bien inséré les informations" , 6);
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            return false ;
        }
    }

    boolean modifyFacture(DBconnection dBConnection, int id, String designation, int depense, String descri) {
        try {
            String idString = String.valueOf(id) ;
            
            if(designation.equals("")){
                designation = null ;
            }else{
                designation = "'"+designation+"'";
            }
            String sqlQuery = "UPDATE `clinicdatabase`.`factures` SET `designation` = "+designation+", `depense` = '"+depense+"', `descri` = '"+descri+"' WHERE (`idfacture` = '"+id+"');";
            Connection con =  dBConnection.getConnection() ;
            PreparedStatement ptst = con.prepareStatement(sqlQuery);
            
            ptst.executeUpdate();
            
            System.out.println("facture modified !");
            
            con.close();
            
            sqlAlert(0 , "facture modifie !" , 3);
            return true ;
        } catch (SQLException ex) {
            Logger.getLogger(UserQueryManager.class.getName()).log(Level.SEVERE, null, ex);
            sqlAlert(1 , "facture non ajoutée , assurez-vous d'avoir bien inséré les informations" , 6);
            return false ;
        }
        
    }

    Facture currentFacture(DBconnection dbc, Object id) throws SQLException {
        Connection con =  dbc.getConnection() ;
        
        String mysql_query = "SELECT *  FROM factures WHERE idfacture="+id+" ";
        
        Facture thisfact = new Facture();
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    thisfact = new Facture(rs.getInt("idfacture") , rs.getString("designation"), rs.getInt("depense") , rs.getString("descri"));
                    st.close();
                    System.out.println("facture obj successfully created ");
                    
                }else{
                    System.out.println("facture obj object wasn't created");
                    //Client client = new Client(0, "" , "", "", "", 0, "") ;
                    thisfact = null ;
                    
                    st.close();
                }
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("error when craeting  facture object ");
                
            }
        return thisfact ;
    }

    void deleteFacture(DBconnection dbc, int id)  throws SQLException {
        Connection con =  dbc.getConnection() ;
        String sql = "DELETE FROM factures WHERE idfacture = "+id+""; 
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.executeUpdate();
        con.close();
        
        sqlAlert(0 , "facture supprime " , 3);
     
    }

    boolean backupData() throws IOException, InterruptedException {
        
        Properties properties = new Properties();
        Runtime runtime = Runtime.getRuntime()  ;
        
        //FileReader fis = new FileReader(ClinicD.class.getResource("/config/config.properties").getFile())
        //
        
        try (InputStream fis = ClinicD.class.getResourceAsStream("/config/config.properties") ) {
            
            
            properties.load(fis);
            String env_var = System.getenv("FLASHDEST") ;
                                //
            Path path = Paths.get(env_var);
            if(!Files.exists(path)){
                
                sqlAlert(1 , "assurez-vous que le flashdisque est inséré " , 4);
                throw new IOException() ;
            }
            String today = java.time.LocalDate.now().toString() ;
                                      //
            File backupFile = new File(env_var +"back-up "+ today+".sql");
            
            String[] command = new String[]{"mysqldump", "-u"+properties.getProperty("db.username"), "-p"+properties.getProperty("db.password"), "clinicdatabase"};
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(command));
            processBuilder.redirectError(Redirect.INHERIT);
            processBuilder.redirectOutput(Redirect.to(backupFile));

            Process process = processBuilder.start();
            
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database backup completed successfully.");
                return true ;
            } else {
                System.err.println("Database backup failed. Exit code: " + exitCode);
                return false ;
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error reading file : " + e.getMessage());
    
            return false ;
            
        }
    }
    
  }

    


