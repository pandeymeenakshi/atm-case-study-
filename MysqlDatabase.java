package ATMWithJava;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlDatabase implements DatabaseInterface {

	private Connection connection;
	public MysqlDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/atm", "root", "root");

	}

	public int authenticateUser(Account accountObject) {
		int value = 1;
		Statement statement;
		try {
			statement = connection.createStatement();
			String sql = "select * from account where AccountNumber="
					+ accountObject.getAccountNumber() + " and PIN="
					+ accountObject.getPin();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				accountObject.setBalance(rs.getDouble(3));
				value = 2;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}


	public int updateObject(Account accountObject) {
		int value = 0;
		try {
			String query = "update account set PIN = ?, Balance = ? where AccountNumber = ?";
			PreparedStatement preparedStmt;
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, accountObject.getPin());
			preparedStmt.setDouble(2, accountObject.getBalance());
			preparedStmt.setInt(3, accountObject.getAccountNumber());
			value = preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	public int updateTransaction(Transaction transactionObject) {
		int value = 0;
		try {
			String query = "Insert into transaction values(?,?,?,?,?)";
			PreparedStatement preparedStmt;
			preparedStmt = connection.prepareStatement(query);	
			preparedStmt.setInt(1, transactionObject.getAccountNumber());
			preparedStmt.setDate(2, transactionObject.getDate());
			preparedStmt.setString(3, transactionObject.getType());
			preparedStmt.setInt(4, transactionObject.getAmount());
			preparedStmt.setDouble(5, transactionObject.getBalance());
			
			value = preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	
	public List<Transaction> getTransactionList(Transaction transactionObject) {
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		Statement statement;
		try {
			statement = connection.createStatement();
			String sql = "select * from transaction where AccountNumber="
					+ transactionObject.getAccountNumber();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				int Number = transactionObject.getAccountNumber();
				Date date = rs.getDate(2);
				String type = rs.getString(3);
				int amount = rs.getInt(4);
				double balance = rs.getDouble(5);
				Transaction transaction = new Transaction(Number, date, type, amount, balance);
				transactionList.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactionList;
	}
}
