package online.upcare.f4hupcaregroup.bestshopping.entities;

import java.util.HashMap;

public class User {
    private String email;
    private String name;
    private HashMap<String, Object> timeJoined;
    private boolean hasLoggedInWithPassword;

    public User(){}

    public User(String email, String name, HashMap<String, Object> timeJoined, boolean hasLoggedInWithPassword) {
        this.email = email;
        this.name = name;
        this.timeJoined = timeJoined;
        this.hasLoggedInWithPassword = hasLoggedInWithPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Object> getTimeJoined() {
        return timeJoined;
    }

    public boolean isHasLoggedInWithPassword() {
        return hasLoggedInWithPassword;
    }
}
