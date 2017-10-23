package pwcdma.asystentgierplanszowych.model;

public class StartViewPagerItem {
    private int image;
    private String text;

    public StartViewPagerItem(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public void setText(String text) {
        this.text = text;
    }
}
