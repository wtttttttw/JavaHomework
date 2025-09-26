public class App {
    public static void main(String[] args) throws Exception {
        /* 
        Button B = new Button();
        B.click();
        B.click();
        B.click();

        Balance Ba = new Balance();
        Ba.addLeft(6);
        Ba.addRight(6);
        Ba.result();
        Ba.addRight(6);
        Ba.result();
        Ba.addLeft(8);
        Ba.result();
        
        Bell Be = new Bell();
        Be.sound();
        Be.sound();
        Be.sound();
        Be.sound();
        Be.sound();
                        

        OddEvenSeparator separator = new OddEvenSeparator();
        separator.addNumber(11);
        separator.addNumber(24);
        separator.addNumber(33);
        separator.addNumber(484);
        separator.addNumber(5);

        System.out.println("Even numbers:");
        separator.even();

        System.out.println("Odd numbers:");
        separator.odd();
                                    */

        Table table = new Table(3, 4);

        table.setValue(0, 0, 1);
        table.setValue(0, 1, 2);
        table.setValue(1, 0, 3);
        table.setValue(1, 1, 4);
        table.setValue(2, 2, 5);

        System.out.println("Your Table: ");
        System.out.println(table.toString());

        System.out.println("Amount of Rows: " + table.rows());
        System.out.println("Amount of Cols: " + table.cols());
        System.out.println("The [1,1] value: " + table.getValue(1, 1));
        System.out.println("The average value is: " + table.average());

    }
}
