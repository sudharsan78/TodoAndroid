package in.testpress.may103;

/**
 * Created by sudharsan on 19/5/16.
 */
public class Profiledetail {
    private Integer id;
    private String username;
    private String tasklist;
    private String owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTasklist() {
        return tasklist;
    }

    public void setTasklist(String tasklist) {
        this.tasklist = tasklist;
    }
}
