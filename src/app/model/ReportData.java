package app.model;

import java.sql.Timestamp;

public class ReportData{
    private Timestamp date;
    private double amountCharged;
    private int numberOfQueries;

    public ReportData(Timestamp date, double amountCharged, int numberOfQueries){
        this.date = date;
        this.amountCharged = amountCharged;
        this.numberOfQueries = numberOfQueries;
    }

    public Timestamp getDate(){
        return date;
    }

    public double getAmountCharged(){
        return amountCharged;
    }

    public int getNumberOfQueries(){
        return numberOfQueries;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public void setAmountCharged(double amountCharged){
        this.amountCharged = amountCharged;
    }

    public void setNumberOfQueries(int numberOfQueries){
        this.numberOfQueries = numberOfQueries;
    }
}
