package rosieblair.donationtracker.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rosieblair.donationtracker.R;

/**
 * Class to allow functionality of employee app screen
 */
public class EmployeeAppScreen extends AppCompatActivity {

    public static int locKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_app_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getIntExtra("locKey",0) != 0) {
            locKey = getIntent().getIntExtra("locKey",0);
        }
        pressSearch();
        pressAddDonation();
        pressViewLocations();
        pressViewMyLocationInventory();
        pressViewMap();
    }

    private void pressViewMap() {
        Button viewMapButton = findViewById(R.id.map1);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void pressSearch() {
        Button searchButton = findViewById(R.id.searchButtonE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchScreen.class);
                startActivity(intent);
            }
        });
    }

    private void pressAddDonation() {
        Button addDonationButton = findViewById(R.id.addItemButton);
        addDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemScreen.class);
                intent.putExtra("locKey", locKey);
                startActivity(intent);
            }
        });

    }

    private void pressViewLocations() {
        Button locationListButton = findViewById(R.id.locListButtonE);
        locationListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListOfLocations.class);
                startActivity(intent);
            }
        });

    }

    private void pressViewMyLocationInventory() {
        Button locationInventoryButton = findViewById(R.id.viewInventoryButton);
        locationInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListOfItems.class);
//                Log.d("Intent locKey", "" + getIntent().getIntExtra("locKey",0));
                intent.putExtra("locKey", locKey);
//                intent.putExtra(ListOfItems.LOCATION, locName);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to redirect user to main app screen once logout
     * button is pressed
     * @param view the view
     */
    public void onLogOutPressed(View view) {
        Log.d("Edit", "logged out");
        Intent intent = new Intent(EmployeeAppScreen.this, MainActivity.class);
        startActivity(intent);
        //pop-up message notifying user that logout was successful
        Toast toast = Toast.makeText(getBaseContext(), "Logout successful!",
                Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        //setting the color of notification's background bubble
        toastView.getBackground().setColorFilter(Color.parseColor("#daeff1"),
                PorterDuff.Mode.SRC);
        toast.show();
    }

    public static final int NAME_POSITION = 1;
    public static final int LATITUDE_POSITION = 2;
    public static final int LONGITUDE_POSITION = 3;
    public static final int STREET_ADDRESS_POSITION = 4;
    public static final int CITY_POSITION = 5;
    public static final int STATE_POSITION = 6;
    public static final int ZIP_CODE_POSITION = 7;
    public static final int TYPE_POSITION = 8;
    public static final int PHONE_NUMBER_POSITION = 9;
    public static final int WEBSITE_POSITION = 10;

}
