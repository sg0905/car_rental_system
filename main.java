import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;


    //Constructor used here
    public Car(String carId, String brand, String model, double basePricePerDay){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getcarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
    
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId(){
        return customerId;
    }
    public String getName(){
        return name;
    }

}


class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer =customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }

}


class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else{
            System.out.println("Car is not available for booking");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar()==car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully");
        }
        else{
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner sc =new Scanner(System.in);
        
        while(true){
            System.out.println("----------Car Rental System---------");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("\n -----Rent a car-----\n");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\n Available Cars: ");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getcarId() + " . " + car.getBrand()+ " . " + car.getModel());

                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = sc.nextLine();


                System.out.print("Enter the days you want to rent car : ");
                int rentalDays =sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS"+ (customers.size() +1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car: cars){
                    if(car.getcarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;

                    }
                }

                    if(selectedCar != null){
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println("\n----Rental Infromation----");
                        System.out.println("Customer ID: " + newCustomer.getCustomerId());
                        System.out.println("Customer Name: " + newCustomer.getName());
                        System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                        System.out.println("Rental Days: "+ rentalDays);
                        System.out.printf("Total Price: $%.2f%n", totalPrice);
                        System.out.print("\nConfirm Rental (Y/N): ");
                        String confirm = sc.nextLine();

                        if(confirm.equalsIgnoreCase("Y")){
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println("\nCar Rented Successfully.");

                        }
                        else{
                            System.out.println("\nRental Cancelled");
                        }

                    }
                    else {
                        System.out.println("\nInvalid car Selection or car is not available for rent.");
                    }
                } else if(choice == 2){
                    System.out.println("\n-------Rent a car-----\n");
                    System.out.print("Enter car ID you want to return: ");
                    String carId = sc.nextLine();

                    Car carToReturn = null;
                    for(Car car : cars){
                        if(car.getcarId().equals(carId) && !car.isAvailable()){
                            carToReturn = car;
                            break;
                        }
                    }
                    if(carToReturn !=null){
                        Customer customer = null;
                        for(Rental rental : rentals){
                            if(rental.getCar() == carToReturn){
                                customer = rental.getCustomer();
                                break;
                            }
                        }
                        if(customer != null){
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getName());
                        }
                        else{
                            System.out.println("Car was not rented or rental information is missing.");
                        }

                    }
                    else {
                        System.out.println("Invalid car ID or car is not rented");
                    }
                }
                else if((choice == 3)){
                    break;
                }
                else{
                    System.out.println("Invalid choice");
                }
            }
            System.out.println("\n Thankyou for using car rental service");

        }
    }
    
public class Main{
    public static void main(String args[]){
        CarRentalSystem rs = new CarRentalSystem(); 

        Car car1 = new Car("C1", "Honda", "City", 100.0);
        Car car2 = new Car("C2", "Toyota", "Innova", 110.0);
        Car car3 = new Car("C3", "Suzuki", "Wagnor", 60.0);
        rs.addCar(car1);
        rs.addCar(car2);
        rs.addCar(car3);

        rs.menu();
    }
}