import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      
         System.out.println("Welcome to the online Exam System!");
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");
            int option = input.nextInt();
            input.nextLine();

            if (option == 1){
                AuthService.register();
            }
            else if (option == 2){
                AuthService.login();
            }
            else if(option==0){
                 System.out.println("Program exit...");
            System.exit(0);
            }
            else{
                System.out.println("Invalid option");
            }
               
        }
    }
}
