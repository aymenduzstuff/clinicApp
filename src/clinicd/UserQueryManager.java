
package clinicd;
import java.awt.Color;
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
        }else if(nbrAdminPrev ==2 && adminsRight ==1){
            String msg = "il existe déjà un administrateur avec les privilèges de gestion des admins";
            sqlAlert( 1 , msg , 6);
        }else{
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

    void insertClient(DBconnection dBConnection,  String nom, String prenom, String adress, String phone, LocalDate date , String note) throws SQLException {
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter) ;
        
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
        
        sqlAlert(0 , "patient inserted " ,3 );
        
        con.close();
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
    void insertVisit(DBconnection dbc, String time, String client_id, String operation_id, String admin_id, String prix, String termine, int[] dents , String note ) throws SQLException {
        
        
        
        
        Connection con =  dbc.getConnection() ;

        String sql = "INSERT INTO clinicdatabase.consultations ( client_id , act_dentaire  , termine) VALUES (?, ?,  ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
        
        ptst.setString(1 ,client_id );
        ptst.setString(2 ,operation_id );
        ptst.setString(3 ,termine );
        
            
        
        int affectedRows = ptst.executeUpdate();
        int lastInsertID = 0 ;
        if (affectedRows > 0) {
                ResultSet generatedKeys = ptst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    lastInsertID = generatedKeys.getInt(1);
                    
                }
        }
        System.out.println("inserted consultation");
        String sql2 = "INSERT INTO clinicdatabase.seances ( id_consultation , nbr_seance , temps , admin_id , montant , notes) VALUES (?, 1, ?,  ?, ? , ?)" ;
        PreparedStatement ptst2 = con.prepareStatement(sql2);
        
        ptst2.setString(1 , String.valueOf(lastInsertID));
        ptst2.setString(2 ,time );
        ptst2.setString(3 ,admin_id );
        ptst2.setString(4 ,prix );
        ptst2.setString(5 ,note );
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
    void insertSeance(DBconnection dbc, String visit_id , String seance_nbr , String time , String admin_id, String prix , String note) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        /*
        String mysql_query = "SELECT * FROM sean WHERE visit_id ='"+visit_id+"' and seance_nbr ='"+seance_nbr+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(mysql_query);
        rs.next();
        
        String client_id = rs.getString("Client_id");
        String operation_id =  rs.getString("Operation_id");
        String tooth =  rs.getString("tooth");
        System.out.println("client :"+client_id +" with tooth "+tooth);
        st.close();
        */
        
                                                    
        String sql = "INSERT INTO clinicdatabase.seances (id_consultation ,nbr_seance, temps , admin_id , montant , notes ) VALUES (?,?,?, ?, ?, ?)" ;
        PreparedStatement ptst = con.prepareStatement(sql);
        int seanceInt = Integer.parseInt(seance_nbr)+1 ;
        String seanceString = String.valueOf(seanceInt);
        
        ptst.setString(1 ,visit_id );
        ptst.setString(2 ,seanceString );
        ptst.setString(3 ,time );
        ptst.setString(4 ,admin_id );
        ptst.setString(5 , prix);
        ptst.setString(6 , note);
        
        
        ptst.executeUpdate();
        
        System.out.println(" seance added !");
        
        con.close();
    
    }

    void insertOperation(DBconnection dBConnection,  String name, String price , String desc) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "INSERT INTO clinicdatabase.acts_dentaire(nom_act, cout, description) values(?,?,?)"; 
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.setString(1, name);
        ptst.setString(2, price);
        ptst.setString(3, desc);
        
        
        ptst.executeUpdate();
        
        sqlAlert(0 , "nouvelle acte dentaire ajoutée" , 3);
        con.close();
    }


    String[] getClientsResults(DBconnection dbc , String string) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT * FROM clients WHERE nom LIKE '%"+string+"%' OR prenom LIKE '%"+string+"%' ";
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
            sql = "select * from acts_dentaire where nom_act not LIKE '%acte sup%' ";
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
            newDbQuery = "select consultations.id , nom_act ,dent , max(nbr_seance) as dern_seance , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%m ') as dern_date ,  client_id ,admin_id , sum(montant) as total , termine from seances join consultations on consultations.id = seances.id_consultation join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  where client_id='"+code+"'  group by id  " ;

        }else if(queryIndex == 2){
            newDbQuery = "select consultations.id as id , max(seances.nbr_seance) as dern_seance , nom_act , max(seances.temps) as last_date , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%i ') as dern_date  , termine , sum(montant) as total,  IFNULL(GROUP_CONCAT(distinct t.dents), 'non spécifiées') AS dents   from consultations \n" +
                    " join seances on consultations.id = seances.id_consultation  \n"
                    + " join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  \n"
                    + " left join(\n"
                    + "  SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n"
                    + ") as t on t.id_consultation = consultations.id\n"
                    + " where client_id='"+code+"' group by consultations.id ORDER BY last_date desc ";

        }
       
        String mysql_query = "select * from visits where client_id='"+code+"'  ";
        
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
                   
                    result[i] = rs.getString("id")+":::"+rs.getString("dern_date")+":::"+rs.getString("nom_act")+":::"+rs.getString("dents")+":::"+rs.getString("dern_seance")+":::"+rs.getString("total")+":::"+rs.getString("termine");
                    
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

    String[] getDetailedVisitsResults(DBconnection dbc, String nom , String prenom , String actDent  , String minPrice  ,String maxPrice , LocalDate minDate , LocalDate maxDate , int termine) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String minDateString ;
        String maxDateString ;
        String Termine = "";
        
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
        
        
        
        
        
        
        String condTime = "seances.temps  BETWEEN "+minDateString+" AND "+maxDateString+"" ;
        String condNomPrenom = " nom LIKE '%"+nom+"%' and prenom LIKE '%"+prenom+"%' " ;
        String condAct =  "nom_act like '%"+actDent+"%'" ;
        String condPrice = "total between '"+minPrice+"' and '"+maxPrice+"' " ;
        
        System.out.println("cond date        : " +condTime);
        System.out.println("cond nom prenom  : " +condNomPrenom );
        System.out.println("cond act         :" + actDent);
        System.out.println("cond price       :" + condPrice );
        
        //not implemnted yet 
        String condAdmin = "username like '%"+actDent+"%'" ;
        
        
        String newDbQuery = "select consultations.id , concat(nom , ' ' , prenom) as nomprenom ,  max(seances.nbr_seance) as dern_seance , nom_act , max(seances.temps) as last_date , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%i ') as dern_date  , termine , sum(montant) as total,  IFNULL(GROUP_CONCAT( distinct t.dents), 'non spécifiées') AS dents   from consultations \n"
                + " join seances on consultations.id = seances.id_consultation  \n"
                + " join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  \n"
                + " join clients on consultations.client_id = clients.id \n"
                + " left join(\n"
                + "  SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n"
                + ") as t on t.id_consultation = consultations.id\n"
                + " where  "+condNomPrenom+" and "+condAct+" and "+condTime+" "+Termine+" group by consultations.id having  "+condPrice+" ORDER BY last_date desc ";


                

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
                System.out.println("number of rows  : "+ k);
                while(rs.next() && i<k) {
                    numberOfUsers++;
                    //result[i] = rs.getString("visit_id")+":::"+rs.getString("time")+":::"+rs.getString("Operation_id")+":::"+rs.getString("tooth")+":::"+rs.getString("seance_nbr")+":::"+rs.getString("prix")+":::"+rs.getString("terminee");
                    
                    result[i] = rs.getString("id")+":::"+rs.getString("nomprenom")+":::"+rs.getString("dern_date")+":::"+rs.getString("nom_act")+":::"+rs.getString("dents")+":::"+rs.getString("dern_seance")+":::"+rs.getString("total")+":::"+rs.getString("termine");
                    
                    
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
        String mysql_query = "select nbr_seance , temps, concat('- ' , IFNULL(notes, '') , ' -' )as note , montant , notes ,  username from seances join clinicdatabase.admins on seances.admin_id = admins.id  where id_consultation='"+code+"'  ";
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
                    result[i] = rs.getString("nbr_seance")+":::"+rs.getString("temps")+":::"+rs.getString("montant")+":::"+rs.getString("username")+":::"+rs.getString("note");
                    
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

    void setVisitTermine(DBconnection dbc, String visit_id) throws SQLException {
    
        Connection con =  dbc.getConnection() ;
        String sql = "UPDATE consultations SET termine = 1 WHERE id = "+visit_id+";"; 
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.executeUpdate();
        sqlAlert(0 , "visit set to termine" , 3 );
        con.close();
    }

    String[] getDetailedClientsResults(DBconnection dbc, String string) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "SELECT * FROM clients WHERE nom LIKE '%"+string+"%' OR prenom LIKE '%"+string+"%' ";
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
                    result[i] = rs.getString("id")+":::"+rs.getString("nom")+":::"+rs.getString("prenom")+"::: "+rs.getString("adresse")+":::"+rs.getString("num_tel")+":::"+rs.getString("date_naiss");
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
    void modifySeance(DBconnection dBConnection,int cons_id , int id, String notes, int montant ) throws SQLException {
        
        String idString = String.valueOf(id) ;
                
        Connection con =  dBConnection.getConnection() ;
        String col = null ;
        if("".equals(notes)){
            col = "notes = NULL " ;
        }else {
             col = "notes = '"+notes+"'" ;
        }
        
        String sqlstmt = "UPDATE clinicdatabase.seances SET "+col+", montant='"+montant+"'  WHERE (`id_consultation` ='"+cons_id+"') and (`nbr_seance` = '"+id+"')" ;
        
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
        
        String mysql_query = "SELECT * FROM clinicdatabase.consultations where id="+id_cons+" ";
        
        Consultation cons = new Consultation(); 
        
        try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(mysql_query);
                
                
               
                if(rs.next()){
                    
                    cons = new Consultation(rs.getInt("id") , rs.getInt("client_id"), rs.getInt("act_dentaire") , rs.getInt("termine"));
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
    void modifyClient(DBconnection dBConnection,int id ,  String nom, String prenom , String phone, String adress, LocalDate date , String note) throws SQLException {
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter) ;
        String idString = String.valueOf(id) ;
        String noteSAFE = note.replace("'", "''");
                
        Connection con =  dBConnection.getConnection() ;
        String sqlstmt = "UPDATE clinicdatabase.clients SET nom='"+nom+"', prenom='"+prenom+"', adresse='"+adress+"' , num_tel='"+phone+"', date_naiss='"+dateString+"', notes='"+noteSAFE+"' WHERE id='"+id+"'" ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("client modified !");
        
        con.close();
        
        sqlAlert(0 , "patient modifie ! " , 3);
    }

    void modifyAct(DBconnection dBConnection, int id, String nom_act, int cout, String description) throws SQLException {
        
        String idString = String.valueOf(id) ;
                
        String sqlstmt = "UPDATE clinicdatabase.acts_dentaire SET nom_act='"+nom_act+"', cout='"+cout+"', description='"+description+"'  WHERE id='"+id+"'" ;
        Connection con =  dBConnection.getConnection() ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("act modified !");
        
        con.close();
        
        sqlAlert(0 , "acte dentiare modifie !" , 3);
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

    void modifyConsultation(DBconnection dBConnection,int cons_id , int client_id, int act_id , int[] dents , int termine ) throws SQLException {
        
                
        Connection con =  dBConnection.getConnection() ;
        String sqlstmt ;
        if(client_id == 0){
            sqlstmt = "UPDATE clinicdatabase.consultations SET act_dentaire='"+act_id+"', termine='"+termine+"'  WHERE `id` ='"+cons_id+"'" ;
       
        }else{
            sqlstmt = "UPDATE clinicdatabase.consultations SET client_id='"+client_id+"', act_dentaire='"+act_id+"', termine='"+termine+"'  WHERE `id` ='"+cons_id+"'" ;
       
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
            Icon icon = new ImageIcon("src\\images\\done.png");
            optionPane.setIcon(icon);
        }else if(type == 1){
            Icon icon = new ImageIcon("src\\images\\warning.png");
            optionPane.setIcon(icon);
        }
        
        
        Timer timer = new Timer(time*1000, e -> {
            dialog.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }
    
  }

    


