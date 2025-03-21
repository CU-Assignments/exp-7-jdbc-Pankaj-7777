
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Thios is Student Model 
public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

// This is Controller in MVC
public class StudentController {
    private Connection con;

    public StudentController() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "your_password");
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Students (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, student.getStudentID());
        pstmt.setString(2, student.getName());
        pstmt.setString(3, student.getDepartment());
        pstmt.setDouble(4, student.getMarks());
        pstmt.executeUpdate();
        System.out.println("Student added successfully!");
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

        while (rs.next()) {
            students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getDouble("Marks")));
        }
        return students;
    }

    public void updateStudentMarks(int studentID, double newMarks) throws SQLException {
        String sql = "UPDATE Students SET Marks = ? WHERE StudentID = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setDouble(1, newMarks);
        pstmt.setInt(2, studentID);
        pstmt.executeUpdate();
        System.out.println("Student marks updated successfully!");
    }

    public void deleteStudent(int studentID) throws SQLException {
        String sql = "DELETE FROM Students WHERE StudentID = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, studentID);
        pstmt.executeUpdate();
        System.out.println("Student deleted successfully!");
    }
}

// This is View op in MVC
public class StudentView {
    public static void main(String[] args) throws SQLException {
        StudentController controller = new StudentController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Add Student\n2. View Students\n3. Update Marks\n4. Delete Student\n5. Exit");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                controller.addStudent(new Student(1, "John", "CS", 90));
                break;
            case 2:
                List<Student> students = controller.getAllStudents();
                for (Student s : students) {
                    System.out.println(s.getStudentID() + " " + s.getName() + " " + s.getDepartment() + " " + s.getMarks());
                }
                break;
            case 3:
                controller.updateStudentMarks(1, 95);
                break;
            case 4:
                controller.deleteStudent(1);
                break;
        }
        scanner.close();
    }
}


