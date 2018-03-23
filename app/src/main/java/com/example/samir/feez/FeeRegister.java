package com.example.samir.feez;
//import java.time.YearMonth;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class FeeRegister {
    static long lastGeneratedFeeID = 0;
    long feeID;
    long studentID;
    Date forYearMonth;
    Date paymentDate;
    long amount;
    boolean isPaid;

    static FeeRegister[] instance;

    FeeRegister(long studentID, Date forYearMonth, Date paymentDate, long amount, boolean isPaid){
        lastGeneratedFeeID++;
        feeID = lastGeneratedFeeID;
        this.studentID = studentID;
        this.forYearMonth = forYearMonth;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    static FeeRegister[] getInstance(){
        //TEMP stub for dummy data creation
        if (instance == null) {
            createDummyData();
        }
        return instance;
    }

    static void createDummyData(){
        Date date = new Date();
        instance = new FeeRegister[]{new FeeRegister(1, date, date, 100, true),
                    new FeeRegister(2, date, date, 200, true),
                    new FeeRegister(3, date, date, 300, true)};
    }
}
