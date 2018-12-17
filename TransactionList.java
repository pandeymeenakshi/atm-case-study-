package ATMWithJava;

import java.util.*;

public class TransactionList {	
	
	static List<Transaction> list = new ArrayList<Transaction>();
	List<Transaction> transactionList =  new ArrayList<Transaction>();
	public void addTransaction(Transaction z) {
		list.add(z);
	}
	
	public static List<Transaction> getTransaction(){
		return list;
	}
	
	public List<Transaction> getTransactionsList(){
		return transactionList;
	}
}
