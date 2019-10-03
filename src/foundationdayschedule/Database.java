
package foundationdayschedule;

import java.awt.Component;
import java.sql.*;
import javax.swing.JOptionPane;

public class Database
{
    Connection con;
    public void initConnection() 
    {
        try
        {  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con = DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/RecordManage?autoReconnect=true&useSSL=false","root","bitch");  
        }
        catch(ClassNotFoundException | SQLException e){ System.out.println(e);}
    }
    public int checkLogin(String userName, String password, Component cmpt) 
    {
        try 
        {
            String accountSearch = "select sno,password from account where userid = ?";
            PreparedStatement stmt = con.prepareStatement(accountSearch);
            stmt.setString(1,userName);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) 
            {
                if(rs.getString(2).equals(password)) 
                {
                    return rs.getInt(1);
                }
            }
        }
        catch( SQLException e) {System.out.println(e);}
        JOptionPane.showMessageDialog(cmpt, "UserName/password does not match");
        return 0;
    }
    public void addRecord(int members, String batch, String eventType, int score, int judgeSession, Component cmpt) 
    {
        try
        {
            String insertRecord = "";
            if(judgeSession == 1)insertRecord =  "insert into records (members, batch, eventType, j1) values (?,?,?,?)";
            if(judgeSession == 2)insertRecord =  "insert into records (members, batch, eventType, j2) values (?,?,?,?)";
            if(judgeSession == 3)insertRecord =  "insert into records (members, batch, eventType, j3) values (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(insertRecord);
            stmt.setInt(1, members);
            stmt.setString(2, batch);
            stmt.setString(3, eventType);
            stmt.setInt(4, score);
            
            int x = stmt.executeUpdate();
            if(x > 0) JOptionPane.showMessageDialog(cmpt, "record added sucessfully");
        }
        catch(SQLException e) {System.out.println(e);} 
    }
    public void editScore(int id, int score, int judgeSession, Component cmpt) {
        try {
            String updateRecord = "";
            if(judgeSession == 1)updateRecord =  "update records set j1 = ? where id = ?";
            if(judgeSession == 2)updateRecord =  "update records set j2 = ? where id = ?";
            if(judgeSession == 3)updateRecord =  "update records set j3 = ? where id = ?";
            PreparedStatement stmt = con.prepareStatement(updateRecord);
            stmt.setInt(1, score);
            stmt.setInt(2, id);
            int x  = stmt.executeUpdate();
            if(x > 0) JOptionPane.showMessageDialog(cmpt,"score updated successfully");
        }
        catch(SQLException e) { System.out.println(e);}
    }
    
    public void register(String userName, String password, Component cmpt) 
    {
        try 
        {
            String search = "select * from account";
            PreparedStatement check = con.prepareStatement(search);
            ResultSet rs = check.executeQuery();
            int ctr = 0;
            while(rs.next())
            {
                if(rs.getString(2).equals(userName)) 
                {
                    JOptionPane.showMessageDialog(cmpt, "userName unavailable");
                    return;
                }
                ctr++;
            }
            if(ctr < 3) 
            {
                String register = "insert into account (userid, password) values (?, ?)";
                PreparedStatement stmt = con.prepareStatement(register);
                stmt.setString(1, userName);
                if(password.length() < 7)
                {
                    JOptionPane.showMessageDialog(null, "password invalid length");
                }
                else
                {
                    stmt.setString(2, password);
                    int x = stmt.executeUpdate();
                    if(x > 0) JOptionPane.showMessageDialog(null, "new user Registered");
                }
            }
            else JOptionPane.showMessageDialog(null, "3 users have already Registered");
        }
        catch(SQLException e) {System.out.println(e);}
    }
    
    public ResultSet getResultSet(String selectQuery) 
    {
        try 
        {
            PreparedStatement stmt = con.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return rs;
            else JOptionPane.showMessageDialog(null, "table is empty");
        }
        catch (SQLException e) {JOptionPane.showMessageDialog(null, e);}
        return null;
    }
}
