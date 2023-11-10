package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            while(result.next()) {
                account = mapRowToAccount(result);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
      return account;
    }
    @Override
    public BigDecimal getBalanceByUserId(int userId) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if(result.next()) {
                balance = result.getBigDecimal("balance");
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return balance;
    }

    @Override
    public void subtractBalance(BigDecimal amount, int accountId){
        String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";
        try{
            int rows = jdbcTemplate.update(sql, amount, accountId);
            if(rows == 0){
                throw new DaoException("Zero rows affected, expected one");
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

    }

    @Override
    public void addBalance(BigDecimal amount, int accountId){
        String sql = "UPDATE account SET balance = balance + ? WHERE account_id = ?;";
        try{
            int rows = jdbcTemplate.update(sql, amount, accountId);
            if(rows == 0){
                throw new DaoException("Zero rows affected, expected one");
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

    }

    public Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
