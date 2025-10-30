import java.util.*;
import java.util.stream.Collectors;

class Student {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int birthYear;
    private String address;
    private String phone;
    private String faculty;
    private int course;
    private String group;
    
    // Конструкторы
    public Student(int id, String lastName, String firstName, String middleName, 
                  int birthYear, String faculty, int course, String group) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthYear = birthYear;
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }
    
    public Student(int id, String lastName, String firstName, String middleName, 
                  int birthYear, String address, String phone, 
                  String faculty, int course, String group) {
        this(id, lastName, firstName, middleName, birthYear, faculty, course, group);
        this.address = address;
        this.phone = phone;
    }
    
    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    
    public int getBirthYear() { return birthYear; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    
    public int getCourse() { return course; }
    public void setCourse(int course) { this.course = course; }
    
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
    
    @Override
    public String toString() {
        return String.format("Студент: %s %s %s, Факультет: %s, Курс: %d, Группа: %s, Год рождения: %d",
                lastName, firstName, middleName, faculty, course, group, birthYear);
    }
}

public class StudentManagement {
    private List<Student> students;
    
    public StudentManagement() {
        students = new ArrayList<>();
        initializeStudents();
    }
    
    private void initializeStudents() {
        students.add(new Student(1, "Иванов", "Иван", "Иванович", 2000, 
                "ул. Ленина 1", "+79111111111", "Информатика", 3, "ИС-31"));
        students.add(new Student(2, "Петров", "Петр", "Петрович", 2001,
                "ул. Мира 5", "+79122222222", "Математика", 2, "ММ-21"));
        students.add(new Student(3, "Сидорова", "Мария", "Сергеевна", 1999,
                "ул. Пушкина 10", "+79133333333", "Информатика", 4, "ИС-41"));
        students.add(new Student(4, "Кузнецов", "Алексей", "Владимирович", 2002,
                "ул. Гагарина 15", "+79144444444", "Физика", 1, "ФЗ-11"));
        students.add(new Student(5, "Смирнова", "Ольга", "Дмитриевна", 2000,
                "ул. Чехова 8", "+79155555555", "Математика", 3, "ММ-31"));
    }
    
    // a. Список студентов заданного факультета
    
    // Способ 1: Циклы и операторы условия
    public List<Student> getStudentsByFacultyLoop(String faculty) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getFaculty().equals(faculty)) {
                result.add(student);
            }
        }
        return result;
    }
    
    // Способ 2: Методы коллекций
    public List<Student> getStudentsByFacultyCollection(String faculty) {
        List<Student> result = new ArrayList<>(students);
        result.removeIf(student -> !student.getFaculty().equals(faculty));
        return result;
    }
    
    // Способ 3: Stream API
    public List<Student> getStudentsByFacultyStream(String faculty) {
        return students.stream()
                .filter(student -> student.getFaculty().equals(faculty))
                .collect(Collectors.toList());
    }
    
    // b. Списки студентов для каждого факультета и курса
    
    // Способ 1: Циклы
    public Map<String, Map<Integer, List<Student>>> getStudentsByFacultyAndCourseLoop() {
        Map<String, Map<Integer, List<Student>>> result = new HashMap<>();
        
        for (Student student : students) {
            String faculty = student.getFaculty();
            int course = student.getCourse();
            
            if (!result.containsKey(faculty)) {
                result.put(faculty, new HashMap<>());
            }
            
            Map<Integer, List<Student>> facultyMap = result.get(faculty);
            if (!facultyMap.containsKey(course)) {
                facultyMap.put(course, new ArrayList<>());
            }
            
            facultyMap.get(course).add(student);
        }
        
        return result;
    }
    
    // Способ 3: Stream API (более эффективный)
    public Map<String, Map<Integer, List<Student>>> getStudentsByFacultyAndCourseStream() {
        return students.stream()
                .collect(Collectors.groupingBy(
                    Student::getFaculty,
                    Collectors.groupingBy(Student::getCourse)
                ));
    }
    
    // c. Список студентов, родившихся после заданного года
    
    // Способ 1: Циклы
    public List<Student> getStudentsBornAfterYearLoop(int year) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getBirthYear() > year) {
                result.add(student);
            }
        }
        return result;
    }
    
    // Способ 2: Методы коллекций
    public List<Student> getStudentsBornAfterYearCollection(int year) {
        List<Student> result = new ArrayList<>(students);
        result.removeIf(student -> student.getBirthYear() <= year);
        return result;
    }
    
    // Способ 3: Stream API
    public List<Student> getStudentsBornAfterYearStream(int year) {
        return students.stream()
                .filter(student -> student.getBirthYear() > year)
                .collect(Collectors.toList());
    }
    
    // Вывод всех студентов
    public void printAllStudents() {
        System.out.println("=== ВСЕ СТУДЕНТЫ ===");
        students.forEach(System.out::println);
        System.out.println();
    }
    
    public static void main(String[] args) {
        StudentManagement management = new StudentManagement();
        
        // Вывод всех студентов
        management.printAllStudents();
        
        // a. Список студентов заданного факультета
        String faculty = "Информатика";
        System.out.println("=== СТУДЕНТЫ ФАКУЛЬТЕТА " + faculty + " ===");
        System.out.println("Stream API:");
        management.getStudentsByFacultyStream(faculty).forEach(System.out::println);
        System.out.println();
        
        // b. Списки студентов по факультетам и курсам
        System.out.println("=== СТУДЕНТЫ ПО ФАКУЛЬТЕТАМ И КУРСАМ ===");
        Map<String, Map<Integer, List<Student>>> byFacultyAndCourse = 
            management.getStudentsByFacultyAndCourseStream();
        
        byFacultyAndCourse.forEach((fac, courses) -> {
            System.out.println("Факультет: " + fac);
            courses.forEach((course, studentList) -> {
                System.out.println("  Курс " + course + ":");
                studentList.forEach(student -> 
                    System.out.println("    - " + student.getLastName() + " " + student.getFirstName()));
            });
        });
        System.out.println();
        
        // c. Студенты, родившиеся после 2000 года
        int year = 2000;
        System.out.println("=== СТУДЕНТЫ, РОДИВШИЕСЯ ПОСЛЕ " + year + " ГОДА ===");
        management.getStudentsBornAfterYearStream(year).forEach(System.out::println);
    }
}