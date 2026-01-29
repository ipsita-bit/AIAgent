package com.example.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * An agent class that analyzes JaCoCo code coverage reports and provides recommendations
 * for improving test coverage using JUnit 5.
 * 
 * This agent can:
 * - Parse JaCoCo CSV coverage reports
 * - Identify classes with low coverage
 * - Suggest specific improvements to reach coverage goals
 * - Generate recommendations for missing test cases
 */
public class CodeCoverageAgent {
    
    private static final double DEFAULT_COVERAGE_THRESHOLD = 0.80;
    private final double coverageThreshold;
    
    /**
     * Coverage data for a single class
     */
    public static class CoverageData {
        private final String className;
        private final int instructionsMissed;
        private final int instructionsCovered;
        private final int linesMissed;
        private final int linesCovered;
        private final int methodsMissed;
        private final int methodsCovered;
        
        public CoverageData(String className, int instructionsMissed, int instructionsCovered,
                          int linesMissed, int linesCovered, int methodsMissed, int methodsCovered) {
            this.className = className;
            this.instructionsMissed = instructionsMissed;
            this.instructionsCovered = instructionsCovered;
            this.linesMissed = linesMissed;
            this.linesCovered = linesCovered;
            this.methodsMissed = methodsMissed;
            this.methodsCovered = methodsCovered;
        }
        
        public String getClassName() {
            return className;
        }
        
        public double getInstructionCoverage() {
            int total = instructionsMissed + instructionsCovered;
            return total == 0 ? 0.0 : (double) instructionsCovered / total;
        }
        
        public double getLineCoverage() {
            int total = linesMissed + linesCovered;
            return total == 0 ? 0.0 : (double) linesCovered / total;
        }
        
        public double getMethodCoverage() {
            int total = methodsMissed + methodsCovered;
            return total == 0 ? 0.0 : (double) methodsCovered / total;
        }
        
        public int getInstructionsMissed() {
            return instructionsMissed;
        }
        
        public int getLinesMissed() {
            return linesMissed;
        }
        
        public int getMethodsMissed() {
            return methodsMissed;
        }
        
        public boolean isBelowThreshold(double threshold) {
            return getInstructionCoverage() < threshold;
        }
    }
    
    public CodeCoverageAgent() {
        this(DEFAULT_COVERAGE_THRESHOLD);
    }
    
    public CodeCoverageAgent(double coverageThreshold) {
        this.coverageThreshold = coverageThreshold;
    }
    
    /**
     * Parse JaCoCo CSV coverage report
     * 
     * @param reportPath Path to the JaCoCo CSV report
     * @return List of coverage data for each class
     * @throws IOException if the report cannot be read
     */
    public List<CoverageData> parseCoverageReport(String reportPath) throws IOException {
        List<CoverageData> coverageList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(reportPath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                String[] fields = line.split(",");
                if (fields.length >= 13) {
                    String className = fields[2];
                    int instructionsMissed = Integer.parseInt(fields[3]);
                    int instructionsCovered = Integer.parseInt(fields[4]);
                    int linesMissed = Integer.parseInt(fields[7]);
                    int linesCovered = Integer.parseInt(fields[8]);
                    int methodsMissed = Integer.parseInt(fields[11]);
                    int methodsCovered = Integer.parseInt(fields[12]);
                    
                    coverageList.add(new CoverageData(className, instructionsMissed, instructionsCovered,
                                                     linesMissed, linesCovered, methodsMissed, methodsCovered));
                }
            }
        }
        
        return coverageList;
    }
    
    /**
     * Analyze coverage and identify classes that need more tests
     * 
     * @param coverageData List of coverage data
     * @return List of classes below coverage threshold
     */
    public List<CoverageData> identifyLowCoverageClasses(List<CoverageData> coverageData) {
        List<CoverageData> lowCoverageClasses = new ArrayList<>();
        
        for (CoverageData data : coverageData) {
            if (data.isBelowThreshold(coverageThreshold)) {
                lowCoverageClasses.add(data);
            }
        }
        
        return lowCoverageClasses;
    }
    
    /**
     * Generate recommendations for improving coverage
     * 
     * @param lowCoverageClasses Classes with low coverage
     * @return List of recommendations
     */
    public List<String> generateRecommendations(List<CoverageData> lowCoverageClasses) {
        List<String> recommendations = new ArrayList<>();
        
        for (CoverageData data : lowCoverageClasses) {
            StringBuilder rec = new StringBuilder();
            rec.append("Class: ").append(data.getClassName())
               .append(" - Coverage: ").append(String.format("%.2f%%", data.getInstructionCoverage() * 100))
               .append("\n");
            
            if (data.getMethodsMissed() > 0) {
                rec.append("  - Add tests for ").append(data.getMethodsMissed())
                   .append(" untested method(s)\n");
            }
            
            if (data.getLinesMissed() > 0) {
                rec.append("  - Cover ").append(data.getLinesMissed())
                   .append(" untested line(s)\n");
            }
            
            // Specific recommendations based on class name
            if (data.getClassName().contains("Response")) {
                rec.append("  - Add tests for getter and setter methods\n");
                rec.append("  - Test both constructors\n");
            } else if (data.getClassName().contains("Application")) {
                rec.append("  - Main method coverage is optional for application entry points\n");
            } else if (data.getClassName().contains("Controller")) {
                rec.append("  - Add tests for all REST endpoints\n");
                rec.append("  - Test error handling scenarios\n");
            } else if (data.getClassName().contains("Service")) {
                rec.append("  - Test all business logic methods\n");
                rec.append("  - Test edge cases and error conditions\n");
            }
            
            recommendations.add(rec.toString());
        }
        
        return recommendations;
    }
    
    /**
     * Generate a comprehensive coverage report
     * 
     * @param reportPath Path to JaCoCo CSV report
     * @return Coverage analysis report as a string
     */
    public String analyzeAndReport(String reportPath) {
        StringBuilder report = new StringBuilder();
        report.append("=== Code Coverage Analysis Report ===\n\n");
        
        try {
            List<CoverageData> coverageData = parseCoverageReport(reportPath);
            
            // Overall statistics
            double totalInstructions = 0;
            double coveredInstructions = 0;
            
            for (CoverageData data : coverageData) {
                totalInstructions += data.instructionsMissed + data.instructionsCovered;
                coveredInstructions += data.instructionsCovered;
            }
            
            double overallCoverage = totalInstructions == 0 ? 0 : coveredInstructions / totalInstructions;
            report.append("Overall Coverage: ").append(String.format("%.2f%%", overallCoverage * 100)).append("\n");
            report.append("Coverage Threshold: ").append(String.format("%.2f%%", coverageThreshold * 100)).append("\n\n");
            
            // Identify low coverage classes
            List<CoverageData> lowCoverageClasses = identifyLowCoverageClasses(coverageData);
            
            if (lowCoverageClasses.isEmpty()) {
                report.append("✓ All classes meet the coverage threshold!\n");
            } else {
                report.append("Classes below coverage threshold:\n\n");
                List<String> recommendations = generateRecommendations(lowCoverageClasses);
                for (String rec : recommendations) {
                    report.append(rec).append("\n");
                }
            }
            
            // Summary
            report.append("\nSummary:\n");
            report.append("- Total classes: ").append(coverageData.size()).append("\n");
            report.append("- Classes below threshold: ").append(lowCoverageClasses.size()).append("\n");
            report.append("- Classes meeting threshold: ").append(coverageData.size() - lowCoverageClasses.size()).append("\n");
            
        } catch (IOException e) {
            report.append("Error reading coverage report: ").append(e.getMessage()).append("\n");
        }
        
        return report.toString();
    }
    
    /**
     * Main method to run the coverage agent
     */
    public static void main(String[] args) {
        String reportPath = args.length > 0 ? args[0] : "build/reports/jacoco/test/jacocoTestReport.csv";
        double threshold = args.length > 1 ? Double.parseDouble(args[1]) : DEFAULT_COVERAGE_THRESHOLD;
        
        CodeCoverageAgent agent = new CodeCoverageAgent(threshold);
        String report = agent.analyzeAndReport(reportPath);
        System.out.println(report);
    }
}
