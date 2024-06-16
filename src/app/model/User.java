package app.model;

import app.TypeUser;

import java.sql.Date;

public class User {
    private int UserID;
    private String name;
    private String lastName;
    private String email;
    private int DNI;
    private Date birthDate;
   // private TypeUser typeUser;

    public User(int userID, String name, String lastName, String email, int DNI, Date birthDate) {
        this.UserID = userID;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.DNI = DNI;
        this.email = email;
        //this.setTypeUser(typeUser);
    }

    public User() {
    }

    public int getUserID() {
        return UserID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getDNI() {
        return DNI;
    }

    public Date getBirthDate() {
        return birthDate;
    }

   /* public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }*/

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
