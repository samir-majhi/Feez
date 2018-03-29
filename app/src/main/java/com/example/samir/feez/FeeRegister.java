package com.example.samir.feez;
//import java.time.YearMonth;

import java.util.ArrayList;
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

    static ArrayList<FeeRegister> instance = new ArrayList<FeeRegister>();

    FeeRegister(long studentID, Date forYearMonth, Date paymentDate, long amount, boolean isPaid){
        lastGeneratedFeeID++;
        feeID = lastGeneratedFeeID;
        this.studentID = studentID;
        this.forYearMonth = forYearMonth;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    static ArrayList<FeeRegister> getInstance(){
        //TEMP stub for dummy data creation
        if (instance.isEmpty()) {
            createDummyData();
        }
        return instance;
    }

    static void createDummyData(){
        Date date = new Date();
        instance.add(new FeeRegister(1, date, date, 100, true));
        instance.add(new FeeRegister(2, date, date, 200, false));
        instance.add(new FeeRegister(3, date, date, 300, true));
    }

    static void addFeeEntry(long studentID, Date forYearMonth, Date paymentDate, long amount, boolean isPaid){
        FeeRegister feeRegister = new FeeRegister(studentID, forYearMonth, paymentDate, amount, isPaid);
        instance.add(feeRegister);
    }
}
