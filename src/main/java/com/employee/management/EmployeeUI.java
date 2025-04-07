package com.employee.management;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeUI extends Frame {

    TextField tfId, tfName, tfAge, tfSalary;
    TextArea taDisplay;
    Button btnAdd, btnView, btnUpdate, btnDelete;

    public EmployeeUI() {
        setTitle("Employee Management System");
        setSize(500, 500);
        setLayout(new BorderLayout(10, 10));

        // Top Panel for Input
        Panel inputPanel = new Panel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new Label("ID:"));
        tfId = new TextField();
        inputPanel.add(tfId);

        inputPanel.add(new Label("Name:"));
        tfName = new TextField();
        inputPanel.add(tfName);

        inputPanel.add(new Label("Age:"));
        tfAge = new TextField();
        inputPanel.add(tfAge);

        inputPanel.add(new Label("Salary:"));
        tfSalary = new TextField();
        inputPanel.add(tfSalary);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel for Buttons
        Panel btnPanel = new Panel(new GridLayout(1, 4, 10, 10));
        btnAdd = new Button("Add");
        btnView = new Button("View All");
        btnUpdate = new Button("Update");
        btnDelete = new Button("Delete");
        btnPanel.add(btnAdd);
        btnPanel.add(btnView);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.CENTER);

        // Text Area at Bottom
        taDisplay = new TextArea("", 10, 50);
        taDisplay.setEditable(false);
        add(taDisplay, BorderLayout.SOUTH);

        // Add Button Logic
        btnAdd.addActionListener(e -> {
            if (!validateInput()) return;
            Employee emp = createEmployeeFromInput();
            if (EmployeeDAO.insert(emp)) {
                taDisplay.setText("✅ Employee added successfully.\n");
            } else {
                taDisplay.setText("❌ Failed to add employee. Check console for error.\n");
            }
        });

        // View Button Logic
        btnView.addActionListener(e -> {
            taDisplay.setText("");
            List<Employee> list = EmployeeDAO.getAll();
            if (list.isEmpty()) {
                taDisplay.setText("No records found.\n");
            } else {
                for (Employee emp : list) {
                    taDisplay.append(emp.getId() + " | " + emp.getName() + " | " + emp.getAge() + " | " + emp.getSalary() + "\n");
                }
            }
        });

        // Update Button Logic
        btnUpdate.addActionListener(e -> {
            if (!validateInput()) return;
            Employee emp = createEmployeeFromInput();
            if (EmployeeDAO.update(emp)) {
                taDisplay.setText("✅ Employee updated successfully.\n");
            } else {
                taDisplay.setText("❌ Failed to update employee. Check if ID exists.\n");
            }
        });

        // Delete Button Logic
        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText());
                if (EmployeeDAO.delete(id)) {
                    taDisplay.setText("✅ Employee deleted successfully.\n");
                } else {
                    taDisplay.setText("❌ Failed to delete employee. Check if ID exists.\n");
                }
            } catch (NumberFormatException ex) {
                taDisplay.setText("❌ Enter a valid numeric ID to delete.\n");
            }
        });

        // Window Close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    // Helper: Validate all input fields
    private boolean validateInput() {
        try {
            Integer.parseInt(tfId.getText());
            Integer.parseInt(tfAge.getText());
            Double.parseDouble(tfSalary.getText());
            if (tfName.getText().isEmpty()) throw new Exception("Empty name");
            return true;
        } catch (Exception e) {
            taDisplay.setText("❌ Invalid input! Please check all fields.\n");
            return false;
        }
    }

    // Helper: Create Employee object from input fields
    private Employee createEmployeeFromInput() {
        int id = Integer.parseInt(tfId.getText());
        String name = tfName.getText();
        int age = Integer.parseInt(tfAge.getText());
        double salary = Double.parseDouble(tfSalary.getText());
        return new Employee(id, name, age, salary);
    }

    public static void main(String[] args) {
        new EmployeeUI();
    }
}
