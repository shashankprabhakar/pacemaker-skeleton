package controllers;

import com.google.common.base.Optional;
import models.Activity;
import models.Location;
import models.Message;
import models.User;

import java.util.*;
import java.util.stream.Collectors;


public class PacemakerAPI {

    private Map<String, User> emailIndex = new HashMap<>();
    private Map<String, User> userIndex = new HashMap<>();
    private Map<String, Activity> activitiesIndex = new HashMap<>();
    private Map<String, List<User>> friendsIndex = new HashMap<>();


    public PacemakerAPI() {
    }

    public Collection<User> getUsers() {
        return userIndex.values();
    }

    public void deleteUsers() {
        userIndex.clear();
        emailIndex.clear();
    }

    public User createUser(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        emailIndex.put(email, user);
        userIndex.put(user.id, user);
        return user;
    }

    public Activity createActivity(String id, String type, String location, double distance) {
        Activity activity = null;
        Optional<User> user = Optional.fromNullable(userIndex.get(id));
        if (user.isPresent()) {
            activity = new Activity(type, location, distance);
            user.get().activities.put(activity.id, activity);
            activitiesIndex.put(activity.id, activity);
        }
        return activity;
    }

    public Activity getActivity(String id) {
        return activitiesIndex.get(id);
    }

    public Collection<Activity> getActivities(String id) {
        Collection<Activity> activities = null;
        Optional<User> user = Optional.fromNullable(userIndex.get(id));
        if (user.isPresent()) {
            activities = user.get().activities.values();
        }
        return activities;
    }

    public List<Activity> listActivities(String userId, String sortBy) {
        List<Activity> activities = new ArrayList<>();
        activities.addAll(userIndex.get(userId).activities.values());
        switch (sortBy) {
            case "type":
                activities.sort((a1, a2) -> a1.type.compareTo(a2.type));
                break;
            case "location":
                activities.sort((a1, a2) -> a1.location.compareTo(a2.location));
                break;
            case "distance":
                activities.sort((a1, a2) -> Double.compare(a1.distance, a2.distance));
                break;
        }
        return activities;
    }

    public void addLocation(String id, double latitude, double longitude) {
        java.util.Optional<Activity> activity = java.util.Optional.ofNullable(activitiesIndex.get(id));
        activity.ifPresent(value -> value.route.add(new Location(latitude, longitude)));
    }

    public User getUserByEmail(String email) {
        return emailIndex.get(email);
    }

    public User getUser(String id) {
        return userIndex.get(id);
    }

    public User deleteUser(String id) {
        User user = userIndex.remove(id);
        return emailIndex.remove(user.email);
    }

    public void deleteActivities(String id) {
        java.util.Optional<User> user = java.util.Optional.ofNullable(userIndex.get(id));
        if (user.isPresent()) {
            user.get().activities.values().forEach(activity -> activitiesIndex.remove(activity.getId()));
            user.get().activities.clear();
        }
    }

        public void follow(String id, String email) {
            Optional<User> user = Optional.fromNullable(userIndex.get(id));
            if (user.isPresent()) {
                friendsIndex.get(id).add(emailIndex.get(email));
            }
        }

        public List<String> listFriends(String id) {
            List<String> friends =null;
            Optional<User> user = Optional.fromNullable(userIndex.get(id));
            if (user.isPresent()) {
                friends = friendsIndex.get(id).stream()
                        .map(friend -> friend.firstname + "--" +friend.email)
                        .collect(Collectors.toList());
            }
            return friends;
        }
        public void unfollowFriend(String id, String email) {
            Optional<User> user = Optional.fromNullable(userIndex.get(id));
            if (user.isPresent()) {
                friendsIndex.get(id).remove(getUserByEmail(email));
            }
        }
        public void messageFriend(String id, Message message) {
            Optional<User> user = Optional.fromNullable(emailIndex.get(message.receiver));
            if (user.isPresent()) {
                user.get().inbox.add(message);
            }
        }
        public void messageAllFriends(String id, String message) {
            Optional<User> user = Optional.fromNullable(userIndex.get(id));
            String senderEmail = user.get().email;
            if (user.isPresent()) {
                friendsIndex.get(id)
                        .forEach(friend -> userIndex.get(friend.id)
                                .inbox.add(new Message(friend.email, senderEmail, message))
                        );
            }
        }

    }

