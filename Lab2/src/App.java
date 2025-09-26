public class App {
    public static void triangle(int a, int b, int c){
        if(a+b>c && a+c>b && c+b>a) {System.out.println("Это треугольник");}
        else {System.out.println("Это не треугольник");}
    }

    public static void distance(double x1, double y1, double x2, double y2){
        double d=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        System.out.println("Расстояние равно " + d);
    }

    public static void power(double a, int n){
        double b=a;
        for(int i=1;i<n;i++){
            b*=a;    
        }
        System.out.println("A в степени N = " + b);
    }

    public static double powerREC(double a, int n){
        if (n == 0){
            return 1;
        }
        return a * powerREC(a, n-1);
    }

    public static int tribonacci(int n){
        if (n == 0){
            return 0;
        }
        else if (n == 1){
            return 0;
        }
        else if (n == 2){
            return 1;
        }
        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }

    public static void main(String[] args) throws Exception {
        triangle(7,1,4);
        triangle(20,13,17);

        distance(5,7,4,1);
        distance(58,57,44,17);

        power(5,2);
        power(2,4);

        System.out.println("Рекурсия: " + powerREC(2, 4));

        System.out.println(tribonacci(3));
        System.out.println(tribonacci(4));
        System.out.println(tribonacci(5));
        System.out.println(tribonacci(7));
    }
}
