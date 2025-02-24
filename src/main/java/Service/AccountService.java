package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account newAccount(Account acc){
        if (acc.getUsername().length() == 0 || acc.getPassword().length() < 4) return null;
        if (accountDAO.getAccountByUsername(acc.getUsername()) != null) return null;
        accountDAO.newAccount(acc);
        return accountDAO.getAccountByUsername(acc.getUsername());
    }

    public Account loginAccount(Account acc){
        return accountDAO.loginAccount(acc);
    }
    
}
