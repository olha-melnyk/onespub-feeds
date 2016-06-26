package ws.bilka.onespubfeeds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ws.bilka.onespubfeeds.R;

public class PhotoAttachmentsAdapter extends RecyclerView.Adapter<PhotoAttachmentsAdapter.MyViewHolder> {

    private String[] mPhotos;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public PhotoAttachmentsAdapter(String[] photos, Context context) {
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
        Picasso.with(mContext)
                .load(mPhotos[position])
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPhotos.length;
    }
}