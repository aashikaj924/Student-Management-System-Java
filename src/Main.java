import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = FileHandler.loadFromFile();

        int choice = 0;
        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name/Course");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Sort Students");
            System.out.println("8. Generate Reports");
            System.out.println("9. Backup Data");
            System.out.println("10. Restore from Backup");
            System.out.println("11. Clear All Students");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Add Student
                    System.out.print("Enter Student ID: ");
                    String id = sc.nextLine();
                    boolean exists = students.stream().anyMatch(s -> s.getId().equals(id));
                    if (exists) {
                        System.out.println("Student with this ID already exists!");
                        break;
                    }
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();
                    double marks;
                    while (true) {
                        System.out.print("Enter Marks: ");
                        try {
                            marks = Double.parseDouble(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid marks! Please enter a number.");
                        }
                    }
                    students.add(new Student(id, name, course, marks));
                    FileHandler.saveToFile(students);
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    // Display students
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        Student.printHeader();
                        students.forEach(Student::displayStudent);
                    }
                    break;

                case 3:
                    // Search by ID
                    System.out.print("Enter Student ID to search: ");
                    String searchId = sc.nextLine();
                    Optional<Student> found = students.stream().filter(s -> s.getId().equals(searchId)).findFirst();
                    if (found.isPresent()) {
                        Student.printHeader();
                        found.get().displayStudent();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    // Search by Name/Course
                    System.out.print("Enter Name or Course to search: ");
                    String query = sc.nextLine().toLowerCase();
                    List<Student> matched = new ArrayList<>();
                    for (Student s : students) {
                        if (s.getName().toLowerCase().contains(query) || s.getCourse().toLowerCase().contains(query)) {
                            matched.add(s);
                        }
                    }
                    if (matched.isEmpty()) {
                        System.out.println("No matching students found.");
                    } else {
                        Student.printHeader();
                        matched.forEach(Student::displayStudent);
                    }
                    break;

                case 5:
                    // Update student
                    System.out.print("Enter Student ID to update: ");
                    String updateId = sc.nextLine();
                    Optional<Student> toUpdate = students.stream().filter(s -> s.getId().equals(updateId)).findFirst();
                    if (toUpdate.isPresent()) {
                        Student s = toUpdate.get();
                        System.out.print("Enter new Name: ");
                        s.setName(sc.nextLine());
                        System.out.print("Enter new Course: ");
                        s.setCourse(sc.nextLine());
                        double newMarks;
                        while (true) {
                            System.out.print("Enter new Marks: ");
                            try {
                                newMarks = Double.parseDouble(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid marks! Please enter a number.");
                            }
                        }
                        s.setMarks(newMarks);
                        FileHandler.saveToFile(students);
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    // Delete student
                    System.out.print("Enter Student ID to delete: ");
                    String deleteId = sc.nextLine();
                    boolean removed = students.removeIf(s -> s.getId().equals(deleteId));
                    FileHandler.saveToFile(students);
                    if (removed) System.out.println("Student deleted successfully!");
                    else System.out.println("Student not found.");
                    break;

                case 7:
                    // Sort students
                    if (students.isEmpty()) {
                        System.out.println("No students to sort.");
                        break;
                    }
                    System.out.println("Sort by:");
                    System.out.println("1. Name Ascending");
                    System.out.println("2. Marks Descending");
                    System.out.println("3. Marks Ascending");
                    System.out.println("4. Grade Ascending");
                    System.out.print("Choice: ");
                    int sortChoice;
                    try { sortChoice = Integer.parseInt(sc.nextLine()); }
                    catch (NumberFormatException e) { System.out.println("Invalid input!"); break; }
                    switch (sortChoice) {
                        case 1: students.sort(Student.byNameAsc()); break;
                        case 2: students.sort(Student.byMarksDesc()); break;
                        case 3: students.sort(Student.byMarksAsc()); break;
                        case 4: students.sort(Student.byGradeAsc()); break;
                        default: System.out.println("Invalid choice for sorting."); continue;
                    }
                    System.out.println("Students sorted successfully!");
                    break;

                case 8:
                    // Generate Reports
                    Reports.printSummary(students);
                    Reports.gradeDistribution(students);
                    Reports.highestLowestAverage(students);
                    break;

                case 9:
                    // Backup Data
                    FileHandler.backupFile();
                    System.out.println("Backup created successfully!");
                    break;

                case 10:
                    // Restore from Backup
                    students = FileHandler.restoreFromBackup();
                    FileHandler.saveToFile(students);
                    System.out.println("Data restored from backup successfully!");
                    break;

                case 11: // Clear all students
                    students.clear();
                    FileHandler.saveToFile(students);
                    System.out.println("All student records have been deleted!");
                    break;
                case 12:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 12);

        sc.close();
    }
}
