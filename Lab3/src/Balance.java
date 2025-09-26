public class Balance{
    private int left = 0;
    private int right = 0;

    public void addRight(int kg){
        right+=kg;
    };

    public void addLeft(int kg){
        left+=kg;
    };

    public void result(){
        if(left==right){System.out.println("=");}
        else if(left>right){System.out.println("L");}
        else if(left<right){System.out.println("R");}
    }

};
