package app.service;

import dataBase.DAO.UserDAO;
import app.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void createUser(User user) throws Exception {
        try{
            userDAO.createUser(user);
        }catch(Exception e){
            throw new Exception("Error creating user");
        }
    }

    public void deleteUser(int id) throws Exception {
        try{
            userDAO.deleteUser(id);
        }catch(Exception e){
            throw new Exception("Error deleting user");
        }
    }

    public User getUser(int id) throws Exception {
        try{
            return userDAO.getUser(id);
        }catch(Exception e){
            throw new Exception("Error getting user");
        }
    }

    public void updateUser(User user, int id) throws Exception {
        try{
            userDAO.updateUser(user, id);
        }catch(Exception e){
            throw new Exception("Error updating user");
        }
    }

    public List<User> getUsers() throws Exception {
        try{
            return userDAO.getUsers();
        }catch(Exception e){
            throw new Exception("Error getting users");
        }
    }
}
