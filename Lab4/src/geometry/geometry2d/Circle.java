package geometry.geometry2d;


public class Circle implements Figure{
    private double radius;

    public Circle(double radius){
        this.radius=radius;
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