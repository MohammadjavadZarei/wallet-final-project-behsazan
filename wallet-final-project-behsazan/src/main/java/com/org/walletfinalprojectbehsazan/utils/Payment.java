package com.org.walletfinalprojectbehsazan.utils;

import com.org.walletfinalprojectbehsazan.Service.TransactionService;
import com.org.walletfinalprojectbehsazan.Service.UserService;
import com.org.walletfinalprojectbehsazan.Service.WalletService;
import com.org.walletfinalprojectbehsazan.exception.PaymentHandlerException;
import com.org.walletfinalprojectbehsazan.exception.TransactionServiceException;
import com.org.walletfinalprojectbehsazan.model.Transaction;
import com.org.walletfinalprojectbehsazan.model.User;
import com.org.walletfinalprojectbehsazan.model.Wallet;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionStatus;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Component
@Transactional
public class Payment {
    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Async
    public void transferMoney(String transactionIdSender, Integer amount, String fromUserName, String toUserName) throws PaymentHandlerException, TransactionServiceException
    {
        try
        {


            //Sender
            User senderDbEntity = userService.getUserDbEntityByUserName(fromUserName);
            Wallet senderWallet = senderDbEntity.getWallet();

            if(senderWallet.getWalletBalance() < amount)
            {
                throw new PaymentHandlerException("Insufficient Balance", HttpStatus.NOT_FOUND);
            }

            senderWallet.setWalletBalance(senderWallet.getWalletBalance() - amount);
            walletService.updateWallet(senderWallet);

            Optional<Transaction> createTransactionForSender = transactionService
                    .updateTransaction(new Transaction(transactionIdSender, TransactionStatus.PENDING,
                            TransactionType.DEBIT, senderWallet, new Date(System.currentTimeMillis()), amount));

            if(createTransactionForSender.isPresent())
            {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Receiver
                User receiverDbEntity = userService.getUserDbEntityByUserName(toUserName);

                if(receiverDbEntity == null)
                {
                    throw new PaymentHandlerException("User validation Failed, No User Profile found with userName:"+toUserName, HttpStatus.NOT_FOUND);
                }
                Wallet receiverWallet = receiverDbEntity.getWallet();
                receiverWallet.setWalletBalance(receiverWallet.getWalletBalance() + amount);
                walletService.updateWallet(receiverWallet);

                String transactionIdReciever = UUID.randomUUID().toString();
                Optional<Transaction> createTransactionForReveiver = transactionService
                        .createTransaction(new Transaction(transactionIdReciever, TransactionStatus.PENDING,
                                TransactionType.CREDIT, receiverWallet, new Date(System.currentTimeMillis()), amount));

                //Update transactions to be successfull
                if(createTransactionForReveiver.isPresent())
                {
                    createTransactionForSender.get().setStatus(TransactionStatus.SUCCESS);
                    createTransactionForReveiver.get().setStatus(TransactionStatus.SUCCESS);
                }

            }
        }catch (Exception e) {
            transactionService.updateTransaction(new Transaction(transactionIdSender, TransactionStatus.FAILED,
                    TransactionType.DEBIT, null, new Date(System.currentTimeMillis()), amount));
            throw new PaymentHandlerException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async
    public void addMoney(String transactionIdSender, String userName, Integer amount) throws PaymentHandlerException, TransactionServiceException
    {
        try
        {
            User userDbEntity = userService.getUserDbEntityByUserName(userName);
            Wallet currentWallet = userDbEntity.getWallet();
            currentWallet.setWalletBalance(currentWallet.getWalletBalance() + amount);

            walletService.updateWallet(currentWallet);

            transactionService.updateTransaction(
                    new Transaction(transactionIdSender, TransactionStatus.SUCCESS, TransactionType.CREDIT,
                            currentWallet, new Date(System.currentTimeMillis()), amount));
        }catch (Exception e) {
            transactionService.updateTransaction(
                    new Transaction(transactionIdSender, TransactionStatus.FAILED, TransactionType.CREDIT, null, new Date(System.currentTimeMillis()), amount));
            throw new PaymentHandlerException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
