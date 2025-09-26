public class Button {
    private int clks = 0;
    public void click(){
        clks++;
        System.out.println("Your button was clicked " + clks + " times");
    }; 
}


