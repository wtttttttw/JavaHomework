import java.util.*;

class PrimesGenerator implements Iterator<Integer> {
    private int count;
    private int current = 2;
    private int generated = 0;
    
    public PrimesGenerator(int count) {
        this.count = count;
    }
    
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    @Override
    public boolean hasNext() {
        return generated < count;
    }
    
    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        while (!isPrime(current)) {
            current++;
        }
        int prime = current;
        current++;
        generated++;
        return prime;
    }
}

class PrimesGeneratorTest {
    public static void main(String[] args) {
        int n = 10;
        PrimesGenerator generator = new PrimesGenerator(n);
        
        // Прямой порядок
        List<Integer> primes = new ArrayList<>();
        while (generator.hasNext()) {
            primes.add(generator.next());
        }
        System.out.println("Простые числа в прямом порядке: " + primes);
        
        // Обратный порядок
        List<Integer> reversedPrimes = new ArrayList<>(primes);
        Collections.reverse(reversedPrimes);
        System.out.println("Простые числа в обратном порядке: " + reversedPrimes);
    }
}