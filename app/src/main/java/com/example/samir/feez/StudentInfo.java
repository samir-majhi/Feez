package com.example.samir.feez;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by samir on 9/3/18.
 */

public class StudentInfo {
      static long lastGeneratedID = 0;
      long ID;
      String name;
      Date joiningDate;
      String phoneNumber;
      String batch;

      private static ArrayList<StudentInfo> instance = new ArrayList<StudentInfo>();

      StudentInfo(String name, Date joiningDate, String phoneNumber, String batch){
            lastGeneratedID++;
            ID = lastGeneratedID;
            this.name = name;
            this.joiningDate = joiningDate;
            this.phoneNumber = phoneNumber;
            this.batch = batch;
      }

      static ArrayList<StudentInfo> getInstance(){
            //TEMP stub for dummy data creation
            if (instance.isEmpty()) {
                createDummyData();
            }
            return instance;
      }

      static void createDummyData(){
          Date date = new Date();
          instance.add(new StudentInfo("Amit", date, "1234567891","1"));
          instance.add(new StudentInfo("Bindra", date, "1234567892","2"));
          instance.add(new StudentInfo("Cassandra", date, "1234567893","3"));
      }

      // TODO: Implement this class as a hash table for faster getting of name from id.
      // Might not be required since we currently don't expect student name to be huge.
      // Given that our customers are solopreneurs

      static String getStudentNameByID (long ID){
          ArrayList<StudentInfo> tempInstance = getInstance();
          for (StudentInfo student : tempInstance){
              if (student.ID == ID) {
                  return student.name;
              }
          }
          return null;
      }

      static void addStudent(String name, Date joiningDate, String phoneNumber, String batch){
          StudentInfo student = new StudentInfo(name, joiningDate, phoneNumber, batch);
          instance.add(student);
      }
}
