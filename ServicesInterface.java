package ATMWithJava;

import java.util.List;

public interface ServicesInterface {
	public int updateData(Account accountObject);
	public int authenticateUser(Account accountObject);
	public int updateTransaction(Transaction transactionObject);
	public int updateTransactionList(List<Transaction> listObject);
	public List<Transaction> getTransactionList(int accountNumber);
}
