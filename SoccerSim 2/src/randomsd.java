import java.util.Random;

public class randomsd {
    public static void main(String[] args) {
        double mean = 3;
        double stdDev = 1.0;

        Random rand = new Random();


        int value = (int)(mean + stdDev * rand.nextGaussian());

        System.out.println("Random value: " + value);
        rand = new Random();
        value = (int)(mean + stdDev * rand.nextGaussian());
    }
}