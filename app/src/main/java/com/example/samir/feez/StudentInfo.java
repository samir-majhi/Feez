package com.example.samir.feez;
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

      static StudentInfo[] instance;

      StudentInfo(String name, Date joiningDate, String phoneNumber, String batch){
            lastGeneratedID++;
            ID = lastGeneratedID;
            this.name = name;
            this.joiningDate = joiningDate;
            this.phoneNumber = phoneNumber;
            this.batch = batch;
      }

      static StudentInfo[] getInstance(){
            //TEMP stub for dummy data creation
            if (instance == null) {
                createDummyData();
            }
            return instance;
      }

      static void createDummyData(){
            instance = new StudentInfo[]{ new StudentInfo("Amit",new Date(), "1234567891","1"),
                         new StudentInfo("Bindra",new Date(), "1234567892","2"),
                         new StudentInfo("Cassandra",new Date(), "1234567893","3")};
      }

      // TODO: Implement this class as a hash table for faster getting of name from id.
      // Might not be required since we currently don't expect student name to be huge.
      // Given that our customers are solopreneurs
      static String getStudentNameByID (long ID){
          StudentInfo[] tempInstance = getInstance();
          for (StudentInfo student : tempInstance){
              if (student.ID == ID) {
                  return student.name;
              }
          }
          return null;
      }
}
