package com.example.samir.feez;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

// This holds the data together in one class
public class StudentInfoAccessor {
    private static ArrayList<StudentInfo> instance = new ArrayList<StudentInfo>();

    static ArrayList<StudentInfo> getInstance(Context context){
        //TEMP stub for dummy data creation
        if (instance.isEmpty()) {
            //createDummyData();
            StorageAccessor.readData(context, StorageAccessor.studentInfoDataFileName);
        }
        return instance;
    }

    static void setInstance(ArrayList<StudentInfo> object){
        instance = object;
    }

    static void createDummyData(){
        Date date1 = new Date(2017-1900, 1-1,1);
        Date date2 = new Date(2018-1900, 4-1,1);
        Date date3 = new Date(2018-1900, 3-1,1);
        instance.add(new StudentInfo("Amit", date1, "1234567891","1"));
        instance.add(new StudentInfo("Bindra", date2, "1234567892","2"));
        instance.add(new StudentInfo("Cassandra", date3, "1234567893","3"));
    }

    // TODO: Implement this class as a hash table for faster getting of name from id.
    // Might not be required since we currently don't expect student name to be huge.
    // Given that our customers are solopreneurs

    static String getStudentNameByID (Context context, int ID){
        getInstance(context);
        for (StudentInfo student : instance){
            if (student.ID == ID) {
                return student.name;
            }
        }
        return null;
    }

    static void addStudent(Context context, String name, Date joiningDate, String phoneNumber, String batch){
        StudentInfo student = new StudentInfo(name, joiningDate, phoneNumber, batch);
        instance.add(student);
        StorageAccessor.writeData(context, StorageAccessor.studentInfoDataFileName);
        FeeRegisterAccessor.addFeeEntry(context, student.ID, new MonthYear(joiningDate), null, 0, false );
    }
}
