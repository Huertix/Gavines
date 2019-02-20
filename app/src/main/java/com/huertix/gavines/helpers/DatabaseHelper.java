package com.huertix.gavines.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ViewGroup;

import com.huertix.gavines.models.Event;

import java.util.ArrayList;
import java.util.List;

import static com.huertix.gavines.MainActivity.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String LOG = "Gavines";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventsdb";

    // Table Names
    private static final String TABLE_EVENT = "event";
    private static final String TABLE_LOCATION = "location";

    // Common column names
    private static final String KEY_ID = "id";

    // EVENT Table - column names
    private static final String KEY_EVENT_DATE = "date";
    private static final String KEY_EVENT_LOCATION = "location";
    private static final String KEY_EVENT_HUMEDITY = "humedity";
    private static final String KEY_EVENT_TEMPERATURE = "temperature";


    // TAGS Table - column names
    private static final String KEY_LOCATION_NAME = "location";


    // Table Create Statements
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE "
            + TABLE_EVENT + "(" + KEY_ID + " INTEGER," + KEY_EVENT_LOCATION
            + " INTEGER," + KEY_EVENT_TEMPERATURE + " DOUBLE," + KEY_EVENT_HUMEDITY
            + " DOUBLE," + KEY_EVENT_DATE + " CHAR,"
            + " PRIMARY KEY (" + KEY_EVENT_DATE + ")" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LOCATION_NAME + " TEXT)";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_LOCATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LOCATION);

        // create new tables
        onCreate(db);
    }

    /*
     * Creating a event
     */
    public long createEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_LOCATION, event.getPlace());
        values.put(KEY_EVENT_DATE, event.getDate());
        values.put(KEY_EVENT_HUMEDITY, event.getHumedity());
        values.put(KEY_EVENT_TEMPERATURE, event.getTemperature());

        // insert row
        long event_id = db.insert(TABLE_EVENT, null, values);

        Log.d(TAG, "Event ID: " + event_id);


        return event_id;

    }


    public Event getLastEventByLocation(String location) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENT + " WHERE "
                + KEY_EVENT_LOCATION + " = \"" + location + "\" ORDER BY " + KEY_EVENT_DATE + " DESC;";


        Log.d(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Event event = new Event();

        if (c != null && c.moveToFirst()) {

            event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            event.setLocation((c.getString(c.getColumnIndex(KEY_EVENT_LOCATION))));
            event.setDate(c.getString(c.getColumnIndex(KEY_EVENT_DATE)));
            event.setHumedity(c.getDouble(c.getColumnIndex(KEY_EVENT_HUMEDITY)));
            event.setTemperature(c.getDouble(c.getColumnIndex(KEY_EVENT_TEMPERATURE)));

        }

        return event;
    }




    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENT + ";";


        Log.d(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst())  {


            do {

                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                event.setLocation((c.getString(c.getColumnIndex(KEY_EVENT_LOCATION))));
                event.setDate(c.getString(c.getColumnIndex(KEY_EVENT_DATE)));
                event.setHumedity(c.getDouble(c.getColumnIndex(KEY_EVENT_HUMEDITY)));
                event.setTemperature(c.getDouble(c.getColumnIndex(KEY_EVENT_TEMPERATURE)));

                events.add(event);


            } while (c.moveToNext());

        }

        return events;
    }

    public List<Event> getAllEventsByLocation(String location) {

        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_EVENT + " WHERE "+ KEY_EVENT_LOCATION
                + " = '" + location +"' ORDER BY " + KEY_EVENT_DATE + " DESC;";

        Log.d(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst())  {


            do {

                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                event.setLocation((c.getString(c.getColumnIndex(KEY_EVENT_LOCATION))));
                event.setDate(c.getString(c.getColumnIndex(KEY_EVENT_DATE)));
                event.setHumedity(c.getDouble(c.getColumnIndex(KEY_EVENT_HUMEDITY)));
                event.setTemperature(c.getDouble(c.getColumnIndex(KEY_EVENT_TEMPERATURE)));

                events.add(event);


            } while (c.moveToNext());

        }

        return events;
    }
}
