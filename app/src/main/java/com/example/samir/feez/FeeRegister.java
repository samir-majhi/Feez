package com.example.samir.feez;
//import java.time.YearMonth;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class FeeRegister {
    static long lastGeneratedFeeID = 0;
    long feeID;
    long studentID;
    MonthYear forMonthYear;
    Date paymentDate;
    long amount;
    boolean isPaid;

    static ArrayList<FeeRegister> instance = new ArrayList<FeeRegister>();
    // Java's YearMonth is supported only API level 26 onwards which is Android Oreo onwards.
    // Can't use it because many existing older phones won't work
    // monthList is Sorted from older to newer dates
    static ArrayList<MonthYear> monthList = new ArrayList<MonthYear>();

    FeeRegister(long studentID, MonthYear forMonthYear, Date paymentDate, long amount, boolean isPaid){
        lastGeneratedFeeID++;
        feeID = lastGeneratedFeeID;
        this.studentID = studentID;
        this.forMonthYear = forMonthYear;
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
        Date date1 = new Date(2017-1900, 1-1,1);
        Date date2 = new Date(2018-1900, 4-1,1);
        Date date3 = new Date(2018-1900, 3-1,1);
        MonthYear monthYear1= new MonthYear(date1);
        MonthYear monthYear2= new MonthYear(date2);
        MonthYear monthYear3= new MonthYear(date3);
        addFeeEntry(1, monthYear1, date1,100,true);
        addFeeEntry(2, monthYear2, date2,200,false);
        addFeeEntry(2, monthYear3, date3,300,true);
    }

    static void addFeeEntry(long studentID, MonthYear forMonthYear, Date paymentDate, long amount, boolean isPaid){
        FeeRegister feeRegister = new FeeRegister(studentID, forMonthYear, paymentDate, amount, isPaid);
        instance.add(feeRegister);
        addToMonthList(forMonthYear);
    }

    private static void addToMonthList(MonthYear my){
        if (!monthList.contains(my)) {
            // add in the right position chronologically
            int i;
            for(i =0; i<monthList.size(); i++){
                MonthYear element = monthList.get(i);
                if (element.after(my)){
                    break;
                }
            }
            monthList.add(i,my);
        }
    }

    static String[] getMonthList(){
        getInstance(); // to ensure dummydata is created
        ArrayList<String> monthListString = new ArrayList<String>();
        for (MonthYear my : monthList){
            monthListString.add(my.getMonth().toString() + " " + my.getYear().toString());
        }
        return monthListString.toArray(new String[0]);
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
