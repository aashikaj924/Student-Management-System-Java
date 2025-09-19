import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "students.txt";
    private static final String BACKUP_FILE = "students_backup.txt";

    // ✅ Save students to file (overwrite with backup)
    public static void saveToFile(ArrayList<Student> students) {
        backupFile(); // backup before saving
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.toString()); // CSV-safe format
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // ✅ Load students from file with validation
    public static ArrayList<Student> loadFromFile() {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(",");
                if (data.length != 4) {
                    System.out.println("Skipping invalid line " + lineNumber + ": " + line);
                    continue;
                }
                try {
                    Student s = new Student(data[0], data[1], data[2], Double.parseDouble(data[3]));
                    students.add(s);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid marks at " + lineNumber + ": " + line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping line with invalid data at " + lineNumber + ": " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // ignore first run
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return students;
    }

    // ✅ Backup existing file before overwrite (now public)
    public static void backupFile() {
        File original = new File(FILE_NAME);
        if (original.exists()) {
            File backup = new File(BACKUP_FILE);
            try (InputStream in = new FileInputStream(original);
                 OutputStream out = new FileOutputStream(backup)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                System.out.println("Error creating backup: " + e.getMessage());
            }
        }
    }

    // ✅ Restore students from backup
    public static ArrayList<Student> restoreFromBackup() {
        ArrayList<Student> students = new ArrayList<>();
        File backup = new File(BACKUP_FILE);
        if (!backup.exists()) {
            System.out.println("No backup file found.");
            return students;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(backup))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(",");
                if (data.length != 4) {
                    System.out.println("Skipping invalid line " + lineNumber + ": " + line);
                    continue;
                }
                try {
                    Student s = new Student(data[0], data[1], data[2], Double.parseDouble(data[3]));
                    students.add(s);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid marks at " + lineNumber + ": " + line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping line with invalid data at " + lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading backup file: " + e.getMessage());
        }
        return students;
    }
}
