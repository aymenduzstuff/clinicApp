
package clinicd;
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

    void insertAdmin(DBconnection dBConnection ,String username, String password) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "insert into admins(username , password) values(?,?)"; 
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.setString(1 , username);
        ptst.setString(2 , password);
        ptst.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "new admin addes");
        con.close();
    }

    void insertClient(DBconnection dBConnection,  String nom, String prenom, String adress, String phone, LocalDate date , String note) throws SQLException {
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter) ;
        
        Connection con =  dBConnection.getConnection() ;
        String sql = "INSERT INTO clinicdatabase.clients ( nom, prenom, adresse, num_tel, date_naiss , notes) VALUES (?, ?, ?, ?, ? , ?) " ;
        PreparedStatement ptst = con.prepareStatement(sql);
        ptst.setString(1 , nom);
        ptst.setString(2 , prenom);
        ptst.setString(3 , phone);
        ptst.setString(4 , adress);
        ptst.setString(5 , dateString);
        ptst.setString(6 , note);
        
        ptst.executeUpdate();
        
        System.out.println("new client added !");
        
        con.close();
    }
    
void deleteClient(DBconnection dBConnection ,int id) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "DELETE FROM clients WHERE id = "+id+""; 
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.executeUpdate();
        JOptionPane.showMessageDialog(null, "client Deleted ");
        con.close();
    }
    void insertVisit(DBconnection dbc, String time, String client_id, String operation_id, String admin_id, String prix, String termine, int[] dents , String note ) throws SQLException {
        
        
        
        
        Connection con =  dbc.getConnection() ;

        String sql = "INSERT INTO clinicdatabase.consultations ( client_id , act_dentaire  , termine, cout) VALUES (?, ?,  ?, 5000)" ;
        PreparedStatement ptst = con.prepareStatement(sql);
        
        ptst.setString(1 ,client_id );
        ptst.setString(2 ,operation_id );
        ptst.setString(3 ,termine );
        
        ptst.executeUpdate();
        
        String sql2 = "INSERT INTO clinicdatabase.seances ( id_consultation , nbr_seance , temps , admin_id , montant , notes) VALUES (LAST_INSERT_ID(), 1, ?,  ?, ? , ?)" ;
        PreparedStatement ptst2 = con.prepareStatement(sql2);
        
        ptst2.setString(1 ,time );
        ptst2.setString(2 ,admin_id );
        ptst2.setString(3 ,prix );
        ptst2.setString(4 ,note );
        
        
        ptst2.executeUpdate();
        
        for(int i = 0 ; i < dents.length ; i++){
            String sql3 = "insert into dents_for_consultation values(LAST_INSERT_ID() , ?) ;" ;
            PreparedStatement ptst3 = con.prepareStatement(sql3);
            System.out.println("dent a ajouter : " + dents[i]);
            ptst3.setInt(1, dents[i]);
            ptst3.executeUpdate();
        }
        
        
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
        
        JOptionPane.showMessageDialog(null, "new operation added");
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
    
    void updateCombo(JComboBox combo, DBconnection dBConnection ) throws SQLException {
        Connection con =  dBConnection.getConnection() ;
        String sql = "";
        String col = "" ;
        System.out.println( "combo name : "+combo.getName());
        if("usersComboBox".equals(combo.getName()) ){
            
            sql = "select * from admins";
            col = "username" ;
        }else if("operationsComboBox".equals(combo.getName()) ){
            sql = "select * from acts_dentaire";
            col = "nom_act" ;
        }else if("teethComboBox".equals(combo.getName()) ){
            for(int j = 0 ; j < 32 ;j++){
                combo.addItem(j);
            }
        }
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getString(col));
                
            }
           
            
        }catch(Exception ex){
            
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
            newDbQuery = "select consultations.id as id , max(seances.nbr_seance) as dern_seance , nom_act , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%m ') as dern_date  , termine , sum(montant) as total,  IFNULL(GROUP_CONCAT(distinct t.dents), 'pas spécifiées') AS dents   from consultations \n" +
                    " join seances on consultations.id = seances.id_consultation  \n"
                    + " join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  \n"
                    + " left join(\n"
                    + "  SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n"
                    + ") as t on t.id_consultation = consultations.id\n"
                    + " where client_id='"+code+"' group by consultations.id";

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
                   
                    result[i] = rs.getString("id")+":::"+rs.getString("dents")+":::"+rs.getString("nom_act")+":::"+rs.getString("dern_seance")+":::"+rs.getString("dern_date")+":::"+rs.getString("total")+":::"+rs.getString("termine");
                    
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
            maxDateString = "CURDATE()";
        }else{
            minDateString = "'"+minDate.format(formatter)+"'" ;
            maxDateString = "'"+maxDate.format(formatter)+"'";
        
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
        
        
        String newDbQuery = "select consultations.id , concat(nom , ' ' , prenom) as nomprenom ,  max(seances.nbr_seance) as dern_seance , nom_act , DATE_FORMAT(max(temps),  ' %W , %Y/%m/%d %H:%m ') as dern_date  , termine , sum(montant) as total,  IFNULL(GROUP_CONCAT( distinct t.dents), 'pas spécifiées') AS dents   from consultations \n"
                + " join seances on consultations.id = seances.id_consultation  \n"
                + " join acts_dentaire on acts_dentaire.id = consultations.act_dentaire  \n"
                + " join clients on consultations.client_id = clients.id \n"
                + " left join(\n"
                + "  SELECT dents_for_consultation.id_consultation , GROUP_CONCAT(distinct tooth_id SEPARATOR ' , ') as dents FROM clinicdatabase.dents_for_consultation  group by dents_for_consultation.id_consultation \n"
                + ") as t on t.id_consultation = consultations.id\n"
                + " where  "+condNomPrenom+" and "+condAct+" and "+condTime+" "+Termine+" group by consultations.id having  "+condPrice+"";


                

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
                    
                    result[i] = rs.getString("id")+":::"+rs.getString("nomprenom")+":::"+rs.getString("dents")+":::"+rs.getString("nom_act")+":::"+rs.getString("dern_seance")+":::"+rs.getString("dern_date")+":::"+rs.getString("total")+":::"+rs.getString("termine");
                    
                    
                    i++;
                    
                }  
                
                st.close();
            } catch (SQLException e) {
                System.out.println("error when retrieving detailed visit data...");
                System.out.println(e.getMessage());
            }
        return result;
    }


    Object getOperationIdByName(DBconnection dbc, String operation) throws SQLException {
        Connection con =  dbc.getConnection() ;
        String sql = "select id from acts_dentaire where nom_act='"+operation+"'";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            System.out.println("returned from if");
            return rs.getString(1);
        }
        
        
        con.close();
        System.out.println("returned from function body");
        return rs.getString("id_operation");
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
     
     
    String[] getseancesResults(DBconnection dbc, String code) throws SQLException {
        
        Connection con =  dbc.getConnection() ;
        String[] result = null;
        String mysql_query = "select * from seances where id_consultation='"+code+"'  ";
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
                    result[i] = rs.getString("nbr_seance")+":::"+rs.getString("temps")+":::mazal maderthomch:::"+rs.getString("montant")+":::"+rs.getString("admin_id");
                    
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
        
        JOptionPane.showMessageDialog(null, "visit set to termine ");
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
    void modifyClient(DBconnection dBConnection,int id ,  String nom, String prenom , String phone, String adress, LocalDate date , String note) throws SQLException {
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter) ;
        String idString = String.valueOf(id) ;
        
        Connection con =  dBConnection.getConnection() ;
        String sql = "INSERT INTO clinicdatabase.clients ( nom, prenom, adresse, num_tel, date_naiss , notes) VALUES (?, ?, ?, ?, ? , ?) " ;
        String sqlstmt = "UPDATE clinicdatabase.clients SET nom='"+nom+"', prenom='"+prenom+"', adresse='"+adress+"' , num_tel='"+phone+"', date_naiss='"+dateString+"', notes='"+note+"' WHERE id='"+id+"'" ;
        PreparedStatement ptst = con.prepareStatement(sqlstmt);
        
        ptst.executeUpdate();
        
        System.out.println("client modified !");
        
        con.close();
        
        JOptionPane optionPane = new JOptionPane("client modified ! ",JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = optionPane.createDialog("Message");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        Timer timer = new Timer(5000, e -> {
            dialog.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }
  }

    


