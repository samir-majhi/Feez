package com.example.samir.feez;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayFeeRegisterActivity extends AppCompatActivity implements OnItemSelectedListener, OnClickListener{
    static int count = 1;
    private static final int paidCheckBoxBaseNumber = 1000000; // Int has a range of 2 billion or so
    private static final int paymentDateTextViewBaseNumber = 2000000;

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
        String[] monthList = FeeRegisterAccessor.getMonthListPretty(this);
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
        Resources res = getResources();

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        rowParams.rightMargin = DisplayHelper.dpToPixel(20, this);

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
        nameTitleTextView.setText("Name");
        nameTitleTextView.setTextSize(res.getDimension(R.dimen.regular_text_size));
        //nameTitleTextView.setTypeface(nameTitleTextView.getTypeface(), Typeface.BOLD);
        nameTitleTextView.setLayoutParams(rowParams);// TableRow is the parent view
        tableRow.addView(nameTitleTextView);

        TextView paymentDateTitleTextView = new TextView(context);
        paymentDateTitleTextView.setText("Payment Date");
        paymentDateTitleTextView.setTextSize(res.getDimension(R.dimen.regular_text_size));
        //paymentDateTitleTextView.setTypeface(paymentDateTitleTextView.getTypeface(), Typeface.BOLD);
        paymentDateTitleTextView.setLayoutParams(rowParams);// TableRow is the parent view
        tableRow.addView(paymentDateTitleTextView);

        TextView paidTitleTextView = new TextView(context);
        paidTitleTextView.setText("Paid");
        paidTitleTextView.setTextSize(res.getDimension(R.dimen.regular_text_size));
        //paidTitleTextView.setTypeface(paidTitleTextView.getTypeface(), Typeface.BOLD);
        paidTitleTextView.setLayoutParams(rowParams);// TableRow is the parent view
        tableRow.addView(paidTitleTextView);

        feeTable.addView(tableRow);

        // Name and Paid rows
        ArrayList<FeeRegister> feeRegister = FeeRegisterAccessor.getInstance(this);
        for (FeeRegister feeDetail : feeRegister){

            if (feeDetail.forMonthYear.equals(forMonthYear)) {

                tableRow = new TableRow(context);
                tableRow.setLayoutParams(tableParams);// TableLayout is the parent view

                TextView nameTextView = new TextView(context);
                nameTextView.setTextSize(res.getDimension(R.dimen.regular_text_size));
                String studentName = StudentInfoAccessor.getStudentNameByID(this, feeDetail.studentID);
                nameTextView.setText(studentName);
                nameTextView.setLayoutParams(rowParams);// TableRow is the parent view
                tableRow.addView(nameTextView);

                TextView paymentDateTextView = new TextView(context);
                paymentDateTextView.setTextSize(res.getDimension(R.dimen.regular_text_size));
                setTextViewDate(paymentDateTextView, feeDetail.paymentDate);
                paymentDateTextView.setId(paymentDateTextViewBaseNumber + feeDetail.feeID); // encoding info about kind of view as well as feeID into TextView
                paymentDateTextView.setLayoutParams(rowParams);// TableRow is the parent view
                tableRow.addView(paymentDateTextView);

                CheckBox paidCheckBox = new CheckBox(context);
                paidCheckBox.setTextSize(res.getDimension(R.dimen.regular_text_size));
                paidCheckBox.setLayoutParams(rowParams);// TableRow is the parent view
                paidCheckBox.setChecked(feeDetail.isPaid);
                paidCheckBox.setId(paidCheckBoxBaseNumber + feeDetail.feeID);
                paidCheckBox.setOnClickListener(this);
                tableRow.addView(paidCheckBox);

                feeTable.addView(tableRow);
            }
        }

    }

    public void onClick (View v){
        if (v instanceof CheckBox){
            int feeID = v.getId() - paidCheckBoxBaseNumber;
            boolean isChecked = ((CheckBox) v).isChecked();

            Date paymentDate = null;
            if (isChecked){
                paymentDate = new Date();
            }
            //update paymentDateTextView
            TextView paymentDateTextView = (TextView) findViewById(paymentDateTextViewBaseNumber + feeID);
            setTextViewDate(paymentDateTextView, paymentDate);

            FeeRegisterAccessor.updatePaymentStatus(this, feeID, isChecked, paymentDate);
        }
    }

    private void setTextViewDate(TextView tv, Date date){
        String paymentDate = "";
        if (date != null){
            String dateFormatToDisplay = "dd-MMM";
            paymentDate = new SimpleDateFormat(dateFormatToDisplay).format(date);
        }
        tv.setText(paymentDate);
    }
}

class DisplayHelper{
    private static Float scale;
    public static int dpToPixel(int dp, Context context) {
        if (scale == null)
            scale = context.getResources().getDisplayMetrics().density;
        return (int) ((float) dp * scale);
    }
}