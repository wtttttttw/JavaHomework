import java.util.function.Supplier;
import java.util.Random;

public class RandomNumberGenerator {
    public static void main(String[] args) {
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(11); // 0-10
        
        // Генерируем 5 случайных чисел
        for (int i = 0; i < 5; i++) {
            System.out.println("Случайное число: " + randomSupplier.get());
        }
    }
}