import java.util.function.Predicate;

public class StringCheckExample {
    public static void main(String[] args) {
        // a. Проверка что строка не null
        Predicate<String> isNotNull = str -> str != null;
        
        // b. Проверка что строка не пуста
        Predicate<String> isNotEmpty = str -> !str.isEmpty();
        
        // c. Комбинированная проверка с методом and()
        Predicate<String> isNotNullAndNotEmpty = isNotNull.and(isNotEmpty);
        
        // Тестирование
        String test1 = null;
        String test2 = "";
        String test3 = "Hello";
        
        System.out.println("test1 (null): " + isNotNullAndNotEmpty.test(test1));
        System.out.println("test2 (пустая): " + isNotNullAndNotEmpty.test(test2));
        System.out.println("test3 (Hello): " + isNotNullAndNotEmpty.test(test3));
    }
}