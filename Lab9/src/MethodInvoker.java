import java.lang.reflect.*;

public class MethodInvoker {
    public void invokeAnnotatedMethods(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(CallCount.class)) {
                CallCount annotation = method.getAnnotation(CallCount.class);
                int count = annotation.value();
                
                method.setAccessible(true);
                
                // Вызываем метод указанное количество раз
                for (int i = 0; i < count; i++) {
                    // Получаем параметры метода
                    Parameter[] params = method.getParameters();
                    Object[] arguments = new Object[params.length];
                    
                    // Заполняем аргументы значениями по умолчанию
                    for (int j = 0; j < params.length; j++) {
                        Class<?> type = params[j].getType();
                        if (type == int.class || type == Integer.class) {
                            arguments[j] = 10 + i; // Произвольные значения
                        } else if (type == String.class) {
                            arguments[j] = "call_" + (i + 1);
                        } else {
                            arguments[j] = null;
                        }
                    }
                    
                    method.invoke(obj, arguments);
                }
            }
        }
    }
}
