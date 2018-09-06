package com.example.husseiny.car.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.husseiny.car.database.CarContract.CarEntity;

/**
 * Created by husseiny on 7/31/2018.
 */

public class CarProvider extends ContentProvider {

    // URI matcher constants!
    private static final int CARS = 1;
    private static final int CAR_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CarContract.CONTENT_AUTHORITY, CarEntity.TABLE_NAME, CARS);
        sUriMatcher.addURI(CarContract.CONTENT_AUTHORITY, CarEntity.TABLE_NAME + "/#", CAR_ID);
    }

    private CarDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new CarDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        // Figure out if the URI matcher could match URI to specific code
        int match = sUriMatcher.match(uri);

        switch (match){
            case CARS:
                cursor = database.query(CarEntity.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case CAR_ID:
                selection = CarEntity._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(CarEntity.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Cannot query unknow URI" + uri);

        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case CARS:
                long id = database.insert(CarEntity.TABLE_NAME, null, contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Cannot insert data!");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case CARS:
                getContext().getContentResolver().notifyChange(uri, null);
                return database.delete(CarEntity.TABLE_NAME, null, null);
                //return deletedRows;
            case CAR_ID:
                selection = CarEntity._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri, null);
                return database.delete(CarEntity.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Cannot delete raws with uri " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){
            case CARS:
                getContext().getContentResolver().notifyChange(uri, null);
                return database.update(CarEntity.TABLE_NAME, contentValues, null, null);
            case CAR_ID:
                selection = CarEntity._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri, null);
                return database.update(CarEntity.TABLE_NAME, contentValues, selection, selectionArgs);
        }
        return 0;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case CARS:
                return CarEntity.CONTENT_LIST_TYPE;
            case CAR_ID:
                return CarEntity.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknow URI " + uri + " with match " + match);
        }
    }
}
