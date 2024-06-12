package dao;

import model.Employee;

import java.util.List;

public class test {
	public static void main(String[] args) {
        // Provide a search keyword for testing
        String searchKeyword = "";

        // Create an instance of the EmployeeDAO class
        EmployeeDao employeeDAO = new EmployeeDao();

        // Call the getEmployees method
        List<Employee> employees = employeeDAO.getEmployees("");

        // Check if the list is not empty
        if (!employees.isEmpty()) {
            // Print the details of each employee in the list
            for (Employee employee : employees) {
                System.out.println("Employee Details:");
                System.out.println("Employee ID: " + employee.getEmployeeID());
                System.out.println("First Name: " + employee.getFirstName());
                System.out.println("Last Name: " + employee.getLastName());
                System.out.println("Address: " + employee.getAddress());
                System.out.println("City: " + employee.getCity());
                System.out.println("State: " + employee.getState());
                System.out.println("Zip Code: " + employee.getZipCode());
                System.out.println("Telephone: " + employee.getTelephone());
                System.out.println("Start Date: " + employee.getStartDate());
                System.out.println("Hourly Rate: " + employee.getHourlyRate());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("------------");
            }
        } else {
            System.out.println("No employees found for the given search keyword: " + searchKeyword);
        }
    }
}