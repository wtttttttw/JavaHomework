import java.util.*;

class WordFrequency {
    public static void main(String[] args) {
        String text = "The path to fox success. Dog, cool, quick. bandom success sentences lol. Dog Fox is quick and brown path.";
        
        // Разделяем строку на слова
        String[] words = text.split("[^a-zA-Z']+");//"найти последовательности из одного или более символов, которые НЕ являются английскими буквами или апострофами"
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        // Подсчитываем частоту каждого слова
        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }
        
        // Разделяем слова на повторяющиеся и неповторяющиеся
        List<String> uniqueWords = new ArrayList<>();    // частота = 1
        List<String> repeatedWords = new ArrayList<>();  // частота > 1
        
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 1) {
                uniqueWords.add(entry.getKey());
            } else {
                repeatedWords.add(entry.getKey() + " (" + entry.getValue() + " раза)");
            }
        }
        
        // Выводим результат
        System.out.println("Частота слов (учитывается регистр):");
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue() + " раз(а)");
        }
        
        System.out.println("\nНеповторяющиеся слова: " + uniqueWords);
    }
}