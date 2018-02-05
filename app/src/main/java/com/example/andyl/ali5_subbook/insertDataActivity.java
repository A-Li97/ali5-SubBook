package com.example.andyl.ali5_subbook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Andy Li
 * @version 1
 * @see MainActivity
 */

public class insertDataActivity extends AppCompatActivity {

    private EditText subscription;
    private EditText cost;
    private EditText editDate;
    private EditText comment;

    private DatePickerDialog.OnDateSetListener date;
    String dateFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.CANADA);
    Calendar calendar = Calendar.getInstance();

    /**
     * Initialized when app is first started.
     * <p>
     * This method saves the state of the application, so that if it ever needs to be recreated,
     * prior information isn't lost.
     *
     * @param savedInstanceState info
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        Bundle extras = getIntent().getExtras();

        subscription = (EditText) findViewById(R.id.editText);
        cost = (EditText) findViewById(R.id.editText2);
        editDate = (EditText) findViewById(R.id.editText3);
        comment = (EditText) findViewById(R.id.editText4);

        // Checks if values were passed in for editing a subscription, if so, set the editTexts to them
        if(getIntent().hasExtra("subscription"))
            subscription.setText(extras.getString("subscription"));
        if(getIntent().hasExtra("cost"))
            cost.setText(extras.getString("cost"));
        if(getIntent().hasExtra("date"))
            editDate.setText(extras.getString("date"));
        if(getIntent().hasExtra("comment"))
            comment.setText(extras.getString("comment"));

        // Taken from https://stackoverflow.com/questions/11535011/edittext-field-is-required-before-moving-on-to-another-activity
        // 2018-01-29

        if (TextUtils.isEmpty(subscription.getText())) {
            subscription.setError("Subscription name is required!");
        }
        if (TextUtils.isEmpty(cost.getText())) {
            cost.setError("Monthly cost required!");
        }

        if(TextUtils.isEmpty(editDate.getText())) {
            editDate.setError("Date is required!");
        }

        // Taken from http://www.moo-code.me/en/2017/04/16/how-to-popup-datepicker-calendar/
        // 2018-02-02

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(insertDataActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    /**
     * This method sets the chosen date from the date picker onto the editText object.
     */

    private void updateDate() {
        editDate.setText(sdf.format(calendar.getTime()));
        editDate.setError(null);
    }

    /**
     * This method takes the values entered by the user and sends it back to the main activity.
     *
     * @param view  the current view
     * @see         MainActivity
     */

    public void insertSubscription(View view) {
        String name = subscription.getText().toString();
        String day = editDate.getText().toString();
        String description = comment.getText().toString();
        double price = 0;

        if(TextUtils.isEmpty(cost.getText())) {
            cost.setError("Fill in cost!");
        }

        else{
            price = Double.parseDouble(cost.getText().toString());
        }

        if (TextUtils.isEmpty(subscription.getText()) || TextUtils.isEmpty(editDate.getText()) || TextUtils.isEmpty(cost.getText())) {
            Toast.makeText(this,"Not all boxes filled in!",Toast.LENGTH_LONG).show();
        }

        else {
            if (subscription.length() > 20) {
                subscription.setError("Name over 20 characters long!");
            } else if (comment.length() > 30) {
                comment.setError("Comment over 30 characters long!");
            } else {
                Intent intent = new Intent();
                intent.putExtra("subscription", name);
                intent.putExtra("cost", price);
                intent.putExtra("date", day);
                intent.putExtra("comment", description);
                setResult(RESULT_OK, intent);
                finish();

            }
        }
    }
}
