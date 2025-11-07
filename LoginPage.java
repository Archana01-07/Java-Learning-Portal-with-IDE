package javaideandcompiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaideandcompiler.models.Student;
import javaideandcompiler.utils.FileManager;

public class LoginPage extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleBox;
    private JTextField fullNameField;
    private JPanel registerPanel;

    public LoginPage() {
        setTitle("Java Learning Portal - Login");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 245, 250));

        // Title
        JLabel titleLabel = new JLabel("JAVA LEARNING PORTAL");
        titleLabel.setBounds(0, 30, 500, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(41, 128, 185));
        add(titleLabel);

        // Role Selection
        JLabel roleLabel = new JLabel("I am a:");
        roleLabel.setBounds(50, 100, 100, 25);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Student", "Teacher"});
        roleBox.setBounds(150, 100, 200, 30);
        roleBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roleBox.setBackground(Color.WHITE);
        roleBox.addActionListener(e -> toggleRegisterFields());
        add(roleBox);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 150, 100, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(150, 150, 200, 30);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(userField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 200, 100, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(150, 200, 200, 30);
        add(passField);

        // Register Panel (initially hidden)
        registerPanel = new JPanel();
        registerPanel.setBounds(50, 250, 400, 50);
        registerPanel.setLayout(null);
        registerPanel.setBackground(new Color(240, 245, 250));
        registerPanel.setVisible(false);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(0, 0, 100, 25);
        fullNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(100, 0, 200, 30);
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(fullNameField);

        add(registerPanel);

        // Buttons
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 320, 120, 35);
        loginBtn.setBackground(new Color(41, 128, 185));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        loginBtn.addActionListener(e -> handleLogin());
        add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(250, 320, 120, 35);
        registerBtn.setBackground(new Color(39, 174, 96));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        registerBtn.addActionListener(e -> handleRegister());
        add(registerBtn);

        setVisible(true);
    }

    private void toggleRegisterFields() {
        String role = (String) roleBox.getSelectedItem();
        registerPanel.setVisible("Student".equals(role));
        revalidate();
        repaint();
    }

    private void handleLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Please fill all fields!");
            return;
        }

        if ("Student".equals(role)) {
            Student student = FileManager.loadStudent(user);
            if (student != null && student.getPassword().equals(pass)) {
                dispose();
                new StudentDashboard(student);
            } else {
                showError("Invalid student credentials!");
            }
        } else {
            // Simple teacher authentication (admin/admin)
            if ("admin".equals(user) && "admin".equals(pass)) {
                dispose();
                new TeacherDashboard();
            } else {
                showError("Invalid teacher credentials!");
            }
        }
    }

    private void handleRegister() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        String fullName = fullNameField.getText().trim();
        String role = (String) roleBox.getSelectedItem();

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Please fill all fields!");
            return;
        }

        if ("Student".equals(role)) {
            if (fullName.isEmpty()) {
                showError("Please enter your full name!");
                return;
            }

            if (FileManager.studentExists(user)) {
                showError("Student already exists!");
            } else {
                Student newStudent = new Student(user, pass, fullName);
                FileManager.saveStudent(newStudent);
                showSuccess("Student registered successfully!");
                clearFields();
            }
        } else {
            showError("Teacher registration requires administrator access.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        userField.setText("");
        passField.setText("");
        fullNameField.setText("");
    }

    public static void main(String[] args) {
        // Simple main method - remove complex look and feel code
        System.out.println("Starting Java Learning Portal...");
        
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoginPage();
                    System.out.println("Login window created successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
                }
            }
        });
    }
}