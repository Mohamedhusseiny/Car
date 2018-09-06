package com.example.husseiny.car;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.husseiny.car.database.CarContract.CarEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class EditActivity extends AppCompatActivity {

    String selection;

    EditText model;
    Spinner brand;
    EditText price;
    EditText info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // connect views to java
        model = findViewById(R.id.model_id);
        price =  findViewById(R.id.price_id);
        brand =  findViewById(R.id.brand_spin);
        info =  findViewById(R.id.info_id);

        setUpSpinner();
    }

    private void setUpSpinner() {
        // TODO: edit spinner internal style
        Spinner spinner = (Spinner) findViewById(R.id.brand_spin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.brands, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = (String) parent.getItemAtPosition(position);
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selection = "Unknown";
            }
        });

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_option:
                insertCar();
            return true;
        }
        return true;
    }

    private void insertCar() {
        ContentValues values = new ContentValues();

        values.put(CarEntity.COLUMN_CAR_BRAND, selection);

        // Check if the model view is not empty
        if (!TextUtils.isEmpty(model.getText())) {
            values.put(CarEntity.COLUMN_CAR_NAME, model.getText().toString());
        }

        // Check if the price view is not empty
        if (!TextUtils.isEmpty(price.getText())) {
            values.put(CarEntity.COLUMN_CAR_PRICE, price.getText().toString());
        }

        // Check if the model view is not empty
        if (!TextUtils.isEmpty(model.getText())) {
            values.put(CarEntity.COLUMN_CAR_DESC, info.getText().toString());
        }

        // Add current date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        values.put(CarEntity.COLUMN_CAR_DATE, mDateFormat.format(date));

        Uri sUri =  getContentResolver().insert(CarEntity.CONTENT_URI, values);
        if(sUri != null){
            Toast.makeText(this, "A car is added scucessfully!", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(this, MainActivity.class);
            startActivity(back);
        }
        else {
            model.setText("");
            brand.setSelection(0);
            price.setText("");
            info.setText("");
            Toast.makeText(this, "Please, insert valid data!", Toast.LENGTH_SHORT).show();
        }


    }
}
