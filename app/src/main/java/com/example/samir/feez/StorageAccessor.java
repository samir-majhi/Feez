package com.example.samir.feez;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.lang.reflect.Modifier;

public class StorageAccessor{
    static final String feeRegisterDataFileName = "FeeRegister.fez";
    static final String studentInfoDataFileName = "StudentInfo.fez";
    private static final String monthListDataFileName = "MonthList.fez";
    static final String allData = "placeholder";

    static final Type feeRegisterType = new TypeToken<ArrayList<FeeRegister>>() {}.getType();
    static final Type studentInfoDataType = new TypeToken<ArrayList<StudentInfo>>() {}.getType();
    static final Type monthListDataType = new TypeToken<ArrayList<MonthYear>>() {}.getType();

    static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
            .create();

    public static void readData(Context context, String whichData){
        switch (whichData){
            case feeRegisterDataFileName:
                readDataEach(context, feeRegisterDataFileName);
                readDataEach(context, monthListDataFileName);
                break;
            case studentInfoDataFileName:
                readDataEach(context, studentInfoDataFileName);
                break;
            case allData:
                readDataEach(context, feeRegisterDataFileName);
                readDataEach(context, monthListDataFileName);
                readDataEach(context, studentInfoDataFileName);
        }
    }


    private static void readDataEach(Context context, String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(context.getFilesDir(),fileName)));
            switch (fileName){
                case feeRegisterDataFileName:
                    ArrayList<FeeRegister> feeRegisterObject = gson.fromJson(br,feeRegisterType);
                    FeeRegisterAccessor.setInstance(feeRegisterObject);
                    break;
                case monthListDataFileName:
                    ArrayList<MonthYear> monthListObject = gson.fromJson(br,monthListDataType);
                    FeeRegisterAccessor.setMonthList(monthListObject);
                    break;
                case studentInfoDataFileName:
                    ArrayList<StudentInfo> studentInfoObject = gson.fromJson(br,studentInfoDataType);
                    StudentInfoAccessor.setInstance(studentInfoObject);
                    break;
            }

        } catch (Exception e) {
            Log.e("StorageAccessor:readDataEach", "Couldn't read stored data: "+ fileName +": "+ e.toString());
            e.printStackTrace();

        }
    }

    public static void writeData(Context context, String whichData){
        switch (whichData){
            case feeRegisterDataFileName:
                writeDataEach(context, feeRegisterDataFileName, FeeRegisterAccessor.getInstance(context));
                writeDataEach(context, monthListDataFileName, FeeRegisterAccessor.getMonthList(context));
                break;
            case studentInfoDataFileName:
                writeDataEach(context, studentInfoDataFileName, StudentInfoAccessor.getInstance(context));
                break;
            case allData:
                writeDataEach(context, feeRegisterDataFileName, FeeRegisterAccessor.getInstance(context));
                writeDataEach(context, monthListDataFileName, FeeRegisterAccessor.getMonthList(context));
                writeDataEach(context, studentInfoDataFileName, StudentInfoAccessor.getInstance(context));
        }
    }

    private static void writeDataEach(Context context, String fileName, Object object) {
        try {
            // need static fields. so exluding others
            String json = null;
            switch (fileName) {
                case feeRegisterDataFileName:
                    json = gson.toJson(object, feeRegisterType);
                    break;
                case monthListDataFileName:
                    json = gson.toJson(object, monthListDataType);
                    break;
                case studentInfoDataFileName:
                    json = gson.toJson(object, studentInfoDataType);
                    break;
            }
            FileWriter fileWriter = new FileWriter(new File(context.getFilesDir(),fileName));
            fileWriter.write(json);
            fileWriter.close();
        }
        catch (Exception e) {
            Log.e("StorageAccessor:rriteDataEach", "Couldn't write stored data: "+ fileName +": "+ e.toString());
            e.printStackTrace();
        }
    }
}
