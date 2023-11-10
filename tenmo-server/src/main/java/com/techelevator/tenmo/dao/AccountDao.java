package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

//    public Account getAccountByUserId(int userId);

    public BigDecimal getBalanceByUserId(int userId);

    public void subtractBalance(BigDecimal amount, int accountId);
    public void addBalance(BigDecimal amount, int accountId);
    public List<Account> getAccounts();




}
