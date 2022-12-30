package com.org.walletfinalprojectbehsazan.controllers;


import com.org.walletfinalprojectbehsazan.Service.WalletService;
import com.org.walletfinalprojectbehsazan.exception.WalletServiceException;
import com.org.walletfinalprojectbehsazan.model.Dto.MoneyTransferDetails;
import com.org.walletfinalprojectbehsazan.model.Dto.PassbookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
		
	@Autowired
	private WalletService walletService;
	
	@PostMapping("/addMoney")
	public void addMoney(@RequestParam Integer moneyToBeAdded, @RequestParam String userName) throws WalletServiceException
	{
		walletService.addMoney(userName, moneyToBeAdded);

	}
	
	@PostMapping("/payToOtherUser")
	public String payToOtherUser(@RequestBody MoneyTransferDetails moneyTransferDetails) throws WalletServiceException
	{
		return walletService.payToOtherUser(moneyTransferDetails);
	}
	
	@GetMapping("/viewPassbook")
	public PassbookDetails viewPassbook(@RequestParam String userName) throws WalletServiceException
	{
		return walletService.viewPassbook(userName);
	}
	
	@GetMapping("/viewTransactionStatus")
	public String viewTransactionStatus(@RequestParam String transactionid) throws WalletServiceException
	{
		return walletService.getTransactionStatus(transactionid);
	}
	
}
