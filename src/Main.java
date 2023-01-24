package src;
import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class Main {
  public static void main (String[] arg) throws SQLException {
    Scanner in = new Scanner(System.in);
        Console console = System.console();
        String user = " ";
        char [] pwd = new char['\0'];
        boolean caseCheck = false;

        while(caseCheck == false)
        {
            System.out.println("Enter Oracle User ID Here:");
            user = in.nextLine();
            System.out.println("Enter Oracle password for "+ user);
            console = System.console();
            pwd = console.readPassword();
            System.out.println(pwd);
            try {
                System.out.println("Attempting connection..."); //new String(pwd)
                Connection con =DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user, new String(pwd));
                caseCheck=true;
            } catch (Exception e) {
                System.out.println("Incorrect information; please try again.");
       
            }       
        }    //end of while loop
        
        System.out.println("Connection Created.");


        try (
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user, new String(pwd));
            Statement s=con.createStatement();
        )
        {
            boolean exit = false;
            do {
                if (mainMenu(con)) {
                    exit = true;
                }
            } while (exit == false);
            //Connection close
            s.close();
            con.close();
        } catch (SQLException e) {
            System.out.print("SQL Error caught: ");
            System.out.println(e.getMessage());
        }
    } //end of main method
    public static boolean mainMenu(Connection con) {        
        Scanner sc = new Scanner(System.in);
        String input;
        boolean selection = false;
        do {
            System.out.println("Select a menu option:");
            System.out.println("1. Customer\n2. Manager\n3. Employee\n4. Exit");
            input = sc.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3" )|| input.equals("4" )) {
                selection = true;
                break; 
            } else {
                System.out.println("Invalid input, please try again.");
            }
            } while (selection == false);
            
            if (input.equals("1")) {
                System.out.println("Customer selected");
                Customer customer = new Customer();
                customer.customerMenu(con);
            } else if (input.equals("2")) {
                System.out.println("Property manager selected");
                Manager manager = new Manager();
                manager.managerMenu(con);
            } 
            else if (input.equals("3")) {
                System.out.println("Employee selected");
                Employee emp = new Employee();
                emp.empMenu(con);
            } 
            else if (input.equals("4")) {
                System.out.println("Exiting Program");
                System.exit(0);
            }
            return false; 
    } //end of mainMenu
    
  } //end of Main class
