package com.example.samir.feez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.content.Intent;
import android.widget.TableRow;
import android.support.constraint.ConstraintLayout ;
import android.content.Context;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Learning Android. Show text from MainActivity
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        TableLayout feeTable = findViewById(R.id.FeeTable);
        TableLayout feeTableLayout = (TableLayout) feeTable;
        feeTableLayout.setLayoutParams(new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));// assuming the parent view is a ConstraintLayout

        Context context = DisplayMessageActivity.this;

        FeeRegister feeRegister[] = FeeRegister.getInstance();
        for (FeeRegister feeDetail : feeRegister){

            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(tableParams);// TableLayout is the parent view

            TextView nameTextView = new TextView(context);
            String studentName = StudentInfo.getStudentNameByID(feeDetail.studentID);
            nameTextView.setText(studentName);
            nameTextView.setLayoutParams(rowParams);// TableRow is the parent view
            tableRow.addView(nameTextView);

            CheckBox paidCheckBox = new CheckBox(context);
            paidCheckBox.setLayoutParams(rowParams);// TableRow is the parent view
            paidCheckBox.setChecked(feeDetail.isPaid);
            tableRow.addView(paidCheckBox);

            feeTable.addView(tableRow);
        }

    }

    // TODO: Implement Checkbox click activity
}
