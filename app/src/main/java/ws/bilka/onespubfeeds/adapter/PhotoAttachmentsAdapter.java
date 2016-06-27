package ws.bilka.onespubfeeds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import ws.bilka.onespubfeeds.R;

public class PhotoAttachmentsAdapter extends RecyclerView.Adapter<PhotoAttachmentsAdapter.MyViewHolder> {

    private List<String> mPhotos;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public PhotoAttachmentsAdapter(List<String> photos, Context context) {
        this.mPhotos = photos;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_attachment_image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       Ion.with(holder.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.noplaceholder)
                .load(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}