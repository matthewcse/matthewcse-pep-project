package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message newMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            int change = ps.executeUpdate();
            if (change == 0) return null;

            sql = "SELECT * FROM message WHERE posted_by=? AND message_text=? AND time_posted_epoch=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message mess = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return mess;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            Message toBeDeleted = getMessageByID(message_id);
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            int change = ps.executeUpdate();
            if (change == 0) return null;
            return toBeDeleted;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message_text);
            ps.setInt(2, message_id);
            int change = ps.executeUpdate();
            if (change == 0) return null;
            return getMessageByID(message_id);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
