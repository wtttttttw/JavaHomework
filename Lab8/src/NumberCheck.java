import java.util.function.Function;

public class NumberCheck {
    public static void main(String[] args) {
        Function<Integer, String> numberClassifier = num -> {
            if (num > 0) {
                return "Положительное число";
            } else if (num < 0) {
                return "Отрицательное число";
            } else {
                return "Ноль";
            }
        };
        
        // Тестирование
        int[] numbers = {5, -3, 0, 10, -7};
        for (int num : numbers) {
            System.out.println(num + ": " + numberClassifier.apply(num));
        }
    }
}