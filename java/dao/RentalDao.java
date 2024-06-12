package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Rental;

public class RentalDao {
	
	public List<Rental> getOrderHisroty(String customerID) {
		
		List<Rental> rentals = new ArrayList<Rental>();
		  
		  Connection con = null;

		  try {
			  con = DBHelper.getInstance().getConnection();
		   String query = "SELECT Rental.* FROM  Rental,Account,Customer "+
		     "WHERE Rental.AccountId = Account.Id AND Account.Customer =Customer.Id AND Customer.Id = "+customerID
		     ;
		   
		   ResultSet rs = null;

		   rs = DBHelper.getInstance().executeQuery(query, con);
		   if(rs == null){
		    System.out.println("Failed");
		    return null;
		   }
		   
		   while (rs.next()) {
		    Rental rental = new Rental();
		    rental.setAccountID(rs.getInt("AccountId"));
		    rental.setCustomerRepID(rs.getInt("CustRepId"));
		    rental.setMovieID(rs.getInt("MovieId"));
		    rental.setOrderID(rs.getInt("OrderId"));
		    rentals.add(rental);
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
		  
		  return rentals;
		  
		 }
}