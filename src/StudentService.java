import java.sql.*;
import java.util.Scanner;

public class StudentService {

    static Scanner input = new Scanner(System.in);

    public static void menu(int studentId) {

        int score = 0;

        try {
            DBConnection db = new DBConnection();
            Connection con = db.getConnection();

           
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM exams");
            System.out.println("\nAvailable Exams:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("exam_name"));
            }

            System.out.print("\nEnter Exam ID to take exam: ");
            int examId = input.nextInt();

         
            PreparedStatement ps = con.prepareStatement(
                    "SELECT q.id, q.question_text FROM questions q " +
                            "JOIN exam_questions eq ON q.id = eq.question_id WHERE eq.exam_id=?");
            ps.setInt(1, examId);
            ResultSet qrs = ps.executeQuery();

            while (qrs.next()) {
                int qid = qrs.getInt("id");
                System.out.println("\n" + qrs.getString("question_text"));

             
                PreparedStatement cps = con.prepareStatement(
                        "SELECT * FROM choices WHERE question_id=?");
                cps.setInt(1, qid);
                ResultSet crs = cps.executeQuery();

                while (crs.next()) {
                    System.out.println(
                            crs.getInt("id") + ". " + crs.getString("option_text"));
                }

                System.out.print("Your answer: ");
                int optId = input.nextInt();

               
                PreparedStatement checkAns = con.prepareStatement(
                        "SELECT * FROM student_answers WHERE student_id=? AND exam_id=? AND question_id=?");
                checkAns.setInt(1, studentId);
                checkAns.setInt(2, examId);
                checkAns.setInt(3, qid);
                ResultSet ansCheck = checkAns.executeQuery();

                if (!ansCheck.next()) {
                 
                    PreparedStatement aps = con.prepareStatement(
                            "INSERT INTO student_answers(student_id, exam_id, question_id, choice_id) VALUES(?,?,?,?)");
                    aps.setInt(1, studentId);
                    aps.setInt(2, examId);
                    aps.setInt(3, qid);
                    aps.setInt(4, optId);
                    aps.executeUpdate();

                 
                    PreparedStatement check = con.prepareStatement(
                            "SELECT is_correct FROM choices WHERE id=?");
                    check.setInt(1, optId);
                    ResultSet cr = check.executeQuery();

                    if (cr.next() && cr.getInt("is_correct") == 1) {
                        score++;
                    }
                } else {
                    System.out.println("You have already answered this question.");
                }
            }

      
            PreparedStatement rsps = con.prepareStatement(
                    "INSERT INTO results VALUES(?,?,?)");
            rsps.setInt(1, studentId);
            rsps.setInt(2, examId);
            rsps.setInt(3, score);
            rsps.executeUpdate();

            System.out.println("\nExam Finished");
            System.out.println("Your Score: " + score);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
