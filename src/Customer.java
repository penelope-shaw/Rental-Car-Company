package src;

import java.io.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {
    public void customerMenu(Connection con) {
        String input;
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        boolean selection = false;
        System.out.println("Welcome, Hertz Customer!");
        do {
            System.out.println("Please select a menu option \n1. Update Personal Info \n2. Rent a Vehicle \n3. Return to Main Menu");
            String cusRes = in.nextLine();
            if (cusRes.matches("1")){
                System.out.println("Update personal info selected");
                customerUpdate(con);
            } else if (cusRes.matches("2")){
                System.out.println("Reserve a Car Selected");
                reserveCar(con);
            } else if (cusRes.matches("3")) {
                selection = true;
                exit = true;
            } else {
                System.out.println("Invalid selection. Please select a valid menu option.");
            }
       } while (selection == false);
    } //end of customerMenu

    public static void reserveCar(Connection con) {
        Scanner input = new Scanner(System.in);
        //--------------------------CHECKING IF CUSTOMER IS REGISTERED IN CUSTOMER DATABASE---------------------------
        int location_id;
        String cust = "select customer_name from customer2 where customer_name = ?";
        //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;  
        try {
            PreparedStatement p = con.prepareStatement(cust);
            boolean checker = false;
            do {
            String name = "";
            ResultSet result;
            System.out.println("Enter your full name here: ");
            name = input.nextLine();

            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setString(1, name);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("Name not found. Please retry or have an employee register you as a customer.");
                return;
            }
            else{
                System.out.println("Welcome customer!");
                System.out.format("%-25s\n", "Name");
                do{
                    System.out.format("%-25s\n", result.getString("customer_name"));
                    //System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker = true;
            } 
        } while (checker == false);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } //end of big try-catch block

        //----------------------------DISPLAYING LOCATION NAMES-----------------------------
        System.out.println("Which location would you like to rent from?");
        String location_name = "";

        try (
            Statement s=con.createStatement();
        ){
        //Statement s=con.createStatement();
           String q3;
           ResultSet result;
           q3 = "select location_id, name from location";
           result = s.executeQuery(q3);
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-25s\n", "Location ID", "Name");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                System.out.format("%-15s%-25s\n", result.getInt("location_id"), result.getString("name"));
                //location_name = result.getString("name");
            } while (result.next());
           }

    } catch(Exception e){e.printStackTrace();} 

    //---------------------CUSTOMER SELECTING LOCATION ------------------------
    //try-catch block for choosing location
    String q5 = "select location_id, name from location where location_id = ?";
    //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;
            try {
            PreparedStatement p = con.prepareStatement(q5);
            boolean checker = false;
            location_id = 0;
            do {
            //String q2;
            ResultSet result;
            System.out.println("Enter the Location ID of the location you would like to rent from.");
            //int vehicle_id = input.nextInt();
            
            do {
            try {
                location_id = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted. Enter the Location ID of the location you would like to rent from.");
                input.next();
            } 

            } while (location_id == 0); //end of while loop

            System.out.println("Location ID Selected: " + location_id);

            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, location_id);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the location that you would like to reserve.");
                System.out.format("%-15s%-25s\n", "Location ID", "Name");
                do{
                    System.out.format("%-15s%-25s\n", result.getInt("location_id"), result.getString("name"));
                    location_name = result.getString("name");
                    //System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker = true;
            }
        } while(checker == false); //end of do-while loop

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    } //end of big try-catch block

    //----------------------CUSTOMER SELECTING VEHICLE FROM CHOSEN LOCATION---------------------------
        //Show cars that are NOT reserved
        String car_to_rent = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = ?";
        System.out.println("What car would you like to rent?");
        try (
            PreparedStatement p2 = con.prepareStatement(car_to_rent);
            
        ){
        //Statement s=con.createStatement();
          // System.out.println("Location Name HI THERE:" + location_name);
           
           ResultSet result;
           p2.setString(1, location_name);
           result = p2.executeQuery();
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", "Vehicle ID", "Tank", "Make", "Type", "Model", "Reserved", "Odometer");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
            } while (result.next());
           }

    } catch(Exception e){e.printStackTrace();} 

    int vehicle_id = 0;
    String vehicle_type = "";

    //try-catch block for choosing vehicle by vehicle ID
    String q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and vehicle_id = ?";
            try {
            PreparedStatement p = con.prepareStatement(q2);
            boolean checker2 = false;
            vehicle_id = 0;
            do {
            //String q2;
            ResultSet result;
            System.out.println("Enter the Vehicle ID of the car you would like to rent.");
            //int vehicle_id = input.nextInt();
            
            do {
            try {
                vehicle_id = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted. Enter the Vehicle ID of the car you would like to rent.");
                input.next();
            }
            } while (vehicle_id < 1); //end of while loop

            System.out.println("Vehicle ID Selected: " + vehicle_id);

            if(vehicle_id == (int)vehicle_id){
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, vehicle_id);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the vehicle that you would like to reserve.");
                System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", "Vehicle ID", "Tank", "Make", "Type", "Model", "Reserved", "Odometer");
                do{
                    System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                    vehicle_type = result.getString("type");

                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker2 = true;
            }
        }
            else{
                System.out.println("Enter Vehicle ID.");
            }
        } while(checker2 == false); //end of do-while loop
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    //------------------GET TYPE OF VEHICLE AND ADD PRICE OF RENTING THAT TYPE TO THE PAYMENT------------------------
    System.out.println("Vehicle Type: " + vehicle_type);

    //Price Data
    double running_balance = 0.0;
    
    double sedan_fee = 200;
    double sports_fee = 400;
    double convertible_fee = 300;
    double  SUV_fee = 200;
    double van_fee = 400;
    double luxury_fee = 600;

    if (vehicle_type.equals("Sedan")) {
        running_balance = running_balance + sedan_fee;
    }
    if (vehicle_type.equals("Sports")) {
        running_balance += sports_fee;
    }
    if (vehicle_type.equals("Convertible")) {
        running_balance += convertible_fee;
    }
    if (vehicle_type.equals("SUV")) {
        running_balance += SUV_fee;
        System.out.println("SUV Running Balance: " + running_balance);
    }
    if (vehicle_type.equals("Van")) {
        running_balance += van_fee;
    }
    if (vehicle_type.equals("Luxury")) {
        running_balance += luxury_fee;
    }

    System.out.println("New Running Balance: " + running_balance);

    //---------------------UPDATING VEHICLE SO THAT IT IS RESERVED---------------------------------

    String reserveUpdate = "update vehicle set reserved = 1 where vehicle_id = ?";
    try {
        PreparedStatement pstmt = con.prepareStatement(reserveUpdate);
        // prepare data for update
        Scanner sc= new Scanner(System.in);
        pstmt.setInt(1, vehicle_id);
        int newReserve = pstmt.executeUpdate();
        //bank name updated
        System.out.println(String.format("Vehicle reserved %d", newReserve));
        //return to customer menu
        //customerMenu(con);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    //---------------------UPDATING LOCATION TO REFLECT NEW NUMBERS OF CARS RENTED---------------------------------

    String rentedUpdate = "update location set number_cars_rented = (select COUNT(*) from vehicle where location = ? and reserved = 1) where name = ?";
    try {
        PreparedStatement pstmt = con.prepareStatement(rentedUpdate);
        // prepare data for update
        Scanner sc= new Scanner(System.in);
        pstmt.setString(1, location_name);
        pstmt.setString(2, location_name);
        int newRented = pstmt.executeUpdate();
        System.out.println(String.format("Rental number of location changed %d", newRented));
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    //---------------------------SELECTING DATE TO RENT/RETURN VEHICLE-----------------------------------
    
    //DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive();
   
    LocalTime myObj = LocalTime.now(); //time
    
    String regex = "[0-9]{2}-[0-9]{2}-[0-9]{2}";
    String rentDate = "";
    String returnDate = "";
    Scanner input2 = new Scanner(System.in);
    do {
        System.out.println("On what date is the vehicle being rented? (Format as such: 01-01-22, DD-MM-YY):");
        rentDate = input2.nextLine();
    } while(rentDate.trim().isEmpty() || !rentDate.matches(regex));

    do {
        System.out.println("On what date is the vehicle being returned? (Format as such: 01-01-22, DD-MM-YY):");
        returnDate = input2.nextLine();
    } while(returnDate.trim().isEmpty() || !returnDate.matches(regex));

    System.out.println("rentDate: " + rentDate);
    System.out.println("returnDate: " + returnDate);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    LocalDate rentingDate = LocalDate.parse(rentDate, formatter);
    LocalDate returningDate = LocalDate.parse(returnDate, formatter);
    System.out.println(rentingDate);
    System.out.println(returningDate);

    ZoneId systemTimeZone = ZoneId.systemDefault();
    ZonedDateTime zonedRent = rentingDate.atStartOfDay(systemTimeZone);
    ZonedDateTime zonedReturn = returningDate.atStartOfDay(systemTimeZone);

    //Date date_of_rent = (Date) Date.from(zonedRent.toInstant());
    //Date date_of_return = (Date) Date.from(zonedReturn.toInstant());

    //------------------------------INSERT INTO RENTAL TABLE---------------------------------------
    System.out.println("Inserting into Rental");
    //Scanner input = new Scanner(System.in);
        try (
            Statement s=con.createStatement();
        ){

            String rent = "INSERT INTO rental(vehicle_id, pick_up, drop_off) " + "VALUES (?, ?, ?)";
            PreparedStatement p = con.prepareStatement(rent);
             // prepare data for update
            p.setInt(1, vehicle_id);
            p.setObject(2, rentingDate);
            p.setObject(3, returningDate);

            int change = p.executeUpdate();
            System.out.println(String.format("Rental was added %d",change));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    //-----------------------------CALCULATING PAYMENT-------------------------------------------------
    //-------------------------------DISPLAYING CHARGES------------------------------


    String charges = "select charge_id, charge_type, charge_amount from additional_charges where persona = 'Customer'";
    //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;
            try {
            PreparedStatement p = con.prepareStatement(charges);
            ResultSet result = p.executeQuery();
            //boolean checker = true;
            if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-30s%-15s\n", "Charge ID", "Charge Type", "Amount");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                System.out.format("%-15s%-30s%-15s\n", result.getInt("charge_id"), result.getString("charge_type"), result.getInt("charge_amount"));
                //location_name = result.getString("name");
            } while (result.next());
           }
        
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } //end of big try-catch block
            
    //-----------------------------ADDING SELECTED CHARGES------------------------------------
    int charge_id = 0;
    double charge_amt = 0;
        
    String charge = "select charge_id, charge_type, charge_amount from additional_charges where persona = 'Customer' and charge_id = ?";   
    try {
            PreparedStatement p = con.prepareStatement(charge);
            boolean checker = false;
            charge_id = 0;
            do {
            //String q2;
            ResultSet result;
            System.out.println("Enter the Charge ID here.");
            //int vehicle_id = input.nextInt();
            
            do {
            try {
                charge_id = input.nextInt();
                System.out.println(charge_id);
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted. Enter the Charge ID.");
                input.next();
            }
            } while (charge_id < 1); //end of while loop

            System.out.println("Charge ID Selected: " + charge_id);

            if(charge_id == (int)charge_id){
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, charge_id);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the charge that you would like to add.");
                System.out.format("%-15s%-30s%-15s\n", "Charge ID", "Charge Type", "Amount");
                do{
                    System.out.format("%-15s%-30s%-15s\n", result.getInt("charge_id"), result.getString("charge_type"), result.getInt("charge_amount"));
                    charge_id = result.getInt("charge_id");
                    charge_amt = result.getInt("charge_amount");
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker = true;
            }
        }
            else{
                System.out.println("Enter Charge ID.");
            }
        } while(checker == false); //end of do-while loop

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    running_balance += charge_amt;
    System.out.println("Charge Amount: " + charge_amt);

    System.out.println("Balance after charges: " + running_balance);

    //-------------------------HANDLE DISCOUNT CODE------------------------------------

    System.out.println("Enter Discount Code if you have one. If not, enter '0'.");


    //----------------DISPLAYING DISCOUNT CODES----------------------------------------
    String discount = "select discount_id, discount_type, dis_amount from discount";
    //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;
            try {
            PreparedStatement p = con.prepareStatement(discount);
            ResultSet result = p.executeQuery();
            //boolean checker = true;
            if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-30s%-15s\n", "Discount ID", "Discount Type", "Amount");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                System.out.format("%-15s%-30s%-15s\n", result.getInt("discount_id"), result.getString("discount_type"), result.getInt("dis_amount"));
                //location_name = result.getString("name");
            } while (result.next());
           }
        
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } //end of big try-catch block
        
    //-------------------------SELECTING DISCOUNT----------------------------------------
    int dis_code = 0;
    int dis_id = -1;
    int dis_amount = 0;
        
    String disc = "select discount_id, discount_type, dis_amount from discount where dis_code = ?";
            try {
            PreparedStatement p = con.prepareStatement(disc);
            boolean checker = false;
            dis_code = -1;
            do {
            //String q2;
            ResultSet result;
            System.out.println("Enter the Discount Code for your associated group here");

            do {
            try {
                dis_code = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted. Enter the discount code.");
                input.next();
            }
            } while (dis_code == -1); //end of while loop

            System.out.println("Discount Code Selected: " + dis_code);

            if(dis_code== (int)dis_code){
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, dis_code);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the discount you are adding.");
                System.out.format("%-15s%-30s%-15s\n", "Discount ID", "Discount Type", "Discount Amount");
                do{
                    System.out.format("%-15s%-30s%-15s\n", result.getInt("discount_id"), result.getString("discount_type"), result.getInt("dis_amount"));
                    dis_id = result.getInt("discount_id");
                    dis_amount = result.getInt("dis_amount");
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker = true;
            }
        }
            else{
                System.out.println("Enter Discount ID.");
            }
        } while(checker == false); //end of do-while loop

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    running_balance -= dis_amount;

    System.out.println("Discount Amount: " + dis_amount);

    System.out.println("Balance after discount: " + running_balance);


    //-----------------------------CALCULATING FEE BASED ON DURATION--------------------------------
    System.out.println();
    System.out.println("Duration Calculation");
    System.out.println();
    int duration = 0;
    double duration_fee = 0;

    String q3 = "select duration from rental where vehicle_id = ?";
    try {
    PreparedStatement p = con.prepareStatement(q3);
    boolean checker2 = false;
    do {
    ResultSet result;
    //int vehicle_id = input.nextInt();
    if(vehicle_id == (int)vehicle_id){

    p.setInt(1, vehicle_id);
    result = p.executeQuery();
    if(!result.next()){
        System.out.println ("No matches. Try again.");
    }
    else{
        System.out.println("Duration is listed below.");
        System.out.format("%-15s\n", "Duration");
        do{
            System.out.format("%-15s\n", result.getInt("Duration"));
            duration = result.getInt("Duration");
            //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
        }while(result.next());
        checker2 = true;
    }
}
        else{
            System.out.println("Enter Vehicle ID.");
        }
    } while(checker2 == false); //end of do-while loop
    } catch (SQLException e) {
    System.out.println(e.getMessage());
    }

    System.out.println("PSS324 Duration: " + duration);

    if (duration < 3) {
        duration_fee = 20;
        running_balance += duration_fee;
        System.out.println("Running Balance with Low Duration Fee: " + running_balance);
    }

    if (duration >= 3 && duration <= 5) {
        duration_fee = 30;
        running_balance += duration_fee;
        System.out.println("Running Balance with Medium Duration Fee: " + running_balance);
    }

    if (duration > 5) {
        duration_fee = 30;
        running_balance += duration_fee;
        System.out.println("Running Balance with High Duration Fee: " + running_balance);
    }
    
    System.out.println("Final running balance: " + running_balance);
        
    //-----------------------INSERT BALANCE INTO PAYMENT TABLE --------------------------------
        //Create Payment Entry
        Scanner in = new Scanner(System.in);
        String balance = "INSERT INTO payment(vehicle_id,  tot_balance) " + "VALUES (?, ?)";
        try {
            PreparedStatement p = con.prepareStatement(balance);

            p.setInt(1, vehicle_id);
            p.setDouble(2, running_balance);
            int change = p.executeUpdate();
            System.out.println(String.format("Payment was added %d", change));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    //--------------------------CUSTOMER RECEIPT-----------------------------------------
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("Customer Receipt");
    System.out.println("Location: " + location_name);
    System.out.println("Vehicle ID: " + vehicle_id);
    System.out.println("Vehicle Type: " + vehicle_type);
    System.out.println("Discount Amount: " + dis_amount);
    System.out.println("Duration Fee: " + duration_fee);
    System.out.println("Total Balance: " + running_balance);
    System.out.println();
    System.out.println();

    } //end of method

    public static void customerUpdate(Connection con) {
        //have customer enter name and be able to update personal information
        Scanner input = new Scanner(System.in);

        //--------------------------CHECKING IF CUSTOMER IS REGISTERED IN CUSTOMER DATABASE---------------------------
        int location_id;
        String cust = "select customer_name from customer2 where customer_name = ?";
        //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;  
        try {
            PreparedStatement p = con.prepareStatement(cust);
            boolean checker = false;
            do {
            String name = "";
            ResultSet result;
            System.out.println("Enter your full name here: ");
            name = input.nextLine();

            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setString(1, name);
            result = p.executeQuery();
            if(!result.next()){
                System.out.println ("Name not found: Please have an employee register you as a customer, or retry.");
                return;
                //customerMenu(con);
            }
            else{
                System.out.println("Welcome customer!");
                System.out.format("%-25s\n", "Name");
                do{
                    System.out.format("%-25s\n", result.getString("customer_name"));
                    //System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker = true;
            } 
        } while (checker == false);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } //end of big try-catch block

        Scanner in = new Scanner(System.in);
        boolean selection = false;
        boolean exit = false;
        System.out.println("What would you like to do? \n1. Update address \n2. Update license \n3. Return to Customer Menu\n");
        do {
            String cusRes = in.nextLine();
            if (cusRes.matches("3")){ //returns to customer menu
                selection = true;
            } else if (cusRes.matches("1")){    
                updateAddress(con);
                selection = true;
            } else if (cusRes.matches("2")){   
                updateLicense(con);
                selection = true;
            } else {
                System.out.println("Invalid selection.");
            }
        } while (selection == false);
    
    }

    public static void updateAddress(Connection con) {
        System.out.println("updateAddress Method");

        String addressUpdate = "UPDATE customer2 SET address = ? WHERE customer_name = ?";
        String name = "";
         try {
              PreparedStatement pstmt = con.prepareStatement(addressUpdate);
             // prepare data for update
        	 Scanner sc= new Scanner(System.in);


             int location_id;
            String cust = "select customer_name from customer2 where customer_name = ?";
            //String q6 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where reserved = 0 and location = " + location_name;  
            try {
                PreparedStatement p = con.prepareStatement(cust);
                boolean checker = false;
                do {
                name = "";
                ResultSet result;
                System.out.println("Enter your full name here: ");
                name = sc.nextLine();

                //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
                //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
                p.setString(1, name);
                result = p.executeQuery();
                if(!result.next()){
                    System.out.println ("Name not found. Please re-enter");
                }
                else{
                    System.out.println("Welcome customer!");
                    System.out.format("%-25s\n", "Name");
                    do{
                        System.out.format("%-25s\n", result.getString("customer_name"));
                        //System.out.format("%-15s%-15s%-15s%-10s%-25s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                        //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                    }while(result.next());
                    checker = true;
                } 
            } while (checker == false);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } //end of big try-catch block

                System.out.println("Enter your new address: ");
             //int phone_num=sc.nextInt();
             String address = sc.nextLine();

             System.out.println("Name: " + name);
             System.out.println("Address: " + address);
             //System.out.println();
             //pstmt.setString(address, address);
             pstmt.setString(1, address);
             pstmt.setString(2, name);
             System.out.println("Before update");
             int row = pstmt.executeUpdate();
             //System.out.println(String.format("Address updated %d",row));

             if (row == 0) {
                System.out.println("Name not found; please try updating again.");
             }
             else {
                System.out.println("Address has been successfully updated!");
             }


         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }

    public static void updateLicense(Connection con) {
        System.out.println("updateLicense Method");

        String licenseUpdate = "UPDATE customer2 SET driving_license = ? WHERE customer_name = ?";
         try {
              PreparedStatement pstmt = con.prepareStatement(licenseUpdate);
             // prepare data for update
        	 Scanner sc2= new Scanner(System.in);

             int driver_license = 0;

             System.out.println("Enter your new license: ");

             do {
                try {
                    driver_license = Integer.parseInt(sc2.nextLine());
                } catch (InputMismatchException e) {
                    System.out.println("Only numbers are permitted. Enter your new license.");
                    sc2.next();
                }
                } while (driver_license < 1); //end of while loop
    
                System.out.println("New license: " + driver_license);
             
             //IMPLEMENT A CHECK TO SEE IF THERE'S A NAME MATCH WITHIN DATABASE
             System.out.println("What is your name? ");
             String name=sc2.nextLine(); 

             System.out.println("Name: " + name);
             System.out.println("License: " + driver_license);
             //System.out.println();
             //pstmt.setString(address, address);
             pstmt.setInt(1, driver_license);
             pstmt.setString(2, name);
             int row = pstmt.executeUpdate();
             //phone number updated
             //System.out.println(String.format("License updated %d",row));

             if (row == 0) {
                System.out.println("Name not found; please try updating again.");
             }
             else {
                System.out.println("License has been successfully updated!");
             }

         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }
}
