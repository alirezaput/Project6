package ir.applinkfinder.hw6.contacts;

public class Contact {
    private String name;
    private String email;
    private String username;
    private String password;
    private int contactId;
    private boolean mCurrentUser;

    public boolean isCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        mCurrentUser = currentUser;
    }

    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
