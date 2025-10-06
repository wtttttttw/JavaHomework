package geometry.test;
import geometry.geometry2d.Circle;
import geometry.geometry2d.Rectangle;
import geometry.geometry3d.Cylinder;
import geometry.Exceptions.InvalidFigureParameterException;
import geometry.Exceptions.NegativeValueException;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Testing 2D Figures ===\n");
        testValidFigures();
        testExceptionScenarios();
        
        System.out.println("\n=== Testing 3D Cylinders ===\n");
        testValidCylinders();
        testCylinderExceptions();
    }


private static void testValidFigures() {
        System.out.println("--- Testing Valid Figures ---");
        try {
            Circle circle = new Circle(5.0);
            circle.Show();
            
            Rectangle rectangle = new Rectangle(4.0, 6.0);
            rectangle.Show();
            
        } catch (InvalidFigureParameterException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    private static void testExceptionScenarios() {
        System.out.println("\n--- Testing Exception Scenarios ---");
        
        // Круг
        try {
            Circle invalidCircle = new Circle(-2.0);
            invalidCircle.Show();
        } catch (NegativeValueException e) {
            System.out.println("Caught NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            System.out.println("Caught InvalidFigureParameterException: " + e.getMessage());
        }
        
        // Прямоугольник
        try {
            Rectangle invalidRectangle = new Rectangle(-3.0, 5.0);
            invalidRectangle.Show();
        } catch (NegativeValueException e) {
            System.out.println("Caught NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            System.out.println("Caught InvalidFigureParameterException: " + e.getMessage());
        }
        
    }
    
    private static void testValidCylinders() {
        System.out.println("--- Testing Valid Cylinders ---");
        try {
            Circle circle = new Circle(3.0);
            Rectangle rectangle = new Rectangle(2.0, 4.0);
            
            Cylinder cylinder1 = new Cylinder(circle, 7.0);
            cylinder1.Show();
            
            Cylinder cylinder2 = new Cylinder(rectangle, 5.0);
            cylinder2.Show();
            
        } catch (InvalidFigureParameterException e) {
            System.out.println("Error creating cylinder: " + e.getMessage());
        }
    }
    
    private static void testCylinderExceptions() {
        System.out.println("\n--- Testing Cylinder Exceptions ---");
        
        // Цилиндр с отрицательной высотой
        try {
            Circle circle = new Circle(3.0);
            Cylinder invalidCylinder = new Cylinder(circle, -2.0);
            invalidCylinder.Show();
        } catch (NegativeValueException e) {
            System.out.println("Caught NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            System.out.println("Caught InvalidFigureParameterException: " + e.getMessage());
        }
        
        // Цилиндр с нулевой высотой
        try {
            Rectangle rectangle = new Rectangle(2.0, 4.0);
            Cylinder zeroHeightCylinder = new Cylinder(rectangle, 0.0);
            zeroHeightCylinder.Show();
        } catch (NegativeValueException e) {
            System.out.println("Caught NegativeValueException: " + e.getMessage());
        } catch (InvalidFigureParameterException e) {
            System.out.println("Caught InvalidFigureParameterException: " + e.getMessage());
        }
        
        
        System.out.println("\n--- Recovery after Exception ---");
        try {
            // Сначала попытка создать неприемлемый цилиндр
            Circle circle = new Circle(2.0);
            Cylinder badCylinder = new Cylinder(circle, -5.0);
            badCylinder.Show();
        } catch (NegativeValueException e) {
            System.out.println("Recovering from exception: " + e.getMessage());
            
            // Восстановление - создаем нормальный цилиндр
            try {
                Circle circle = new Circle(2.0);
                Cylinder goodCylinder = new Cylinder(circle, 5.0);
                System.out.print("Successfully created after recovery: ");
                goodCylinder.Show();
            } catch (InvalidFigureParameterException ex) {
                System.out.println("Unexpected error during recovery: " + ex.getMessage());
            }
        } catch (InvalidFigureParameterException e) {
            System.out.println("Caught InvalidFigureParameterException: " + e.getMessage());
        }
    }
}