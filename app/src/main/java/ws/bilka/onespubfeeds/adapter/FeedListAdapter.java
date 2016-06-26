package ws.bilka.onespubfeeds.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import ws.bilka.onespubfeeds.AppController;
import ws.bilka.onespubfeeds.R;
import ws.bilka.onespubfeeds.model.FeedItem;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.post_item, null);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        NetworkImageView avatarImage = (NetworkImageView) convertView
                .findViewById(R.id.avatarImage);
        TextView text = (TextView) convertView.findViewById(R.id.text);

        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        FeedItem item = feedItems.get(position);
        title.setText(item.getTitle());

        recyclerView.setAdapter(new PhotoAttachmentsAdapter(new String[]{item.getPhoto()}, activity));

        avatarImage.setImageUrl(item.getAvatarImage(), imageLoader);

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(item.getTimeStamp(),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        if (!TextUtils.isEmpty(item.getText())) {
            text.setText(item.getText());
            text.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.GONE);
        }

        LinearLayout repostLayout = (LinearLayout) convertView.findViewById(R.id.repostLayout);

        if(item.getReposts() != null && item.getReposts().size() > 0) {
            TextView repostTitle = (TextView) convertView.findViewById(R.id.repostTitle);
            TextView repostText = (TextView) convertView.findViewById(R.id.repostText);
            NetworkImageView repostAvatarImage = (NetworkImageView) convertView
                    .findViewById(R.id.repostAvatarImage);
            TextView repostTimestamp = (TextView) convertView.findViewById(R.id.repostTimestamp);
            RecyclerView repostRecyclerView = (RecyclerView) convertView.findViewById(R.id.repostRecyclerView);
            RecyclerView.LayoutManager repostLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            repostRecyclerView.setLayoutManager(repostLayoutManager);

            FeedItem repostFeedItem = item.getReposts().get(0);
            repostTitle.setText(repostFeedItem.getTitle());
            repostText.setText(repostFeedItem.getText());
            repostAvatarImage.setImageUrl(repostFeedItem.getAvatarImage(), imageLoader);
            repostTimestamp.setText(timeAgo);
            repostRecyclerView.setAdapter(new PhotoAttachmentsAdapter(new String[]{repostFeedItem.getPhoto()},activity));
            repostLayout.setVisibility(View.VISIBLE);
        } else {
            repostLayout.setVisibility(View.GONE);
        }

        return convertView;
    }
}
