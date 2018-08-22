package com.example.husseiny.car;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.husseiny.car.database.CarContract.CarEntity;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();
        Uri sUri = intent.getData();

        String[] projection = {CarEntity._ID, CarEntity.COLUMN_CAR_NAME, CarEntity.COLUMN_CAR_BRAND,
                CarEntity.COLUMN_CAR_PRICE, CarEntity.COLUMN_CAR_DESC};
        // Parsing query for the specific id
        // No need for putting selection & selectionArgs as they are handled in CarProvider class.
        Cursor cursor = getContentResolver().query(sUri, projection, null, null, null);

        TextView model = (TextView) findViewById(R.id.model_detail);
        TextView brand = (TextView) findViewById(R.id.model_detail);
        TextView price = (TextView) findViewById(R.id.model_detail);
        TextView info = (TextView) findViewById(R.id.model_detail);

        int modelClm = cursor.getColumnIndex(CarEntity.COLUMN_CAR_NAME);
        //cursor.moveToLast();
        String modelData = cursor.getString(modelClm);
        model.setText(": " + modelData);
    }
}
