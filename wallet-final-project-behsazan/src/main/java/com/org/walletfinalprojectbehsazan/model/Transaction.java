package com.org.walletfinalprojectbehsazan.model;

import com.org.walletfinalprojectbehsazan.model.enums.TransactionStatus;
import com.org.walletfinalprojectbehsazan.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transaction")
@Data
@NoArgsConstructor
public class Transaction {
	@Id
	private String transactionId;

	@Basic
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Column
	private Integer amount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	private Wallet wallet;

	public Transaction(String transactionId, TransactionStatus status, TransactionType type, Date transactionDate) {
		super();
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.status = status;
		this.type = type;
	}
	public Transaction(String transactionId, TransactionStatus status, TransactionType type, Wallet wallet, Date transactionDate, Integer amount) {
		super();
		this.transactionId = transactionId;
		this.status = status;
		this.type = type;
		this.wallet = wallet;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}


}




	

