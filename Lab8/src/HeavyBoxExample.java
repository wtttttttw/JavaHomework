import java.util.function.Consumer;

class HeavyBox {
    private double weight;
    
    public HeavyBox(double weight) {
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
}

public class HeavyBoxExample {
    public static void main(String[] args) {
        Consumer<HeavyBox> shipBox = box -> 
            System.out.println("Отправляем ящик с весом " + box.getWeight());
        
        Consumer<HeavyBox> unloadBox = box -> 
            System.out.println("Отгрузили ящик с весом " + box.getWeight());
        
        // Комбинируем с помощью andThen
        Consumer<HeavyBox> processBox = shipBox.andThen(unloadBox);
        
        HeavyBox box = new HeavyBox(25.5);
        processBox.accept(box);
    }
}