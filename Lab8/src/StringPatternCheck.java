import java.util.function.Predicate;

public class StringPatternCheck {
    public static void main(String[] args) {
        Predicate<String> startsWithJorN = str -> 
            str != null && (str.startsWith("J") || str.startsWith("N"));
        
        Predicate<String> endsWithA = str -> 
            str != null && str.endsWith("A");
        
        Predicate<String> combinedCheck = startsWithJorN.and(endsWithA);
        
        // Тестирование
        String[] testStrings = {"Java", "Nova", "JavaScript", "Python", "JavA", null};
        
        for (String str : testStrings) {
            System.out.println("'" + str + "' соответствует: " + combinedCheck.test(str));
        }
    }
}