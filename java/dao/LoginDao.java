package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */


	public Login login(String username, String password) {
<<<<<<< HEAD
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */

		/*Sample data begins*/
		Connection con = null;
		ResultSet rs = null;
		Login login = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT * FROM Login WHERE username = '"
					+ username +"' AND password = '" + password +"'";
=======
	    Connection con = null;
	    ResultSet rs = null;
	    Login login = null;
>>>>>>> branch 'main' of git@github.com:inukbaik/CSE305.git

<<<<<<< HEAD
			rs = DBHelper.getInstance().executeQuery(query, con);
			try {

				if (rs.next()) {
					String roleString = rs.getString("role");
					if (roleString != null) {
						switch (roleString.toLowerCase()) {
						case "manager":
							login = new Login();
							login.setRole("manager");
							break;
						case "customerrepresentative":
							login = new Login();
							login.setRole("customerRepresentative");
							break;
						case "customer":
							login = new Login();
							login.setRole("customer");
							break;
						default:
							login = null;
						}
					}
				}else {
					return null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return login;
		/*Sample data ends*/

=======
	    try {
	        con = DBHelper.getInstance().getConnection();
	        
	        // Use a PreparedStatement to prevent SQL injection and handle special characters
	        String query = "SELECT * FROM Login WHERE username = ? AND password = ?";
	        try (PreparedStatement pstmt = con.prepareStatement(query)) {
	            pstmt.setString(1, username);
	            pstmt.setString(2, password);

	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	                String roleString = rs.getString("role");
	                login = new Login();
	                if (roleString != null) {
	                    switch (roleString.toLowerCase()) {
	                        case "manager":
	                            login.setRole("manager");
	                            break;
	                        case "customerrepresentative":
	                            login.setRole("customerRepresentative");
	                            break;
	                        case "customer":
	                            login.setRole("customer");
	                            break;
	                        default:
	                            login = null;
	                    }
	                }
	            } else {
	                return null;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (con != null)
	                con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return login;
>>>>>>> branch 'main' of git@github.com:inukbaik/CSE305.git
	}

	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		Connection con = null;

		String insertUserQuery = String.format(
				"INSERT INTO Login (username, password, role) " +
						"VALUES ('%s', '%s', '%s');",
						login.getUsername(),
						login.getPassword(),
						login.getRole()
				);

		try {
			con = DBHelper.getInstance().getConnection();

			// Execute insert query
			DBHelper.getInstance().execute(insertUserQuery, con);

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
		/* Sample data ends */
	}

	/*Sample data begins*/
	//return "success";
	/*Sample data ends*/
}

}
