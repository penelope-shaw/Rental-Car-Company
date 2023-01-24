Name: Penelope Shaw

Instructions to run pss324.jar file from pss324shaw directory:
    1. java -jar pss324.jar


Instructions to compile from pss324shaw directory: 
    1. cd into pss324 
    2. cd into  src
    3. javac *.java 
    4. cd ..
    5. jar cvfm pss324.jar Manifest.txt -C bin .
    6. java -jar pss324.jar 

Description of Interfaces: 
- Customer
    Customers have the options to update their information and to make a reservation for a rental. 

    Renting a Vehicle: 
        A customer needs to be registered in the system in order to make a reservation.
        Customer Names to test: 
        "Penelope Shaw", "James Charles". Once that customer is registered, 
        then they can make a reservation.

        Customers first choose a location, and then a vehicle from that location. 
        
        A rent fee is calculated based on the type of vehicle, and then the vehicle entry is updated
        so that it is flagged as reserved. The location is also updated to reflect the new number of cars
        rented from it.

        The customer then selects the date of renting and returning the vehicle. A rental entry is created, 
        and then the customer can select a charge. 

        IMPORTANT - Ideally, the customer would be able to choose more than one charge, but
        I was having trouble implementing a do-while loop to allow that. 

        The customer can then apply a discount code if they have one. If they do not have one,
        then they can enter 0 to continue. 

        The discount codes are shown here for testing: 

        Group                    Discount Code
        Lehigh                   	 101
        Google	                     555
        AAA	                         999
        No Group Applicable     	  0

        Then a fee is calculated based on the duration of the rental. Finally, 
        a payment entry is created for that vehicle. 

        Finally, a receipt for the customer is generated.   

    Updating Information:
        A customer can update their information if they are registered in the system. They can update their 
        address or their email. 


- Employee
    Employees have the options to view rentals, return a vehicle, view payment records, and register a new customer.

    Viewing Rentals:
        By selecting the corresponding option, an employee can view a list of rentals.
    
    Returning a Rental:
        An employee chooses a vehicle to return from a list of rentals. Then, the employee
        chooses a dropoff charge to apply. 

        NOTE - Similarly to the customer charges, it would be ideal if more tha one charge could be chosen
        by using a do-while loop, but unfortunately I was having a difficult time implementing that. 

        The odometer of the vehicle is updated, and then the dropoff charge is added to the current balance of the 
        vehicle to produce the final cost of the rental.

        The vehicle's reservation status is updated as well as the inventory of the vehicle's location

        Finally, the rental is removed from the rental table. 

    Viewing Payments: 
        By selecting the corresponding option, an employee can view a list of payments.

    Registering a new Customer: 
        An employee enters requested customer information to register a new customer.


- Manager
    Managers have the options to view customers, view payment records, view locations, add a charge,
    add a vehicle, and add a discount.

    Viewing Customers:
        By selecting the corresponding option, a manager can view a list of customers.

    Viewing Payment Records:
        By selecting the corresponding option, a manager can view a list of payments.

    Viewing Locations:
        By selecting the corresponding option, a manager can view a list of locations.

    Add a charge:
        By selecting the corresponding option, a manager can add a charge.

    Add a vehicle:
        By selecting the corresponding option, a manager can add a vehicle.

    Add a discount:
        By selecting the corresponding option, a manager can add a discount.


Ideal Way to Test:
- As a customer, rent a vehicle. Then, as an employee, return the vehicle. 

Data Source: 
- https://www.mockaroo.com/schemas
