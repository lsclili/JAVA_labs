import java.util.*;

// Модель: Визначає дані та логіку
interface Drawable {
    void draw();
}

abstract class Shape implements Drawable {
    protected String shapeColor; // Колір фігури

    // Конструктор для ініціалізації кольору фігури
    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    // Абстрактний метод для обчислення площі
    public abstract double calcArea();

    // Перевизначення методу toString для опису фігури
    @Override
    public String toString() {
        return "Фігура: " + getClass().getSimpleName() + ", Колір: " + shapeColor + ", Площа: " + calcArea();
    }
}

class Rectangle extends Shape {
    private double width;  // Ширина прямокутника
    private double height; // Висота прямокутника

    // Конструктор для ініціалізації прямокутника
    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    // Реалізація обчислення площі
    @Override
    public double calcArea() {
        return width * height;
    }

    // Реалізація методу draw для прямокутника
    @Override
    public void draw() {
        System.out.println("Малювання прямокутника");
    }
}

class Triangle extends Shape {
    private double base;   // Основа трикутника
    private double height; // Висота трикутника

    // Конструктор для ініціалізації трикутника
    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    // Реалізація обчислення площі
    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    // Реалізація методу draw для трикутника
    @Override
    public void draw() {
        System.out.println("Малювання трикутника");
    }
}

class Circle extends Shape {
    private double radius; // Радіус кола

    // Конструктор для ініціалізації кола
    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    // Реалізація обчислення площі
    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    // Реалізація методу draw для кола
    @Override
    public void draw() {
        System.out.println("Малювання кола");
    }
}

// Представлення: Відображає дані користувачу
class View {
    // Метод для відображення всіх фігур
    public void displayShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }

    // Метод для відображення повідомлення
    public void displayMessage(String message) {
        System.out.println(message);
    }
}

// Контролер: Обробляє ввід користувача та оновлює представлення
class Controller {
    private Shape[] shapes; // Масив фігур
    private View view;       // Об'єкт представлення

    // Конструктор для ініціалізації контролера
    public Controller(Shape[] shapes, View view) {
        this.shapes = shapes;
        this.view = view;
    }

    // Метод для відображення всіх фігур
    public void displayShapes() {
        view.displayShapes(shapes);
    }

    // Метод для обчислення та відображення сумарної площі всіх фігур
    public void displayTotalArea() {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calcArea();
        }
        view.displayMessage("Загальна площа всіх фігур: " + totalArea);
    }

    // Метод для обчислення та відображення сумарної площі фігур заданого типу
    public void displayTotalAreaByType(Class<?> shapeType) {
        double totalArea = 0;
        for (Shape shape : shapes) {
            if (shapeType.isInstance(shape)) {
                totalArea += shape.calcArea();
            }
        }
        view.displayMessage("Загальна площа " + shapeType.getSimpleName() + ": " + totalArea);
    }

    // Метод для сортування фігур за площею
    public void sortShapesByArea() {
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
        view.displayMessage("Фігури відсортовані за площею.");
    }

    // Метод для сортування фігур за кольором
    public void sortShapesByColor() {
        Arrays.sort(shapes, Comparator.comparing(shape -> shape.shapeColor));
        view.displayMessage("Фігури відсортовані за кольором.");
    }
}

// Головний клас програми
public class lab3 {
    public static void main(String[] args) {
        // Ініціалізація масиву фігур
        Shape[] shapes = {
            new Rectangle("Червоний", 3, 4),
            new Triangle("Синій", 5, 6),
            new Circle("Зелений", 7),
            new Rectangle("Жовтий", 2, 3),
            new Triangle("Червоний", 6, 8),
            new Circle("Синій", 5),
            new Rectangle("Зелений", 4, 5),
            new Triangle("Жовтий", 7, 9),
            new Circle("Червоний", 3),
            new Rectangle("Синій", 5, 6)
        };

        // Ініціалізація представлення та контролера
        View view = new View();
        Controller controller = new Controller(shapes, view);

        // Виклик методів контролера для обробки фігур
        controller.displayShapes();
        controller.displayTotalArea();
        controller.displayTotalAreaByType(Rectangle.class);
        controller.sortShapesByArea();
        controller.displayShapes();
        controller.sortShapesByColor();
        controller.displayShapes();
    }
}
