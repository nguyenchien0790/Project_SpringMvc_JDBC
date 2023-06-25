package rikkei.academy.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String userName;
    private String avatar = "https://firebasestorage.googleapis.com/v0/b/nguyenchien-40b00.appspot.com/o/avatar%20default.png?alt=media&token=d75f9a64-8009-4978-a1f9-0e1a086168c4";
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private String address;
    private boolean status;

    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(int id, String name, String userName, String avatar, String email, String password, String confirmPassword, String phone, String address, boolean status, Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.roleSet = roleSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", roleSet=" + roleSet +
                '}';
    }
}
