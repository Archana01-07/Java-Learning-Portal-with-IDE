package javaideandcompiler.models;

import java.io.Serializable;
import java.util.HashMap;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String fullName;
    private HashMap<String, Progress> progressMap;
    
    public Student(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.progressMap = new HashMap<>();
        initializeProgress();
    }
    
    private void initializeProgress() {
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
            progressMap.put(topic, new Progress(topic));
        }
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public HashMap<String, Progress> getProgressMap() { return progressMap; }
    public Progress getProgress(String topic) { return progressMap.get(topic); }
}