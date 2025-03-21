import java.sql.*;

public class EmployeeFetch {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "root";
        String password = "your_password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") +
                        ", Name: " + rs.getString("Name") +
                        ", Salary: " + rs.getDouble("Salary"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
