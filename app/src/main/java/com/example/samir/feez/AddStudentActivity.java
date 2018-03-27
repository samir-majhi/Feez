package com.example.samir.feez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Color.RED;

public class AddStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
    }

    public void addStudentCancel (View view){
        finish();
    }

    public void addStudentSave (View view){
        EditText StudentNameEntry = (EditText) findViewById(R.id.StudentNameEntry);
        String name = StudentNameEntry.getText().toString();

        EditText JoiningDateEntry = (EditText) findViewById(R.id.JoiningDateEntry);
        String joiningDateString = JoiningDateEntry.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        Date joiningDate;
        try {
            joiningDate = sdf.parse(joiningDateString);
        }
        catch (ParseException e){
            //invalid date format
            showInvalidDateFormatMessage();
            return;
        }

        EditText PhoneNumberEntry = (EditText) findViewById(R.id.PhoneNumberEntry);
        String phoneNumber = PhoneNumberEntry.getText().toString();

        EditText BatchEntry = (EditText) findViewById(R.id.BatchEntry);
        String batch = BatchEntry.getText().toString();

        StudentInfo.addStudent(name, joiningDate, phoneNumber, batch);
    }

    private void showInvalidDateFormatMessage(){
        EditText JoiningDateEntry = (EditText) findViewById(R.id.JoiningDateEntry);
        String joiningDateString = JoiningDateEntry.getText().toString();
        int currentColor = JoiningDateEntry.getCurrentTextColor();
        //show error message for sometime
        JoiningDateEntry.setTextColor(RED);
        JoiningDateEntry.setText("Invalid Date Format. Please use dd-MM-YYYY");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //restore date entered
        JoiningDateEntry.setTextColor(currentColor);
        JoiningDateEntry.setText(joiningDateString);
    }
    // TODO: Input validation
}
