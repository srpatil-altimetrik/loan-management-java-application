package com.altimetrik;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private int customerId;
	private String customerName;
	private String customerEmail;
	private String customerPhoneNo;
	private String customerAddress;
	private int customerCreditScore;
}
