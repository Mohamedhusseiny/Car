package com.example.husseiny.car.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by husseiny on 7/30/2018.
 */

public class CarContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.car";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CAR = "car";

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
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CAR);

        // the MIME type of the CONTENT_URI for a list of pets
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_CAR;

        // the MIME type of the CONTENT_URI for a single pet
        public static final String CONTENT_ITEM_TYPE = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_CAR;


    }
}
