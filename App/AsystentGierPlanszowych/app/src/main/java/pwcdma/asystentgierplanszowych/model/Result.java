package pwcdma.asystentgierplanszowych.model;


public class Result {
    String name;
    int result;

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