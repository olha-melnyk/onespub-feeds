package ws.bilka.onespubfeeds.model;

public class UserItem {

    private int id;
    private String name;
    private String avatar;

    public UserItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserItem: " + id + " " + name + " " + avatar;
    }
}


