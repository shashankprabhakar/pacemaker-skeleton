package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;
import java.util.*;

import com.google.common.base.Objects;

public class User implements Serializable {

    public String id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    public Map<String, Activity> activities = new HashMap<>();
    public List<Message> inbox = new ArrayList<>();
    public User() {
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public User(String firstname, String lastname, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof User) {
            final User other = (User) obj;
            return Objects.equal(firstname, other.firstname)
                    && Objects.equal(lastname, other.lastname)
                    && Objects.equal(email, other.email)
                    && Objects.equal(password, other.password)
                    && Objects.equal(inbox, other.inbox)
                    && Objects.equal(activities, other.activities);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .addValue(firstname)
                .addValue(lastname)
                .addValue(password)
                .addValue(email)
                .addValue(activities)
                .addValue(inbox)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.lastname, this.firstname, this.email, this.password);
    }
}