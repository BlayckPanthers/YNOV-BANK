package com.ynov.java.transaction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ynov.java.transaction.model.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction,Long> {
	List<Transaction> findAllBySenderAccount(long senderAccount);
	List<Transaction> findAllByReceiverAccount(long receiverAccount);
	Transaction findById(long id);
	
	List<Transaction> findAllByDateDemandeTransactionBetween(Date dateStart, Date dateEnd);
	List<Transaction> findAllByDateTraitementTransactionBetween(Date dateStart, Date dateEnd);
	
	List<Transaction> findAllBySenderAccountAndDateDemandeTransactionBetween(long id ,Date dateStart, Date dateEnd);
	List<Transaction> findAllBySenderAccountAndDateTraitementTransactionBetween(long id, Date dateStart, Date dateEnd);
}
