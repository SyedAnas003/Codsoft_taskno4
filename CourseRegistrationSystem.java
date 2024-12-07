import java.util.*;

// Class to represent a Course
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean registerStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }
}

// Class to represent a Student
class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            System.out.println("You are already registered for this course.");
        } else if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Course registered successfully.");
        } else {
            System.out.println("No available slots for this course.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("You are not registered for this course.");
        }
    }
}

// Main class for the system
public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Adding some default courses
        courses.add(new Course("C101", "Java Programming", "Learn the basics of Java.", 30));
        courses.add(new Course("C102", "Data Structures", "Introduction to data structures.", 25));
        courses.add(new Course("C103", "Database Systems", "Learn about relational databases.", 20));

        System.out.println("=== Welcome to the Student Course Registration System ===");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Register Student");
            System.out.println("2. List Courses");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. View Registered Courses");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    students.put(id, new Student(id, name));
                    System.out.println("Student registered successfully.");
                    break;
                case 2:
                    listCourses();
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();
                    Student student = students.get(id);
                    if (student != null) {
                        listCourses();
                        System.out.print("Enter Course Code to register: ");
                        String courseCode = scanner.nextLine();
                        Course course = findCourse(courseCode);
                        if (course != null) {
                            student.registerCourse(course);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();
                    student = students.get(id);
                    if (student != null) {
                        System.out.println("Your Registered Courses:");
                        listStudentCourses(student);
                        System.out.print("Enter Course Code to drop: ");
                        String courseCode = scanner.nextLine();
                        Course course = findCourse(courseCode);
                        if (course != null) {
                            student.dropCourse(course);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();
                    student = students.get(id);
                    if (student != null) {
                        System.out.println("Your Registered Courses:");
                        listStudentCourses(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println(course.getCode() + " - " + course.getTitle() + " (" + course.getAvailableSlots() + " slots available)");
        }
    }

    private static Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static void listStudentCourses(Student student) {
        List<Course> registeredCourses = student.getRegisteredCourses();
        if (registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
        } else {
            for (Course course : registeredCourses) {
                System.out.println(course.getCode() + " - " + course.getTitle());
            }
        }
    }
}
