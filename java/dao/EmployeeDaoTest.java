package dao;

import java.util.List;

import model.Customer;
import model.Employee;

public class EmployeeDaoTest {

    public static void main(String[] args) {
        testGetEmployees();
    }

    private static void testGetEmployees() {
        EmployeeDao employeeDao = new EmployeeDao(); 

        List<Employee> employees = employeeDao.getEmployees("");
        
        String existingEmployeeID = "123-45-6789";
        String result = employeeDao.deleteEmployee(existingEmployeeID);
        if ("success".equals(result)) {
            System.out.println("Employee deletion successful for existing employee.");
        } else {
            System.out.println("Unexpected result: " + result);
        }
        
        if (employees != null && !employees.isEmpty()) {
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getEmployeeID());
                System.out.println("Employee Name: " + employee.getFirstName() + " " + employee.getLastName());
                
                System.out.println("--------------------");
            }
        } else {
            System.out.println("No employees found.");
        }
    }
}