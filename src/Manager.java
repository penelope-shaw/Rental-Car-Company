package src;
import java.io.*;
import java.sql.Date;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
    public void managerMenu(Connection con) {
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        boolean selection = false;
        do {
            System.out.println("Welcome Hertz manager! Select a menu option:");
            System.out.println("1. View Customers\n2. View Payment Records \n3. View Vehicles \n4. Add Charge \n5. View Locations \n6. Add Vehicle \n7. Add Discount \n8. Return to Main Menu");
            String mangSel = in.nextLine();
            if (mangSel.matches("1")){//visit data
                System.out.println("View Customer Records");
                viewCustomers(con);
                  //visitData(con);
            } else if (mangSel.matches("2")){//record lease data
                System.out.println("View Payment Records");
                viewPayment(con);
            } else if (mangSel.matches("3")){
                System.out.println("View Vehicles");
                viewVehicles(con);
            }else if (mangSel.matches("4")) {
              addCharge(con);
            } else if (mangSel.matches("5")) {
              viewLocations(con);
            } else if (mangSel.matches("6")) {
              addVehicle(con);
            } else if (mangSel.matches("7")) {
              addDiscount(con);
            } else if (mangSel.matches("8")) {
              //return back to main menu
              selection = true;
              exit = true;
            }
            
            else {
                //not 1-4 or invalid character
                System.out.println("Invalid selection.");
            }
        } while (selection == false);

   } //end of managerMenu


   public void viewCustomers(Connection con) {
        System.out.println("viewCustomers Method (Test)");
        try {
        Statement s=con.createStatement();
           String q;
           ResultSet result;
           q = "select customer_id, customer_name, address, driving_license from customer2 order by customer_id";
           result = s.executeQuery(q);
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Customer ID\t Customer Name\t\t Group Name\t\t Address\t Driving License");
               System.out.format("%-15s%-20s%-35s%-15s\n", "Customer ID", "Customer Name", "Address", "Driver's License");
             do {
                System.out.format("%-15s%-20s%-35s%-15s\n", result.getInt("customer_id"),  result.getString("customer_name"), result.getString("address"), result.getInt("driving_license"));
                //System.out.println (result.getInt("customer_id")+ " " + result.getString("customer_name")+ " " + result.getString("group_name")+ " " + result.getString("address")+ " " + result.getInt("driving_license"));
             } while (result.next());
           }
         } catch(Exception e){e.printStackTrace();}
        //System.out.println("End of viewCustomers Method");
   } //end of viewCustomers 

   public void viewPayment(Connection con) {
    System.out.println("viewPayment Method (Test)");
        try {
        Statement s=con.createStatement();
           String q;
           ResultSet result;
           q = "select payment_id, vehicle_id, tot_balance from payment order by payment_id";
           result = s.executeQuery(q);
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Customer ID\t Customer Name\t\t Group Name\t\t Address\t Driving License");
               System.out.format("%-15s%-20s%-20s\n", "Payment ID", "Vehicle ID", "Total Balance");
             do {
                System.out.format("%-15s%-20s%-20s\n", result.getInt("payment_id"),  result.getInt("vehicle_id"),   result.getInt("tot_balance"));
                //System.out.println (result.getInt("customer_id")+ " " + result.getString("customer_name")+ " " + result.getString("group_name")+ " " + result.getString("address")+ " " + result.getInt("driving_license"));
             } while (result.next());
           }
         } catch(Exception e){e.printStackTrace();}
   }

   public void viewVehicles(Connection con) {
    try (
            Statement s=con.createStatement();
        ){
        //Statement s=con.createStatement();
           String q;
           ResultSet result;
           q = "select vehicle_id, tank, make, type, model, reserved, odometer from vehicle order by vehicle_id";
           result = s.executeQuery(q);
           if (!result.next()) System.out.println ("Empty result.");
           else {
			   //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
               System.out.format("%-15s%-15s%-15s%-15s%-23s%-10s%-10s\n", "Vehicle ID", "Tank", "Make", "Type", "Model", "Reserved", "Odometer");
             do {
               //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
                System.out.format("%-15s%-15s%-15s%-15s%-23s%-10s%-10s\n", result.getString("vehicle_id"), result.getInt("tank"), result.getString("make"), result.getString("type"),  result.getString("model"), result.getInt("reserved"),  result.getInt("odometer"));
            } while (result.next());
           }

    } catch(Exception e){e.printStackTrace();} 
   }

   public void viewLocations(Connection con) {
      try (
          Statement s=con.createStatement();
      ){
      //Statement s=con.createStatement();
        String q;
        ResultSet result;
        q = "select location_id, name, inventory, number_cars_rented from location order by location_id";
        result = s.executeQuery(q);
        if (!result.next()) System.out.println ("Empty result.");
        else {
      //System.out.println("Vehicle ID\t Tank\t Make\t Model\t Reserved \t Odometer");
            System.out.format("%-15s%-30s%-15s%-10s\n", "Location ID", "Name", "Inventory", "Number of Cars Rented Out");
          do {
            //System.out.println (result.getString("vehicle_id")+ " " + result.getInt("tank")+ " " + result.getString("make")+ " " + result.getString("type")+ " " + result.getString("model") + " " + result.getInt("reserved")+ " " + result.getInt("odometer"));
              System.out.format("%-15s%-30s%-15s%-10s\n", result.getInt("location_id"), result.getString("name"), result.getInt("inventory"), result.getInt("number_cars_rented"));
          } while (result.next());
        }

    } catch(Exception e){e.printStackTrace();} 
   }

   public void addCharge(Connection con) {
    System.out.println("addCharge");
    Scanner input = new Scanner(System.in);
        String charge = "INSERT INTO additional_charges(charge_type, charge_amount, persona) " + "VALUES (?, ?, ?)";
        try {
            PreparedStatement p = con.prepareStatement(charge);
             // prepare data for update
             //Collect Customer Information
            System.out.println("Enter name of charge: ");
            String chargeName = input.nextLine();

            System.out.println("Enter charge amount: "); //handle exception 
            int chargeAmount = 0;
            do {
            try {
                chargeAmount = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (chargeAmount < 1); //end of while loop

            String persona = "";
            do {
                System.out.println("Enter persona: (Emp or Customer)");
                persona = input.nextLine();
            } while (!(persona.equals("Emp") || !(persona.equals("Customer"))));

            p.setString(1, chargeName);
            p.setInt(2, chargeAmount);
            p.setString(3, persona);

            int change = p.executeUpdate();
            System.out.println(String.format("Charge was added %d",change));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
   }


   public void addDiscount(Connection con) {
    System.out.println("addDiscount");
    Scanner input = new Scanner(System.in);
        String discount = "INSERT INTO discount(discount_type, dis_amount, dis_code) " + "VALUES (?, ?, ?)";
        try {
            PreparedStatement p = con.prepareStatement(discount);
             // prepare data for update
             //Collect Customer Information
            System.out.println("Enter name of discount: ");
            String discountName = input.nextLine();

            System.out.println("Enter discount amount: "); //handle exception 
            int discountAmount = 0;
            do {
            try {
                discountAmount = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (discountAmount < 1); //end of while loop


            System.out.println("Enter discount code: "); //handle exception 
            int discountCode = 0;
            do {
            try {
                discountCode = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (discountCode < 1); //end of while loop

            p.setString(1, discountName);
            p.setInt(2, discountAmount);
            p.setInt(3, discountCode);

            int change = p.executeUpdate();
            System.out.println(String.format("Discount was added %d", change));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
   }

   public void addVehicle(Connection con) {
    System.out.println("addVehicle");
    Scanner input = new Scanner(System.in);
        String vehicle = "INSERT INTO vehicle(VIN, tank, make, type, model, reserved, odometer, location) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement p = con.prepareStatement(vehicle);
             // prepare data for update
             //Collect Customer Information
            System.out.println("Enter VIN: ");
            String VIN = input.nextLine();

            System.out.println("Enter gallons in tank: "); //handle exception 
            int tank = 0;
            do {
            try {
                tank = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers between 1 and 25 are permitted.");
                input.next();
            }
            } while (tank < 1 || tank > 25); //end of while loop

            System.out.println("Enter make: ");
            String make = input.nextLine();

            System.out.println("Enter type: ");
            String type = input.nextLine();

            System.out.println("Enter model: ");
            String model = input.nextLine();

            System.out.println("Enter reserved designation (0 if not reserved, 1 if reserved): "); //handle exception 
            int reserve = 2;
            do {
            try {
                reserve = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only values 0 or 1 are permitted.");
                input.next();
            }
            } while (reserve != 1 || tank != 0); //end of while loop

            System.out.println("Enter odometer reading: "); //handle exception 
            int odom = 0;
            do {
            try {
                odom = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are permitted.");
                input.next();
            }
            } while (odom < 1); //end of while loop

            System.out.println("Enter location: ");
            String location = input.nextLine();


            p.setString(1, VIN);
            p.setInt(2, tank);
            p.setString(3, make);
            p.setString(4, type);
            p.setString(5, model);
            p.setInt(6, reserve);
            p.setInt(7, odom);
            p.setString(8, location);

            int change = p.executeUpdate();
            System.out.println(String.format("Vehicle was added %d", change));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
   }

}

