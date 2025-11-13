public class Main {
    public static void main(String[] args) throws Exception {
        AnnoTest obj = new AnnoTest();
        MethodInvoker caller = new MethodInvoker();

        // Вызываем аннотированные protected и private методы
        caller.invokeAnnotatedMethods(obj);

    }
}
