package com.example.samir.feez;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class StudentInfo {
      static int lastGeneratedID = 0;
      int ID;
      String name;
      Date joiningDate;
      String phoneNumber;
      String batch;

      StudentInfo(String name, Date joiningDate, String phoneNumber, String batch){
            lastGeneratedID++;
            ID = lastGeneratedID;
            this.name = name;
            this.joiningDate = joiningDate;
            this.phoneNumber = phoneNumber;
            this.batch = batch;
      }
}
