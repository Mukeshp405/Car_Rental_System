import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Car Class
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

// Customer Class
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getcustomerId() {
        return customerId;
    }

    public String getname() {
        return name;
    }
}

// Rental Classs
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// Rental System Class
class RentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public RentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("          --------------");
            System.out.println("         /    |     |   \\");
            System.out.println("        /     |     |    \\");
            System.out.println(" ------- ----- ----- ---- --------");
            System.out.println("|                                 |");
            System.out.println("|         Car Rental System       |");
            System.out.println(" -------(  *  )--------(  *  )----");
            System.out.println();
            System.out.println();
            System.out.println("-------------------");
            System.out.println("Choose the option:");
            System.out.println("-------------------");
            System.out.println("| 1. Rent a Car   |");
            System.out.println("| 2. Return a Car |");
            System.out.println("| 3. Exit         |");
            System.out.println("-------------------");
            System.out.println("--------------------------");
            System.out.print(" Enter your choice :- ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println("--------------------------");
            System.out.println();

            if (choice == 1) {
                System.out.println("       -------------");
                System.out.println("      | Rent a Car: |");
                System.out.println("       -------------");
                System.out.println("-----------------------------");
                System.out.print("Enter your name :- ");
                String customerName = scanner.nextLine();
                System.out.println("-----------------------------");
                System.out.println();

                System.out.println("-----------------------");
                System.out.println("Available Cars:");
                System.out.println("-----------------------");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.println("-----------------------");

                System.out.print("\nEnter the car ID you want to rent :- ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental :- ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume new line

                Customer newcustomer = new Customer("Cus" + (customers.size() + 1), customerName);
                addCustomer(newcustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalprice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("     ----------------------");
                    System.out.println("-----| Rental Information |-----");
                    System.out.println("     ----------------------");
                    System.out.println("Customer ID :- " + newcustomer.getcustomerId());
                    System.out.println("Customer Name :- " + newcustomer.getname());
                    System.out.println("Car :- " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days :- " + rentalDays);
                    System.out.printf("Total price :- $%.2f%n", totalprice);
                    System.out.println("------------------------------------");

                    System.out.print("\nConfirm rental (Y/N) :- ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newcustomer, rentalDays);
                        System.out.println("\nCar rented Successfully.");
                        System.out.println();
                    } else {
                        System.out.println("\nRental Cancled.");
                        System.out.println();
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("    ----------------");
                System.out.println("----| Return a Car |----");
                System.out.println("    ----------------");
                System.out.print("Enter the car ID you want to return :- ");
                String carId = scanner.nextLine();

                Car carToRetCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToRetCar = car;
                        break;
                    }
                }
                if (carToRetCar != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToRetCar) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToRetCar);
                        System.out.println("Car returned Successfully by " + customer.getname());
                        System.out.println();
                    } else {
                        System.out.println("Car was not returned or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Main {
    public static void main(String[] args) {
        RentalSystem rentalsystem = new RentalSystem();

        Car car1 = new Car("c001", "Toyota", "Camry", 200.0);
        Car car2 = new Car("c002", "Honda", "Accord", 150.0);
        Car car3 = new Car("c003", "Mahindra", "Thar", 250.0);
        Car car4 = new Car("c004", "Mahindra", "Scorpio", 220.0);
        Car car5 = new Car("c005", "Audi", "A4", 400.0);
        rentalsystem.addCar(car1);
        rentalsystem.addCar(car2);
        rentalsystem.addCar(car3);
        rentalsystem.addCar(car4);
        rentalsystem.addCar(car5);

        rentalsystem.menu();
    }
}