package src;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Employee {
    public void empMenu(Connection con) {
        String input;
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        boolean selection = false;
        System.out.println("Welcome, Hertz Employee!");
        do {
            System.out.println("Please select a menu option \n1. View Rentals \n2. Return Vehicle \n3. View Payment Records \n4. Register a new customer \n5. Return to Menu \n");
            String empChoice = in.nextLine();
            if (empChoice.matches("1")){
                System.out.println("View Rentals");
                viewRentals(con);
                //copy method from Customer.java here
            } else if (empChoice.matches("2")){
                System.out.println("Return Vehicle");
                returnVehicle(con);
            } else if (empChoice.matches("3")) {
                System.out.println("View Payments");
                viewPayment(con);
            }else if (empChoice.matches("4")) {
                System.out.println("Register a new customer");
                newCustomer(con);
            }
            else if (empChoice.matches("5")) {
                selection = true;
                exit = true;
            }  
            else {
                System.out.println("Invalid selection.");
            }
       } while (selection == false);
    } //end of empMenu

    public void viewPayment(Connection con) {
        try (
          Statement s=con.createStatement();
      ){
      //Statement s=con.createStatement();
        String q;
        ResultSet result;
        q = "select payment_id, vehicle_id, tot_balance from payment";
        result = s.executeQuery(q);
        if (!result.next()) System.out.println ("Empty result.");
        else {
      //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
            System.out.format("%-15s%-15s%-15s\n", "Payment ID", "Vehicles ID", "Total Balance");
          do {
            //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
              System.out.format("%-15s%-15s%-15s\n", result.getInt("payment_id"), result.getInt("vehicle_id"), result.getInt("tot_balance"));
          } while (result.next());
        }

    } catch(Exception e){e.printStackTrace();} 
    }

    public void viewRentals(Connection con) {
        try (
          Statement s=con.createStatement();
      ){
      //Statement s=con.createStatement();
        String q;
        ResultSet result;
        q = "select rental_id, vehicle_id, Pick_Up, Drop_Off, Duration from rental order by rental_id";
        result = s.executeQuery(q);
        if (!result.next()) System.out.println ("Empty result.");
        else {
      //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
            System.out.format("%-15s%-15s%-30s%-30s%-15s\n", "Rental ID", "Vehicle ID", "Pick Up", "Drop Off", "Duration");
          do {
            //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
              System.out.format("%-15s%-15s%-30s%-30s%-15s\n", result.getInt("rental_id"), result.getInt("vehicle_id"), result.getObject("Pick_Up"), result.getObject("Drop_Off"), result.getObject("Duration"));
          } while (result.next());
        }

    } catch(Exception e){e.printStackTrace();} 
    }

    public void newCustomer(Connection con) {
        Scanner input = new Scanner(System.in);
        ResultSet r;
        System.out.println("newCustomer Method (Test)");
        String reserve = "INSERT INTO customer2(customer_name,address,driving_license) " + "VALUES (?, ?, ?)";
        try {
            PreparedStatement p = con.prepareStatement(reserve);
             // prepare data for update
             //Collect Customer Information
            System.out.println("Enter name: ");
            String name = input.nextLine();

            System.out.println("Enter address: ");
            String address = input.nextLine();
            
            System.out.println("Enter driver's license number: "); //handle exception 
            int driver_license = 0;
            do {
            try {
                driver_license = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (driver_license < 1); //end of while loop

            p.setString(1, name);
            p.setString(2, address);
            p.setInt(3, driver_license);

            int change = p.executeUpdate();
            System.out.println(String.format("Customer was added %d",change));

            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void returnVehicle(Connection con) {
        System.out.println("returnVehicle function");

        //PROBLEM: Employee should be looking at rental table. 
        //-----------------------------DISPLAY RENTED CARS--------------------------------
        System.out.println("Which car would you like to return?");
        String ret_car = "";
        try (
            Statement s=con.createStatement();
        ){
        //Statement s=con.createStatement();
           String q3;
           ResultSet result;
           q3 = "select vehicle_id, pick_up, drop_off from rental";
           result = s.executeQuery(q3);
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-30s%-30s\n", "Vehicle ID", "Pick_Up", "Drop_Off");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                //System.out.format("%-15s%-25s\n", result.getInt("location_id"), result.getString("name"));
                //location_name = result.getString("name");
                System.out.format("%-15s%-30s%-30s\n", result.getInt("vehicle_id"), result.getObject("Pick_Up"), result.getObject("Drop_off"));
            } while (result.next());
           }

    } catch(Exception e){e.printStackTrace();} 

    int vehicle_id = 0;
    Scanner input = new Scanner(System.in);
    //try-catch block for choosing vehicle by vehicle ID
    String q2 = "select vehicle_id, pick_up, drop_off from rental where vehicle_id = ?";
            try {
            PreparedStatement p = con.prepareStatement(q2);
            boolean checker2 = false;
            vehicle_id = 0;
            do {
            //String q2;
            ResultSet result;
            System.out.println("Enter the Vehicle ID of the car you would like to return.");
            //int vehicle_id = input.nextInt();
            
            do {
            try {
                vehicle_id = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted. Enter the Vehicle ID of the car you would like to return.");
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
                System.out.println("Here is the vehicle that you would like to return.");
                System.out.format("%-15s%-30s%-30s\n", "Vehicle ID", "Pick_Up", "Drop_Off");
                do{
                    System.out.format("%-15s%-30s%-30s\n", result.getInt("vehicle_id"), result.getObject("Pick_Up"), result.getObject("Drop_off"));
                    //vehicle_type = result.getString("type");
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

      //-------------------------------DISPLAYING CHARGES------------------------------
      double dropoff_charge = 0;
      System.out.println();
      System.out.println("Dropoff Charges");
      System.out.println();
      
      String charges = "select charge_id, charge_type, charge_amount from additional_charges where persona = 'Emp' order by charge_id";
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
        
    String charge = "select charge_id, charge_type, charge_amount from additional_charges where persona = 'Emp' and charge_id = ?";
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

    dropoff_charge += charge_amt;
    System.out.println("Dropoff Charge Amount: " + dropoff_charge);

    //------------------------UPDATE ODOMETER READING THROUGH VEHICLE ID-----------------------------------

        String odomUpdate = "UPDATE vehicle SET odometer = ? WHERE vehicle_id = ?";
         try {
              PreparedStatement pstmt = con.prepareStatement(odomUpdate);
             // prepare data for update
        	 Scanner sc= new Scanner(System.in);
             System.out.println("Enter new odometer reading: "); //handle exception 
            int odom = 0;
            do {
            try {
                odom = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (odom < 1); //end of while loop

            //check if vehicle ID is valid
             pstmt.setInt(1, odom);
             pstmt.setInt(2, vehicle_id);
             System.out.println("Before update");
             int row = pstmt.executeUpdate();
             System.out.println(String.format("Odometer updated %d",row));


         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }

        

    //--------ACCESS PAYMENT BALANCE THROUGH VEHICLE ID, THEN ADD PAYMENT BALANCE TO THE DROP OFF CHARGE AMOUNT
    
    //try-catch block for getting location by vehicle ID
    String q3 = "select tot_balance from payment where vehicle_id = ?";
    double running_bal = 0;
            try {
            PreparedStatement p = con.prepareStatement(q3);
            boolean checker2 = false;
            do {
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, vehicle_id);
            ResultSet result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the current balance of this vehicle.");
                System.out.format("%-15s\n", "Total Balance");
                do{
                    System.out.format("%-15s\n",  result.getString("tot_balance"));
                    running_bal = result.getDouble("tot_balance");
                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker2 = true;
            }
        } while(checker2 == false); //end of do-while loop
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    System.out.println("Calculating Final Cost: ");
    System.out.println("Dropoff Charges: " + dropoff_charge);
    System.out.println("Payment Balance: " + running_bal);
    double total = dropoff_charge + running_bal;
    System.out.println("Final Cost: " + total);


    //-------------------UPDATE RESERVE STATUS ON VEHICLE-----------------------------------
    String reserveUpdate = "UPDATE vehicle SET reserved = 0 WHERE vehicle_id = ?";
         try {
              PreparedStatement pstmt = con.prepareStatement(reserveUpdate);
             // prepare data for update
        	 Scanner sc= new Scanner(System.in);
            

            //check if vehicle ID is valid
             pstmt.setInt(1, vehicle_id);
             System.out.println("Before update");
             int row = pstmt.executeUpdate();
             System.out.println(String.format("Reservation status updated %d",row));


         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    
    //-----------------UPDATE LOCATION NUMBER OF CARS RENTED----------------------------------

    System.out.println("Updating Location: Number of Cars Rented");
    //1. Get location from vehicle ID

    //try-catch block for getting location by vehicle ID
    String q4 = "select location from vehicle where vehicle_id = ?";
    String location_name = "";
            try {
            PreparedStatement p = con.prepareStatement(q4);
            boolean checker2 = false;
            do {
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id LIKE '%" + vehicle_id + "%'";
            //q2 = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle where vehicle_id = ?";
            p.setInt(1, vehicle_id);
            ResultSet result = p.executeQuery();
            if(!result.next()){
                System.out.println ("No matches. Try again.");
            }
            else{
                System.out.println("Here is the location that is going to be updated.");
                System.out.format("%-30s\n", "Location");
                do{
                    System.out.format("%-30s\n",  result.getString("location"));
                    location_name = result.getString("location");

                    //System.out.format("%-15s%-15s%-20s%-20s%-25s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
                }while(result.next());
                checker2 = true;
            }
        } while(checker2 == false); //end of do-while loop
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    //2. Update location
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

    //-----------------------REMOVE RENTAL FROM RENTAL TABLE BY VEHICLE ID---------------------------------
    String removeRental = "DELETE from rental WHERE vehicle_id = ?";
         try {
              PreparedStatement pstmt = con.prepareStatement(removeRental);
             // prepare data for update
        	 Scanner sc= new Scanner(System.in);
        
            //check if vehicle ID is valid
             pstmt.setInt(1, vehicle_id);
             System.out.println("Before update");
             int row = pstmt.executeUpdate();
             System.out.println(String.format("Rental Table Updated %d",row));


         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }

}
