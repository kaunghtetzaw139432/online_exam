import java.sql.*;
import java.util.Scanner;

public class AdminService {
    static Scanner input;

    public static void menu() {
        input = new Scanner(System.in);
        System.out.println("1. Add Question");
        System.out.println("2. Create Exam");
        System.out.print("Enter option: ");
        int option = Integer.parseInt(input.nextLine());

        if (option == 1)
            addQuestion();
        else if (option == 2)
            createExam();
        else
            System.out.println("Invalid option!");
    }

    private static void addQuestion() {
        input = new Scanner(System.in);
        System.out.print("Enter question text: ");
        String qText = input.nextLine();

        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();

         
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO questions(question_text) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, qText);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int qId = rs.getInt(1);

 
            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.print("Enter option " + (i + 1) + ": ");
                options[i] = input.nextLine();
            }

       
            System.out.print("Enter correct option number (1-4): ");
            int correctOption = Integer.parseInt(input.nextLine());

       
            for (int i = 0; i < 4; i++) {
                int isCorrect = (i + 1 == correctOption) ? 1 : 0;

                PreparedStatement cps = con.prepareStatement(
                        "INSERT INTO choices(question_id, option_text, is_correct) VALUES(?, ?, ?)"
                );
                cps.setInt(1, qId);
                cps.setInt(2, i);
                cps.setInt(3, isCorrect);
                cps.executeUpdate();
            }

            System.out.println("Question Added SUCCESSFULLY...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createExam() {
        input = new Scanner(System.in);
        System.out.print("Enter Exam Name: ");
        String name = input.nextLine();
        System.out.print("Question IDs (comma separated): ");
        String ids = input.nextLine();

        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO exams(exam_name) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int examId = rs.getInt(1);

            for (String q : ids.split(",")) {
                PreparedStatement eps = con.prepareStatement(
                        "INSERT INTO exam_questions(exam_id, question_id) VALUES(?, ?)"
                );
                eps.setInt(1, examId);
                eps.setInt(2, Integer.parseInt(q.trim()));
                eps.executeUpdate();
            }

            System.out.println("Exam Created Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
