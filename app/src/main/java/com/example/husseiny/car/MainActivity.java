package com.example.husseiny.car;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.husseiny.car.database.CarContract.CarEntity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CAR_LOADER = 0;
    ListView display;
    CarCursorAdapter carCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let the fab button to go through the next activity when it's pressed!
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_car);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, EditActivity.class);
                startActivity(nextActivity);
            }
        });



        display = (ListView) findViewById(R.id.listview_cars);
        /** set empty view to null is necessary because when backing from edit activity(adding new car),
         no need to show empty view again before cursor loading data in the list view **/
        display.setEmptyView(null);
        carCursorAdapter = new CarCursorAdapter(this, null);
        display.setAdapter(carCursorAdapter);

        getLoaderManager().initLoader(CAR_LOADER, null, this);

        // Display the context menu when long pressed on item
        registerForContextMenu(display);
        // When user presses on item, he will be able to view the details of the item!
    }


    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_activity_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get info of the selected item of the list and use it to know the requested ID
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_update:
                Intent updateOption = new Intent(this, EditActivity.class);
                updateOption.setData(ContentUris.withAppendedId(CarEntity.CONTENT_URI, info.id));
                startActivity(updateOption);
                return true;
            case R.id.menu_delete:
                Uri selectedUri = ContentUris.withAppendedId(CarEntity.CONTENT_URI, info.id);
                int deletedRows = getContentResolver().delete(selectedUri,
                        null, null);
                Toast.makeText(this, deletedRows + " car is removed from your store!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                // TODO: Search algorithm
            case R.id.menu_share:
                // TODO: Share Intent
                return true;
            case R.id.menu_delete_items:
                // TODO; for delete option, View dialog
                getContentResolver().delete(CarEntity.CONTENT_URI, null, null);
                Toast.makeText(this, "your store is empty!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {CarEntity._ID, CarEntity.COLUMN_CAR_NAME, CarEntity.COLUMN_CAR_BRAND,
                CarEntity.COLUMN_CAR_PRICE, CarEntity.COLUMN_CAR_DESC, CarEntity.COLUMN_CAR_DATE};
        return new CursorLoader(this, CarEntity.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        display.setEmptyView(findViewById(R.id.listview_empty));
        carCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        carCursorAdapter.swapCursor(null);

    }
}
