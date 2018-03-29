package com.example.samir.feez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayFeeRegisterActivity extends AppCompatActivity {
    static int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.d("DisplayFeeRegisterActivity","onResume call number" + count);
        count++;
        super.onStart();
        setContentView(R.layout.activity_display_fee_register);
        Intent intent = getIntent();

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableLayout feeTable = findViewById(R.id.FeeTable);
        TableLayout feeTableLayout = (TableLayout) feeTable;
        feeTableLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// assuming the parent view is a ConstraintLayout

        Context context = DisplayFeeRegisterActivity.this;

        ArrayList<FeeRegister> feeRegister = FeeRegister.getInstance();
        for (FeeRegister feeDetail : feeRegister){

            TableRow tableRow = new TableRow(context);
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
    public void openAddStudentActivity (View view){
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }
    // TODO: Implement Checkbox click behaviour
}
