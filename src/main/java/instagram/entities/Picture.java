package instagram.entities;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Picture {

    @Id
    private String ID;

    private String name;

    public Picture() {}

    public Picture(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
