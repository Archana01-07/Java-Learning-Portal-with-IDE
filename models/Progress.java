package javaideandcompiler.models;

import java.io.Serializable;

public class Progress implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String topicName;
    private boolean isLocked;
    private int exercisesCompleted;
    private int totalExercises;
    private boolean isCompleted;
    
    public Progress(String topicName) {
        this.topicName = topicName;
        this.isLocked = !topicName.equals("1. Variables and Data Types");
        this.exercisesCompleted = 0;
        this.totalExercises = 5; // 5 exercises per topic
        this.isCompleted = false;
    }
    
    public void completeExercise() {
        if (exercisesCompleted < totalExercises) {
            exercisesCompleted++;
            // Complete topic when all exercises are done
            if (exercisesCompleted >= totalExercises) {
                isCompleted = true;
            }
        }
    }
    
    public void unlock() {
        this.isLocked = false;
    }
    
    // Getters
    public String getTopicName() { return topicName; }
    public boolean isLocked() { return isLocked; }
    public int getExercisesCompleted() { return exercisesCompleted; }
    public int getTotalExercises() { return totalExercises; }
    public boolean isCompleted() { return isCompleted; }
    public int getProgressPercentage() {
        if (totalExercises == 0) return 0;
        return (int) ((exercisesCompleted / (double) totalExercises) * 100);
    }
}