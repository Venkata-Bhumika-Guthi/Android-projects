package edu.niu.android.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactsDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    // Constructor
    public ContactsDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Open Database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close Database
    public void close() {
        dbHelper.close();
    }

    // Add a new contact
    public long addContact(String firstName, String lastName, String email, long phone) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DBHelper.COLUMN_LAST_NAME, lastName);
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_PHONE, phone);

        return database.insert(DBHelper.TABLE_CONTACTS, null, values);
    }

    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LAST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL));
                long phone = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PHONE));
                contacts.add(new Contact(id, firstName, lastName, email, phone));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contacts;
    }

    // Retrieve a contact by email
    public Contact getContactByEmail(String email) {
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null,
                DBHelper.COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LAST_NAME));
            long phone = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PHONE));
            cursor.close();
            return new Contact(id, firstName, lastName, email, phone);
        }
        return null;
    }

    // Update an existing contact
    // Update Contact by email
    public int updateContact(String email, String firstName, String lastName, long phone) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DBHelper.COLUMN_LAST_NAME, lastName);
        values.put(DBHelper.COLUMN_PHONE, phone);

        return database.update(DBHelper.TABLE_CONTACTS, values, DBHelper.COLUMN_EMAIL + "=?", new String[]{email});
    }


    // Delete a contact by email
    public void deleteContact(String email) {
        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.COLUMN_EMAIL + "=?", new String[]{email});
    }
}
