package dataBase.DAO;

import app.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    private Connection connection;

    public UserDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createUser(User user){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (name, lastname, email, DNI, birthDate) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getDNI());
            preparedStatement.setDate(5, user.getBirthDate());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                user.setUserID(generatedKeys.getInt(1));
            }else{
                throw new SQLException("No ID obtained.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int id){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE UserID = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int UserID = resultSet.getInt("UserID");
                int DNI = resultSet.getInt("DNI");
                Date birthDate = resultSet.getDate("birthDate");
                return new User(UserID, name, lastname, email, DNI, birthDate);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(int UserID){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE UserID = ?");
            preparedStatement.setInt(1, UserID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user, int id){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET name = ? , lastName = ? , email = ? , DNI = ? , birthDate = ? WHERE UserID = ?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getDNI());
            preparedStatement.setDate(5, user.getBirthDate());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users");
            ResultSet userResult = preparedStatement.executeQuery();
            while(userResult.next()){
                String name = userResult.getString("name");
                String lastName = userResult.getString("lastName");
                String email = userResult.getString("email");
                int UserID = userResult.getInt("UserID");
                int DNI = userResult.getInt("DNI");
                Date birthDate = userResult.getDate("birthDate");
                User user = new User(UserID, name, lastName, email, DNI, birthDate);
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
