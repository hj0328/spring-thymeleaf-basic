package hello.itemservice.domain.item;

public class ItemTypeSingleton {
    private ItemTypeSingleton() {};

    private static ItemType[] itemTypeSingleton = null;

    public static ItemType[] getInstance() {
        if(itemTypeSingleton == null) {
            itemTypeSingleton = ItemType.values();
        }
        return itemTypeSingleton;
    }
}
