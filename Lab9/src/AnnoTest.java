import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CallCount{
    int value();
}

public class AnnoTest {

    public void PubOut1(int n){
        System.out.println("It's first public method. Your value is " + n);
    }

    @CallCount(3)
    public void PubOut2(String msg, int n){
        System.out.println("It's second public method. Your values is " + msg + " and " + n);
    }

    @CallCount(2)
    protected void ProtOut1(int n){
        System.out.println("It's first protected method. Your value is " + n);
    }

    protected void ProtOut2(int n, String msg){
        System.out.println("It's second protected method. Your values is " + n + " and " + msg);
    }

    @CallCount(4)
    private void PrivOut1(int n){
        System.out.println("It's first private method. Your value is " + n);
    }

    @CallCount(1)
    private void PrivOut2(String msg1, String msg2){
        System.out.println("It's second private method. Your values is " + msg1 + " and " + msg2);
    }


}

 