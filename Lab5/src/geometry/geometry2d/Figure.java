package geometry.geometry2d;

public interface Figure {
    double Area() throws geometry.Exceptions.InvalidFigureParameterException;
    void Show() throws geometry.Exceptions.InvalidFigureParameterException;
}