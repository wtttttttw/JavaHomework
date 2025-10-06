package geometry.geometry2d;
import geometry.Exceptions.NegativeValueException;

public class Circle implements Figure{
    private double radius;

    public Circle(double radius) throws NegativeValueException {
        setRadius(radius);
    }
    
    private void setRadius(double radius) throws NegativeValueException {
        if (radius <= 0) {
            throw new NegativeValueException("radius", radius);
        }
        this.radius = radius;
    }
    
    public double Area(){
        return Math.PI * radius * radius;
    }

    public void Show(){
        System.out.println("Circle: radius = " + radius + ", area = " +  Area());
    };

    public double GetRadius(){
        return radius;
    }
}