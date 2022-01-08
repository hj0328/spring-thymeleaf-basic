package hello.itemservice.domain.item;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemRegionsSingleton {
    private ItemRegionsSingleton() {}

    private static Map<String, String> itemRegionsSingleton = null;

    public static Map<String, String> getInstance() {
        if(itemRegionsSingleton == null){
            itemRegionsSingleton = new LinkedHashMap<>();
            itemRegionsSingleton.put("SEOUL", "서울");
            itemRegionsSingleton.put("BUSAN", "부산");
            itemRegionsSingleton.put("JEJU", "제주");
        }
        return itemRegionsSingleton;
    }
}
