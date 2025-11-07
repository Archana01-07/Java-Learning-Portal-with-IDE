package javaideandcompiler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import javaideandcompiler.models.Student;
import javaideandcompiler.models.Progress;
import javaideandcompiler.utils.FileManager;

public class TeacherDashboard extends JFrame {
    private JTable studentsTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsArea;
    private HashMap<String, Student> students;

    public TeacherDashboard() {
        loadStudents();
        initializeUI();
    }

    private void loadStudents() {
        students = FileManager.loadAllStudents();
    }

    private void initializeUI() {
        setTitle("Java Learning Portal - Teacher Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(142, 68, 173));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Teacher Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel countLabel = new JLabel("Students: " + students.size());
        countLabel.setFont(new Font("Arial", Font.BOLD, 16));
        countLabel.setForeground(Color.WHITE);
        headerPanel.add(countLabel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);

        // Left Panel - Students List
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Students Table
        String[] columns = {"Username", "Full Name", "Progress", "Completed Topics", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentsTable = new JTable(tableModel);
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.getSelectionModel().addListSelectionListener(e -> showStudentDetails());
        studentsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentsTable.setRowHeight(25);
        
        JScrollPane tableScroll = new JScrollPane(studentsTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Students List"));
        
        leftPanel.add(tableScroll, BorderLayout.CENTER);

        // Refresh Button
        JButton refreshBtn = new JButton("Refresh Data");
        refreshBtn.setBackground(new Color(41, 128, 185));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> refreshData());
        leftPanel.add(refreshBtn, BorderLayout.SOUTH);

        // Right Panel - Student Details
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailsArea = new JTextArea();
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setEditable(false);
        detailsArea.setText("Select a student to view details...");
        
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBorder(BorderFactory.createTitledBorder("Student Details"));
        
        rightPanel.add(detailsScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);

        loadStudentsData();
        setVisible(true);
    }

    private void loadStudentsData() {
        tableModel.setRowCount(0);
        
        for (Student student : students.values()) {
            int completedTopics = 0;
            int totalTopics = student.getProgressMap().size();
            
            for (Progress progress : student.getProgressMap().values()) {
                if (progress.isCompleted()) {
                    completedTopics++;
                }
            }
            
            int progressPercentage = totalTopics > 0 ? (int) ((completedTopics / (double) totalTopics) * 100) : 0;
            String status = progressPercentage == 100 ? "Completed" : 
                           progressPercentage > 0 ? "In Progress" : "Not Started";
            
            tableModel.addRow(new Object[]{
                student.getUsername(),
                student.getFullName(),
                progressPercentage + "%",
                completedTopics + "/" + totalTopics,
                status
            });
        }
    }

    private void showStudentDetails() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow >= 0) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            Student student = students.get(username);
            
            if (student != null) {
                StringBuilder details = new StringBuilder();
                details.append("STUDENT DETAILS\n");
                details.append("===============\n\n");
                details.append("Username: ").append(student.getUsername()).append("\n");
                details.append("Full Name: ").append(student.getFullName()).append("\n\n");
                
                int completedTopics = 0;
                int totalTopics = student.getProgressMap().size();
                
                details.append("TOPIC PROGRESS\n");
                details.append("==============\n");
                
                // Sort topics for better display
                String[] sortedTopics = {
                    "1. Variables and Data Types",
                    "2. Conditional Statements", 
                    "3. Loops",
                    "4. Arrays",
                    "5. Methods",
                    "6. Classes and Objects",
                    "7. Exception Handling"
                };
                
                for (String topicName : sortedTopics) {
                    Progress progress = student.getProgress(topicName);
                    if (progress != null) {
                        String status = progress.isLocked() ? "🔒 Locked" : 
                                      progress.isCompleted() ? "✅ Completed" : 
                                      "📝 In Progress (" + progress.getExercisesCompleted() + "/" + progress.getTotalExercises() + ")";
                        
                        if (progress.isCompleted()) {
                            completedTopics++;
                        }
                        
                        details.append("\n").append(progress.getTopicName()).append("\n");
                        details.append("  Status: ").append(status).append("\n");
                        details.append("  Progress: ").append(progress.getProgressPercentage()).append("%\n");
                    }
                }
                
                details.append("\nSUMMARY\n");
                details.append("=======\n");
                details.append("Completed Topics: ").append(completedTopics).append("/").append(totalTopics).append("\n");
                details.append("Overall Progress: ").append(totalTopics > 0 ? (int)((completedTopics / (double)totalTopics) * 100) : 0).append("%\n");
                
                detailsArea.setText(details.toString());
            }
        }
    }

    private void refreshData() {
        loadStudents();
        loadStudentsData();
        JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
    }
}