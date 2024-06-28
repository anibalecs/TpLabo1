package dataBase;

import java.sql.*;

public class DataBaseConfig{
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
}
