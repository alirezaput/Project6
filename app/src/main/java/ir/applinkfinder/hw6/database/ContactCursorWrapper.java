package ir.applinkfinder.hw6.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import ir.applinkfinder.hw6.contacts.Contact;

public class ContactCursorWrapper extends CursorWrapper {

    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
//        int contact_id = Integer.valueOf(getString(getColumnIndex(TaskDbSchema.ContactsTable.Cols.ID)));
        String name = getString(getColumnIndex(TaskDbSchema.ContactsTable.Cols.NAME));
        String email = getString(getColumnIndex(TaskDbSchema.ContactsTable.Cols.EMAIL));
        String username = getString(getColumnIndex(TaskDbSchema.ContactsTable.Cols.USERNAME));
        String password = getString(getColumnIndex(TaskDbSchema.ContactsTable.Cols.PASSWORD));

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setUsername(username);
        contact.setPassword(password);

        return contact;
    }//getTask
}