package ws.bilka.onespubfeeds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ws.bilka.onespubfeeds.adapter.FeedListAdapter;
import ws.bilka.onespubfeeds.model.FeedItem;
import ws.bilka.onespubfeeds.model.UserItem;

public class FeedsActivity extends AppCompatActivity {

    private static final String TAG = FeedsActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;

    private boolean loadingInProgress = false;
    private static final int PAGE_SIZE = 10;
    private static final int FEED_OWNER_ID = -34613199;
    private int cursor = 0;

    private Map<Integer, UserItem> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (totalItemCount != 0 && !loadingInProgress) {
                        cursor += PAGE_SIZE;
                        loadFeeds(cursor);
                        loadingInProgress = true;
                    }
                }
            }
        });
        loadFeeds(cursor);
    }

    private void loadFeeds(int skip) {
        VKRequest request = VKApi
                .wall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, FEED_OWNER_ID,
                        VKApiConst.OFFSET, skip,
                        VKApiConst.COUNT, PAGE_SIZE,
                        VKApiConst.EXTENDED, 1));
        request.secure = false;
        request.useSystemLanguage = false;

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                parseVkResponse(response.json);
                loadingInProgress = false;
            }
        });
    }

    private void parseVkResponse(JSONObject response) {
        try {
            JSONObject r = response.getJSONObject("response");

            parseProfiles(r.optJSONArray("profiles"));
            parseGroups(r.optJSONArray("groups"));

            JSONArray items = r.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = (JSONObject) items.get(i);

                VKApiPost vkApiPost = new VKApiPost().parse(item);
                FeedItem feedItem = new FeedItem();
                feedItem.setId(vkApiPost.id);
                feedItem.setText(vkApiPost.text);
                feedItem.setTimeStamp(secondsToMilliseconds(vkApiPost.date));
                int ownerId = vkApiPost.from_id;
                if(ownerId != 0) {
                    UserItem user = users.get(Math.abs(ownerId));
                    if(user != null) {
                        feedItem.setAvatarImage(user.getAvatar());
                        feedItem.setTitle(user.getName());
                    }
                }

                feedItems.add(feedItem);
            }

            listAdapter.notifyDataSetChanged();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void parseProfiles(JSONArray profiles) {
        if(profiles == null) return;
        for(int i=0; i<profiles.length(); i++) {
            JSONObject profile = profiles.optJSONObject(i);
            if(profile == null) continue;
            VKApiUser vkUser = new VKApiUser().parse(profile);
            UserItem user = new UserItem();
            user.setId(vkUser.id);
            user.setName(vkUser.first_name + " " + vkUser.last_name);
            user.setAvatar(vkUser.photo_100);
            users.put(user.getId(), user);
            Log.i(TAG, user.toString());
        }
    }

    private void parseGroups (JSONArray groups){
        if(groups == null) return;
        for (int i=0; i<groups.length(); i++) {
            JSONObject group = groups.optJSONObject(i);
            if (group == null) continue;;
            VKApiCommunity vkGroup = new VKApiCommunity().parse(group);
            UserItem userItem = new UserItem();
            userItem.setId(vkGroup.id);
            userItem.setName(vkGroup.name);
            userItem.setAvatar(vkGroup.photo_100);
            users.put(userItem.getId(), userItem);
        }
    }

    private long secondsToMilliseconds(long seconds) {
        return seconds * DateUtils.SECOND_IN_MILLIS;
    }

}
