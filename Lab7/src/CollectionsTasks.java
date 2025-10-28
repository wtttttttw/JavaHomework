import java.util.*;

public class CollectionsTasks {
    public static void main(String[] args) {
        // a) Создайте массив из N чисел
        int n = 10;
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = (i * 3 + 7) % 10; // для генерации условно произвольных чисел
        }
        System.out.println("a) Исходный массив: " + Arrays.toString(array));
        
        // b) На основе массива создайте список List
        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        System.out.println("b) Список из массива: " + list);
        
        // c) Отсортируйте список в натуральном порядке
        Collections.sort(list);
        System.out.println("c) Отсортированный в натуральном порядке: " + list);
        
        // d) Отсортируйте список в обратном порядке
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("d) Отсортированный в обратном порядке: " + list);
        
        // e) Перемешайте список
        Collections.shuffle(list);
        System.out.println("e) Перемешанный список: " + list);
        
        // f) Выполните циклический сдвиг на 1 элемент
        Collections.rotate(list, 1);
        System.out.println("f) Циклический сдвиг на 1: " + list);
        
        // g) Оставьте в списке только уникальные элементы
        Set<Integer> uniqueSet = new LinkedHashSet<>(list);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);
        System.out.println("g) Только уникальные элементы: " + uniqueList);
        
        // h) Оставьте в списке только дублирующиеся элементы
        List<Integer> listWithDuplicates = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 4, 4, 4, 5));
        Set<Integer> tempSet = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (Integer num : listWithDuplicates) {
            if (!tempSet.add(num)) {
                duplicates.add(num);
            }
        }
        List<Integer> duplicatesList = new ArrayList<>(duplicates);
        System.out.println("h) Только дублирующиеся элементы: " + duplicatesList);
        
        // i) Из списка получите массив
        Integer[] newArray = uniqueList.toArray(new Integer[0]);
        System.out.println("i) Массив из списка: " + Arrays.toString(newArray));
    }
}