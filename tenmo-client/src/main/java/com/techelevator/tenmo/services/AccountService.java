package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final String API_BASE_URL = "http://localhost:8080/accounts/";
    private final RestTemplate restTemplate = new RestTemplate();


    public BigDecimal getAccountBalance(AuthenticatedUser user){
        BigDecimal responseEntity = null;
        String url = API_BASE_URL + "balance/" + user.getUser().getId();
        try{
            responseEntity = restTemplate.getForObject(url, BigDecimal.class);

        }
        catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return responseEntity;
    }
    public void sendBucks(TransferDto newtransferDto){
        String url = API_BASE_URL + "transfer/send";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransferDto> entity = new HttpEntity<>(newtransferDto, headers);
        try{
            TransferDto responseEntity = restTemplate.postForObject(url, entity, TransferDto.class);
        }catch (RestClientResponseException e) {
            // handles exceptions thrown by rest template and contains status codes
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        }
        catch (ResourceAccessException e) {
            // i/o error, ex: the server isn't running
            BasicLogger.log(e.getMessage());
        }

    }

    public Account[] printAccountList(){
        Account[] accountList = null;
        try{
            accountList = restTemplate.getForObject(API_BASE_URL, Account[].class);
        }
        catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountList;
    }


}
