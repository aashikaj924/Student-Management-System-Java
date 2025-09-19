import java.util.*;

public class Reports {

    public static void printSummary(List<Student> students) {
        System.out.println("\n=== Summary Report ===");
        System.out.println("Total Students: " + students.size());
        long passed = students.stream().filter(s -> s.getStatus().equals("Pass")).count();
        long failed = students.size() - passed;
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed + "\n");
    }

    public static void gradeDistribution(List<Student> students) {
        System.out.println("=== Grade Distribution ===");
        Map<String, Long> gradeMap = new TreeMap<>();
        for (Student s : students) {
            gradeMap.put(s.getGrade(), gradeMap.getOrDefault(s.getGrade(), 0L) + 1);
        }
        for (Map.Entry<String, Long> entry : gradeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }

    public static void highestLowestAverage(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to calculate marks.");
            return;
        }
        double highest = students.stream().mapToDouble(Student::getMarks).max().orElse(0);
        double lowest = students.stream().mapToDouble(Student::getMarks).min().orElse(0);
        double avg = students.stream().mapToDouble(Student::getMarks).average().orElse(0);

        System.out.println("=== Marks Analysis ===");
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);
        System.out.printf("Average Marks: %.2f\n\n", avg);
    }
}
