package application;

public class Mediator {
    private static Mediator INSTANCE;

    private User user;

    public static Mediator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Mediator();
        }
        return INSTANCE;
    }

    private Mediator() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User email) {
        this.user = email;
    }
}