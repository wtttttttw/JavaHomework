import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class StreamAPIExamples {
    
    // a. Среднее значение списка целых чисел
    public static OptionalDouble average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }
    
    // b. Строки в верхний регистр с префиксом
    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(str -> "new" + str.toUpperCase())
                .collect(Collectors.toList());
    }
    
    // c. Квадраты уникальных элементов
    public static List<Integer> squaresOfUnique(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey() * entry.getKey())
                .collect(Collectors.toList());
    }
    
    // d. Строки начинающиеся с заданной буквы, отсортированные
    public static List<String> filterAndSort(Collection<String> strings, char letter) {
        return strings.stream()
                .filter(str -> str != null && !str.isEmpty() && str.charAt(0) == letter)
                .sorted()
                .collect(Collectors.toList());
    }
    
    // e. Последний элемент коллекции
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }
    
    // f. Сумма чётных чисел массива
    public static int sumEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }
    
    // g. Преобразование строк в Map
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .filter(str -> str != null && !str.isEmpty())
                .collect(Collectors.toMap(
                    str -> str.charAt(0), // ключ - первый символ
                    str -> str.length() > 1 ? str.substring(1) : "", // значение - остальные символы
                    (existing, replacement) -> existing // обработка дубликатов
                ));
    }
    
    public static void main(String[] args) {
        // Тестирование методов
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3);
        List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");
        
        System.out.println("Среднее: " + average(numbers));
        System.out.println("С префиксом: " + toUpperCaseWithPrefix(strings));
        System.out.println("Квадраты уникальных: " + squaresOfUnique(numbers));
        System.out.println("Фильтр по 'a': " + filterAndSort(strings, 'a'));
        System.out.println("Последний элемент: " + getLastElement(strings));
        
        int[] arr = {1, 2, 3, 4, 5, 6};
        System.out.println("Сумма чётных: " + sumEvenNumbers(arr));
        System.out.println("Map из строк: " + stringsToMap(strings));
    }
}