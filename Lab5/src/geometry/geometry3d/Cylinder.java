package geometry.geometry3d;
import geometry.geometry2d.Figure;
import geometry.Exceptions.NegativeValueException;

public class Cylinder {
    private Figure base;
    private double h;

    public Cylinder(Figure base, double h) throws NegativeValueException {
        setBaseAndHeight(base, h);
    }
    
    private void setBaseAndHeight(Figure base, double h) throws NegativeValueException {
        if (base == null) {
            throw new IllegalArgumentException("Base figure cannot be null");
        }
        if (h <= 0) {
            throw new NegativeValueException("height", h);
        }
        this.base = base;
        this.h = h;
    }

    public double Volume() throws geometry.Exceptions.InvalidFigureParameterException{
        return base.Area() * h;
    };

    public void Show() throws geometry.Exceptions.InvalidFigureParameterException {
        System.out.print("Cylinder with base: ");
        base.Show();
        System.out.println("Height = " + h + ", volume = " + Volume());
    }
    
    public Figure getBase() {
        return base;
    }
    
    public double getH() {
        return h;
    }
}
