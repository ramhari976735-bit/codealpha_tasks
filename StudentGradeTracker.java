import java.util.*;

public class StudentGradeTracker {
    private static final Scanner SC = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();

    static class Student {
        String id, name;
        double[] marks;

        Student(String id, String name, double[] marks) {
            this.id = id; this.name = name; this.marks = marks;
        }

        double total() { return Arrays.stream(marks).sum(); }
        double average() { return marks.length == 0 ? 0 : total() / marks.length; }
        double highest() { return Arrays.stream(marks).max().orElse(0); }
        double lowest() { return Arrays.stream(marks).min().orElse(0); }

        String grade() {
            double avg = average();
            if (avg >= 90) return "A+";
            if (avg >= 80) return "A";
            if (avg >= 70) return "B";
            if (avg >= 60) return "C";
            if (avg >= 50) return "D";
            if (avg >= 40) return "E";
            return "F";
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========== STUDENT GRADE TRACKER ==========");
            System.out.println("1. Add student and marks");
            System.out.println("2. View all student reports");
            System.out.println("3. Search student by ID");
            System.out.println("4. Class performance summary");
            System.out.println("5. Exit");
            int choice = readInt("Choose an option: ", 1, 5);

            switch (choice) {
                case 1: addStudent(); break;
                case 2: showAll(); break;
                case 3: searchStudent(); break;
                case 4: classSummary(); break;
                case 5: return;
            }
        }
    }

    private static void addStudent() {
        String id = readNonEmpty("Enter student ID: ");
        String name = readNonEmpty("Enter student name: ");
        int count = readInt("Number of subjects (1-10): ", 1, 10);
        double[] marks = new double[count];
        for (int i = 0; i < count; i++)
            marks[i] = readDouble("Enter marks for subject " + (i+1) + ": ", 0, 100);
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    private static void showAll() {
        if (students.isEmpty()) { System.out.println("No records."); return; }
        for (Student s : students) printStudent(s);
    }

    private static void searchStudent() {
        String id = readNonEmpty("Enter student ID: ");
        for (Student s : students)
            if (s.id.equalsIgnoreCase(id)) { printStudent(s); return; }
        System.out.println("Student not found.");
    }

    private static void classSummary() {
        if (students.isEmpty()) { System.out.println("No records."); return; }
        double totalAvg = 0;
        Student top = students.get(0), low = students.get(0);
        for (Student s : students) {
            totalAvg += s.average();
            if (s.average() > top.average()) top = s;
            if (s.average() < low.average()) low = s;
        }
        System.out.printf("Students: %d%n", students.size());
        System.out.printf("Class average: %.2f%n", totalAvg / students.size());
        System.out.printf("Top student: %s (%.2f)%n", top.name, top.average());
        System.out.printf("Lowest student: %s (%.2f)%n", low.name, low.average());
    }

    private static void printStudent(Student s) {
        System.out.println("\nID: " + s.id + " | Name: " + s.name);
        System.out.println("Marks: " + Arrays.toString(s.marks));
        System.out.printf("Total: %.2f, Average: %.2f, Grade: %s%n",
                s.total(), s.average(), s.grade());
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = SC.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Enter a number between " + min + " and " + max + ".");
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(SC.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Enter a number between " + min + " and " + max + ".");
        }
    }
}
