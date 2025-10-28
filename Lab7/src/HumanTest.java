import java.util.*;

class Human implements Comparable<Human> {
    private String fName;
    private String lName;
    private int age;
    
    public Human(String fName, String lName, int age) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }
    
    public String getfName() { return fName; }
    public String getlName() { return lName; }
    public int getAge() { return age; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Human human = (Human) obj;
        return age == human.age && 
               Objects.equals(fName, human.fName) && 
               Objects.equals(lName, human.lName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(fName, lName, age);
    }
    
    @Override
    public int compareTo(Human o) {
        int rez;
        if (this.equals(o))
            return 0;
        rez = fName.compareToIgnoreCase(o.getfName());
        if (rez != 0)
            return rez;
        rez = lName.compareToIgnoreCase(o.getlName());
        if (rez != 0)
            return rez;
        else
            return age - o.getAge();
    }
    
    @Override
    public String toString() {
        return fName + " " + lName + " (" + age + ")";
    }
}

class HumanComparatorByLName implements Comparator<Human> {
    @Override
    public int compare(Human h1, Human h2) {
        return h1.getlName().compareToIgnoreCase(h2.getlName());
    }
}

class HumanTest {
    public static void main(String[] args) {
        // a) Коллекция на основе HashSet
        Set<Human> s = new HashSet<>();
        s.add(new Human("Иван", "Петров", 25));
        s.add(new Human("Мария", "Иванова", 30));
        s.add(new Human("Алексей", "Сидоров", 22));
        s.add(new Human("Ольга", "Петрова", 25));
        s.add(new Human("Иван", "Иванов", 28));
        
        System.out.println("a) HashSet коллекция:");
        for (Human human : s) {
            System.out.println(human);
        }
        
        // b) Коллекция LinkedHashSet на основе существующей коллекции s
        Set<Human> linkedHashSet = new LinkedHashSet<>(s);
        System.out.println("\nb) LinkedHashSet коллекция:");
        for (Human human : linkedHashSet) {
            System.out.println(human);
        }
        
        // c) Коллекция TreeSet на основе существующей коллекции s
        Set<Human> treeSet = new TreeSet<>(s);
        System.out.println("\nc) TreeSet коллекция (сортировка по Comparable):");
        for (Human human : treeSet) {
            System.out.println(human);
        }
        
        // d) TreeSet с компаратором HumanComparatorByLName
        Set<Human> treeSetByLName = new TreeSet<>(new HumanComparatorByLName());
        treeSetByLName.addAll(s);
        System.out.println("\nd) TreeSet с компаратором по фамилии:");
        for (Human human : treeSetByLName) {
            System.out.println(human);
        }
        
        // e) TreeSet с анонимным компаратором по возрасту
        Set<Human> treeSetByAge = new TreeSet<>(new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return Integer.compare(h1.getAge(), h2.getAge());
            }
        });
        treeSetByAge.addAll(s);
        System.out.println("\ne) TreeSet с анонимным компаратором по возрасту:");
        for (Human human : treeSetByAge) {
            System.out.println(human);
        }
    }
}