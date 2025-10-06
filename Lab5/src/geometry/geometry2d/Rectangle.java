package geometry.geometry2d;
import geometry.Exceptions.NegativeValueException;

public class Rectangle implements Figure {
    private double a;
    private double b;
    
    public Rectangle(double a, double b) throws NegativeValueException {
        setDimensions(a, b);
    }
    
    private void setDimensions(double a, double b) throws NegativeValueException {
        if (a <= 0) {
            throw new NegativeValueException("width", a);
        }
        if (b <= 0) {
            throw new NegativeValueException("height", b);
        }
        this.a = a;
        this.b = b;
    }
    
    public double Area() {
        return a * b;
    }
    
    public void Show() {
        System.out.println("Rectangle: a = " + a + ", b = " + b + ", area = " + Area());
    }
    
    public double GetA() {
        return a;
    }
    
    public double GetB() {
        return b;
    }
}