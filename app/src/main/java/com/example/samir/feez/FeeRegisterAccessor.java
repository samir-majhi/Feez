package com.example.samir.feez;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class FeeRegisterAccessor {

    static ArrayList<FeeRegister> instance = new ArrayList<FeeRegister>();

    // Java 8's YearMonth is supported only API level 26 onwards which is Android Oreo onwards.
    // Can't use it because many existing older phones won't work
    // monthList is Sorted from older to newer dates
    static ArrayList<MonthYear> monthList = new ArrayList<MonthYear>();

    static ArrayList<FeeRegister> getInstance(Context context){
        //TEMP stub for dummy data creation
        if (instance.isEmpty()) {
            //createDummyData(context);
            StorageAccessor.readData(context, StorageAccessor.feeRegisterDataFileName);
        }
        return instance;
    }

    static void setInstance(ArrayList<FeeRegister> object){
        instance = object;
    }

    static void createDummyData(Context context){
        Date date1 = new Date(2017-1900, 1-1,1);
        Date date2 = new Date(2018-1900, 4-1,1);
        Date date3 = new Date(2018-1900, 3-1,1);
        MonthYear monthYear1 = new MonthYear(date1);
        MonthYear monthYear2 = new MonthYear(date2);
        MonthYear monthYear3 = new MonthYear(date3);
        addFeeEntry(context,1, monthYear1, date1,100,true);
        addFeeEntry(context,2, monthYear1, date1,100,false);
        addFeeEntry(context,3, monthYear1, date1,100,true);
        addFeeEntry(context,2, monthYear2, date2,200,false);
        addFeeEntry(context,2, monthYear3, date3,300,true);
    }

    static void addFeeEntry(Context context, int studentID, MonthYear forMonthYear, Date paymentDate, float amount, boolean isPaid){
        FeeRegister feeRegister = new FeeRegister(studentID, forMonthYear, paymentDate, amount, isPaid);
        instance.add(feeRegister);
        addToMonthList(forMonthYear);
        StorageAccessor.writeData(context, StorageAccessor.feeRegisterDataFileName);
    }

    static void updatePaymentStatus(Context context, int feeID, boolean isChecked, Date paymentDate){
        getInstance(context);
        for (FeeRegister feeEntry : instance){
            if (feeEntry.feeID == feeID){
                feeEntry.isPaid = isChecked;
                feeEntry.paymentDate = paymentDate;
            }
        }
        StorageAccessor.writeData(context, StorageAccessor.feeRegisterDataFileName);
    }

    private static void addToMonthList(MonthYear my){
        if (!monthList.contains(my)) {
            // add in the right position chronologically
            int i;
            for(i=0; i<monthList.size(); i++){
                MonthYear element = monthList.get(i);
                if (element.after(my)){
                    break;
                }
            }
            monthList.add(i,my);
        }
    }

    static String[] getMonthListPretty(Context context){
        getInstance(context); // to ensure dummydata is created
        ArrayList<String> monthListString = new ArrayList<String>();
        for (MonthYear my : monthList){
            monthListString.add(my.getMonth().toString() + " " + my.getYear().toString());
        }
        return monthListString.toArray(new String[0]);
    }

    static ArrayList<MonthYear> getMonthList(Context context){
        getInstance(context); // to ensure dummydata is created
        return monthList;
    }
    static void setMonthList(ArrayList<MonthYear> object){
        monthList = object;
    }
}
