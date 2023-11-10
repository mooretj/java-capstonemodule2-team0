package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.TransferDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TransferDto getTransferById(int transferId) {//TODO: change SELECT to not all
        TransferDto transferDto = null;
        String sql = "SELECT t.transfer_id, t.amount, tu.username, t.account_from, t.account_to, t.transfer_type_id, t.transfer_status_id FROM transfer AS t " +
                "JOIN account AS a ON a.account_id = t.account_from " +
                "JOIN tenmo_user AS tu ON tu.user_id = a.user_id " +
                "WHERE t.transfer_id = ?;";


//        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer t\n" +
//                "WHERE transfer_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
            while (result.next()){
                transferDto = mapRowToTransferDto(result);
            }

        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transferDto;

    }

    @Override
    public TransferDto sendTransferDto(TransferDto transferDto){
        TransferDto sendTransfer = null;
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(2, 2, ?, ?, ?) " +
                "RETURNING transfer_id;";
        try{
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class, transferDto.getAcctFromId(),
                    transferDto.getAcctToId(), transferDto.getAmount());
            sendTransfer = getTransferById(newTransferId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return sendTransfer;

    }

    @Override
    public TransferDto requestTransferDto(TransferDto transferDto){
        TransferDto sendTransfer = null;
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES(1, 1, ?, ?, ?) " +
                "RETURNING transfer_id;";
        try{
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class, transferDto.getAcctFromId(),
                    transferDto.getAcctToId(), transferDto.getAmount());
            sendTransfer = getTransferById(newTransferId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return sendTransfer;

    }

    @Override
    public List<TransferDto> getPendingTransByUserId (int userId){
        List<TransferDto> pendingTransList = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.amount, tu.username, t.account_from, t.account_to, t.transfer_type_id, t.transfer_status_id FROM transfer AS t " +
                "JOIN account AS a ON a.account_id = t.account_from " +
                "JOIN tenmo_user AS tu ON tu.user_id = a.user_id " +
                "WHERE t.transfer_status_id = 1 AND tu.user_id = ?;";

//        String sql = "SELECT tu.username, t.amount, t.transfer_id FROM transfer AS t " +
//                "JOIN account AS a ON a.account_id = t.account_from " +
//                "JOIN tenmo_user AS tu ON tu.user_id = a.user_id " +
//                "WHERE t.transfer_status_id = 1 AND tu.user_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            while(result.next()){
                pendingTransList.add(mapRowToTransferDto(result));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return pendingTransList;
    }
    @Override
    public List<TransferDto> getTransferHistoryByUserId(int userId) {//TODO: change SELECT to not all
        List<TransferDto> transferDto = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.amount, tu.username, t.account_from, t.account_to, t.transfer_type_id, t.transfer_status_id FROM transfer AS t " +
                "JOIN account AS a ON a.account_id = t.account_from " +
                "JOIN tenmo_user AS tu ON tu.user_id = a.user_id " +
                "WHERE tu.user_id = ?;";


//        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer t\n" +
//                "JOIN account a ON a.account_id = t.account_from\n" +
//                "WHERE a.user_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            while (result.next()){
                transferDto.add(mapRowToTransferDto(result));
            }

        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transferDto;

    }







    public TransferDto mapRowToTransferDto(SqlRowSet rowSet){
        TransferDto transferDto = new TransferDto();
        transferDto.setTransId(rowSet.getInt("transfer_id"));
        transferDto.setTransTypeId(rowSet.getInt("transfer_type_id"));
        transferDto.setTransStatusId(rowSet.getInt("transfer_status_id"));
        transferDto.setAcctFromId(rowSet.getInt("account_from"));
        transferDto.setAcctToId(rowSet.getInt("account_to"));
        transferDto.setAmount(rowSet.getBigDecimal("amount"));
        transferDto.setUsername(rowSet.getString("username"));
        return transferDto;
    }
}
