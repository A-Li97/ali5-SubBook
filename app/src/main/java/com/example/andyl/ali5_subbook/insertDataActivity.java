package com.example.andyl.ali5_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class insertDataActivity extends AppCompatActivity {

    private EditText subscription;
    private EditText cost;
    //private EditText date;
    private EditText comment;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        button = (ImageButton) findViewById(R.id.imageButton);

        subscription = (EditText) findViewById(R.id.editText);
        cost = (EditText) findViewById(R.id.editText2);
        //date = (EditText) findViewById(R.id.editText3);
        comment = (EditText) findViewById(R.id.editText4);

        // Taken from https://stackoverflow.com/questions/11535011/edittext-field-is-required-before-moving-on-to-another-activity
        // 2018-01-29

        if (TextUtils.isEmpty(subscription.getText())) {
            subscription.setError("Subscription name is required!");
        }
        if (TextUtils.isEmpty(cost.getText())) {
            cost.setError("Cost is required!");
        }
 /*
        if(TextUtils.isEmpty(date.getText())) {
            date.setError("Date is required!");
        }
*/
        button.setEnabled(false);

        subscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Auto generated method
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(subscription.getText())) {
                    button.setEnabled(false);
                } else
                    button.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Auto generated method
            }
        });

        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Auto generated method
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(cost.getText())) {
                    button.setEnabled(false);
                } else
                    button.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Auto generated method
            }
        });
/*
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Auto generated method
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(date.getText())) {
                    button.setEnabled(false);
                }
                else
                    button.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Auto generated method
            }
        });
*/
    }

    public void insertSubscription(View view){
            String name = subscription.getText().toString();
            Float price = Float.parseFloat(cost.getText().toString());
            // Add Date

            if(TextUtils.isEmpty(comment.getText())){

                Intent intent = new Intent();
                intent.putExtra("subscription", "name");
                intent.putExtra("cost", "price");
                setResult(RESULT_OK, intent);
                finish();

            }

            else{

                String description = comment.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("subscription", "name");
                intent.putExtra("cost", "price");
                intent.putExtra("comment","description");
                setResult(RESULT_OK, intent);
                finish();

            }
        }
}
