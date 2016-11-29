package instagram.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by kalma on 16/11/29.
 */

@Entity
public class User {

    @Id
    String id;
    String last_name;

    @OneToMany(mappedBy = "user")
    private Set<Picture> photos ;

    public String getUserID() {
        return id;
    }

    public void setUserID(String userID) {
        id = userID;
    }

    public String getName() {
        return last_name;
    }

    public void setName(String name) {
        this.last_name = name;
    }

    public Set<Picture> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Picture> photos) {
        this.photos = photos;
    }

}
