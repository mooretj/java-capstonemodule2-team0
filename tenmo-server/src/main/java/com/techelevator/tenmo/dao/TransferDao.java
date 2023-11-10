package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDto;

import java.util.List;

public interface TransferDao {
    public List<TransferDto> getTransferHistoryByUserId(int userId);
    public TransferDto getTransferById(int transferId);

    public TransferDto sendTransferDto(TransferDto transferDto);
    public TransferDto requestTransferDto(TransferDto transferDto);
    public List<TransferDto> getPendingTransByUserId(int userId);
}
