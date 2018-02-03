package com.example.andyl.ali5_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.view.Menu.NONE;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subscriptions.sav";

    private ArrayList<Subscription> subscriptionList; // List that holds subscriptions
    private ListView oldSubscriptionList;
    private ArrayAdapter<Subscription> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oldSubscriptionList = (ListView) findViewById(R.id.oldSubscriptionList);

        registerForContextMenu(oldSubscriptionList);

    }

    public void calculateCost() {
        double temp = 0;
        double totalCost;

        for(int i = 0; i < subscriptionList.size(); i++) {
            temp += subscriptionList.get(i).getCost();
        }

        totalCost = Math.round(temp*100.0)/100.0;

        TextView textView = findViewById(R.id.textView);
        textView.setText(String.valueOf("$ " + totalCost));
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();

        loadFromFile();
        calculateCost();

        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);
        oldSubscriptionList.setAdapter(adapter);
    }

    // Taken from https://stackoverflow.com/questions/18632331/using-contextmenu-with-listview-in-android
    // 2018-02-01

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        if (view.getId() == R.id.oldSubscriptionList) {

            //ListView lv = (ListView) view;
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //addSubscription obj = (addSubscription) lv.getItemAtPosition(info.position);

            menu.add("edit");
            menu.add("delete");
            menu.add("more info");
        }
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        super.onContextItemSelected(item);

        int index;
        int choice;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        index = info.position;
        choice = item.getItemId();           // Selected option

        switch (choice) {
            case R.id.edit:
                return true;

            case R.id.delete:
                adapter.remove(adapter.getItem(index));
                subscriptionList.remove(subscriptionList.get(index));

                Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_LONG).show();

                adapter.notifyDataSetChanged();

                saveInFile();

                return true;

            case R.id.more_info:
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    // When called, switch to another screen to deal with addition of new subscription
    public void addNewSubscription(View view){
        Intent intent = new Intent(this, insertDataActivity.class);
        startActivityForResult(intent,1);
    }

    // Taken from https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android/14292451
    // 2018-01-31

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        final String subscription;
        final double cost;
        final String date;
        final String comment;

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                subscription = data.getStringExtra("subscription");
                cost = data.getDoubleExtra("cost", 0);
                comment = data.getStringExtra("comment");
                date = data.getStringExtra("date");

                Subscription newSubscription = new Subscription(subscription, date, cost, comment);
                subscriptionList.add(newSubscription);

                adapter.notifyDataSetChanged();

                saveInFile();
            }
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<Subscription>();
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
}
