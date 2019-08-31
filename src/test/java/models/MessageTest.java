package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MessageTest {
    Message message = new Message("hello", "my", "friend");

    @Test
    public void testCreate()
    {
        assertEquals ("hello", message.sender);
        assertEquals ("my", message.receiver);
        assertEquals ("friend!",   message.message);
    }


    @Test
    public void testToString()
    {
        assertEquals ("Message{" + "hello my friend}", message.toString());
    }

    @Test
    public void testEquals()
    {
        Message otherMessage = new Message(message.receiver, message.sender, message.message);
        assertEquals(otherMessage, message);
        assertNotEquals( otherMessage, "str");

    }

}


