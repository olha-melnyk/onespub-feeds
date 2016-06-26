package ws.bilka.onespubfeeds.model;

import java.util.List;

public class FeedItem {
    private int id;
    private String title;
    private String avatarImage;
    private String text;
    private String photo;
    private long timeStamp;
    private List<FeedItem> reposts;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<FeedItem> getReposts() {
        return reposts;
    }

    public void setReposts(List<FeedItem> reposts) {
        this.reposts = reposts;
    }
}
