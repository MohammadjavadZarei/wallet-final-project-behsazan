package com.org.walletfinalprojectbehsazan.Dao;


import com.org.walletfinalprojectbehsazan.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
