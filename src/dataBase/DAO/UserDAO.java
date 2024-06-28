package dataBase.DAO;

import app.model.User;
import java.util.List;

public interface UserDAO{
    void createUser(User user);
    User getUser(int id);
    void deleteUser(int id);
    void updateUser(User user, int id);
    List<User> getUsers();
}
