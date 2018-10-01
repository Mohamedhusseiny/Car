package com.example.husseiny.car;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.husseiny.car.database.CarContract;

/**
 * Created by husseiny on 7/31/2018.
 */

public class CarCursorAdapter extends CursorAdapter {
    public CarCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView carModel = view.findViewById(R.id.model_content);
        TextView carBrand = view.findViewById(R.id.brand_content);
        TextView carPrice = view.findViewById(R.id.price_content);
        TextView carDesc = view.findViewById(R.id.info_content);
        TextView carDate = view.findViewById(R.id.date_item_id);


        String model = cursor.getString(cursor.getColumnIndex(CarContract.CarEntity.COLUMN_CAR_NAME));
        String brand = cursor.getString(cursor.getColumnIndex(CarContract.CarEntity.COLUMN_CAR_BRAND));
        int price = cursor.getInt(cursor.getColumnIndex(CarContract.CarEntity.COLUMN_CAR_PRICE));
        String desc = cursor.getString(cursor.getColumnIndex(CarContract.CarEntity.COLUMN_CAR_DESC));
        String date = cursor.getString(cursor.getColumnIndex(CarContract.CarEntity.COLUMN_CAR_DATE));

        carModel.setText(model);
        carBrand.setText(brand);
        carPrice.setText(String.valueOf(price) + "$");
        carDesc.setText(desc);
        carDate.setText(date);
    }
}
