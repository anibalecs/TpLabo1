import dataBase.DataBaseConfig;
import graficInterface.MedicalTurnerApp;

public class Main{
    public static void main(String[] args){
        DataBaseConfig dataBaseConnection = new DataBaseConfig();
        dataBaseConnection.connect();

        MedicalTurnerApp.startPanel();
    }
}