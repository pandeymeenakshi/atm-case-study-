package ATMWithJava;

public class ATMCaseStudy {
	public static void main(String[] args) {
		try {
			ATM theATM = new ATM();
			theATM.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("system too busy try after sometime");
			// e.printStackTrace();
		}
	}
}
