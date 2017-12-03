package pwcdma.asystentgierplanszowych.content;

import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pwcdma.asystentgierplanszowych.model.Game;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Content {
    public static int sizeOfList = 0;
    /**
     * An array of sample (dummy) items.
     */
    public static  List<Item> ITEMS = new ArrayList<Item>();
    public static  List<Item> GAMES= new ArrayList<Item>();
    public static  List<Item> GROUPS= new ArrayList<Item>();
    public static  List<Item> TAGS= new ArrayList<Item>();


    public static  List<Item> USER= new ArrayList<Item>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static  Map<String, Item> ITEM_MAP = new HashMap<String, Item>();
    public static  Map<String, Item> GAME_MAP = new HashMap<String, Item>();
    public static  Map<String, Item> GROUP_MAP = new HashMap<String, Item>();

    private static  int COUNT = 25;

    static {
        // Add some sample items.
       /* for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }*/
    }

    public static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static void addGame(Item item) {
        GAMES.add(item);
        GAME_MAP.put("1", item);
    }
    public static void addGroup(Item item) {
        GROUPS.add(item);
        GROUP_MAP.put("1", item);
    }

    public static void addUser(Item item) {
        USER.add(item);
    }
    public static void addTags(Item item){
        TAGS.add(item);
    }

    /*private static Item createDummyItem(int position) {
        return new Item(String.valueOf(position), "Item " + position, makeDetails(position),new Button());
    }*/

    public static void clearList(List<Item> listIn,Map<String, Item> mapIn ){
        listIn.clear();
        mapIn.clear();
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Item {
        public final String id;
        public final String content;
        public final String details;
        public final Button button;
        public String getContent(){return content;}

        public Item(String id, String content, String details,Button button) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.button=button;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
