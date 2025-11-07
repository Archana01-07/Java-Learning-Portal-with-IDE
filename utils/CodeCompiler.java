package javaideandcompiler.utils;

import java.io.*;

public class CodeCompiler {
    public static String compileAndRun(String code, String className) {
        StringBuilder output = new StringBuilder();
        
        try {
            // Write code to temporary file
            File tempFile = new File(className + ".java");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                bw.write(code);
            }
            
            // Compile
            ProcessBuilder compilePb = new ProcessBuilder("javac", tempFile.getAbsolutePath());
            Process compileProcess = compilePb.start();
            BufferedReader compileReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            
            String line;
            boolean hasErrors = false;
            while ((line = compileReader.readLine()) != null) {
                output.append("Compile Error: ").append(line).append("\n");
                hasErrors = true;
            }
            
            compileProcess.waitFor();
            
            if (!hasErrors) {
                // Run
                ProcessBuilder runPb = new ProcessBuilder("java", "-cp", ".", className);
                Process runProcess = runPb.start();
                
                BufferedReader runReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                
                while ((line = runReader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                
                while ((line = errorReader.readLine()) != null) {
                    output.append("Runtime Error: ").append(line).append("\n");
                }
                
                runProcess.waitFor();
            }
            
            // Clean up
            tempFile.delete();
            new File(className + ".class").delete();
            
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
        }
        
        return output.toString();
    }
}