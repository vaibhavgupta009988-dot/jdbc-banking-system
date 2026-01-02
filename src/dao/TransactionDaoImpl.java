package dao;

import model.Transaction;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public void saveTransaction(Transaction transaction) throws Exception {
        String sql = "INSERT INTO transactions(account_id,transaction_type,amount) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, transaction.getAccountId());
            ps.setString(2, transaction.getTransactionType());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.executeUpdate();
        }
    }

    @Override
    public void saveTransactions(List<Transaction> transactions) throws Exception {
        try {
            for (Transaction t : transactions) {
                saveTransaction(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getTransactionByAccountId(int accountId) throws Exception {
        String sql = "SELECT transaction_type, amount FROM transactions WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accountId);          // ✅ set parameter FIRST
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String transactionType = rs.getString("transaction_type");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    Transaction t = new Transaction(accountId, transactionType, amount);
                    transactions.add(t);
                }
            }
        }
        return transactions;
    }

}
