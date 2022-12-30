package com.org.walletfinalprojectbehsazan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer userId;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column
	private String nationalCode;

	@Column
	private String phoneNumber;


	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "walletid", referencedColumnName = "walletid")
	private Wallet wallet;

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}


}
	
