import java.util.*;

public class OddEvenSeparator {
    private List<Integer> numbers = new ArrayList<>();
    
    public void addNumber(int n) {
        numbers.add(n);
    }
    
    public void even() {
        for (int num : numbers) {
            if (num % 2 == 0) {
                System.out.println(num);
            }
        }
    }
    
    public void odd() {
        for (int num : numbers) {
            if (num % 2 != 0) {
                System.out.println(num);
            }
        }
    }
}