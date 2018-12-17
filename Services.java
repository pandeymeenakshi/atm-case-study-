package ATMWithJava;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Services implements ServicesInterface  {

	private DatabaseInterface databaseObject;
	private ResourceBundle r;

	public Services() throws Exception {
		r = ResourceBundle.getBundle("config");
		String database = r.getString("database");
		Class<?> classObject = Class.forName(database);
		Object object = classObject.newInstance();
		databaseObject = (DatabaseInterface) object;
	}


	public int updateData(Account accountObject) {
		int value = databaseObject.updateObject(accountObject);
		return value;
	}

	
	public int authenticateUser(Account accountObject) {
		int value = databaseObject.authenticateUser(accountObject);
		return value;
	}



	public int updateTransactionList(List<Transaction> list) {
		int value = 0;
		for(Transaction trans: list) 
		value = updateTransaction(trans);	
		return value;
	}

	public int updateTransaction(Transaction transactionObject) {
		int value = databaseObject.updateTransaction(transactionObject);
		return value;
	}


	public List<Transaction> getTransactionList(int accountNumber) {
		Transaction transactionObject = new Transaction(accountNumber, null, null, 0, 0.0);
		List<Transaction> transactionList=null;
		transactionList = databaseObject.getTransactionList(transactionObject);
		Collections.reverse(transactionList);
		return transactionList;
	}	

}
