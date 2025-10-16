package geometry.geometry2d;

import geometry.Exceptions.NegativeValueException;
import java.util.logging.*;
import java.io.IOException;

public class Rectangle implements Figure {
    private double a;
    private double b;
    private static final Logger logger = Logger.getLogger(Rectangle.class.getName());
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("figures.log", true);
            fileHandler.setFormatter(new XMLFormatter());
            fileHandler.setLevel(Level.INFO);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Ошибка настройки логгера: " + e.getMessage());
        }
    }
    
    public Rectangle(double a, double b) throws NegativeValueException {
        if (a <= 0) {
            logger.info("Попытка создания Rectangle с отрицательной стороной a: " + a);
            throw new NegativeValueException("a", a);
        }
        if (b <= 0) {
            logger.info("Попытка создания Rectangle с отрицательной стороной b: " + b);
            throw new NegativeValueException("b", b);
        }
        this.a = a;
        this.b = b;
        logger.info("Создан Rectangle: a = " + a + ", b = " + b);
    }
    
    @Override
    public double Area() {
        double area = a * b;
        logger.info("Вычислена площадь Rectangle: " + area);
        return area;
    }
    
    @Override
    public void Show() {
        logger.info("Вывод информации о Rectangle");
        System.out.println("Rectangle: a = " + a + ", b = " + b + 
                          ", area = " + Area());
    }
    
    public double getA() {
        return a;
    }
    
    public double getB() {
        return b;
    }
}