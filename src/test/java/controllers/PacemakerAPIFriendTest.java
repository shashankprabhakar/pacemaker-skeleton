package controllers;

import controllers.PacemakerAPI;
import models.Message;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PacemakerAPIFriendTest {

    private PacemakerAPI pacemaker;
    private User user,friend1,friend2;

    @BeforeEach
    public void setup()
    {
        pacemaker = new PacemakerAPI();
        pacemaker.createUser("bart", "simpson", "bart@simpson.com", "secret");
        user = pacemaker.getUserByEmail("bart@simpson.com");
        friend1 = pacemaker.createUser("maggie", "simpson", "maggie@simpson.com", "secret");
        friend2= pacemaker.createUser("lisa", "simpson", "lisa@simpson.com", "secret");
    }

    @AfterEach
    public void tearDown()
    {
        pacemaker = null;
    }

    @Test
    public void follow() {
        assertEquals(0, pacemaker.listFriends(user.id).size());

        pacemaker.follow(user.id, friend1.email);
        assertEquals(1, pacemaker.listFriends(user.id).size());

        User user1=pacemaker.getUserByEmail(friend1.email);
        assertEquals(user1.firstname + "--" +user1.email, pacemaker.listFriends(user.id).get(0));

    }

    @Test
    public void unFollow() {
        assertEquals(0, pacemaker.listFriends(user.id).size());

        pacemaker.follow(user.id, friend1.email);
        pacemaker.follow(user.id, friend2.email);
        assertEquals(2, pacemaker.listFriends(user.id).size());

        pacemaker.unfollowFriend(user.id, "maggie@simpson.com");
        assertEquals(1, pacemaker.listFriends(user.id).size());
        User user1=pacemaker.getUserByEmail("maggie@simpson.com");
        assertNotEquals(user1.firstname + "--" +user1.email, pacemaker.listFriends(user.id).get(0));

    }

    @Test
    public void testMessageFriend() {

        pacemaker.follow(user.id, friend1.email);
        assertEquals(0, friend1.inbox.size());

        pacemaker.messageFriend(user.id, new Message(friend1.email, user.email, "Hello friend!"));
        assertEquals(1, friend1.inbox.size());
        assertEquals("Hello friend",friend1.inbox.get(0).message);

    }

    @Test
    public void testMessageAll() {

        pacemaker.follow(user.id, friend1.email);
        pacemaker.follow(user.id, friend2.email);
        assertEquals(0, friend1.inbox.size());
        assertEquals(0, friend2.inbox.size());

        pacemaker.messageAllFriends(user.id, "Hello all friends!!");
        assertEquals(1, friend1.inbox.size());
        assertEquals(1, friend2.inbox.size());
        assertEquals("Hello all friends !!", friend1.inbox.get(0).message);
        assertEquals("Hello all friends!!", friend2.inbox.get(0).message);
        assertEquals(friend1.inbox.get(0).message, friend1.inbox.get(0).message);
    }

}

