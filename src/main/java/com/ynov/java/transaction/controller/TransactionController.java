package com.ynov.java.transaction.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.java.transaction.dao.TransactionDAO;
import com.ynov.java.transaction.model.Transaction;


@Controller
@RestController
public class TransactionController {
	
	@Autowired
    private  TransactionDAO transactionRepository ;
	
	
	@RequestMapping(value= "/listTransaction", method=RequestMethod.GET)
	public List<Transaction> listTransaction() {
		
		return transactionRepository.findAll();
		
	}
	
	@RequestMapping(value= "/transById/{id}", method=RequestMethod.GET)
	public Transaction getTransactonById(@PathVariable long id) {
		
		return transactionRepository.findById(id);
		
	}
	
	@RequestMapping(value= "/transByAccount/{accountId}", method=RequestMethod.GET)
	public Map<String,Object> getTransactonByAccount(@PathVariable long accountId) {
		
		Map<String,Object> map = new HashMap<>();
		List<Transaction> ts = transactionRepository.findAllBySenderAccount(accountId);
		List<Transaction> tr = transactionRepository.findAllByReceiverAccount(accountId);
		map.put("Send", ts);
		map.put("Receive", tr);
		
		return map;	
	}
	
	@RequestMapping(value="/transByType/{type}/{accountId}", method=RequestMethod.GET)
	public List<Transaction> getTransactionReceivedById (@PathVariable String type,@PathVariable long accountId){
		
		if(type.equals("sent")) {
			return transactionRepository.findAllBySenderAccount(accountId);			
		}else if(type.equals("received")){
			return transactionRepository.findAllByReceiverAccount(accountId);
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/transCount/{accountId}", method=RequestMethod.GET)
	public Map<String,Integer> getNumberOfTransactionById(@PathVariable long accountId) {
	
		List<Transaction> ts = transactionRepository.findAllBySenderAccount(accountId);
		List<Transaction> tr = transactionRepository.findAllByReceiverAccount(accountId);
		
		Map<String,Integer> map = new HashMap<>();
		map.put("NumberOfTransaction", ts.size() + tr.size());
		return map;
	}
	
	@RequestMapping(value="/transfer", method=RequestMethod.POST)
	public Transaction createTransaction(@RequestBody Transaction trans) {
		
		Transaction t = transactionRepository.saveAndFlush(trans);
		
		return t;
	}
	
	@RequestMapping(value= "/transByDate/{date}", method=RequestMethod.GET)
	public List<Transaction> getAllTransactonsByDate(@PathVariable String date) {
		
		try {
			Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
			Date now = new Date(System.currentTimeMillis());
			
			return transactionRepository.findAllByDateDemandeTransactionBetween(dt, now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value= "/transByDate/{type}/{dateStart}/{dateEnd}", method=RequestMethod.GET)
	public Map<String,Object> getAllTransactonsBetweenDateAndType(@PathVariable String type, @PathVariable String dateStart, @PathVariable String dateEnd) {
		
		Map<String,Object> map = new HashMap<>();
		
		
		try {
			Date dtStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStart);
			Date dtEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateEnd);
			
			if(type.equals("pending")) {
				map.put("pending", transactionRepository.findAllByDateDemandeTransactionBetween(dtStart, dtEnd));
			}else if(type.equals("terminated")) {
				map.put("terminated",transactionRepository.findAllByDateTraitementTransactionBetween(dtStart, dtEnd));
			}else if(type.equals("all")){
				map.put("pending", transactionRepository.findAllByDateDemandeTransactionBetween(dtStart, dtEnd));
				map.put("terminated",transactionRepository.findAllByDateTraitementTransactionBetween(dtStart, dtEnd));
			}else {
				map.put("error", "Unknown type");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value= "/transByDate/{accountId}/{type}/{dateStart}/{dateEnd}", method=RequestMethod.GET)
	public Map<String,Object> getAllTransactonsByIdAndTypeBetweenDate(@PathVariable long accountId,@PathVariable String type, @PathVariable String dateStart, @PathVariable String dateEnd) {
		
		Map<String,Object> map = new HashMap<>();
		
		try {
			Date dtStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStart);
			Date dtEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateEnd);
			
			if(type.equals("pending")) {
				map.put("pending", transactionRepository.findAllBySenderAccountAndDateDemandeTransactionBetween(accountId,dtStart, dtEnd));
			}else if(type.equals("terminated")) {
				map.put("terminated",transactionRepository.findAllBySenderAccountAndDateTraitementTransactionBetween(accountId,dtStart, dtEnd));
			}else if(type.equals("all")){
				map.put("pending", transactionRepository.findAllBySenderAccountAndDateDemandeTransactionBetween(accountId,dtStart, dtEnd));
				map.put("terminated",transactionRepository.findAllBySenderAccountAndDateTraitementTransactionBetween(accountId,dtStart, dtEnd));
			}else {
				map.put("error", "Unknown type");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
}
