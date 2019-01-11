package ir.applinkfinder.hw6.database;

public class TaskDbSchema {
    public static final String NAME = "tasks.db"; // DB Name
    public static final int VERSION = 1;           // DB Version

    //Table 1: Tasks
    public static final class TaskTable {         // Table Definition
        public static final String NAME = "tasks"; // Table Name

        public static final class Cols {        // Columns of Table
            public static final String UUID = "uuid";
            public static final String CONTACT_ID = "contact_id";
            public static final String TITLE = "title";
            public static final String DETAIL = "detail";
            public static final String DATE = "date";
            public static final String HOUR = "hour";
            public static final String DONE = "done";
        }//Cols
    }//CrimeTable

    //Table 2: Login...
    public static final class ContactsTable {
        public static final String NAME = "contacts"; // Table Name

        public static final class Cols {
            public static final String CONTACT_ID = "contact_id";
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String EMAIL = "email";
        }//Cols
    }//ContactsTable
}