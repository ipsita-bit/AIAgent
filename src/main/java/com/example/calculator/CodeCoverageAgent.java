package com.example.calculator;

import java.io.BufferedReader;
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
    public record CoverageData(
        String className,
        int instructionsMissed,
        int instructionsCovered,
        int linesMissed,
        int linesCovered,
        int methodsMissed,
        int methodsCovered
    ) {
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
        var coverageList = new ArrayList<CoverageData>();
        
        try (var reader = Files.newBufferedReader(Paths.get(reportPath), java.nio.charset.StandardCharsets.UTF_8)) {
            var isFirstLine = true;
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                var fields = line.split(",");
                if (fields.length < 13) {
                    continue; // Skip malformed lines
                }
                
                try {
                    var className = fields[2];
                    var instructionsMissed = Integer.parseInt(fields[3]);
                    var instructionsCovered = Integer.parseInt(fields[4]);
                    var linesMissed = Integer.parseInt(fields[7]);
                    var linesCovered = Integer.parseInt(fields[8]);
                    var methodsMissed = Integer.parseInt(fields[11]);
                    var methodsCovered = Integer.parseInt(fields[12]);
                    
                    coverageList.add(new CoverageData(className, instructionsMissed, instructionsCovered,
                                                     linesMissed, linesCovered, methodsMissed, methodsCovered));
                } catch (NumberFormatException e) {
                    // Skip lines with invalid numeric data
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
        return coverageData.stream()
                .filter(data -> data.isBelowThreshold(coverageThreshold))
                .toList();
    }
    
    /**
     * Generate recommendations for improving coverage
     * 
     * @param lowCoverageClasses Classes with low coverage
     * @return List of recommendations
     */
    public List<String> generateRecommendations(List<CoverageData> lowCoverageClasses) {
        return lowCoverageClasses.stream()
                .map(this::generateRecommendationForClass)
                .toList();
    }
    
    private String generateRecommendationForClass(CoverageData data) {
        var rec = new StringBuilder();
        rec.append("Class: ").append(data.className())
           .append(" - Coverage: ").append(String.format("%.2f%%", data.getInstructionCoverage() * 100))
           .append("\n");
        
        if (data.methodsMissed() > 0) {
            rec.append("  - Add tests for ").append(data.methodsMissed())
               .append(" untested method(s)\n");
        }
        
        if (data.linesMissed() > 0) {
            rec.append("  - Cover ").append(data.linesMissed())
               .append(" untested line(s)\n");
        }
        
        // Specific recommendations based on class name
        var className = data.className();
        if (className.contains("Response")) {
            rec.append("  - Add tests for getter and setter methods\n");
            rec.append("  - Test both constructors\n");
        } else if (className.contains("Application")) {
            rec.append("  - Main method coverage is optional for application entry points\n");
        } else if (className.contains("Controller")) {
            rec.append("  - Add tests for all REST endpoints\n");
            rec.append("  - Test error handling scenarios\n");
        } else if (className.contains("Service")) {
            rec.append("  - Test all business logic methods\n");
            rec.append("  - Test edge cases and error conditions\n");
        }
        
        return rec.toString();
    }
    
    /**
     * Generate a comprehensive coverage report
     * 
     * @param reportPath Path to JaCoCo CSV report
     * @return Coverage analysis report as a string
     */
    public String analyzeAndReport(String reportPath) {
        var report = new StringBuilder();
        report.append("=== Code Coverage Analysis Report ===\n\n");
        
        try {
            var coverageData = parseCoverageReport(reportPath);
            
            // Overall statistics using streams
            var totalInstructions = coverageData.stream()
                    .mapToDouble(data -> data.instructionsMissed() + data.instructionsCovered())
                    .sum();
            var coveredInstructions = coverageData.stream()
                    .mapToDouble(CoverageData::instructionsCovered)
                    .sum();
            
            var overallCoverage = totalInstructions == 0 ? 0 : coveredInstructions / totalInstructions;
            report.append("Overall Coverage: ").append(String.format("%.2f%%", overallCoverage * 100)).append("\n");
            report.append("Coverage Threshold: ").append(String.format("%.2f%%", coverageThreshold * 100)).append("\n\n");
            
            // Identify low coverage classes
            var lowCoverageClasses = identifyLowCoverageClasses(coverageData);
            
            if (lowCoverageClasses.isEmpty()) {
                report.append("✓ All classes meet the coverage threshold!\n");
            } else {
                report.append("Classes below coverage threshold:\n\n");
                var recommendations = generateRecommendations(lowCoverageClasses);
                recommendations.forEach(rec -> report.append(rec).append("\n"));
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
        var reportPath = args.length > 0 ? args[0] : "build/reports/jacoco/test/jacocoTestReport.csv";
        double threshold;
        
        try {
            threshold = args.length > 1 ? Double.parseDouble(args[1]) : DEFAULT_COVERAGE_THRESHOLD;
            if (threshold < 0.0 || threshold > 1.0) {
                System.err.println("Error: Threshold must be between 0.0 and 1.0");
                System.exit(1);
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid threshold value. Must be a number between 0.0 and 1.0");
            System.exit(1);
            return;
        }
        
        var agent = new CodeCoverageAgent(threshold);
        var report = agent.analyzeAndReport(reportPath);
        System.out.println(report);
    }
}
