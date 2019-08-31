package controllers;
import io.javalin.Javalin;
public class RestMain {
    public static void main(String[] args) throws Exception {
        Javalin app = Javalin.create();
        app.port(getAssignedPort());
        app.start();
        PacemakerRestService service = new PacemakerRestService();
        configRoutes(app, service);
    }
    private static int getAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }

    static void configRoutes(Javalin app, PacemakerRestService service) {

        app.post("/users", ctx -> {
            service.createUser(ctx);
        });
        app.get("/users/:id", ctx -> {
            service.listUser(ctx);
        });
        app.get("/users/:id/activities/:activityid", ctx -> {
            service.getActivity(ctx);
        });

        app.post("/users/:id/activities", ctx -> {
            service.createActivity(ctx);
        });

        app.get("/users/:id/activities/:activityid/locations", ctx -> {
            service.getActivityLocations(ctx);
        });

        app.post("/users/:id/activities/:activityid/locations", ctx -> {
            service.addLocation(ctx);
        });

        app.delete("/users", ctx -> {
            service.deleteUsers(ctx);
        });

        app.delete("/users/:id", ctx -> {
            service.deleteUser(ctx);
        });

        app.delete("/users/:id/activities", ctx -> {
            service.deleteActivities(ctx);
        });

        app.post("/users/:id/friends", ctx -> {
            service.follow(ctx);
        });

        app.get("/users/:id/friends", ctx -> {
            service.listFriends(ctx);
        });

        app.put("/users/:id/friends", ctx -> {
            service.unfollowFriend(ctx);
        });

        app.put("/users/:id/message", ctx -> {
            service.messageFriend(ctx);
        });

        app.get("/users/:id/message", ctx -> {
            service.listMessages(ctx);
        });

        app.post("/users/:id/message", ctx -> {
            service.messageAllFriends(ctx);
        });


    }
}
