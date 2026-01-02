package dao;

import model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    //create
    void createAccount(Account account) throws Exception;

    //read
    Account getAccountById(int accountId)throws Exception;

    //update
    void updateBalance(int accountId, BigDecimal newBalance) throws Exception;
}
