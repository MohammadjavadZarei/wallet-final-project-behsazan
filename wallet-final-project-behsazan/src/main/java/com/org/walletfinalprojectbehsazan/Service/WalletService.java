package com.org.walletfinalprojectbehsazan.Service;

import com.org.walletfinalprojectbehsazan.exception.WalletServiceException;
import com.org.walletfinalprojectbehsazan.model.Dto.MoneyTransferDetails;
import com.org.walletfinalprojectbehsazan.model.Dto.PassbookDetails;
import com.org.walletfinalprojectbehsazan.model.Wallet;

import java.util.Optional;

public interface WalletService {

    public Optional<Wallet> createWallet(Wallet wallet) throws WalletServiceException;
    public Optional<Wallet> updateWallet(Wallet wallet) throws WalletServiceException;
    public Optional<Wallet> deleteWallet(Integer walletId) throws WalletServiceException;
    public Optional<Wallet> getWallet(Integer walletId) throws WalletServiceException;
    public void addMoney(String userName, Integer moneyToBeAdded) throws WalletServiceException;
    public String payToOtherUser(MoneyTransferDetails moneyTransferDetails) throws WalletServiceException;
    public PassbookDetails viewPassbook(String userName) throws WalletServiceException;
    public String getTransactionStatus(String transactionId) throws WalletServiceException;

}
