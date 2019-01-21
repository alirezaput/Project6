package ir.applinkfinder.hw6.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Contact {

    @org.greenrobot.greendao.annotation.Id(autoincrement = true)
    private Long contactId;

    private String name;
    private String email;

    @Unique
    private String username;

    private String password;
    private boolean currentUser;

    @Generated(hash = 1627332310)
    public Contact(Long contactId, String name, String email, String username,
            String password, boolean currentUser) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.currentUser = currentUser;
    }
    @Generated(hash = 672515148)
    public Contact() {
    }
    public Long getContactId() {
        return this.contactId;
    }
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getCurrentUser() {
        return this.currentUser;
    }
    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }

//    public boolean isCurrentUser() {
//        return mCurrentUser;
//    }
//
//    public void setCurrentUser(boolean currentUser) {
//        mCurrentUser = currentUser;
//    }
//
//    public int getContactId() {
//        return contactId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

}
