package com.org.walletfinalprojectbehsazan.Service.Imp;

import com.org.walletfinalprojectbehsazan.Service.PaymentService;
import com.org.walletfinalprojectbehsazan.Service.TransactionService;
import com.org.walletfinalprojectbehsazan.exception.PaymentGatewayServiceException;
import com.org.walletfinalprojectbehsazan.model.Transaction;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionStatus;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionType;
import com.org.walletfinalprojectbehsazan.utils.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private Payment payment;

    @Autowired
    private TransactionService transactionService;

    @Override
    public String addMoney(String userName, Integer amount) throws PaymentGatewayServiceException
    {
        String transactionId = null;
        try
        {
            transactionId = UUID.randomUUID().toString();
            transactionService.createTransaction(
                    new Transaction(transactionId, TransactionStatus.NEW, TransactionType.CREDIT, null, new Date(System.currentTimeMillis()), amount));
            LOGGER.debug("Transaction to add money started at:{}, TransactionId:{}", System.currentTimeMillis(), transactionId);

            payment.addMoney(transactionId, userName, amount);
        }catch (Exception e) {
            throw new PaymentGatewayServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return transactionId;
    }

    @Override
    public String transferMoney(String fromUserName, String toUserName, Integer amount) throws PaymentGatewayServiceException
    {
        String transactionId = null;
        try
        {
            transactionId = UUID.randomUUID().toString();
            transactionService.createTransaction(new Transaction(
                    transactionId, TransactionStatus.NEW, TransactionType.DEBIT, new Date(System.currentTimeMillis())));

            LOGGER.debug("Transaction to transfer money started at:{}, TransactionId:{}", System.currentTimeMillis(), transactionId);
            payment.transferMoney(transactionId, amount, fromUserName, toUserName);
        }catch (Exception e) {
            throw new PaymentGatewayServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return transactionId;
    }

}
