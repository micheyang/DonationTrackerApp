package rosieblair.donationtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import rosieblair.donationtracker.R;
import rosieblair.donationtracker.model.Item;
import rosieblair.donationtracker.model.Location;

import static rosieblair.donationtracker.activities.EmployeeAppScreen.CITY_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.LATITUDE_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.LONGITUDE_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.NAME_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.PHONE_NUMBER_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.STATE_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.STREET_ADDRESS_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.TYPE_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.WEBSITE_POSITION;
import static rosieblair.donationtracker.activities.EmployeeAppScreen.ZIP_CODE_POSITION;

import rosieblair.donationtracker.database.ItemDBHelper;
import rosieblair.donationtracker.database.LocationDBHelper;
import rosieblair.donationtracker.model.Item;

/**
 * Class to allow functionality of adding items
 */
public class AddItemScreen extends AppCompatActivity {

//    private final AppCompatActivity activity = AddItemScreen.this;

    private EditText time;
    private Spinner location;
    private EditText shortDescription;
    private EditText fullDescription;
    private EditText value;
    private Spinner category;
    private TextView invalid_Location;
    private Button addButton;
    private Button cancelButton;
    private ArrayList<Location> temp;

    private Location loc;
    private Item newItem;
    private ItemDBHelper itemhelper;
    private LocationDBHelper lochelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add();
        cancel();
    }

    /**
     * Method used to add item into inventory
     */
    public void add() {

        lochelper = new LocationDBHelper(AddItemScreen.this);
        itemhelper = new ItemDBHelper(AddItemScreen.this);
        time = (EditText) findViewById(R.id.enterDonateDate);
        location = (Spinner) findViewById(R.id.selectLocation);
        shortDescription= (EditText) findViewById(R.id.enterShortDescr);
        fullDescription = (EditText) findViewById(R.id.enterFullDescr);
        value = (EditText) findViewById(R.id.enterItemValue);
        category = (Spinner) findViewById(R.id.selectItemCategory);


        invalid_Location = (TextView) findViewById(R.id.invalidLocation);

        List<Location> locList = lochelper.locationList();
        String[] spinEntries = new String[locList.size()];
        for (int i = 0; i < locList.size(); i++) {
            spinEntries[i] = locList.get(i).getName();
        }

        ArrayAdapter<String> spinAdapt = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinEntries);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(spinAdapt);


        addButton = (Button) findViewById(R.id.addItemButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String locName = location.getSelectedItem().toString();
                loc = lochelper.getLocationByName(locName);
                int locKey = loc.getKey();
                newItem = new Item();
                newItem.setTime(time.getText().toString());
                newItem.setShortDescription(shortDescription.getText().toString());
                newItem.setFullDescription(fullDescription.getText().toString());
                newItem.setValue(value.getText().toString());
                newItem.setCategory(category.getSelectedItem().toString());
                newItem.setItemKey(locKey);
                itemhelper.addItem(newItem);

                Intent intent = new Intent(getApplicationContext(), EmployeeAppScreen.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Method to cancel addition of item
     */
    public void cancel() {
        Log.d("Cancel", "cancel register");
        cancelButton = (Button) findViewById(R.id.cancelItemButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployeeAppScreen.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Method used to find location by key
     * @param key key used to find location
     * @return the requested location to find
     */
    public Location findLocationByKey(int key) {
        for (Location d : temp) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find key: " + key);
        return null;
    }

}

