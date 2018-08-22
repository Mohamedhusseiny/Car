package com.example.husseiny.car.database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by husseiny on 7/30/2018.
 */

public class CarContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.car";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class CarEntity implements BaseColumns{

        // Set Table Name!
        public static final String TABLE_NAME = "car";

        // Set Table Attributes!
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CAR_NAME = "name";
        public static final String COLUMN_CAR_BRAND = "brand";
        public static final String COLUMN_CAR_PRICE = "price";
        public static final String COLUMN_CAR_DESC = "description";
        public static final String COLUMN_CAR_DATE = "date";

        // Car Provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);


    }
}
