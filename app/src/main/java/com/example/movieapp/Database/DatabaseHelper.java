package com.example.movieapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movieapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "datamanager";

    //table name
    private static final String TABLE_USER = "user";

    private static final String USER_ID = "user_id";
    private static final String USER_NAME="user_name";
    private static final String USER_PW="user_pw"; //password
    private static final String USER_PROFILEIMAGE="user_profileimage";

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME
            + " TEXT," + USER_PW + " TEXT," + USER_PROFILEIMAGE + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);

    }

    public long createUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID, user.getId());
        values.put(USER_NAME, user.getName());
        values.put(USER_PW, user.getPassword());
        values.put(USER_PROFILEIMAGE, user.getProfileimage());

        // insert row
        long id = db.insert(TABLE_USER, null, values);
        return id;
    }

    public User getUser(long user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.setId(c.getInt(c.getColumnIndex(USER_ID)));
        u.setName((c.getString(c.getColumnIndex(USER_NAME))));
        u.setPassword(c.getString(c.getColumnIndex(USER_PW)));
        u.setProfileimage(c.getString(c.getColumnIndex(USER_PROFILEIMAGE)));

        return u;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setId(c.getInt((c.getColumnIndex(USER_ID))));
                u.setName((c.getString(c.getColumnIndex(USER_NAME))));
                u.setPassword(c.getString(c.getColumnIndex(USER_PW)));
                u.setProfileimage(c.getString(c.getColumnIndex(USER_PROFILEIMAGE)));


                users.add(u);
            } while (c.moveToNext());
        }
        return users;
    }

    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(USER_PW, user.getPassword());
        values.put(USER_PROFILEIMAGE, user.getProfileimage());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public int updateUserPassword (User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(USER_PW, user.getPassword());


        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public int updateUserPicture (User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(USER_PROFILEIMAGE, user.getProfileimage());


        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public void deleteAllUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_USER );
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
