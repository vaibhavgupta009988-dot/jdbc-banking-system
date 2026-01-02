package dao;

import model.Account;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

    @Override
    public void createAccount(Account account) throws Exception{
        String sql= "INSERT INTO accounts (holder_name, balance) VALUES (?,?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,account.getHolderName());
            ps.setBigDecimal(2,account.getBalance());

            ps.executeUpdate();

        }
    }

    @Override
    public Account getAccountById(int accountId)throws Exception{

        String sql="SELECT holder_name,balance from accounts where account_id = ?";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,accountId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   String holderName = rs.getString("holder_name");
                   BigDecimal balance = rs.getBigDecimal("balance");
                   return  new Account(accountId,holderName,balance);
               }
           }
        }
        return  null;
    }

    @Override
    public void updateBalance(int accountId, BigDecimal newBalance) throws Exception{
        String sql = "UPDATE accounts set balance = ? WHERE account_id=?";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setBigDecimal(1,newBalance);
            ps.setInt(2,accountId);
            ps.executeUpdate();
        }
    }
}
