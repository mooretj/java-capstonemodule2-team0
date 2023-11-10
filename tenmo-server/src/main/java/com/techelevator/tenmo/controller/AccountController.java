package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("accounts/")
public class AccountController {

    private final AccountDao accountDao;
    private final TransferDao transferDao;

    public AccountController(AccountDao accountDao, TransferDao transferDao){
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }


    @RequestMapping(path = "balance/{userId}", method = RequestMethod.GET)
    public BigDecimal getBalanceById(@PathVariable("userId") int userId) {
        return accountDao.getBalanceByUserId(userId);
    }

    @RequestMapping(path = "{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable("userId") int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    @RequestMapping(path = "transfer/{transferId}", method = RequestMethod.GET)
    public TransferDto getTransferById(@PathVariable("transferId") int transferId){
        return transferDao.getTransferById(transferId);
    }

    @RequestMapping(path = "transfer/user/{userId}", method = RequestMethod.GET)
    public List<TransferDto> getTransferHistoryByUserId(@PathVariable("userId") int userId){
        return transferDao.getTransferHistoryByUserId(userId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfer/send", method = RequestMethod.POST)
    public TransferDto createSendTransfer(@RequestBody TransferDto transferDto){
        return transferDao.sendTransferDto(transferDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfer/request/{userId}", method = RequestMethod.POST)
    public TransferDto createRequestTransfer(@PathVariable("userId") int userId, @RequestBody TransferDto transferDto){
        return transferDao.sendTransferDto(transferDto);
    }

    @RequestMapping(path= "transfer/pending/{userId}", method = RequestMethod.GET)
    public List<TransferDto> getListOfPendingTransByUserId(@PathVariable("userId") int userId){
        return transferDao.getPendingTransByUserId(userId);
    }


}
