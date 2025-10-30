import java.util.*;
import java.util.stream.Collectors;

// Перечисление для типа телефона
enum PhoneType {
    MOBILE, LANDLINE
}

class Phone {
    private String number;
    private PhoneType type;
    
    public Phone(String number, PhoneType type) {
        this.number = number;
        this.type = type;
    }
    
    public String getNumber() { return number; }
    public PhoneType getType() { return type; }
}

class Client {
    private String id;
    private String name;
    private int age;
    private List<Phone> phones;
    
    public Client(String id, String name, int age, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phones = phones;
    }
    
    // Геттеры
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public List<Phone> getPhones() { return phones; }
}

public class ClientAnalysis {
    public static void main(String[] args) {
        // Создаем тестовые данные
        List<Client> clients = Arrays.asList(
            new Client("1", "Иван", 25, Arrays.asList(
                new Phone("+79111111111", PhoneType.MOBILE),
                new Phone("+74951111111", PhoneType.LANDLINE)
            )),
            new Client("2", "Мария", 22, Arrays.asList(
                new Phone("+74952222222", PhoneType.LANDLINE)
            )),
            new Client("3", "Петр", 19, Arrays.asList(
                new Phone("+79133333333", PhoneType.MOBILE)
            )),
            new Client("4", "Анна", 30, Arrays.asList(
                new Phone("+79144444444", PhoneType.MOBILE),
                new Phone("+79145555555", PhoneType.MOBILE)
            ))
        );
        
        // Находим самого молодого клиента с мобильным телефоном
        Optional<Client> youngestClientWithMobile = clients.stream()
                .filter(client -> client.getPhones().stream()
                        .anyMatch(phone -> phone.getType() == PhoneType.MOBILE))
                .min(Comparator.comparingInt(Client::getAge));
        
        if (youngestClientWithMobile.isPresent()) {
            Client client = youngestClientWithMobile.get();
            System.out.println("Самый молодой клиент с мобильным телефоном:");
            System.out.println("Имя: " + client.getName() + ", Возраст: " + client.getAge());
        } else {
            System.out.println("Клиенты с мобильными телефонами не найдены");
        }
    }
}