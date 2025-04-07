package com.employee.management;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {

    // Insert new employee
    public static boolean insert(Employee e) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employee (id, name, age, salary) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, e.getId());
            ps.setString(2, e.getName());
            ps.setInt(3, e.getAge());
            ps.setDouble(4, e.getSalary());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Fetch all employees
    public static List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employee")) {
                 
            while (rs.next()) {
                list.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getDouble("salary")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Update an existing employee
    public static boolean update(Employee emp) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE employee SET name=?, age=?, salary=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getAge());
            ps.setDouble(3, emp.getSalary());
            ps.setInt(4, emp.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an employee by ID
    public static boolean delete(int id) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM employee WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
