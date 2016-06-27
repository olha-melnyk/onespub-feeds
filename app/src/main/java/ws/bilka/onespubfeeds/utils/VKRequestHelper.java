package ws.bilka.onespubfeeds.utils;


import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class VKRequestHelper {

    public static void addLike(String type, int ownerId, int itemId) {
        VKRequest request = new VKRequest("likes.add", VKParameters.from("type", type, "owner_id", ownerId, "item_id", itemId));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }
        });
    }

    public static void deleteLike(String type, int ownerId, int itemId) {
        VKRequest request = new VKRequest("likes.delete", VKParameters.from("type", type, "owner_id", ownerId, "item_id", itemId));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }
        });
    }
}
