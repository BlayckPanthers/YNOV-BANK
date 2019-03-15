package com.ynov.java.transaction.model;


import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.util.Date;




@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private long senderAccount;
	@Column(nullable = false)
	private long receiverAccount;
	@Column(nullable = false)
	private Double amount;
	private Date dateDemandeTransaction;
	private Date dateTraitementTransaction;
	
	
	
	public Transaction() {
	}
	
	public Transaction(long id, Integer senderAccount, Integer receiverAccount, Double amount,
			Date dateDemandeTransaction, Date dateTraitementTransaction) {
		super();
		this.id = id;
		this.senderAccount = senderAccount;
		this.receiverAccount = receiverAccount;
		this.amount = amount;
		this.dateDemandeTransaction = dateDemandeTransaction;
		this.dateTraitementTransaction = dateTraitementTransaction;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSenderAccount() {
		return senderAccount;
	}
	public void setSenderAccount(Integer senderAccount) {
		this.senderAccount = senderAccount;
	}
	public long getReceiverAccount() {
		return receiverAccount;
	}
	public void setReceiverAccount(Integer receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getDateDemandeTransaction() {
		return dateDemandeTransaction;
	}
	public void setDateDemandeTransaction(Date dateDemandeTransaction) {
		this.dateDemandeTransaction = dateDemandeTransaction;
	}
	public Date getDateTraitementTransaction() {
		return dateTraitementTransaction;
	}
	public void setDateTraitementTransaction(Date dateTraitementTransaction) {
		this.dateTraitementTransaction = dateTraitementTransaction;
	}
	
	
}
