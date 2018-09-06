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
import android.widget.Toast;

import com.example.husseiny.car.database.CarContract.CarEntity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
 {


    private static final int CAR_LOADER = 0;
    ListView display;
    CarCursorAdapter carCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let the fab button to go through the next activity when it's pressed!
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, EditActivity.class);
                startActivity(nextActivity);
            }
        });

        // TODO: Set Empty ListView
        display = (ListView) findViewById(R.id.list_view);
        carCursorAdapter = new CarCursorAdapter(this, null);
        display.setAdapter(carCursorAdapter);

        getLoaderManager().initLoader(CAR_LOADER, null, this);

        // Display the context menu when long pressed on item
        registerForContextMenu(display);
        // When user presses on item, he will be able to view the details of the item!
        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent detailsActivity = new Intent(MainActivity.this, DetailedActivity.class);
                // Send selected Uri to the detailed activity
                detailsActivity.setData(ContentUris.withAppendedId(CarEntity.CONTENT_URI, id));
                startActivity(detailsActivity);
            }
        });
    }


     @Override
     public void onCreateContextMenu(final ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         getMenuInflater().inflate(R.menu.context_menu_update_delete, menu);
     }

     @Override
     public boolean onContextItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.update_option:
                 Intent updateOption = new Intent(this, EditActivity.class);
                 updateOption.setData(ContentUris.withAppendedId(CarEntity.CONTENT_URI, display.getSelectedItemId()));
                 startActivity(updateOption);
                 return true;
             case R.id.delete_option:
                 long id = 0;
                 Uri selectedUri = ContentUris.withAppendedId(CarEntity.CONTENT_URI, id);
                 int deletedRows = getContentResolver().delete(selectedUri,
                         null, null);
                 Toast.makeText(this, deletedRows + " row is deleted successfully!", Toast.LENGTH_SHORT).show();
                 return true;
         }
         return true;
     }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_dummy_item_option:
                // TODO: Share Intent
                return true;
            case R.id.delete_item_option:
                // TODO; for delete option, View dialog
                getContentResolver().delete(CarEntity.CONTENT_URI, null, null);
                return true;
        }
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {CarEntity._ID ,CarEntity.COLUMN_CAR_NAME, CarEntity.COLUMN_CAR_BRAND,
                CarEntity.COLUMN_CAR_PRICE, CarEntity.COLUMN_CAR_DESC, CarEntity.COLUMN_CAR_DATE };
        return new CursorLoader(this, CarEntity.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        carCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        carCursorAdapter.swapCursor(null);

    }
}
