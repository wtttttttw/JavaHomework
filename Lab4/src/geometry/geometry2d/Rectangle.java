package geometry.geometry2d;

public class Rectangle implements Figure {
    private double a;
    private double b;
    
    public Rectangle(double a, double b) {
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