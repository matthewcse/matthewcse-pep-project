package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO {
    public Account newAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            int change = ps.executeUpdate();
            if(change == 0) return null;
            return acc;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Account loginAccount(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),rs.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}