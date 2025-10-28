import java.util.*;

class MapUtils {
    public static <K, V> Map<V, Collection<K>> reverseMap(Map<K, V> originalMap) {
        Map<V, Collection<K>> reversedMap = new HashMap<>();
        
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            
            // Если значения еще нет в новой карте, создаем коллекцию для него
            if (!reversedMap.containsKey(value)) {
                reversedMap.put(value, new ArrayList<>());
            }
            
            // Добавляем исходный ключ в коллекцию для этого значения
            reversedMap.get(value).add(key);
        }
        
        return reversedMap;
    }
}

class MapReverseTest {
    public static void main(String[] args) {
        // Создаем тестовую Map
        Map<String, Integer> originalMap = new HashMap<>();
        originalMap.put("apple", 1);
        originalMap.put("banana", 2);
        originalMap.put("cherry", 1);
        originalMap.put("date", 3);
        originalMap.put("elderberry", 2);
        
        System.out.println("Исходная Map:");
        for (Map.Entry<String, Integer> entry : originalMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        
        // Применяем метод reverseMap
        Map<Integer, Collection<String>> reversedMap = MapUtils.reverseMap(originalMap);
        
        System.out.println("\nОбращенная Map:");
        for (Map.Entry<Integer, Collection<String>> entry : reversedMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}