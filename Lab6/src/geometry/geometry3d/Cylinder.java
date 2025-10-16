package geometry.geometry3d;

import geometry.geometry2d.Figure;
import geometry.Exceptions.NegativeValueException;
import java.util.logging.*;
import java.io.IOException;

public class Cylinder {
    private Figure base;
    private double h;
    private static final Logger logger = Logger.getLogger(Cylinder.class.getName());

     static {
        try {
            FileHandler fileHandler = new FileHandler("cylinder.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.FINEST);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.FINEST);
        } catch (IOException e) {
            System.err.println("Ошибка настройки логгера: " + e.getMessage());
        }
    }


    public Cylinder(Figure base, double h) throws NegativeValueException {
        if (base == null) {
            logger.finest("Попытка создания Cylinder с null основанием");
            throw new IllegalArgumentException("Base figure cannot be null");
        }
        if (h <= 0) {
            logger.finest("Попытка создания Cylinder с отрицательной высотой h: " + h);
            throw new NegativeValueException("h", h);
        }
        this.base = base;
        this.h = h;
        logger.finest("Создан Cylinder с высотой h: " + h);
    }
    
    public double Volume() throws geometry.Exceptions.InvalidFigureParameterException {
        double volume = base.Area() * h;
        logger.finest("Вычислен объем Cylinder: " + String.format("%.2f", volume));
        return volume;
    }
    
    public void Show() throws geometry.Exceptions.InvalidFigureParameterException {
        logger.finest("Вывод информации о Cylinder");
        System.out.print("Cylinder with base: ");
        base.Show();
        double volume = Volume();
        logger.finest("Объем цилиндра: " + String.format("%.2f", volume));
        System.out.println("h = " + h + ", volume = " + String.format("%.2f", volume));
    }
    
    public Figure getBase() {
        logger.finest("Получение основания цилиндра");
        return base;
    }
    
    public double getH() {
        logger.finest("Получение высоты цилиндра h: " + h);
        return h;
    }
}
