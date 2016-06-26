package ws.bilka.onespubfeeds;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class AppController extends Application {

    private static AppController mInstance;
    BitmapCache mBitmapCache;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(AppController.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AppController.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mBitmapCache);
        }

        return this.mImageLoader;
    }

    public BitmapCache getBitmapCache() {
        if (mBitmapCache == null)
            mBitmapCache = new BitmapCache();
        return this.mBitmapCache;
    }

}
