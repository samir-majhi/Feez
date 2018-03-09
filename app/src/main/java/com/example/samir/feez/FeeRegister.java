package com.example.samir.feez;
import java.time.Month;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class FeeRegister {
    static long lastGeneratedFeeID = 0;
    long feeID;
    long studentID;
    Month forMonth;
    Date paymentDate;
    long amount;

    FeeRegister(long studentID, Month forMonth, Date paymentDate, long amount){
        lastGeneratedFeeID++;
        feeID = lastGeneratedFeeID;
        this.studentID = studentID;
        this.forMonth = forMonth;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}
