package ws.bilka.onespubfeeds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ws.bilka.onespubfeeds.adapter.FeedListAdapter;
import ws.bilka.onespubfeeds.model.FeedItem;

public class FeedsActivity extends AppCompatActivity {

    private static final String TAG = FeedsActivity.class.getSimpleName();
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;

    private boolean loadingInProgress = false;
    private static final int PAGE_SIZE = 10;
    private static final int FEED_OWNER_ID = -34613199;
    private int cursor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        ListView listView = (ListView) findViewById(R.id.list);

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
            JSONArray items = r.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = (JSONObject) items.get(i);

                FeedItem feedItem = new FeedItem();
                feedItem.setId(item.getInt("id"));
                feedItem.setTitle(item.getString("text"));
                feedItem.setTimeStamp(secondsToMilliseconds(item.getLong("date")));

                feedItems.add(feedItem);
            }

            listAdapter.notifyDataSetChanged();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private long secondsToMilliseconds(long seconds) {
        return seconds * DateUtils.SECOND_IN_MILLIS;
    }

}
