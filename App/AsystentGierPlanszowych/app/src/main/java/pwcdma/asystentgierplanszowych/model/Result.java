package pwcdma.asystentgierplanszowych.model;


public class Result {
    private String name;
    private int result;

    public Result(String name, int result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public int getResult() {
        return result;
    }
}