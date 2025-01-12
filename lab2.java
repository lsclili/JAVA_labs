import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Клас для збереження запису в журналі
class JournalEntry {
    private String lastName;
    private String firstName;
    private String birthDate;
    private String phone;
    private String address;

    // Конструктор класу для ініціалізації даних
    public JournalEntry(String lastName, String firstName, String birthDate, String phone, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    // Метод для виведення інформації про запис
    @Override
    public String toString() {
        return "Прізвище: " + lastName + ", Ім'я: " + firstName + ", Дата народження: " + birthDate +
               ", Телефон: " + phone + ", Адреса: " + address;
    }
}

public class JournalApp {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<JournalEntry> journal = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            // Виведення головного меню
            System.out.println("Виберіть опцію:");
            System.out.println("1. Додати запис до журналу");
            System.out.println("2. Показати всі записи");
            System.out.println("3. Вийти");

            String option = scanner.nextLine();

            // Обробка вибору користувача
            switch (option) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    showEntries();
                    break;
                case "3":
                    System.out.println("Вихід з програми.");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте знову.");
            }
        }
    }

    // Метод для додавання нового запису до журналу
    private static void addEntry() {
        String lastName = getInput("Введіть прізвище студента: ");
        String firstName = getInput("Введіть ім'я студента: ");
        String birthDate = getValidDate("Введіть дату народження студента (формат: dd.MM.yyyy): ");
        String phone = getValidPhone("Введіть телефон студента: ");
        String address = getInput("Введіть домашню адресу (вулиця, будинок, квартира): ");

        // Додавання нового запису в журнал
        journal.add(new JournalEntry(lastName, firstName, birthDate, phone, address));
        System.out.println("Запис додано до журналу.");
    }

    // Метод для вводу текстових даних
    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Метод для перевірки та отримання правильної дати
    private static String getValidDate(String prompt) {
        while (true) {
            String date = getInput(prompt);
            if (isValidDate(date)) {
                return date;
            } else {
                System.out.println("Невірний формат дати. Спробуйте знову.");
            }
        }
    }

    // Перевірка формату дати (dd.MM.yyyy)
    private static boolean isValidDate(String date) {
        String regex = "^([0-2][0-9]|(3)[0-1])\\.(0[1-9]|1[0-2])\\.(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    // Метод для перевірки та отримання правильного телефону
    private static String getValidPhone(String prompt) {
        while (true) {
            String phone = getInput(prompt);
            if (isValidPhone(phone)) {
                return phone;
            } else {
                System.out.println("Невірний формат телефону. Спробуйте знову.");
            }
        }
    }

    // Перевірка формату телефону (для України)
    private static boolean isValidPhone(String phone) {
        String regex = "^(\\+380)(\\d{9})$"; // Український формат номера
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // Метод для відображення всіх записів в журналі
    private static void showEntries() {
        if (journal.isEmpty()) {
            System.out.println("Журнал порожній.");
        } else {
            System.out.println("Записи в журналі:");
            for (JournalEntry entry : journal) {
                System.out.println(entry);
            }
        }
    }
}
