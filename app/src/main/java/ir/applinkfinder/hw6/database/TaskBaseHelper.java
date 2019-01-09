package ir.applinkfinder.hw6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    //constructor
    public TaskBaseHelper(Context context){
        super(context, TaskDbSchema.NAME, null, TaskDbSchema.VERSION); //name: esme db
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // db.execSQL(create table myTableName(Col1Name text, Col2Name integer, ...));
        db.execSQL("create table " + TaskDbSchema.TaskTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TaskDbSchema.TaskTable.Cols.UUID + ", " +
                TaskDbSchema.TaskTable.Cols.TITLE + ", " +
                TaskDbSchema.TaskTable.Cols.DETAIL + ", " +
                TaskDbSchema.TaskTable.Cols.DATE + ", " +
                TaskDbSchema.TaskTable.Cols.HOUR + ", " +
                TaskDbSchema.TaskTable.Cols.DONE +
                ")"
        );

        //table 2: contacts
        db.execSQL("create table " + TaskDbSchema.ContactsTable.NAME + "(" +
                "_id integer primary key not null, " +
                TaskDbSchema.ContactsTable.Cols.NAME + ", " +
                TaskDbSchema.ContactsTable.Cols.EMAIL + ", " +
                TaskDbSchema.ContactsTable.Cols.USERNAME + ", " +
                TaskDbSchema.ContactsTable.Cols.PASSWORD +
                ")"
        );
    }

    //alter kardan: ezafe kardane column and .... dar onUpgrade anjam mishe
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "Drop table if exists " + TaskDbSchema.ContactsTable.NAME;
        db.execSQL(query);
        onCreate(db);
    }
}
