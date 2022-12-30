package com.org.walletfinalprojectbehsazan.Service;

import com.org.walletfinalprojectbehsazan.exception.TransactionServiceException;
import com.org.walletfinalprojectbehsazan.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface TransactionService {

    public Optional<Transaction> createTransaction(Transaction transaction) throws TransactionServiceException;
    public Optional<Transaction> updateTransaction(Transaction transaction) throws TransactionServiceException;
    public Optional<Transaction> deleteTransaction(String transactionId) throws TransactionServiceException;
    public Optional<Transaction> getTransaction(String transactionId) throws TransactionServiceException;
}
