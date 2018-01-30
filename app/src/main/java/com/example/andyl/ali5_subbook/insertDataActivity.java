package com.example.andyl.ali5_subbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class insertDataActivity extends AppCompatActivity {

    private static final String FILENAME = "subscriptions.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
    }

    // Adds subscription
    public void addSubscription(View view){
        EditText subscription = (EditText) findViewById(R.id.editText);
        EditText cost = (EditText) findViewById(R.id.editText2);
        EditText date = (EditText) findViewById(R.id.editText3);
        EditText comment = (EditText) findViewById(R.id.editText4);

        // Taken from https://stackoverflow.com/questions/11535011/edittext-field-is-required-before-moving-on-to-another-activity
        // 2018-01-29

        if(TextUtils.isEmpty(subscription.getText())){
            subscription.setError("Subscription name is required!");
        }
        else if(TextUtils.isEmpty(cost.getText())){
            cost.setError("Cost is required!");
        }

        else if(TextUtils.isEmpty(date.getText())){
            date.setError("Date is required!");
        }

        else{

        }
    }
}
