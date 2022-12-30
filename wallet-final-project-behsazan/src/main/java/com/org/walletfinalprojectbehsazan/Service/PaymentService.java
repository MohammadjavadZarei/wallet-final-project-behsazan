package com.org.walletfinalprojectbehsazan.Service;

import com.org.walletfinalprojectbehsazan.exception.PaymentGatewayServiceException;

public interface PaymentService {

    public String addMoney(String userName, Integer amount) throws PaymentGatewayServiceException;
    public String transferMoney(String fromUserName, String toUserName, Integer amount) throws PaymentGatewayServiceException;

}
