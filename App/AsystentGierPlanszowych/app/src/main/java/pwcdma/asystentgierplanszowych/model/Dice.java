package pwcdma.asystentgierplanszowych.model;


public class Dice {
    private String name;
    private int numberOfSides;
    private boolean isPicked;

    public Dice(String name, int numberOfSides, boolean isPicked) {
        this.name = name;
        this.numberOfSides = numberOfSides;
        this.isPicked = isPicked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }
}
