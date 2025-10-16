package geometry.test;
import geometry.geometry2d.Circle;
import geometry.geometry2d.Rectangle;
import geometry.geometry3d.Cylinder;
import geometry.Exceptions.InvalidFigureParameterException;
import geometry.Exceptions.NegativeValueException;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.FINE);
        
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);
    }
    public static void main(String[] args) throws Exception {
        logger.fine("=== Начало тестирования программы ===");
        testValidFigures();
        testExceptionScenarios();
        testValidCylinders();
        testCylinderExceptions();
        logger.fine("=== Завершение тестирования программы ===");
    }


private static void testValidFigures() {
        logger.fine("--- Начало тестирования валидных фигур ---");
        try {
            Circle circle = new Circle(5.0);
            circle.Show();
            
            Rectangle rectangle = new Rectangle(4.0, 6.0);
            rectangle.Show();
            
            logger.fine("Тестирование валидных фигур завершено успешно");
        } catch (InvalidFigureParameterException e) {
            logger.fine("Ошибка при тестировании валидных фигур: " + e.getMessage());
        }
    }
    
    private static void testExceptionScenarios() {
        logger.fine("--- Начало тестирования исключительных ситуаций ---");
        
        try {
            Circle invalidCircle = new Circle(-2.0);
            invalidCircle.Show();
        } catch (NegativeValueException e) {
            logger.fine("Поймано NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            logger.fine("Поймано InvalidFigureParameterException: " + e.getMessage());
        }
        
        try {
            Rectangle invalidRectangle2 = new Rectangle(3.0, -5.0);
            invalidRectangle2.Show();
        } catch (NegativeValueException e) {
            logger.fine("Поймано NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            logger.fine("Поймано InvalidFigureParameterException: " + e.getMessage());
        }
        
        logger.fine("Тестирование исключительных ситуаций завершено");
    }
    
    private static void testValidCylinders() {
        logger.fine("--- Начало тестирования валидных цилиндров ---");
        try {
            Circle circle = new Circle(3.0);
            Rectangle rectangle = new Rectangle(2.0, 4.0);
            
            Cylinder cylinder1 = new Cylinder(circle, 7.0);
            cylinder1.Show();
            
            Cylinder cylinder2 = new Cylinder(rectangle, 5.0);
            cylinder2.Show();
            
            logger.fine("Тестирование валидных цилиндров завершено успешно");
        } catch (InvalidFigureParameterException e) {
            logger.fine("Ошибка при тестировании цилиндров: " + e.getMessage());
        }
    }
    
    private static void testCylinderExceptions() {
        logger.fine("--- Начало тестирования исключений цилиндров ---");
        
        try {
            Circle circle = new Circle(3.0);
            Cylinder invalidCylinder = new Cylinder(circle, -2.0);
            invalidCylinder.Show();
        } catch (NegativeValueException e) {
            logger.fine("Поймано NegativeValueException для цилиндра: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            logger.fine("Поймано InvalidFigureParameterException для цилиндра: " + e.getMessage());
        }
        
        logger.fine("Тестирование исключений цилиндров завершено");
    }
}
