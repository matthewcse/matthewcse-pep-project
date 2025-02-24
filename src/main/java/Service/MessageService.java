package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message newMessage(Message message){
        if (message.getMessage_text().length() > 255 || message.getMessage_text().length() == 0) return null;
        return messageDAO.newMessage(message);
    }
    
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    public Message deleteMessage(int message_id){
        if (messageDAO.getMessageByID(message_id) == null) return null;
        return messageDAO.deleteMessage(message_id);
    }

    public Message updateMessage(int message_id, String message_text){
        if (message_text.length() > 255 || message_text.length() == 0) return null;
        if (messageDAO.getMessageByID(message_id) == null) return null;
        return messageDAO.updateMessage(message_id, message_text);
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        return messageDAO.getAllMessagesByAccount(account_id);
    }
}
