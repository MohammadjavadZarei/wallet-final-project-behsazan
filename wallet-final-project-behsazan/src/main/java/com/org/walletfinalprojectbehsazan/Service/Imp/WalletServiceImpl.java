package com.org.walletfinalprojectbehsazan.Service.Imp;

import com.org.walletfinalprojectbehsazan.Dao.WalletRepository;
import com.org.walletfinalprojectbehsazan.Service.PaymentService;
import com.org.walletfinalprojectbehsazan.Service.TransactionService;
import com.org.walletfinalprojectbehsazan.Service.UserService;
import com.org.walletfinalprojectbehsazan.Service.WalletService;
import com.org.walletfinalprojectbehsazan.exception.WalletServiceException;
import com.org.walletfinalprojectbehsazan.model.Dto.MoneyTransferDetails;
import com.org.walletfinalprojectbehsazan.model.Dto.PassbookDetails;
import com.org.walletfinalprojectbehsazan.model.Transaction;
import com.org.walletfinalprojectbehsazan.model.User;
import com.org.walletfinalprojectbehsazan.model.Wallet;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WalletServiceImpl implements WalletService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    private static final String DATE = "Date";
    private static final String ACTION = "Action";
    private static final String AMOUNT = "Amount";

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Override
    public Optional<Wallet> createWallet(Wallet wallet) throws WalletServiceException
    {
        try
        {
            if(wallet != null)
            {
                LOGGER.debug("Wallet creation at:{}", System.currentTimeMillis());
                return Optional.of(walletRepository.save(wallet));
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Wallet> updateWallet(Wallet wallet) throws WalletServiceException
    {
        try
        {
            if(wallet != null)
            {
                LOGGER.debug("Wallet updation at:{}", System.currentTimeMillis());
                return Optional.of(walletRepository.save(wallet));
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Wallet> deleteWallet(Integer walletId) throws WalletServiceException
    {
        try
        {
            Optional<Wallet> fetchedWallet = walletRepository.findById(walletId);
            if(fetchedWallet.isPresent())
            {
                LOGGER.debug("Wallet deletion at:{}", System.currentTimeMillis());
                walletRepository.delete(fetchedWallet.get());
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Wallet> getWallet(Integer walletId) throws WalletServiceException
    {
        try
        {
            if(walletId != null)
            {
                LOGGER.debug("Wallet retrieval at:{}", System.currentTimeMillis());
                return walletRepository.findById(walletId);
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public void addMoney(String userName, Integer moneyToBeAdded) throws WalletServiceException
    {
        try
        {
            LOGGER.debug("Adding money to wallet, Initiated at:{}", System.currentTimeMillis());
            paymentService.addMoney(userName, moneyToBeAdded);
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String payToOtherUser(MoneyTransferDetails moneyTransferDetails) throws WalletServiceException
    {
        try
        {
            LOGGER.debug("Transfer money call to PaymentGateway at:{}", System.currentTimeMillis());
            return paymentService.transferMoney(
                    moneyTransferDetails.getFromUserName(), moneyTransferDetails.getToUserName(), moneyTransferDetails.getAmount());
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PassbookDetails viewPassbook(String userName) throws WalletServiceException
    {
        PassbookDetails passbookDetails = new PassbookDetails();
        LOGGER.debug("View Passbook Triggered at:{}", System.currentTimeMillis());
        try
        {
            User userByUserName = userService.getUserDbEntityByUserName(userName);
            Wallet currentUserWallet = userByUserName.getWallet();
            List<Transaction> transactions = currentUserWallet.getTransactions();
            LOGGER.debug("No. of Transaction fetched:{}", transactions.size());

            if(transactions != null && !transactions.isEmpty())
            {
                List<Map<String, Object>> passbookData = new ArrayList<>();
                for(Transaction transaction :transactions)
                {
                    Map<String, Object> passbook = new LinkedHashMap<>();
                    passbook.put(DATE, transaction.getTransactionDate());
                    passbook.put(ACTION, transaction.getType());
                    passbook.put(AMOUNT, transaction.getAmount());
                    passbookData.add(passbook);
                }
                passbookDetails.setColumnHeaders(Arrays.asList(DATE, ACTION, AMOUNT));
                passbookDetails.setPassbookData(passbookData);
                passbookDetails.setCurrentBalance(currentUserWallet.getWalletBalance());
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return passbookDetails;
    }

    @Override
    public String getTransactionStatus(String transactionId) throws WalletServiceException
    {
        try
        {
            Optional<Transaction> transaction = transactionService.getTransaction(transactionId);
            if(transaction.isPresent())
            {
                return transaction.get().getStatus().toString();
            }else
            {
                LOGGER.debug("No. Transaction found for Id:{}", transactionId);
            }
        }catch (Exception e) {
            throw new WalletServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return TransactionStatus.FAILED.toString();
    }


}
