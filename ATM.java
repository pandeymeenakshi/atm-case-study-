package ATMWithJava;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ATM {

	private ResourceBundle r;
	
	private ServicesInterface servicesInterface;
	private Scanner sc;
	private Account accountObject;
	private int userAuthenticated;
	private int count;
	
	

	public ATM() throws Exception {
		sc = new Scanner(System.in);
		r = ResourceBundle.getBundle("config");
		String service = r.getString("service");
		Class<?> classObject = Class.forName(service);
		Object object = classObject.newInstance();
		servicesInterface = (ServicesInterface) object;
		userAuthenticated = 1;
		count = 0;		
	}

	public void run() throws SQLException {
		System.out.println(r.getString("greeting"));
		while (userAuthenticated != 2) {
			authenticateUser();
		}
		performTransactions();
		System.out.println("\nThank you! Goodbye!");
	}

	private void performTransactions() {
		boolean userExited = false;
		int amount;
		while (!userExited) {

			int mainMenuSelection = displayMainMenu();
			switch (mainMenuSelection) {
			case 1:
				System.out
						.println("Balance is = " + accountObject.getBalance());
				break;
			case 2:
				System.out.print("Enter amount to withdraw = ");
				amount = sc.nextInt();
				if (amount > accountObject.getBalance()) {
					System.out.println("Insufficient fund");
					System.out.println("Available balance is = "
							+ accountObject.getBalance());
				} else {
					accountObject.debit(amount);
//					int update = servicesInterface.updateData(accountObject);
//					if (update > 0) {
//						System.out.println("Withdrawal successful");
//						System.out.println("Available balance is = "
//								+ accountObject.getBalance());
//					} else {
//						accountObject.credit(amount);
//						System.out.println("System is busy try after sometime");
//					}
				}
				break;
			case 3:
				System.out.print("Enter amount to deposit = ");
				amount = sc.nextInt();
				accountObject.credit(amount);
//				int update = servicesInterface.updateData(accountObject);
//				if (update > 0) {
//					System.out.println("Deposit successful");
//					System.out.println("Available balance is = "
//							+ accountObject.getBalance());
//				} else {
//					accountObject.debit(amount);
//					System.out.println("System is busy try after sometime");
//				}
				break;
			case 4:
				listTransaction();
				break;
			case 5:
				if (count < 3) {
					while (count < 3) {
						System.out.print("Enter your old pin = ");
						int pin = sc.nextInt();
						if (pin == accountObject.getPin()) {
							boolean matchPin = true;
							while (matchPin) {
								System.out.print("Enter new pin = ");
								int newPin = sc.nextInt();
								System.out.print("Confirm new pin = ");
								int confirmPin = sc.nextInt();							
								if (newPin == confirmPin) {
									accountObject.setPin(confirmPin);
									matchPin = false;
//									int value = servicesInterface.updateData(accountObject);
//									if (value > 0) {
//										System.out.println("Pin is updated");
//										matchPin = false;
//									} else {
//										System.out.println("Pin is not updated");
//										System.out.println("System is busy try after sometime");
//										accountObject.setPin(pin);										
//										matchPin = false;
//									}
									count = 4;
								} else {
									System.out.println("New pin and confirm pin not match");
								}
							}
						} else {
							System.out.println("Pin is wrong. Try again..");
							count++;
						}
						if (count == 3) {
							userExited = exitSystem(userExited);
							
						}
					}
				} else {
					userExited = exitSystem(userExited);
				}
				break;
			case 6:
				servicesInterface.updateData(accountObject);
				servicesInterface.updateTransactionList(TransactionList.getTransaction());
				System.out.println("\nExiting the system...");
				userExited = true;
				break;
			default:
				System.out.println("\nYou did not enter a valid selection. Try again.");
				break;
			}
		}
	}

	
	private void listTransaction() {
		boolean userExited = false;
		
		while (!userExited) {

//			System.out.println("2 - Date to Date Transaction");
//			System.out.println("3 - From Date Transaction");

			List<Transaction> transactionList = servicesInterface.getTransactionList(accountObject.getAccountNumber());
			int transactionMenuSelection = transactionListMenu();
			switch (transactionMenuSelection) {
			case 1:
				for (Transaction tranObject : transactionList) {
					displayTransactionList(tranObject);					
				}				
				System.out.println("*****************************************************************");				
				break;
			case 2:
				System.out.print("Enter date to get transaction from = ");
				String fDate = sc.next();
				Date fdate = Date.valueOf(fDate);
				System.out.print("Enter date to get transaction to   = ");
				String tDate = sc.next();
				Date tdate = Date.valueOf(tDate);
				boolean check = true;
				for (Transaction tranObject : transactionList) {
					if(tranObject.getDate().before(tdate) || tranObject.getDate().equals(tdate)){
						if(tranObject.getDate().after(fdate) || tranObject.getDate().equals(fdate)){
							displayTransactionList(tranObject);
							check = false;
						}
					}
				}	
				if(check) System.out.println(" No Transaction");
				break;
			case 3:
				System.out.print("Enter date to get transaction from = ");
				String fromDates = sc.next();
				Date date3 = Date.valueOf(fromDates);
				for (Transaction tranObject : transactionList) {
					if(tranObject.getDate().before(date3)) break;
					displayTransactionList(tranObject);	
				}	
				
				break;
			case 4:
				int count = 0;
				for (Transaction tranObject : transactionList) {
					if(count==3) break;
					displayTransactionList(tranObject);	
					count++;
				}				
				break;
			case 5:
				userExited = true;
				break;
			default:
				System.out.println("\nYou did not enter a valid selection. Try again.");
				break;
			}
		}
	}

	private void displayTransactionList(Transaction tranObject) {
		System.out.println("********************** Transaction Details **********************");
		System.out.println("Date    = "+tranObject.getDate());
		System.out.println("Type    = "+tranObject.getType());
		System.out.println("Amount  = "+tranObject.getAmount());
		System.out.println("Balance = "+tranObject.getBalance());		
	}

	private void authenticateUser() throws SQLException {
		System.out.print("\nPlease enter your account number = ");
		int accountNumber = sc.nextInt();
		System.out.print("\nEnter your PIN = ");
		int pin = sc.nextInt();

		accountObject = new Account(accountNumber, pin, 0);
		userAuthenticated = servicesInterface.authenticateUser(accountObject);
		if (userAuthenticated != 2) {
			System.out
					.println("Invalid account number or PIN. Please try again.");
		}
	}
	
	private static boolean exitSystem(boolean userExited) {
		System.out.println("Too many attempts, Account is locked");
		System.out.println("\nExiting the system...");
		return userExited = true;
	}

	private int transactionListMenu() {
		System.out.println("\nTransaction menu:");
		System.out.println("1 - All Transaction");
		System.out.println("2 - Date to Date Transaction");
		System.out.println("3 - From Date Transaction");
		System.out.println("4 - Last 3 Transaction");
		System.out.println("5 - Main menu\n");
		System.out.print("Enter a choice: ");
		return sc.nextInt();
	}

	

	private int displayMainMenu() {
		System.out.println("\nMain menu:");
		System.out.println("1 - View my balance");
		System.out.println("2 - Withdraw cash");
		System.out.println("3 - Deposit funds");
		System.out.println("4 - Transaction List");
		System.out.println("5 - Change pin");
		System.out.println("6 - Exit\n");
		System.out.print("Enter a choice: ");
		return sc.nextInt();
	}

	

}
