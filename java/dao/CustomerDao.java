package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Customer;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers() {
		/*
		 * This method fetches one or more customers and returns it as an ArrayList
		 */
		
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		/*Sample data begins*/
		Connection con = null;
		try {
			
			con = DBHelper.getInstance().getConnection();
			ResultSet rs = null;
			
			String query = 
					"SELECT  P.SSN, C.Email, C.CreditCardNumber, C.Rating "+
					"CSE305_2.Person P, CSE305_2.Customer C "+
					"where  C.CustomerId = P.SSN ";
			
			rs = DBHelper.getInstance().executeQuery(query, con);
			
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("SSN"));
				customer.setEmail(rs.getString("Email"));
				customer.setCreditCard(rs.getString("CreditCardNumber"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
				
			}
		} catch (SQLException e) {
			
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} 
		
		return customers;
	}
		/*Sample data ends*/
		

	public Customer getHighestRevenueCustomer() {
		
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
	
	    Customer customer = new Customer();
	    
	    Connection con = null;
	    try {
	        con = DBHelper.getInstance().getConnection();
	        ResultSet rs = null;
	        //need to fix later
	        String query = "SELECT ((year(NOW())*12+month(NOW())) - (year(DateOpened)*12+month(DateOpened)))*BookingFee AS result, CustomerId FROM Account ORDER BY result DESC LIMIT 1"; 

	        rs = DBHelper.getInstance().executeQuery(query, con);
	        
	        if(rs == null){
				System.out.println("no customers found");
				if (con != null)
					con.close();
				return null;
			}

	        try {
	            if (rs.next()) {
	                String customerId = rs.getString("CustomerId");
	                customer = this.getCustomer(customerId);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();  
	        
	    } 
	    
	    return customer;
	}


	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		
		List<Customer> customers = new ArrayList<Customer>();
		/*Sample data begins*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBHelper.getInstance().getConnection();
			
			String query = 
					"SELECT  P.SSN, C.Email, C.CreditCardNumber, C.Rating "+
					"CSE305_2.Person P, CSE305_2.Customer C "+
					"where  C.CustomerId = P.SSN ";
			
			rs = DBHelper.getInstance().executeQuery(query, con);

			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("SSN"));
				customer.setEmail(rs.getString("Email"));
				customer.setCreditCard(rs.getString("CreditCardNumber"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}

		} catch (SQLException e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		/*Sample data ends*/
		return customers;
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		/*Sample data begins*/
		Customer customer = new Customer();
		Connection con = null;
		
		try {
			
			con = DBHelper.getInstance().getConnection();
			
			String query = 
					"SELECT  P.SSN, C.CreditCardNumber, C.Rating "+
					"FROM cse305_2.Location L, cse305_2.Person P, cse305_2.Customer C "+
					"where C.CustomerId = P.SSN and P.SSN = "+ customerID;

			ResultSet rs = null;
			
			rs = DBHelper.getInstance().executeQuery(query, con);

			if (rs.next()) {
				customer.setCustomerID(rs.getString("SSN"));
				customer.setEmail(rs.getString("Email"));
				customer.setCreditCard(rs.getString("CreditCardNumber"));
				customer.setRating(rs.getInt("Rating"));
			}


		} catch (SQLException e) {
			
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		/*Sample data ends*/
		
		return customer;
	}
	
	public String deleteCustomer(String customerID) {

		Connection con = null;
		try {
			
			con = DBHelper.getInstance().getConnection();
			
			String query = "DELETE FROM cse305_2.Customer WHERE CustomerId = "
					+ customerID;
	
			DBHelper.getInstance().execute(query, con);

		} catch (Exception e) {
	
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return "failure";
		}

		try {
			
			con = DBHelper.getInstance().getConnection();
			
			String query = "DELETE FROM cse305_2.Person WHERE SSN = "
					+ customerID;

			DBHelper.getInstance().execute(query, con);

		} catch (Exception e) {
			
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return "failure";
		}

		try {
			
			con = DBHelper.getInstance().getConnection();
			
			String query = "DELETE FROM cse305_2.Account WHERE Id = "
					+ customerID;

			DBHelper.getInstance().execute(query, con);

			} catch (Exception e) {
			
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return "failure";
		}

		/*Sample data begins*/
		return "success";
		/*Sample data ends*/
		
	}


	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */

		String customerID = null;
		Connection con = null;
		try {
			
			con = DBHelper.getInstance().getConnection();
			
			//username is email
			String query = "SELECT Id FROM cse305_2.Customer WHERE Email = '" + username + "'";

			ResultSet rs = null;
			
			rs = DBHelper.getInstance().executeQuery(query, con);
			
			if(rs == null){
				System.out.println("Failed");
				if (con != null)
					con.close();
				return null;
			}
			
			if (rs.next()) {
				customerID = rs.getString("Id");
			}
			
		} catch (SQLException e) {
			
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "failure";
			
		} 
		return customerID;
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();
		
		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setCustomerID("111-11-1111");
			customer.setAddress("123 Success Street");
			customer.setLastName("Lu");
			customer.setFirstName("Shiyong");
			customer.setCity("Stony Brook");
			customer.setState("NY");
			customer.setEmail("shiyong@cs.sunysb.edu");
			customer.setZipCode(11790);
			customers.add(customer);			
		}
		/*Sample data ends*/
		
		return customers;

	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		/*Sample data begins*/
		Connection con = null;

		String[] queries = new String[3];

		queries[0] = String.format(
				"INSERT INTO  cse305_2.Location " +
				"values(\'%s\',\'%s\', \'%s\');",
			Integer.toString(customer.getZipCode()),
			customer.getCity(),
			customer.getState());

		queries[1] = String.format(
				"INSERT INTO  cse305_2.Person " +
				"values(\'%s\',\'%s\', \'%s\', \'%s\', %s, \'%s\' ,\'%s\')",
			customer.getCustomerID(),
			customer.getLastName(),
			customer.getFirstName(),
			customer.getAddress(),
			customer.getZipCode(),
			customer.getTelephone());

		queries[2] = String.format(
				"INSERT INTO  cse305_2.Customer " +
				"values(\'%s\',\'%s\', \'%s\');",
			customer.getCustomerID(),
			customer.getEmail(),
			Integer.toString(customer.getRating()),
			customer.getCreditCard());

		try {

			con = DBHelper.getInstance().getConnection();
			
			for(int i=0; i<queries.length;i++) {
				System.out.println(queries[i]);
				DBHelper.getInstance().execute(queries[i], con);
				
			}			
			
		} catch (Exception e) {
			
			try {
				if (con != null)
					con.close();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return "failure";
			
		} 
		return "success";
		/*Sample data ends*/

	}

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		Connection con = null;

	    String updateLocationQuery = String.format(
	            "UPDATE cse305_2.Location SET City='%s', State='%s' WHERE ZipCode='%s';",
	            customer.getCity(),
	            customer.getState(),
	            Integer.toString(customer.getZipCode())
	    );

	    String updatePersonQuery = String.format(
	            "UPDATE cse305_2.Person SET LastName='%s', FirstName='%s', Address='%s', ZipCode=%s, Telephone='%s' WHERE SSN='%s';",
	            customer.getLastName(),
	            customer.getFirstName(),
	            customer.getAddress(),
	            Integer.toString(customer.getZipCode()),
	            customer.getTelephone(),
	            customer.getCustomerID()
	    );

	    String updateCustomerQuery = String.format(
	            "UPDATE cse305_2.Customer SET Email='%s', Rating=%s, CreditCardNumber='%s' WHERE CustomerId='%s';",
	            customer.getEmail(),
	            Integer.toString(customer.getRating()),
	            customer.getCreditCard(),
	            customer.getCustomerID()
	    );

	    try {
	        con = DBHelper.getInstance().getConnection();

	        // Execute update queries
	        DBHelper.getInstance().execute(updateLocationQuery, con);
	        DBHelper.getInstance().execute(updatePersonQuery, con);
	        DBHelper.getInstance().execute(updateCustomerQuery, con);

	    } catch (Exception e) {

	        try {
	            if (con != null)
	                con.close();

	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }

	        e.printStackTrace();
	        return "failure";

	    } finally {
	        try {
	            if (con != null)
	                con.close();

	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return "success";
		/*Sample data begins*/
		//return "success";
		/*Sample data ends*/

	}

}
