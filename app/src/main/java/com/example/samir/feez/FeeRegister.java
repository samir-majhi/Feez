package com.example.samir.feez;
//import java.time.YearMonth;

import android.content.Context;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class FeeRegister {
    static int lastGeneratedFeeID = 0;
    int feeID;
    int studentID;
    MonthYear forMonthYear;
    Date paymentDate;
    float amount;
    boolean isPaid;

    FeeRegister(int studentID, MonthYear forMonthYear, Date paymentDate, float amount, boolean isPaid){
        lastGeneratedFeeID++;
        feeID = lastGeneratedFeeID;
        this.studentID = studentID;
        this.forMonthYear = forMonthYear;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.isPaid = isPaid;
    }
}

class MonthYear{
    private String month;
    private String year;
    private Date date;

    public MonthYear(Date date){
        this.setMonth(date);
        this.setYear(date);
        this.setDate(date);
    }

    public MonthYear (String monthYear){
        //String[] monthYearSplit = monthYear.split(" ");
        this((new SimpleDateFormat("MMM yyyy")).parse(monthYear,new ParsePosition(0)));
    }

    @Override
    public boolean equals(Object o){
        MonthYear my = (MonthYear) o;
        return getMonth().equals(my.getMonth()) && getYear().equals(my.getYear());
    }

    boolean after(MonthYear my){
        return getDate().after(my.getDate());
    }

    public String getMonth() {
        return month;
    }

    private void setMonth(Date date) {
        this.month = (new SimpleDateFormat("MMM")).format(date).toString();
    }

    public String getYear() {
        return year;
    }

    private void setYear(Date date) {
        this.year = (new SimpleDateFormat("yyyy")).format(date).toString();
    }

    public Date getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = date;
    }
}
