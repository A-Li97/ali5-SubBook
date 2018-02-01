package com.example.andyl.ali5_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class insertDataActivity extends AppCompatActivity {

    private EditText subscription;
    private EditText cost;
    //private EditText date;
    private EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

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
        if(TextUtils.isEmpty(date.getText())){
            date.setError("Date is required!");
        }
        */
    }

    public void insertSubscription(View view){
            String name = subscription.getText().toString();
            Integer price = Integer.parseInt(cost.getText().toString());
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
