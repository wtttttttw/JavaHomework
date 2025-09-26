public class Bell {
    private int DD = 0;
    public void sound(){
        if (DD%2 == 0){
            System.out.println("ding");
        }
        else if (DD%2 != 0) {
            System.out.println("dong");
        }
    DD++;
    };
}
