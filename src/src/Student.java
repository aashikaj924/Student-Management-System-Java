import java.util.Comparator;

public class Student {
    private String id;
    private String name;
    private String course;
    private double marks;
    private String grade;
    private String status;

    public Student(String id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
        calculateGradeAndStatus();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCourse(String course) { this.course = course; }
    public void setMarks(double marks) { this.marks = marks; calculateGradeAndStatus(); }

    private void calculateGradeAndStatus() {
        if (marks >= 90) grade = "A+";
        else if (marks >= 75) grade = "A";
        else if (marks >= 60) grade = "B";
        else if (marks >= 40) grade = "C";
        else grade = "F";

        status = (marks >= 40) ? "Pass" : "Fail";
    }

    public void displayStudent() {
        System.out.printf("%-10s %-15s %-10s %-7.2f %-5s %-5s\n", id, name, course, marks, grade, status);
    }

    public static void printHeader() {
        System.out.printf("%-10s %-15s %-10s %-7s %-5s %-5s\n", "ID", "Name", "Course", "Marks", "Grade", "Status");
        System.out.println("-------------------------------------------------------------");
    }

    // Sorting helpers
    public static Comparator<Student> byNameAsc() { return Comparator.comparing(Student::getName); }
    public static Comparator<Student> byMarksDesc() { return Comparator.comparingDouble(Student::getMarks).reversed(); }
    public static Comparator<Student> byMarksAsc() { return Comparator.comparingDouble(Student::getMarks); }
    public static Comparator<Student> byGradeAsc() { return Comparator.comparing(Student::getGrade); }

    // ✅ Added: toString() for CSV saving
    @Override
    public String toString() {
        return id + "," + name + "," + course + "," + marks;
    }
}
