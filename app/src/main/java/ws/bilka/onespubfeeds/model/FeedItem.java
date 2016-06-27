package ws.bilka.onespubfeeds.model;

import java.util.List;

public class FeedItem {
    private int id;
    private String title;
    private String avatarImage;
    private String text;
    private long timeStamp;
    private List<FeedItem> reposts;
    private List<String> photos;

    public FeedItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public List<FeedItem> getReposts() {
        return reposts;
    }

    public void setReposts(List<FeedItem> reposts) {
        this.reposts = reposts;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
