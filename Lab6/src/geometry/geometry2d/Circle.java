package geometry.geometry2d;

import geometry.Exceptions.NegativeValueException;
import java.util.logging.*;
import java.io.IOException;

public class Circle implements Figure{
    private double radius;
    private static final Logger logger = Logger.getLogger(Circle.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("figures.log", true);
            fileHandler.setFormatter(new XMLFormatter());
            fileHandler.setLevel(Level.SEVERE);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Ошибка настройки логгера: " + e.getMessage());
        }
    }

    public Circle(double radius) throws NegativeValueException {
        if (radius <= 0) {
            logger.severe("Попытка создания Circle с отрицательным радиусом: " + radius);
            throw new NegativeValueException("radius", radius);
        }
        this.radius = radius;
        logger.severe("Создан Circle с радиусом: " + radius);
    }
    
    @Override
    public double Area() {
        double area = Math.PI * radius * radius;
        logger.severe("Вычислена площадь Circle: " + area);
        return area;
    }
    
    @Override
    public void Show() {
        logger.severe("Вывод информации о Circle: radius = " + radius);
        System.out.println("Circle: radius = " + radius + ", area = " + Area());
    }
    
    public double getRadius() {
        return radius;
    }
}