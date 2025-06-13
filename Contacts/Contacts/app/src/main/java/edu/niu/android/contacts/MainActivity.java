/************************************************************************
 * *
 * CSCI 522                  Assignment 6                current semester *
 * *
 * App Name: Contacts*
 * *
 * Class Name: MainActivity.java *
 * *
 * Developers: Venkata Bhumika Guthi
 *             Bharath Kumar Bandi*
 * *
 * Due Date: 11-15-2024 *
 * *
 * Purpose: This .java file develops a Contacts app that lets users to create a contacts app and lets the user perform add, update delete and search operations.
 * *
 ************************************************************************/




package edu.niu.android.contacts;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactsDataSource contactsDataSource;
    private ListView contactListView;
    private AutoCompleteTextView searchEmail;
    private ArrayAdapter<String> contactAdapter;
    private EditText firstNameInput, lastNameInput, emailInput, phoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a LinearLayout to organize the UI programmatically
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Initialize Database
        contactsDataSource = new ContactsDataSource(this);
        contactsDataSource.open();

        // Input Fields for Adding/Updating Contact
        firstNameInput = new EditText(this);
        firstNameInput.setHint("First Name");
        layout.addView(firstNameInput);

        lastNameInput = new EditText(this);
        lastNameInput.setHint("Last Name");
        layout.addView(lastNameInput);

        emailInput = new EditText(this);
        emailInput.setHint("Email");
        layout.addView(emailInput);

        phoneInput = new EditText(this);
        phoneInput.setHint("Phone Number (e.g., 1234567890)");
        layout.addView(phoneInput);

        // Buttons for Add, Update, and Delete
        Button addButton = new Button(this);
        addButton.setText("Add Contact");
        layout.addView(addButton);

        Button updateButton = new Button(this);
        updateButton.setText("Update Contact");
        layout.addView(updateButton);

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete Contact");
        layout.addView(deleteButton);

        // TextView for Search Label
        TextView searchLabel = new TextView(this);
        searchLabel.setText("Search Email:");
        searchLabel.setTextSize(18);
        layout.addView(searchLabel);

        // AutoCompleteTextView for Search
        searchEmail = new AutoCompleteTextView(this);
        searchEmail.setHint("Type email here");
        searchEmail.setTextSize(16);
        layout.addView(searchEmail);

        // ListView for Displaying Contacts
        contactListView = new ListView(this);
        layout.addView(contactListView);

        // Set ContentView to the Programmatic Layout
        setContentView(layout);

        // Populate List and Setup AutoCompleteTextView
        refreshContactList();

        // Add Button Listener
        addButton.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String phoneStr = phoneInput.getText().toString();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneStr)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long phone = Long.parseLong(phoneStr);
            contactsDataSource.addContact(firstName, lastName, email, phone);
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
            refreshContactList();
        });

        // Update Button Listener
        updateButton.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String phoneStr = phoneInput.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Email is required to update a contact", Toast.LENGTH_SHORT).show();
                return;
            }

            long phone = TextUtils.isEmpty(phoneStr) ? 0 : Long.parseLong(phoneStr);
            contactsDataSource.updateContact(email, firstName, lastName, phone);
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            refreshContactList();
        });

        // Delete Button Listener
        deleteButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Email is required to delete a contact", Toast.LENGTH_SHORT).show();
                return;
            }

            contactsDataSource.deleteContact(email);
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show();
            refreshContactList();
        });

        // Search Feature
        searchEmail.setOnItemClickListener((parentView, selectedItemView, position, id) -> {
            String email = (String) parentView.getItemAtPosition(position);
            Contact contact = contactsDataSource.getContactByEmail(email);
            if (contact != null) {
                Toast.makeText(MainActivity.this,
                        "Found: " + contact.getFirstName() + " " + contact.getLastName(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No Contact Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshContactList() {
        List<Contact> allContacts = contactsDataSource.getAllContacts();
        List<String> contactDisplay = new ArrayList<>();
        List<String> emails = new ArrayList<>();

        for (Contact contact : allContacts) {
            // Display all attributes in the format you want
            String contactInfo = "First Name: " + contact.getFirstName() + "\nLast Name " + contact.getLastName() +
                    "\nEmail: " + contact.getEmail() +
                    "\nPhone: " + contact.getFormattedPhone();  // Add the phone number as well
            contactDisplay.add(contactInfo);
            emails.add(contact.getEmail());
        }

        // AutoComplete Adapter for Search
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, emails);
        searchEmail.setAdapter(emailAdapter);

        // ListView Adapter for displaying all contacts with all attributes
        contactAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplay);
        contactListView.setAdapter(contactAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactsDataSource.close();
    }
}
