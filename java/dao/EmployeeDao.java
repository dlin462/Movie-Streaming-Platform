package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */

	public String addEmployee(Employee employee) {
		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */

		/*Sample data begins*/
		Connection con = null;

		String[] queries = new String[3];

		queries[0] = String.format("INSERT INTO  Location " +
				"values(\'%s\',\'%s\', \'%s\');",
				Integer.toString(employee.getZipCode()),
				employee.getCity(),
				employee.getState());

		queries[1] = String.format("INSERT INTO  Person " +
				"values(\'%s\',\'%s\', \'%s\', \'%s\', %s, \'%s\')",
				employee.getEmployeeID(),
				employee.getLastName(),
				employee.getFirstName(),
				employee.getAddress(),
				employee.getZipCode(),
				employee.getTelephone());
		//				employee.getEmail());

		String role = "manager";

		queries[2] = String.format("INSERT INTO  Employee " +
				"values(\'%s\', \'%s\',\'%s\', \'%s\');",
				employee.getEmployeeID(),
				employee.getStartDate(),
				employee.getHourlyRate(),
				role);



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

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return "success";
		/*Sample data ends*/

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */

		/*Sample data begins*/
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "UPDATE cse305_2.Employee SET FirstName=?, LastName=?, Email=?, Address=?, City=?, State=?, " +
					"ZipCode=?, Telephone=?, HourlyRate=? WHERE EmployeeID=?";

			try (PreparedStatement a = con.prepareStatement(query)) {
				a.setString(1, employee.getFirstName());
				a.setString(2, employee.getLastName());
				a.setString(3, employee.getEmail());
				a.setString(4, employee.getAddress());
				a.setString(5, employee.getCity());
				a.setString(6, employee.getState());
				a.setInt(7, employee.getZipCode());
				a.setString(8, employee.getTelephone());
				a.setDouble(9, employee.getHourlyRate());
				a.setString(10, employee.getEmployeeID());

				//				employee.executeUpdate();
			}

			return "success";
		} catch (SQLException e) {

			return "failure";
		} finally {
			//			con.close();
		}
	}

	/*Sample data ends*/


	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		Connection con = null;
		try {

			con = DBHelper.getInstance().getConnection();

			String query = 
					"DELETE FROM cse305_2.Employee WHERE Id = '" + employeeID +"'";

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

			String query = 
					"DELETE FROM cse305_2.Person WHERE SSN = '" + employeeID +"'";

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


	//	works in test but cannot see on the website? all information is correctly added and connects to db
	public List<Employee> getEmployees(String searchKeyword) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<Employee>();

		/*Sample data begins*/
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query =
					"SELECT * FROM cse305_2.Employee";

			rs = DBHelper.getInstance().executeQuery(query, con);

			if (rs == null) {
				System.out.println("Query is incorrect.");
				return null;
			}

			while (rs.next()) {
				//				System.out.println(rs.getString("Id"));

				Employee employee = this.getEmployee(rs.getString("Id"));
				//				System.out.println(employee);
				employees.add(employee);
				//				System.out.println(employees);

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

		/*Sample data ends*/
		return employees;
	}

	public Employee getEmployee(String employeeID) {
		Employee employee = new Employee();
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();


			String query = "SELECT * FROM Person WHERE SSN = '" + employeeID + "'";
			rs = DBHelper.getInstance().executeQuery(query, con);
			if (rs == null || !rs.next()) {
				System.out.println("No data found in Person table for ID: " + employeeID);
				return null;
			}

			int zipCode = rs.getInt("ZipCode");
			employee.setZipCode(zipCode);
			employee.setEmployeeID(rs.getString("SSN"));
			employee.setFirstName(rs.getString("FirstName"));
			employee.setLastName(rs.getString("LastName"));
			employee.setAddress(rs.getString("Address"));
			employee.setTelephone(rs.getString("Telephone"));

			String query2 = "SELECT * FROM Employee WHERE Id = '" + employeeID + "'";
			String query3 = "SELECT * FROM Location WHERE ZipCode = " + zipCode;
			String query4 = "SELECT * FROM Login WHERE SSN = '" + employeeID + "'";

			try (ResultSet rs2 = DBHelper.getInstance().executeQuery(query2, con);
					ResultSet rs3 = DBHelper.getInstance().executeQuery(query3, con);
					ResultSet rs4 = DBHelper.getInstance().executeQuery(query4, con)) {

				if (rs2.next()) {
					employee.setStartDate(rs2.getString("StartDate"));
					employee.setHourlyRate(rs2.getInt("HourlyRate"));
				} else {
					System.out.println("No data found in Employee table for ID: " + employeeID);
					return null;
				}

				if (rs3.next()) {
					employee.setCity(rs3.getString("City"));
					employee.setState(rs3.getString("State"));
				} else {
					System.out.println("No data found in Location table for ZipCode: " + zipCode);
					return null;
				}

				if (rs4.next()) {
					employee.setEmail(rs4.getString("username"));
				} else {
					System.out.println("No login data found for ID: " + employeeID);
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employee;
	}



	public Employee getHighestRevenueEmployee() {

		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee employee =  null;
		Connection con = null;

		try {
			con = DBHelper.getInstance().getConnection();
			ResultSet rs = null;
			//fix query, same as customer highest revenue right now
			String query = "SELECT CustomerId, SUM(BookingFee) AS totalRevenue " +
					"FROM Account " +
					"GROUP BY CustomerId " +
					"ORDER BY totalRevenue DESC " +
					"LIMIT 1";

			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Query is empty");
				if (con != null)
					con.close();
				return null;
			}

			try {
				if (rs.next()) {
					try {
						con = DBHelper.getInstance().getConnection();

						query = "SELECT Id FROM Employee WHERE Id = '" + 
								rs.getString("Id") +"'";

						ResultSet rs2 = null;
						rs2 = DBHelper.getInstance().executeQuery(query, con);
						if(rs2.next()){
							//get SSN
							String employeeId = rs2.getString("Id");
							employee = this.getEmployee(employeeId);
						}

					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return employee;
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */

		String employeeID = null;
		Connection con = null;
		ResultSet rs = null;

		try {
			con = DBHelper.getInstance().getConnection();

			// Assuming "username" is the email address stored in the "Login" table
			String query = "SELECT Id FROM Login WHERE username = '" + username + "'";

			rs = DBHelper.getInstance().executeQuery(query, con);

			if (rs != null && rs.next()) {
				employeeID = rs.getString("Id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return employeeID;
	}

}
