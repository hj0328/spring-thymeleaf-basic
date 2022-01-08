package hello.itemservice.domain.item;

import java.util.ArrayList;

public class DeliveryCodeSingleton {
    private DeliveryCodeSingleton() {}

    private static ArrayList<DeliveryCode> deliveryCodeSingleton = null;

    public static ArrayList<DeliveryCode> getInstance() {
        if(deliveryCodeSingleton == null) {
            deliveryCodeSingleton = new ArrayList<>();
            deliveryCodeSingleton.add(new DeliveryCode("FAST", "빠른 배송"));
            deliveryCodeSingleton.add(new DeliveryCode("NORMAL", "일반 배송"));
            deliveryCodeSingleton.add(new DeliveryCode("SLOW", "느린 배송"));
        }
        return deliveryCodeSingleton;
    }
}
