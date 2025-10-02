package geometry.geometry3d;
import geometry.geometry2d.Figure;

public class Cylinder {
    private Figure base;
    private double h;

    public Cylinder(Figure base, double h) {
        this.base = base;
        this.h = h;
    }

    public double Volume(){
        return base.Area() * h;
    };

    public void Show() {
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
