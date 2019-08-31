package models;

import com.google.common.base.Objects;
import static com.google.common.base.MoreObjects.toStringHelper;

public class Message {

    public String sender;
    public String message;
    public String receiver;

    public Message(String receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Message) {
            final Message other = (Message) obj;
            return Objects.equal(sender, other.sender)
                    && Objects.equal(message, other.message)
                    && Objects.equal(receiver, other.receiver);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(sender)
                .addValue(message)
                .addValue(receiver)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.sender, this.message, this.receiver);
    }

}

