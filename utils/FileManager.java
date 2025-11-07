package javaideandcompiler.utils;

import javaideandcompiler.models.Student;
import javaideandcompiler.models.Progress;
import java.io.*;
import java.util.HashMap;

public class FileManager {
    private static final String BASE_DIR = "data/";
    private static final String STUDENTS_DIR = BASE_DIR + "students/";
    private static final String PROGRESS_DIR = BASE_DIR + "progress/";
    
    static {
        // Create directories if they don't exist
        new File(STUDENTS_DIR).mkdirs();
        new File(PROGRESS_DIR).mkdirs();
    }
    
    public static void saveStudent(Student student) {
        String filename = STUDENTS_DIR + student.getUsername() + ".dat";
        System.out.println("Saving student to: " + filename); // Debug line
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(student);
            System.out.println("Student saved successfully: " + student.getUsername()); // Debug
        } catch (IOException e) {
            System.err.println("Error saving student: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Student loadStudent(String username) {
        String filename = STUDENTS_DIR + username + ".dat";
        System.out.println("Loading student from: " + filename); // Debug line
        
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filename); // Debug
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            Student student = (Student) ois.readObject();
            System.out.println("Student loaded successfully: " + username); // Debug
            return student;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading student: " + e.getMessage());
            return null;
        }
    }
    
    public static HashMap<String, Student> loadAllStudents() {
        HashMap<String, Student> students = new HashMap<>();
        File dir = new File(STUDENTS_DIR);
        System.out.println("Looking for students in: " + dir.getAbsolutePath()); // Debug
        
        File[] files = dir.listFiles((d, name) -> name.endsWith(".dat"));
        
        if (files != null) {
            System.out.println("Found " + files.length + " student files"); // Debug
            for (File file : files) {
                String username = file.getName().replace(".dat", "");
                Student student = loadStudent(username);
                if (student != null) {
                    students.put(username, student);
                }
            }
        } else {
            System.out.println("No student files found or directory doesn't exist"); // Debug
        }
        return students;
    }
    
    public static boolean studentExists(String username) {
        String filename = STUDENTS_DIR + username + ".dat";
        boolean exists = new File(filename).exists();
        System.out.println("Checking if student exists: " + username + " = " + exists); // Debug
        return exists;
    }
}