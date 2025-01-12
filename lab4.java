import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Клас для пасажирів
abstract class Person implements Serializable {
    String name;

    public Person(String name) {
        this.name = name;
    }
}

class Passenger extends Person {
    public Passenger(String name) {
        super(name);
    }
}

class Firefighter extends Person {
    public Firefighter(String name) {
        super(name);
    }
}

class Policeman extends Person {
    public Policeman(String name) {
        super(name);
    }
}

// Абстрактний клас для транспортних засобів
abstract class Vehicle<T extends Person> implements Serializable {
    protected int maxSeats;
    protected int occupiedSeats = 0;
    protected List<T> passengers = new ArrayList<>();

    public Vehicle(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void boardPassenger(T passenger) throws Exception {
        if (occupiedSeats >= maxSeats) {
            throw new Exception("Немає вільних місць!");
        }
        passengers.add(passenger);
        occupiedSeats++;
    }

    public void disembarkPassenger(T passenger) throws Exception {
        if (!passengers.contains(passenger)) {
            throw new Exception("Цей пасажир не знаходиться на борту!");
        }
        passengers.remove(passenger);
        occupiedSeats--;
    }
}

class Bus extends Vehicle<Passenger> {
    public Bus(int maxSeats) {
        super(maxSeats);
    }
}

class Taxi extends Vehicle<Passenger> {
    public Taxi(int maxSeats) {
        super(maxSeats);
    }
}

class FireTruck extends Vehicle<Firefighter> {
    public FireTruck(int maxSeats) {
        super(maxSeats);
    }
}

class PoliceCar extends Vehicle<Policeman> {
    public PoliceCar(int maxSeats) {
        super(maxSeats);
    }
}

// Клас для роботи з файлами
class SerializationUtil {
    // Збереження транспортних засобів у файл
    public static void savePassengers(List<? extends Vehicle<?>> vehicles, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(vehicles);
        }
    }

    // Завантаження транспортних засобів з файлу
    public static List<? extends Vehicle<?>> loadPassengers(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<? extends Vehicle<?>>) in.readObject();
        }
    }
}

// Клас для зберігання автомобілів на дорозі
class Road {
    public List<Vehicle<?>> carsOnRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<? extends Person> car) {
        carsOnRoad.add(car); // Це тепер буде працювати
    }

    public int getCountOfHumans() {
        int total = 0;
        for (Vehicle<? extends Person> vehicle : carsOnRoad) {
            total += vehicle.getOccupiedSeats();
        }
        return total;
    }
}


// Основний клас для тестування
public class Main
    {
    public static void main(String[] args) {
        try {
            // Створення транспортних засобів
            Bus bus = new Bus(5);
            Taxi taxi = new Taxi(4);
            FireTruck fireTruck = new FireTruck(3);
            PoliceCar policeCar = new PoliceCar(2);

            // Створення пасажирів
            Passenger passenger = new Passenger("Іван");
            Firefighter firefighter = new Firefighter("Марія");
            Policeman policeman = new Policeman("Олексій");

            // Тест посадки пасажирів
            bus.boardPassenger(passenger);
            taxi.boardPassenger(passenger);
            fireTruck.boardPassenger(firefighter);
            policeCar.boardPassenger(policeman);

            // Тест висадки пасажирів
            bus.disembarkPassenger(passenger);

            // Тест перевищення кількості місць
            try {
                bus.boardPassenger(new Passenger("Микола"));
                bus.boardPassenger(new Passenger("Анастасія"));
                bus.boardPassenger(new Passenger("Дмитро"));
            } catch (Exception e) {
                System.out.println(e.getMessage());  // Повідомлення про перевищення місць
            }

            // Тест збереження та завантаження
            List<Vehicle<?>> vehicles = new ArrayList<>();
            vehicles.add(bus);
            vehicles.add(taxi);
            vehicles.add(fireTruck);
            vehicles.add(policeCar);

            // Збереження в файл
            SerializationUtil.savePassengers(vehicles, "vehicles.dat");

            // Завантаження з файлу
            List<? extends Vehicle<?>> loadedVehicles = SerializationUtil.loadPassengers("vehicles.dat");
            System.out.println("Завантажено транспортних засобів: " + loadedVehicles.size());

            // Виведення пасажирів кожного транспортного засобу після завантаження
            for (Vehicle<? extends Person> vehicle : loadedVehicles) {
                System.out.println("Тип транспортного засобу: " + vehicle.getClass().getSimpleName());
                System.out.println("Кількість пасажирів: " + vehicle.getOccupiedSeats());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
