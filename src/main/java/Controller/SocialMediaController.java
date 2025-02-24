package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::newMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccount);

        return app;
    }

    
    private void newAccount(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(context.body(), Account.class);
        Account newAcc = accountService.newAccount(acc);
        if (newAcc != null){
            context.json(newAcc).status(200);
        }
        else{
            context.status(400);
        }
    }

    private void loginAccount(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(context.body(), Account.class);
        Account authAcc = accountService.loginAccount(acc);
        if (authAcc != null){
            context.json(authAcc).status(200);
        }
        else{
            context.status(401);
        }
    }
    private void newMessage(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message mess = om.readValue(context.body(),Message.class);
        Message newMess = messageService.newMessage(mess);
        if (newMess != null){
            context.json(newMess).status(200);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByID(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(id);
        if (message != null){
            context.json(message);
        }
        else{
            context.status(200);
        }
    }

    private void deleteMessage(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessage(id);
        if (message != null){
            context.json(message);
        }
        else{
            context.status(200);
        }
    }

    private void updateMessage(Context context) throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper om = new ObjectMapper();
        Message m = om.readValue(context.body(), Message.class);
        Message message = messageService.updateMessage(id, m.getMessage_text());
        if (message != null){
            context.json(message);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesByAccount(Context context) {
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> message = messageService.getAllMessagesByAccount(id);
        context.json(message);
    }

}