package dao;

import model.Transaction;

import java.util.List;

public interface TransactionDao {

    void saveTransaction(Transaction transaction) throws Exception;

    void saveTransactions(List<Transaction> transactions) throws Exception;

    List<Transaction> getTransactionByAccountId(int accountId) throws Exception;

}
