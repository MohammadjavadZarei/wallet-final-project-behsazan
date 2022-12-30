package com.org.walletfinalprojectbehsazan.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {


	@NotNull
	@NotBlank
	private String userName;
	@NotNull
	@NotBlank
	@Size(min = 6)
	private String password;
	@NotNull
	@NotBlank
	private String emailId;
	@NotNull
	@NotBlank
	private String nationalCode;
	@NotNull
	@NotBlank
	private String PhoneNumber;

}
