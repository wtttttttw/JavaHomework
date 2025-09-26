import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
        //Task 1
        for(int i=1;i<=500;i++){
            if(i%5==0 && i%7==0) {System.out.println("fizzbuzz");}
            else if(i%5==0) {System.out.println("fizz");}
            else if(i%7==0) {System.out.println("buzz");}
            else {System.out.println(i);}
            
            
        }
        
        //Task 2
        String str="make install";
        String strrev = "";
        for (int i=str.length() - 1;i >= 0;i--){
            strrev += str.charAt(i);
        }
        System.out.println();
        System.out.println(strrev);
        System.out.println();
        
        //Task 3
        System.out.println("Input a:");
        int a = (new Scanner(System.in).nextInt());
        System.out.println("Input b:");
        int b = (new Scanner(System.in).nextInt());
        System.out.println("Input c:");
        int c = (new Scanner(System.in).nextInt());
        double x1,x2;
        double D = ((b*b)-4*a*c);
        x1 = (-b + Math.sqrt(D)) / 2 * a;
        x2 = (-b - Math.sqrt(D)) / 2 * a;
        if (D < 0) {System.out.println("No roots");}
        else if (D == 0) {System.out.println(-b / 2 * a);}
        else if (D > 0) {System.out.printf("x1=%f  x2=%f", x1, x2);}
       
        //Task 4
        double SUM = 0.0;
        int n = 2;
        double MinMemb = 1e-6;

        while (true) {
            double temp = 1.0 / (n * n + n - 2);
            if (temp < MinMemb) {
                break;
            }
            SUM += temp;
            n++;
        }
        System.out.println();
        System.out.println("Your summ is: " + SUM);
        //Task 5
        System.out.println();
        System.out.println("Input your string: ");
        String Palindrom = (new Scanner(System.in)).nextLine();

        String PalindromReverse = "";
        for(int i = Palindrom.length() - 1; i >=0;i--){
            PalindromReverse += Palindrom.charAt(i);
        }
        if(Palindrom.equals(PalindromReverse)){
            System.out.printf("String %s is palindrom \n", PalindromReverse);
        } else {
            System.out.printf("String %s isn't palindrom \n", Palindrom);
        }
        System.out.println();

    }
}
