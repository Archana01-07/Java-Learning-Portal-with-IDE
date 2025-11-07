package javaideandcompiler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javaideandcompiler.models.Student;
import javaideandcompiler.models.Progress;
import javaideandcompiler.models.Topic;
import javaideandcompiler.utils.CodeCompiler;
import javaideandcompiler.utils.FileManager;
import java.util.HashMap;

public class StudentDashboard extends JFrame {
    private Student student;
    private JTextArea codeEditor, outputArea, theoryArea;
    private JProgressBar overallProgressBar;
    private JPanel topicsPanel;
    private JLabel welcomeLabel, progressLabel;
    private HashMap<String, Topic> topicsMap;
    private String currentTopic;
    private int currentExerciseIndex = 0;

    public StudentDashboard(Student student) {
        this.student = student;
        initializeTopics();
        initializeUI();
        loadProgress();
    }

    private void initializeTopics() {
        topicsMap = new HashMap<>();
        
        // Topic 1: Variables and Data Types
        topicsMap.put("1. Variables and Data Types", new Topic(
            "1. Variables and Data Types",
            "VARIABLES AND DATA TYPES\n\n" +
            "Variables are containers for storing data values.\n\n" +
            "Primitive Data Types:\n" +
            "- int: integer numbers (e.g., 10, -5)\n" +
            "- double: decimal numbers (e.g., 3.14, -2.5)\n" +
            "- boolean: true/false values\n" +
            "- char: single characters (e.g., 'A', '1')\n\n" +
            "Example:\n" +
            "int age = 25;\n" +
            "double salary = 50000.50;\n" +
            "boolean isJavaFun = true;\n" +
            "char grade = 'A';",
            new String[]{
                "int x = 10;\nSystem.out.println(x);",
                "double pi = 3.14;\nSystem.out.println(pi);",
                "boolean flag = true;\nSystem.out.println(flag);"
            },
            new String[]{
                "Create an integer variable named 'score' with value 85 and print it",
                "Create a double variable named 'temperature' with value 98.6 and print it",
                "Create a boolean variable named 'isPassed' with value true and print it",
                "Create a char variable named 'grade' with value 'A' and print it",
                "Create two integer variables, add them, and print the result"
            }
        ));

        // Topic 2: Conditional Statements
        topicsMap.put("2. Conditional Statements", new Topic(
            "2. Conditional Statements",
            "CONDITIONAL STATEMENTS\n\n" +
            "Conditional statements allow you to execute different code blocks based on conditions.\n\n" +
            "if statement:\n" +
            "if (condition) {\n    // code to execute if condition is true\n}\n\n" +
            "if-else statement:\n" +
            "if (condition) {\n    // code if true\n} else {\n    // code if false\n}\n\n" +
            "if-else-if ladder:\n" +
            "if (condition1) {\n    // code 1\n} else if (condition2) {\n    // code 2\n} else {\n    // default code\n}\n\n" +
            "Example:\n" +
            "int age = 18;\n" +
            "if (age >= 18) {\n" +
            "    System.out.println(\"You are an adult\");\n" +
            "} else {\n" +
            "    System.out.println(\"You are a minor\");\n" +
            "}",
            new String[]{
                "int x = 10;\nif (x > 5) {\n    System.out.println(\"x is greater than 5\");\n}",
                "int score = 85;\nif (score >= 80) {\n    System.out.println(\"Excellent!\");\n} else {\n    System.out.println(\"Good effort!\");\n}"
            },
            new String[]{
                "Check if a number is positive and print a message",
                "Check if a student passed (score >= 50) and print result",
                "Compare two numbers and print which is larger",
                "Check if a number is even or odd",
                "Create a simple grading system (A, B, C, D, F)"
            }
        ));

        // Topic 3: Loops
        topicsMap.put("3. Loops", new Topic(
            "3. Loops",
            "LOOPS\n\n" +
            "Loops are used to execute a block of code repeatedly.\n\n" +
            "for loop:\n" +
            "for (initialization; condition; increment) {\n    // code to repeat\n}\n\n" +
            "while loop:\n" +
            "while (condition) {\n    // code to repeat\n}\n\n" +
            "do-while loop:\n" +
            "do {\n    // code to repeat\n} while (condition);\n\n" +
            "Example:\n" +
            "for (int i = 1; i <= 5; i++) {\n" +
            "    System.out.println(\"Count: \" + i);\n" +
            "}",
            new String[]{
                "for (int i = 0; i < 3; i++) {\n    System.out.println(\"Hello \" + i);\n}",
                "int i = 1;\nwhile (i <= 3) {\n    System.out.println(i);\n    i++;\n}"
            },
            new String[]{
                "Print numbers from 1 to 10 using a for loop",
                "Print even numbers between 1 and 20 using a while loop",
                "Calculate the sum of first 10 natural numbers",
                "Print a multiplication table for a given number",
                "Find the factorial of a number using a loop"
            }
        ));

        // Topic 4: Arrays
        topicsMap.put("4. Arrays", new Topic(
            "4. Arrays",
            "ARRAYS\n\n" +
            "Arrays are used to store multiple values in a single variable.\n\n" +
            "Declaration:\n" +
            "int[] numbers = new int[5];\n" +
            "String[] names = {\"Alice\", \"Bob\", \"Charlie\"};\n\n" +
            "Accessing elements:\n" +
            "numbers[0] = 10; // First element\n" +
            "int x = numbers[0]; // Get first element\n\n" +
            "Array length:\n" +
            "int length = numbers.length;\n\n" +
            "Example:\n" +
            "int[] scores = {85, 90, 78, 92, 88};\n" +
            "for (int i = 0; i < scores.length; i++) {\n" +
            "    System.out.println(\"Score \" + i + \": \" + scores[i]);\n" +
            "}",
            new String[]{
                "int[] arr = {1, 2, 3};\nfor (int num : arr) {\n    System.out.println(num);\n}",
                "String[] names = {\"Alice\", \"Bob\"};\nSystem.out.println(names[0]);"
            },
            new String[]{
                "Create an array of 5 integers and print all elements",
                "Find the maximum value in an integer array",
                "Calculate the average of array elements",
                "Reverse an array and print the result",
                "Search for an element in an array"
            }
        ));

        // Topic 5: Methods
        topicsMap.put("5. Methods", new Topic(
            "5. Methods",
            "METHODS\n\n" +
            "Methods are blocks of code that perform specific tasks.\n\n" +
            "Method declaration:\n" +
            "returnType methodName(parameters) {\n    // method body\n}\n\n" +
            "Void method (no return):\n" +
            "public void printMessage() {\n    System.out.println(\"Hello!\");\n}\n\n" +
            "Method with return value:\n" +
            "public int add(int a, int b) {\n    return a + b;\n}\n\n" +
            "Example:\n" +
            "public static int multiply(int x, int y) {\n" +
            "    return x * y;\n" +
            "}\n" +
            "int result = multiply(5, 3); // result = 15",
            new String[]{
                "public static void greet() {\n    System.out.println(\"Hello!\");\n}",
                "public static int square(int n) {\n    return n * n;\n}"
            },
            new String[]{
                "Create a method that prints 'Hello World'",
                "Write a method that takes two numbers and returns their sum",
                "Create a method that checks if a number is prime",
                "Write a method that calculates the area of a rectangle",
                "Create a method that returns the maximum of three numbers"
            }
        ));

        // Topic 6: Classes and Objects
        topicsMap.put("6. Classes and Objects", new Topic(
            "6. Classes and Objects",
            "CLASSES AND OBJECTS\n\n" +
            "A class is a blueprint for creating objects.\n\n" +
            "Class definition:\n" +
            "public class ClassName {\n    // fields (variables)\n    // methods\n}\n\n" +
            "Creating objects:\n" +
            "ClassName obj = new ClassName();\n\n" +
            "Example:\n" +
            "public class Student {\n" +
            "    String name;\n" +
            "    int age;\n" +
            "    \n" +
            "    public void display() {\n" +
            "        System.out.println(\"Name: \" + name);\n" +
            "        System.out.println(\"Age: \" + age);\n" +
            "    }\n" +
            "}\n" +
            "Student s1 = new Student();\n" +
            "s1.name = \"Alice\";\n" +
            "s1.age = 20;\n" +
            "s1.display();",
            new String[]{
                "class Car {\n    String color;\n    void start() {\n        System.out.println(\"Car started\");\n    }\n}",
                "Car myCar = new Car();\nmyCar.color = \"Red\";\nmyCar.start();"
            },
            new String[]{
                "Create a class 'Person' with name and age fields",
                "Add a method to Person class to display details",
                "Create a class 'Rectangle' with length and width",
                "Add methods to calculate area and perimeter in Rectangle class",
                "Create multiple objects and call their methods"
            }
        ));

        // Topic 7: Exception Handling
        topicsMap.put("7. Exception Handling", new Topic(
            "7. Exception Handling",
            "EXCEPTION HANDLING\n\n" +
            "Exception handling helps manage runtime errors.\n\n" +
            "try-catch block:\n" +
            "try {\n    // code that might throw exception\n} catch (ExceptionType e) {\n    // handle exception\n}\n\n" +
            "Multiple catch blocks:\n" +
            "try {\n    // code\n} catch (IOException e) {\n    // handle IO exception\n} catch (NumberFormatException e) {\n    // handle number format exception\n}\n\n" +
            "finally block:\n" +
            "try {\n    // code\n} catch (Exception e) {\n    // handle exception\n} finally {\n    // always executes\n}\n\n" +
            "Example:\n" +
            "try {\n" +
            "    int result = 10 / 0;\n" +
            "} catch (ArithmeticException e) {\n" +
            "    System.out.println(\"Cannot divide by zero!\");\n" +
            "}",
            new String[]{
                "try {\n    int num = Integer.parseInt(\"abc\");\n} catch (NumberFormatException e) {\n    System.out.println(\"Invalid number\");\n}",
                "try {\n    int[] arr = new int[5];\n    arr[10] = 50;\n} catch (ArrayIndexOutOfBoundsException e) {\n    System.out.println(\"Array index error\");\n}"
            },
            new String[]{
                "Handle division by zero exception",
                "Handle number format exception when parsing strings",
                "Handle array index out of bounds exception",
                "Use multiple catch blocks for different exceptions",
                "Create a try-catch block with finally"
            }
        ));
    }

    private void initializeUI() {
        setTitle("Java Learning Portal - Student: " + student.getFullName());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        welcomeLabel = new JLabel("Welcome, " + student.getFullName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        progressLabel = new JLabel("Overall Progress: 0%");
        progressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        progressLabel.setForeground(Color.WHITE);
        headerPanel.add(progressLabel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(300);
        mainSplitPane.setResizeWeight(0.25);

        // Left Panel - Topics and Progress
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Overall Progress
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createTitledBorder("Overall Progress"));
        
        overallProgressBar = new JProgressBar(0, 100);
        overallProgressBar.setStringPainted(true);
        overallProgressBar.setForeground(new Color(39, 174, 96));
        overallProgressBar.setPreferredSize(new Dimension(280, 30));
        progressPanel.add(overallProgressBar, BorderLayout.CENTER);
        
        leftPanel.add(progressPanel, BorderLayout.NORTH);

        // Topics List
        topicsPanel = new JPanel();
        topicsPanel.setLayout(new BoxLayout(topicsPanel, BoxLayout.Y_AXIS));
        topicsPanel.setBorder(BorderFactory.createTitledBorder("Learning Topics"));
        
        refreshTopicsList();
        
        JScrollPane topicsScroll = new JScrollPane(topicsPanel);
        topicsScroll.setPreferredSize(new Dimension(280, 400));
        leftPanel.add(topicsScroll, BorderLayout.CENTER);

        // Right Panel - Learning Area
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Theory Panel
        JPanel theoryPanel = new JPanel(new BorderLayout());
        theoryPanel.setBorder(BorderFactory.createTitledBorder("Theory"));
        theoryPanel.setPreferredSize(new Dimension(600, 200));
        
        theoryArea = new JTextArea();
        theoryArea.setFont(new Font("Arial", Font.PLAIN, 14));
        theoryArea.setEditable(false);
        theoryArea.setText("Select a topic to start learning...");
        
        JScrollPane theoryScroll = new JScrollPane(theoryArea);
        theoryPanel.add(theoryScroll, BorderLayout.CENTER);
        
        rightPanel.add(theoryPanel, BorderLayout.NORTH);

        // Code and Output Split Pane
        JSplitPane codeOutputSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        codeOutputSplitPane.setDividerLocation(400);
        codeOutputSplitPane.setResizeWeight(0.6);
        codeOutputSplitPane.setOneTouchExpandable(true);

        // Code Editor Panel
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Code Editor - Exercise " + (currentExerciseIndex + 1) + "/5",
            TitledBorder.LEFT, TitledBorder.TOP));

        codeEditor = new JTextArea();
        codeEditor.setFont(new Font("Consolas", Font.PLAIN, 14));
        codeEditor.setText("// Select a topic and exercise will appear here\n");

        JScrollPane editorScroll = new JScrollPane(codeEditor);
        editorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        editorPanel.add(editorScroll, BorderLayout.CENTER);

        // Output Panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Output Console",
            TitledBorder.LEFT, TitledBorder.TOP));

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.GREEN);
        outputArea.setEditable(false);
        outputArea.setText("Program output will appear here...\n");

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        outputPanel.add(outputScroll, BorderLayout.CENTER);

        codeOutputSplitPane.setTopComponent(editorPanel);
        codeOutputSplitPane.setBottomComponent(outputPanel);

        rightPanel.add(codeOutputSplitPane, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton runBtn = createStyledButton("Run Code", new Color(41, 128, 185));
        runBtn.addActionListener(e -> runCode());
        
        JButton submitBtn = createStyledButton("Submit Exercise", new Color(39, 174, 96));
        submitBtn.addActionListener(e -> submitExercise());
        
        JButton nextExerciseBtn = createStyledButton("Next Exercise", new Color(155, 89, 182));
        nextExerciseBtn.addActionListener(e -> loadNextExercise());
        
        JButton clearBtn = createStyledButton("Clear Output", new Color(231, 76, 60));
        clearBtn.addActionListener(e -> outputArea.setText(""));
        
        controlPanel.add(runBtn);
        controlPanel.add(submitBtn);
        controlPanel.add(nextExerciseBtn);
        controlPanel.add(clearBtn);
        
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);
        add(mainSplitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void refreshTopicsList() {
        topicsPanel.removeAll();
        
        String[] topics = {
            "1. Variables and Data Types",
            "2. Conditional Statements", 
            "3. Loops",
            "4. Arrays",
            "5. Methods",
            "6. Classes and Objects",
            "7. Exception Handling"
        };
        
        for (String topic : topics) {
            Progress progress = student.getProgress(topic);
            JPanel topicPanel = createTopicPanel(topic, progress);
            topicsPanel.add(topicPanel);
            topicsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        topicsPanel.revalidate();
        topicsPanel.repaint();
        updateOverallProgress();
    }

    private JPanel createTopicPanel(String topicName, Progress progress) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(260, 60));
        
        JLabel nameLabel = new JLabel(topicName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 2, 10));
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress.getProgressPercentage());
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(260, 20));
        progressBar.setForeground(progress.isCompleted() ? new Color(39, 174, 96) : 
                                progress.isLocked() ? Color.LIGHT_GRAY : new Color(41, 128, 185));
        
        String statusText = getStatusText(progress);
        JLabel statusLabel = new JLabel(statusText);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 5, 10));
        statusLabel.setForeground(Color.DARK_GRAY);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        
        if (!progress.isLocked()) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    selectTopic(topicName);
                }
            });
        } else {
            panel.setBackground(new Color(240, 240, 240));
        }
        
        return panel;
    }

    private String getStatusText(Progress progress) {
        if (progress.isLocked()) return "🔒 Locked";
        if (progress.isCompleted()) return "✅ Completed";
        return "📝 " + progress.getExercisesCompleted() + "/" + progress.getTotalExercises() + " exercises";
    }

    private void selectTopic(String topicName) {
        currentTopic = topicName;
        currentExerciseIndex = 0;
        Topic topic = topicsMap.get(topicName);
        if (topic != null) {
            theoryArea.setText(topic.getTheory());
            loadCurrentExercise();
        }
    }

    private void loadCurrentExercise() {
        if (currentTopic != null) {
            Topic topic = topicsMap.get(currentTopic);
            if (topic != null && topic.getExercises().length > currentExerciseIndex) {
                String exercise = topic.getExercises()[currentExerciseIndex];
                codeEditor.setText("// Exercise " + (currentExerciseIndex + 1) + ": " + exercise + "\n\n" +
                                 "public class Main {\n" +
                                 "    public static void main(String[] args) {\n" +
                                 "        // Your code here\n" +
                                 "    }\n" +
                                 "}");
                
                // Update editor title
                ((JPanel)codeEditor.getParent().getParent()).setBorder(
                    BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY), 
                        "Code Editor - Exercise " + (currentExerciseIndex + 1) + "/5",
                        TitledBorder.LEFT, TitledBorder.TOP)
                );
            }
        }
    }

    private void loadNextExercise() {
        if (currentTopic != null) {
            Topic topic = topicsMap.get(currentTopic);
            if (topic != null) {
                if (currentExerciseIndex < topic.getExercises().length - 1) {
                    currentExerciseIndex++;
                    loadCurrentExercise();
                    outputArea.setText("Loaded next exercise...\n");
                } else {
                    outputArea.setText("No more exercises in this topic!\n");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a topic first!");
        }
    }

    private void runCode() {
        String code = codeEditor.getText();
        outputArea.setText("Compiling and running your code...\n\n");
        
        String output = CodeCompiler.compileAndRun(code, "Main");
        outputArea.append(output);
        
        if (!output.contains("Error") && output.trim().length() > 0) {
            outputArea.append("\n✓ Code executed successfully!");
        }
    }

    private void submitExercise() {
        if (currentTopic == null) {
            JOptionPane.showMessageDialog(this, "Please select a topic first!");
            return;
        }
        
        Progress progress = student.getProgress(currentTopic);
        
        // Only allow submitting current exercise if it's the next one in sequence
        if (currentExerciseIndex == progress.getExercisesCompleted()) {
            progress.completeExercise();
            FileManager.saveStudent(student);
            
            // Update UI immediately after saving
            refreshTopicsList();
            updateOverallProgress();
            
            JOptionPane.showMessageDialog(this, 
                "Exercise " + (currentExerciseIndex + 1) + " submitted!\n" +
                "Completed: " + progress.getExercisesCompleted() + "/" + progress.getTotalExercises() + "\n" +
                "Progress: " + progress.getProgressPercentage() + "%");
            
            // Automatically move to next exercise
            if (currentExerciseIndex < progress.getTotalExercises() - 1) {
                currentExerciseIndex++;
                loadCurrentExercise();
            } else {
                JOptionPane.showMessageDialog(this, "Congratulations! You've completed all exercises in this topic!");
                
                // Unlock next topic immediately when current topic is completed
                if (progress.isCompleted()) {
                    unlockNextTopic();
                }
            }
        } else if (currentExerciseIndex < progress.getExercisesCompleted()) {
            JOptionPane.showMessageDialog(this, 
                "Exercise " + (currentExerciseIndex + 1) + " already completed!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please complete exercise " + (progress.getExercisesCompleted() + 1) + " first!");
        }
    }

    private void unlockNextTopic() {
        String[] topics = {
            "1. Variables and Data Types",
            "2. Conditional Statements", 
            "3. Loops",
            "4. Arrays",
            "5. Methods",
            "6. Classes and Objects",
            "7. Exception Handling"
        };
        
        for (int i = 0; i < topics.length - 1; i++) {
            if (topics[i].equals(currentTopic)) {
                Progress nextProgress = student.getProgress(topics[i + 1]);
                if (nextProgress != null && nextProgress.isLocked()) {
                    nextProgress.unlock();
                    FileManager.saveStudent(student);
                    JOptionPane.showMessageDialog(this, "Next topic unlocked: " + topics[i + 1]);
                    refreshTopicsList();
                    System.out.println("DEBUG: Unlocked topic: " + topics[i + 1]);
                }
                break;
            }
        }
    }

    private void loadProgress() {
        updateOverallProgress();
    }

    private void updateOverallProgress() {
        int totalTopics = 7;
        int completedTopics = 0;
        
        for (Progress progress : student.getProgressMap().values()) {
            if (progress.isCompleted()) {
                completedTopics++;
            }
        }
        
        int overallProgress = totalTopics > 0 ? (int) ((completedTopics / (double) totalTopics) * 100) : 0;
        overallProgressBar.setValue(overallProgress);
        progressLabel.setText("Overall Progress: " + overallProgress + "%");
    }
}