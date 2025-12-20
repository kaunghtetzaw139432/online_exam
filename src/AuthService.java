import java.sql.*;
import java.util.Scanner;

public class AuthService {
    static Scanner input;

    public static void register() {
        input = new Scanner(System.in);

        System.out.print("Enter username: ");
        String name = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Role (admin/student): ");
        String role = input.nextLine();

        User user = new User(name, password, role);

        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection(); 
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(username,password,role) VALUES(?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
            System.out.println("Register Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login() {
        input= new Scanner(System.in);

        System.out.print("Enter username: ");
        String name = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection(); 
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"));

                if (user.isAdmin()) {
                    AdminService.menu();
                } else {
                    StudentService.menu(user.getId());
                }
            } else {
                System.out.println("Login Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
