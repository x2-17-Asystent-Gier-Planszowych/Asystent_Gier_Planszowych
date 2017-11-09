package pwcdma.asystentgierplanszowych.model;


import java.util.Random;

public class Dice {
    private final Random random;
    private String name;
    private int numberOfSides;
    private boolean isPicked;

    public Dice(String name, int numberOfSides, boolean isPicked) {
        this.random = new Random();
        this.name = name;
        this.numberOfSides = numberOfSides;
        this.isPicked = isPicked;
    }

    public int roll(){
        return 1 + random.nextInt(numberOfSides);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
