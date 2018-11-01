package com.example.samir.feez;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageAccessor{
    static final String feeRegisterData = "FeeRegister.fez";
    static final String studentInfoData = "StudentInfo.fez";
    private static final String monthListData = "MonthList.fez";
    static final String allData = "placeholder";


    public static void readData(Context context, String whichData){
        switch (whichData){
            case feeRegisterData:
                readDataEach(context, feeRegisterData);
                readDataEach(context, monthListData);
                break;
            case studentInfoData:
                readDataEach(context, studentInfoData);
                break;
            case allData:
                readDataEach(context, feeRegisterData);
                readDataEach(context, monthListData);
                readDataEach(context, studentInfoData);
        }
    }


    private static void readDataEach(Context context, String fileName) {
        FileInputStream fis;
        try {
            fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object object = is.readObject();
            is.close();

            switch (fileName){
                case feeRegisterData:
                    FeeRegister.setInstance((ArrayList<FeeRegister>)object);
                    break;
                case monthListData:
                    FeeRegister.setMonthList((ArrayList<MonthYear>)object);
                    break;
                case studentInfoData:
                    StudentInfo.setInstance((ArrayList<StudentInfo>)object);
                    break;
            }

        } catch (Exception e) {
            //Alert.show(context,"Couldn't read stored data: "+ fileName +": "+ e.toString());
            Log.e("StorageAccessor:readDataEach", "Couldn't write stored data: "+ fileName +": "+ e.toString());
            e.printStackTrace();

        }
    }

    public static void writeData(Context context, String whichData){
        switch (whichData){
            case feeRegisterData:
                writeDataEach(context, feeRegisterData, FeeRegister.getInstance(context));
                writeDataEach(context, monthListData, FeeRegister.getMonthList(context));
                break;
            case studentInfoData:
                writeDataEach(context, studentInfoData, StudentInfo.getInstance(context));
                break;
            case allData:
                writeDataEach(context, feeRegisterData, FeeRegister.getInstance(context));
                writeDataEach(context, monthListData, FeeRegister.getMonthList(context));
                writeDataEach(context, studentInfoData, StudentInfo.getInstance(context));
        }
    }

    private static void writeDataEach(Context context, String fileName, Object object) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
        }
        catch (Exception e) {
            //Alert.show(context,"Couldn't write stored data: "+ fileName +": "+ e.toString());
            Log.e("StorageAccessor:rriteDataEach", "Couldn't write stored data: "+ fileName +": "+ e.toString());
            e.printStackTrace();
        }
    }
}
