package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Movie;
import model.Account;

public class AccountDao {
	
	public int getSalesReport(Account account) {
			
			/*
			 * The students code to fetch data from the database will be written here
			 * Query to get sales report for a particular month must be implemented
			 * account, which has details about the month and year for which the sales report is to be generated, is given as method parameter
			 * The month and year are in the format "month-year", e.g. "10-2018" and stored in the dateOpened attribute of account object
			 * The month and year can be accessed by getter method, i.e., account.getAcctCreateDate()
			 */
	
<<<<<<< HEAD
			int income = 0;
					
		ja 	/*Sample data begins*/
			income = 100;
			/*Sample data ends*/
=======
		int income = 0;
		
		String[] date = account.getAcctCreateDate().split("-");
				
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();
>>>>>>> branch 'main' of git@github.com:inukbaik/CSE305.git
			
			String query = "SELECT * FROM Account WHERE DateOpened >= '"+ date[1]+"-"+(date[0]+1)+"-01" + "'";
			
			ResultSet rs = null;
			
			rs = DBHelper.getInstance().executeQuery(query, con);
			if(rs == null){
				System.out.println("Failed");
				return 0;
			}
			
			else {
				while(rs.next()) {
					income += 500;
				}
			}
			

		} catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (con != null)
	                con.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return income;
	}

	
	public String setAccount(String customerID, String accountType) {
	    Connection con = null;
	    try {
	        con = DBHelper.getInstance().getConnection();

	        String query = "UPDATE Account SET Type = ? WHERE Account.Customer = ?";
	        try (PreparedStatement pstmt = con.prepareStatement(query)) {
	            pstmt.setString(1, accountType);
	            pstmt.setString(2, customerID);
	            pstmt.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "failure";
	    } finally {
	        try {
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return "success";
	}
}
	
