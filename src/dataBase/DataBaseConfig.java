package dataBase;

import java.sql.*;

public class DataBaseConfig {
    private static String Username = "sa";
    private static String Password = "";
    private static String Url = "jdbc:h2:file:C:/Users/aniba/test;IFEXISTS=FALSE";
    private static String Drive_DB = "org.h2.Driver";

    public static Connection connect(){
        Connection connection = null;
        try{
            Class.forName(Drive_DB);
            connection = DriverManager.getConnection(Url, Username, Password);
            System.out.println("Conexion exitosa!");
        }catch(ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
            System.out.println("Error en la conexion con la BD");
        }
        return connection;
    }









    public static Statement createDeclaration(Connection connection) throws SQLException {
        try{
            return connection.createStatement();
        }catch(SQLException a){
            throw new SQLException("Error in the generation of the new declaration");
        }
    }

    public static void carryUpdate(Connection connection, Statement statement, String SQL) throws SQLException{
        try{
            statement.executeUpdate(SQL);
        } catch (SQLException a) {
            throw new SQLException("Error when executing the declaration");
        }
    }

    public static void wrapDeclaration(Connection connection) throws SQLException{
        try{
            connection.commit();
        } catch (SQLException a) {
            throw new SQLException("Error when performing the wrap");
        }
    }

    public static void closeConnection(Connection connection) throws SQLException{
        try{
            connection.close();
        }catch(SQLException a){
            throw new SQLException("Error closing connection");
        }
    }

    public static  ResultSet useInform(Statement statement, String comand) throws SQLException {
        ResultSet response = null;
        try{
            response = statement.executeQuery(comand);
            return response;
        }catch(SQLException a){
            throw new SQLException("Error in the use of the report");
        }

    }

    public static ResultSet creatInform(String comand) throws SQLException {
        Connection connection = DataBaseConfig.connect();
        Statement statement = DataBaseConfig.createDeclaration(connection);
        ResultSet response = DataBaseConfig.useInform(statement, comand);
        return response;
    }

    public static void createStatementDecl(String comand) throws SQLException {
        Connection connection = DataBaseConfig.connect();
        Statement statement = DataBaseConfig.createDeclaration(connection);
        DataBaseConfig.carryUpdate(connection, statement, comand);
        DataBaseConfig.wrapDeclaration(connection);
        DataBaseConfig.closeConnection(connection);
    }

}
