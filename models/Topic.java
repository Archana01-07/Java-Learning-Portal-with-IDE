package javaideandcompiler.models;

import java.io.Serializable;

public class Topic implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private String theory;
    private String[] examples;
    private String[] exercises;
    
    public Topic(String title, String theory, String[] examples, String[] exercises) {
        this.title = title;
        this.theory = theory;
        this.examples = examples;
        this.exercises = exercises;
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getTheory() { return theory; }
    public String[] getExamples() { return examples; }
    public String[] getExercises() { return exercises; }
}