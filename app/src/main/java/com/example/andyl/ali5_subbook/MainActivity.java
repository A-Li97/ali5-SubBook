package com.example.andyl.ali5_subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import static android.view.Menu.NONE;

/**
 * @author Andy Li
 * @version 1
 * @see insertDataActivity
 * @see Subscription
 */

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subscriptions.sav";

    private ArrayList<Subscription> subscriptionList; // List that holds subscriptions
    private ListView oldSubscriptionList;
    private ArrayAdapter<Subscription> adapter;

    private int index;

    /**
     * Initialized when app is first started.
     * <p>
     * This method saves the state of the application, so that if it ever needs to be recreated,
     * prior information isn't lost.
     *
     * @param savedInstanceState info
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oldSubscriptionList = (ListView) findViewById(R.id.oldSubscriptionList);

        registerForContextMenu(oldSubscriptionList);

    }

    /**
     * This method prints the total cost of all subscriptions currently added,
     * if there is no subscription it prints out 0.
     */

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

    /**
     * This method differs from onCreate since it is called every instance the activity is visible to the user,
     * this is different then onCreate since in onCreate it is only called when the process is started.
     */

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();

        loadFromFile();
        calculateCost();

        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);
        oldSubscriptionList.setAdapter(adapter);
    }

    /**
     * This method creates a context menu with the options, add, delete,
     * and comment for each item in a listview.
     *
     * @param menu          menu being buiit
     * @param view          view to print context menu on
     * @param menuInfo      extra info about the item
     */


    // Taken from https://stackoverflow.com/questions/18632331/using-contextmenu-with-listview-in-android
    // 2018-02-01

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        if (view.getId() == R.id.oldSubscriptionList) {
            menu.add(NONE,R.id.edit,0,"edit");
            menu.add(NONE,R.id.delete,0,"delete");
            menu.add(NONE,R.id.more_info,0,"comment");
        }
    }

    /**
     * This methods determines which option of the context menu for the given item clicked on,
     * which include editting, the comment (if it has one) and the option to delete the item
     *
     * @param item  item on listview that was clicked on
     * @return      boolean
     * @see         insertDataActivity
     */

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        super.onContextItemSelected(item);

        int choice;

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        index = info.position;
        choice = item.getItemId();                                      // Selected option

        switch (choice) {
            case R.id.edit:                                             // Deals with editing of a subscription
                final String tempDouble;
                tempDouble = String.valueOf(subscriptionList.get(index).getCost());

                Intent intent = new Intent(this, insertDataActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("subscription", subscriptionList.get(index).getSubscription());
                bundle.putString("date", subscriptionList.get(index).getDate());
                bundle.putString("comment", subscriptionList.get(index).getComment());
                bundle.putString("cost", tempDouble);

                intent.putExtras(bundle);

                startActivityForResult(intent, 2);

                return true;

            case R.id.delete:                                           // Deletes subscription from list
                subscriptionList.remove(subscriptionList.get(index));
                calculateCost();

                adapter.notifyDataSetChanged();

                saveInFile();

                return true;

            case R.id.more_info:                                        // Shows comment
                AlertDialog showComment = new AlertDialog.Builder(this).create();

                showComment.setTitle(adapter.getItem(index).getSubscription());
                showComment.setMessage(adapter.getItem(index).getComment());

                showComment.show();

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * This method goes to a new activity to deal with adding a new subscription
     *
     * @param view starts it from the current view
     * @see   insertDataActivity
     */

    // When called, switch to another screen to deal with addition of new subscription
    public void addNewSubscription(View view){
        Intent intent = new Intent(this, insertDataActivity.class);
        startActivityForResult(intent,1);
    }

    /**
     * This method retrieves info from insertDataActivity and adds a new subscription to the arrayList.
     * It then notifies the adapter and saves it to file.
     *
     * @param requestCode   determines which intent it was from
     * @param resultCode    result code from child
     * @param data          data retrieved from other activity
     * @see                 Subscription
     */

    // Taken from https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android/14292451
    // 2018-01-31

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String subscription;
        final double cost;
        final String date;
        final String comment;

        if (requestCode == 1) {                                         // Deals with addition of a subscription
            if (resultCode == RESULT_OK) {
                subscription = data.getStringExtra("subscription");
                cost = data.getDoubleExtra("cost", 0);
                comment = data.getStringExtra("comment");
                date = data.getStringExtra("date");

                Subscription newSubscription = new Subscription(subscription, date, cost, comment);
                subscriptionList.add(newSubscription);

                adapter.notifyDataSetChanged();

                saveInFile();
            }
        } else if (requestCode == 2) {                                  // Deals with editing of a subscription
            if (resultCode == RESULT_OK) {
                subscription = data.getStringExtra("subscription");
                cost = data.getDoubleExtra("cost", 0);
                comment = data.getStringExtra("comment");
                date = data.getStringExtra("date");

                Subscription newSubscription = new Subscription(subscription, date, cost, comment);

                subscriptionList.set(index, newSubscription);

                adapter.notifyDataSetChanged();

                saveInFile();
            }
        }
    }

    /**
     * This method loads the subscriptions previously saved (if any) from file into and arrayList.
     */

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

    /**
     * This method saves the currently added subscriptions in the arrayList (if any) to file.
     */

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
