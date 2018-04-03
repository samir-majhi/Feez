package com.example.samir.feez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayFeeRegisterActivity extends AppCompatActivity implements OnItemSelectedListener{
    static int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Log.d("DisplayFeeRegisterActivity","onResume call number" + count);
        count++; */
        setContentView(R.layout.activity_display_fee_register);
        //Intent intent = getIntent();

        // Month filter Drop down
        Spinner monthFilter = (Spinner) findViewById(R.id.monthFilter);
        String[] monthList = FeeRegister.getMonthList();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, monthList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthFilter.setAdapter(adapter);
        monthFilter.setOnItemSelectedListener(this);

        //resetFeeTable(new MonthYear(new Date())); not needed because the month filter will automatically select the first item
    }

    public void openAddStudentActivity (View view){
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        Log.d(this.toString(),"onItemSelected called");
        String monthYearSelectedString = (String) parent.getItemAtPosition(pos);
        MonthYear monthYearSelected = new MonthYear(monthYearSelectedString);
        resetFeeTable(monthYearSelected);
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    public void resetFeeTable(MonthYear forMonthYear){

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableLayout feeTable = findViewById(R.id.FeeTable);
        TableLayout feeTableLayout = (TableLayout) feeTable;
        feeTableLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// assuming the parent view is a ConstraintLayout

        Context context = DisplayFeeRegisterActivity.this;

        //clear all rows except the first row (title row of Name and Paid)
        //int tableChildCount = feeTable.getChildCount();
        //for(int i=1;i<tableChildCount;i++) {
        //    feeTable.removeView(feeTable.getChildAt(i));
        //}
        feeTable.removeAllViewsInLayout();

        // Display fee register
        // Title of table: Name and Paid
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);// TableLayout is the parent view

        TextView nameTitleTextView = new TextView(context);
        nameTitleTextView.setTextSize(14);
        nameTitleTextView.setText("Name");
        nameTitleTextView.setLayoutParams(rowParams);// TableRow is the parent view
        tableRow.addView(nameTitleTextView);

        TextView paidTitleTextView = new TextView(context);
        paidTitleTextView.setText("Paid");
        paidTitleTextView.setTextSize(14);
        paidTitleTextView.setLayoutParams(rowParams);// TableRow is the parent view
        tableRow.addView(paidTitleTextView);

        feeTable.addView(tableRow);

        // Name and Paid rows
        ArrayList<FeeRegister> feeRegister = FeeRegister.getInstance();
        for (FeeRegister feeDetail : feeRegister){

            if (feeDetail.forMonthYear.equals(forMonthYear)) {

                tableRow = new TableRow(context);
                tableRow.setLayoutParams(tableParams);// TableLayout is the parent view

                TextView nameTextView = new TextView(context);
                nameTextView.setTextSize(14);
                String studentName = StudentInfo.getStudentNameByID(feeDetail.studentID);
                nameTextView.setText(studentName);
                nameTextView.setLayoutParams(rowParams);// TableRow is the parent view
                tableRow.addView(nameTextView);

                CheckBox paidCheckBox = new CheckBox(context);
                paidCheckBox.setTextSize(14);
                paidCheckBox.setLayoutParams(rowParams);// TableRow is the parent view
                paidCheckBox.setChecked(feeDetail.isPaid);
                tableRow.addView(paidCheckBox);

                feeTable.addView(tableRow);
            }
        }

    }

    // TODO: Implement Checkbox click behaviour
}

