package geometry.test;
import geometry.geometry2d.Circle;
import geometry.geometry2d.Rectangle;
import geometry.geometry3d.Cylinder;

public class Main {
    public static void main(String[] args) throws Exception {
        Circle Circ = new Circle(4);
        Circ.Show();
        
        Rectangle Rect = new Rectangle(5, 7);
        Rect.Show();

        Cylinder CylRect = new Cylinder(Rect, 8);
        CylRect.Show();

        Cylinder CylCirc = new Cylinder(Circ, 13);
        CylCirc.Show();
    }
}
