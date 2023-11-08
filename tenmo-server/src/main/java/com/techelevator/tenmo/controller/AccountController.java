package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("accounts/")
public class AccountController {

    private final AccountDao accountDao;

    public AccountController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "balance/{userId}", method = RequestMethod.GET)
    public BigDecimal getBalanceById(@PathVariable("userId") int userId) {
        return accountDao.getBalanceByUserId(userId);
    }

    @RequestMapping(path = "{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable("userId") int userId) {
        return accountDao.getAccountByUserId(userId);
    }


}
