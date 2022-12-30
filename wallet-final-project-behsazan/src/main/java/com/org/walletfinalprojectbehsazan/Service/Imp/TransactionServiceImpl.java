package com.org.walletfinalprojectbehsazan.Service.Imp;

import com.org.walletfinalprojectbehsazan.Dao.TransactionRepository;
import com.org.walletfinalprojectbehsazan.Service.TransactionService;
import com.org.walletfinalprojectbehsazan.exception.TransactionServiceException;
import com.org.walletfinalprojectbehsazan.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Optional<Transaction> createTransaction(Transaction transaction) throws TransactionServiceException
    {
        try
        {
            if(transaction != null)
            {
                return Optional.of(transactionRepository.save(transaction));
            }
        }catch (Exception e) {
            throw new TransactionServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction transaction) throws TransactionServiceException
    {
        try
        {
            if(transaction != null)
            {

                return Optional.of(transactionRepository.save(transaction));
            }
        }catch (Exception e) {
            throw new TransactionServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> deleteTransaction(String transactionId) throws TransactionServiceException
    {
        try
        {
            Optional<Transaction> fetchedTransaction = transactionRepository.findById(transactionId);
            if(fetchedTransaction.isPresent())
            {

                transactionRepository.delete(fetchedTransaction.get());
            }
        }catch (Exception e) {
            throw new TransactionServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> getTransaction(String transactionId) throws TransactionServiceException
    {
        try
        {
            if(transactionId != null)
            {

                return transactionRepository.findById(transactionId);
            }
        }catch (Exception e) {
            throw new TransactionServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Optional.empty();
    }
}
