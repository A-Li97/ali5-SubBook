package com.example.andyl.ali5_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subscriptions.sav";

    private String subscription;
    private Float cost;
    //private EditText date;
    private String comment;
    private Float totalCost;

    private ArrayList<addSubscription> subscriptionList; // List that holds subscriptions
    private ListView oldSubscriptionList;
    private ArrayAdapter<addSubscription> adapter;
    //private int ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        oldSubscriptionList = (ListView) findViewById(R.id.oldSubscriptionList);

        registerForContextMenu(oldSubscriptionList);

        totalCost = (float) 0;

        TextView textView = findViewById(R.id.textView);
        textView.setText(totalCost.toString());
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();

        loadFromFile();

        adapter = new ArrayAdapter<addSubscription>(this, R.layout.list_item, subscriptionList);
        oldSubscriptionList.setAdapter(adapter);
    }

    // Taken from https://stackoverflow.com/questions/18632331/using-contextmenu-with-listview-in-android
    // 2018-02-01

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if (view.getId() == R.id.oldSubscriptionList) {

            ListView lv = (ListView) view;
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            addSubscription obj = (addSubscription) lv.getItemAtPosition(info.position);

            menu.add("edit");
            menu.add("delete");

        }
    }

    public boolean onContextItemSelected(MenuItem item) {

        int index;
        int choice;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        index = info.position;
        choice = item.getItemId();           // Selected option

        if(choice == R.id.edit){
            return true;
        }

        else if(choice == R.id.delete){
            return false;
        }

        return true;
    }


    // When called, switch to another screen to deal with addition of new subscription
    public void addNewSubscription(View view){
        // Do something in response to button
        Intent intent = new Intent(this, insertDataActivity.class);
        startActivityForResult(intent,1);
    }

    // Taken from https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android/14292451
    // 2018-01-31

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                subscription = data.getStringExtra("subscription");
                cost = data.getFloatExtra("cost", 0);
                comment = data.getStringExtra("comment");

                addSubscription newSubscription = new addSubscription(subscription,cost);
                subscriptionList.add(newSubscription);

                /*
                if(!subscriptionList.isEmpty()){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                }
                */
                adapter.notifyDataSetChanged();

                saveInFile();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

      private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25

            Type listType = new TypeToken<ArrayList<addSubscription>>(){}.getType();
            subscriptionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<addSubscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    private void saveInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subscriptionList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // Add long click hold menu for edit and delete
}
