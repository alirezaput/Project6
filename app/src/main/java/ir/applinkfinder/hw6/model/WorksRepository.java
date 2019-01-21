package ir.applinkfinder.hw6.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.App;
import ir.applinkfinder.hw6.database.orm.MyOrmOpenHelper;
//import ir.applinkfinder.hw6.database.sqlite.ContactCursorWrapper;
//import ir.applinkfinder.hw6.database.sqlite.TaskBaseHelper;
//import ir.applinkfinder.hw6.database.sqlite.TaskCursorWrapper;
//import ir.applinkfinder.hw6.database.sqlite.TaskDbSchema;

public class WorksRepository {

//    private SQLiteDatabase mDataBase;
    private Database mDataBase;
    private Context mContext;
    List<WorksModel> mWorksListDone;// = new ArrayList<>();
    private static WorksRepository instance;
    private long contactRowInserted;
    private long contactID;
    private WorksModelDao worksModelDao;
    private ContactDao contactDao;
    public static final String DATABASE_NAME = "tasks";

    // ----------------------------------------------------------------------------------------------
    private WorksRepository(Context context){ // 1 constructor e private misazim ke dg kasi natune new sh kone
        mContext = context.getApplicationContext();
        // SQLite
//        mDataBase = new TaskBaseHelper(mContext).getWritableDatabase(); // qabl az getWritableDatabase, onCreate e CrimeBaseHelper ejra mishe

        // ORM
        MyOrmOpenHelper myOrmOpenHelper = new MyOrmOpenHelper(mContext, DATABASE_NAME); // context, name of DB
        mDataBase = myOrmOpenHelper.getWritableDb();
    }//WorksRepository Constructor

    public static WorksRepository getInstance(Context context) {
        if (instance == null){ // baraye ine ke faqat 1 bar new she
            instance = new WorksRepository(context);
        }
        return instance;
    }

    //--
    public List<WorksModel> getmWorksList() {

        // SQLite:
//        List<WorksModel> mWorksList = new ArrayList<>();
////        TaskCursorWrapper taskCursorWrapper = queryTask(null, null);
//
//        String query = "select * from " + TaskDbSchema.ContactsTable.NAME + " inner join " + TaskDbSchema.TaskTable.NAME + " on " +
//                TaskDbSchema.ContactsTable.NAME + "." + TaskDbSchema.ContactsTable.Cols.CONTACT_ID + " = " +
//                TaskDbSchema.TaskTable.NAME + "." + TaskDbSchema.TaskTable.Cols.CONTACT_ID + " where " +
//                TaskDbSchema.ContactsTable.NAME + "." + TaskDbSchema.ContactsTable.Cols.CONTACT_ID + " = " + getContactId();
//        Cursor cursor = mDataBase.rawQuery(query, null);
//        try {
//            if (cursor.getCount() == 0){
//                return mWorksList;
//            }
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()){ // age isLast bezarim akhario dar nazar nemigire
//                UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID)));
//                String title = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
//                String detail = cursor.getString(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.DETAIL));
//                Date date = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.DATE)));
////                Date hour = new Date(cursor.getLong(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.HOUR)));
//                boolean isDone = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.TaskTable.Cols.DONE)) != 0;
//                WorksModel worksModel = new WorksModel(uuid);
//                worksModel.setContactId(getContactId());
//                worksModel.setTitle(title);
//                worksModel.setDetail(detail);
//                worksModel.setDate(date);
////                worksModel.setHour(hour);
//                worksModel.setDone(isDone);
//                mWorksList.add(worksModel);
//                cursor.moveToNext();
//            }//while
//        }// try
//        finally {
//            cursor.close();
//        }
//        return mWorksList;

//        // ORM
//        DaoSession daoSession = (App.getApp()).getDaoSession();
//        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
////        for (int i = 0; i < worksModelDao.count(); i++){
////            mWorksList.add(worksModelDao.loadAll())
////        }

        DaoSession daoSession = (App.getApp()).getDaoSession();
        worksModelDao = daoSession.getWorksModelDao();
//        List<WorksModel> worksList = worksModelDao.loadAll();

//        QueryBuilder<WorksModel> queryBuilder = worksModelDao.queryBuilder();
//                queryBuilder.join(Contact.class, ContactDao.Properties.ContactId)
//                .where(WorksModelDao.Properties.ContactId.eq(ContactDao.Properties.ContactId));
//        List<WorksModel> worksList = queryBuilder.list();

        QueryBuilder<WorksModel> queryBuilder = worksModelDao.queryBuilder();
                queryBuilder.join(Contact.class, ContactDao.Properties.ContactId)
                .where(ContactDao.Properties.ContactId.eq(getContactId()));
        List<WorksModel> worksList = queryBuilder.list();

        // ---------------
//        QueryBuilder<WorksModel> queryBuilder = worksModelDao.queryBuilder();
//        Join joinTaskContact = queryBuilder.join(Contact.class, ContactDao.Properties.ContactId);
//
//                queryBuilder.join(Contact.class, ContactDao.Properties.ContactId)
//                .where(WorksModelDao.Properties.ContactId.eq(ContactDao.Properties.ContactId));
//        List<WorksModel> worksList = queryBuilder.list();



        // ---------------
//        String query = " Inner join " + ContactDao.TABLENAME + " on " + WorksModelDao.TABLENAME + "." + WorksModelDao.Properties.ContactId + " = " +
//                        ContactDao.TABLENAME + "." + ContactDao.Properties.ContactId + " where " + ContactDao.Properties.ContactId + " = " + getContactId();
//        List<WorksModel> worksList = daoSession.getWorksModelDao().queryRaw(query);
//        return worksList;

        // ----------------
//        List<WorksModel> worksList = worksModelDao.queryBuilder()
//                .list();
        return worksList;

    }//getmWorksList

    //--
    public List<WorksModel> getmWorkListDone(){
        //SQLite
//        List<WorksModel> mWorksListDone = new ArrayList<>();
//
//        String whereClause = TaskDbSchema.TaskTable.Cols.DONE + " = 1";
//        TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, null);
//
//        try {
//            if (taskCursorWrapper.getCount() == 0){
//                return mWorksListDone;
//            }
//            // cursor ruye db peymayesh mikone
//            taskCursorWrapper.moveToFirst();
//            while (!taskCursorWrapper.isAfterLast()){ // age isLast bezarim akhario dar nazar nemigire
//                mWorksListDone.add(taskCursorWrapper.getDoneTask()); //getDoneTask()
//                Log.d("Tag22", "getmWorkListDone"); //test
//                taskCursorWrapper.moveToNext();
//            }//while
//        }// try
//        finally {
//            taskCursorWrapper.close();
//        }
//        return mWorksListDone;

        //ORM:
        List<WorksModel> worksListDone = worksModelDao.queryBuilder()
                .where(WorksModelDao.Properties.Done.eq(1))
                .list();
        return worksListDone;
    }

    public WorksModel getWork(UUID id){

        //SQLite:
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        String[] whereArgs = new String[]{id.toString()};
//        TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, whereArgs);
//
//        try {
//            if (taskCursorWrapper.getCount() == 0) {
//                return null;
//            }
//            // cursor ruye db peymayesh mikone
//            taskCursorWrapper.moveToFirst();
//            return taskCursorWrapper.getTask();
//        }// try
//        finally {
//            taskCursorWrapper.close();
//        }

        //ORM:
        WorksModel worksModel = new WorksModel(id);
        DaoSession daoSession = (App.getApp()).getDaoSession();
        worksModelDao = daoSession.getWorksModelDao();
        List<WorksModel> worklist = worksModelDao.queryBuilder()
//                .where(WorksModelDao.Properties.Id.eq(String.valueOf(id)))
                .where(WorksModelDao.Properties.Id.eq(id.toString()))

//                .where(WorksModelDao.Properties.Title.eq("Task1"))
                .list();
        Log.d("asas", "getWork "+ worklist.size());
//        Query query = worksModelDao.queryBuilder()
////                .where(WorksModelDao.Properties.Id.eq(id))
//                .where(WorksModelDao.Properties.Id.eq(String.valueOf(id)))
//                .build();
//        query.setParameter(0, worksModel);
//        List<WorksModel> work = query.list();

//        Cursor cursor = daoSession.getDatabase().rawQuery(query.getSql(),WorksModelDao.Properties.Id.eq(String.valueOf(id)));
//        try {
//            if (cursor.getCount() == 0) {
//                return null;
//            }
//            cursor.moveToFirst();
//            return cursor.getTask();
//        }// try
//        finally {
//            cursor.close();
//        }

//        for (int i = 0; i < work.size(); i++){
//            worksModel = work.get(i);
//        }

        Toast.makeText(mContext, worklist + "", Toast.LENGTH_SHORT).show();
        worksModel.setTitle(worklist.get(0).getTitle());
        worksModel.setDetail(worklist.get(0).getDetail());
        worksModel.setDone(worklist.get(0).getDone());
        worksModel.setDate(worklist.get(0).getDate());
        worksModel.setHour(worklist.get(0).getHour());
//        worksModel.setContact(worklist.get(0).getContact());
        worksModel.setContactId(worklist.get(0).getContactId());
        worksModel.setORMId(worklist.get(0).getORMId());
        return worksModel;
//        return (WorksModel) work;
    }//getWork

    public Contact getContact(int id){
        //SQLite:
//        String whereClause = TaskDbSchema.ContactsTable.Cols.CONTACT_ID + " = ? ";
//        String[] whereArgs = new String[]{String.valueOf(id)};
//        ContactCursorWrapper contactCursorWrapper = queryContact(whereClause, whereArgs);
//
//        try {
//            if (contactCursorWrapper.getCount() == 0) {
//                return null;
//            }
//            // cursor ruye db peymayesh mikone
//            contactCursorWrapper.moveToFirst();
//            return contactCursorWrapper.getContact();
//        }// try
//        finally {
//            contactCursorWrapper.close();
//        }

        DaoSession daoSession = (App.getApp()).getDaoSession();
        contactDao = daoSession.getContactDao();
        List<Contact> contact = contactDao.queryBuilder()
                .where(WorksModelDao.Properties.Id.eq(id))
                .list();

        return (Contact) contact;
    }//getContact

    // raw haye DB
//    public ContentValues getContentValues(WorksModel worksModel){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TaskDbSchema.TaskTable.Cols.UUID, worksModel.getId().toString());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.CONTACT_ID, worksModel.getContactId());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DONE, worksModel.isDone());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.TITLE, worksModel.getTitle());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DETAIL, worksModel.getDetail());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DATE, worksModel.getDate().getTime()); //getTime haman TimeStamp ra bar migardune
////        contentValues.put(TaskDbSchema.TaskTable.Cols.HOUR, worksModel.getHour().getTime()); //getTime haman TimeStamp ra bar migardune
//
////        contentValues.put(TaskDbSchema.TaskTable.Cols.DATE, ""); //getTime haman TimeStamp ra bar migardune
//        contentValues.put(TaskDbSchema.TaskTable.Cols.HOUR, ""); //getTime haman TimeStamp ra bar migardune
//
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DONE, worksModel.isDone() ? 1 : 0); //if ebarat 1, true ---- else false
//        return contentValues;
//    }

//    public ContentValues getContentValuesContacts(Contact contact){
////        String query = "select * from " + TaskDbSchema.ContactsTable.NAME;
////        Cursor cursor = mDataBase.rawQuery(query, null);
////        int numOfContacts = cursor.getCount();
//
//        ContentValues contentValues = new ContentValues();
////        contentValues.put(TaskDbSchema.ContactsTable.Cols.ID, numOfContacts);
//        contentValues.put(TaskDbSchema.ContactsTable.Cols.NAME, contact.getName());
//        contentValues.put(TaskDbSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
//        contentValues.put(TaskDbSchema.ContactsTable.Cols.USERNAME, contact.getUsername());
//        contentValues.put(TaskDbSchema.ContactsTable.Cols.PASSWORD, contact.getPassword());
//
//        return contentValues;
//    }


//    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
//        Cursor cursor = mDataBase.query(
//                TaskDbSchema.TaskTable.NAME,
//                null,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                null);
//        return new TaskCursorWrapper(cursor);
//    }


//    private ContactCursorWrapper queryContact(String whereClause, String[] whereArgs) {
//        Cursor cursor = mDataBase.query(
//                TaskDbSchema.ContactsTable.NAME,
//                null,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                null);
//        return new ContactCursorWrapper(cursor);
//    }

    //--
    public void addWork(WorksModel worksModel){
        //SQLite:
//        mDataBase.insert(TaskDbSchema.TaskTable.NAME, null, getContentValues(worksModel));

        //ORM:
        Toast.makeText(mContext, "auto increment id: "+worksModel.getORMId()+"", Toast.LENGTH_SHORT).show();
        DaoSession daoSession = (App.getApp()).getDaoSession();
        worksModelDao = daoSession.getWorksModelDao();
        Long idd = worksModelDao.insert(worksModel);
        Toast.makeText(mContext, "inserted row id: " + idd, Toast.LENGTH_SHORT).show();

    }

    //--
    public void addWorkDone(WorksModel worksModel){
        //SQLite:
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DONE, true); //if ebarat 1, true ---- else false
//
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});


// -----------------------------------------------------------------
        //ORM:
        worksModel.setDone(true);
        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        worksModelDao.update(worksModel);
    }

    //--
    public void deleteWork(UUID id){

        // SQLite:
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        mDataBase.delete(TaskDbSchema.TaskTable.NAME, whereClause, new String[] {id.toString()});

        //ORM:
        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        worksModelDao.delete(WorksRepository.getInstance(mContext).getWork(id));
    } //deleteWork

    public void deleteWorkDone(UUID id){
        //SQLite:
//        WorksModel wm = null;
//        for(int i=0;i<WorksRepository.getInstance(mContext).getmWorksList().size();i++) {
//            if(WorksRepository.getInstance(mContext).getmWorksList().get(i).getId().equals(id)) {
//                wm = WorksRepository.getInstance(mContext).getmWorkListDone().get(i);
//                WorksRepository.getInstance(mContext).getmWorksList().remove(i);
//                Log.d("MyTag", "remove from all tasks");
//            }
//        }
//        WorksRepository.getInstance(mContext).getmWorkListDone().remove(wm);
    }//deleteWorkDone

    //--
    public void update(WorksModel worksModel){
        // SQLite
//        ContentValues contentValues = getContentValues(worksModel);
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});

        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        worksModelDao.update(worksModel);
    }

    //--
    public void editWork(WorksModel worksModel){
        // SQLite:
//        ContentValues contentValues = getContentValues(worksModel);
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});

        //ORM:
        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        worksModelDao.update(worksModel);
    }//editWork

    //--
    public void editWorkDone(WorksModel worksModel){
        // SQLite:
//        ContentValues contentValues = getContentValues(worksModel);
//        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? " + " And " + TaskDbSchema.TaskTable.Cols.DONE + " = ?";
//        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString(), "1"});

        //ORM:
        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        worksModelDao.update(worksModel);
    }//editWorkDone

    //--
    public void insertContact(Contact contact){
        //SQLite:
////        String query = "select * from " + TaskDbSchema.ContactsTable.NAME;
////        Cursor cursor = mDataBase.rawQuery(query, null);
////        int numOfContacts = cursor.getCount();

//        mDataBase = new TaskBaseHelper(mContext).getWritableDatabase();
//        contactRowInserted = mDataBase.insert(TaskDbSchema.ContactsTable.NAME, null, getContentValuesContacts(contact));

        // ORM:
        DaoSession daoSession = (App.getApp()).getDaoSession();
        ContactDao contactDao = daoSession.getContactDao();
        contactDao.insert(contact);
    }//insertContact

    //--
    public String searchContactPassword(String username){
        //SQLite:
//        mDataBase = new TaskBaseHelper(mContext).getReadableDatabase();
//        String[] columns = new String[] {TaskDbSchema.ContactsTable.Cols.USERNAME, TaskDbSchema.ContactsTable.Cols.PASSWORD};
//        Cursor cursor = mDataBase.query(TaskDbSchema.ContactsTable.NAME,
//                columns,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        String user;
//        String pass = "not found";
//        if (cursor.moveToFirst()){
//            do {
//                user = cursor.getString(0);
//                if (user.equals(username)){
//                    pass = cursor.getString(1);
//                    break;
//                }
//            }
//            while (cursor.moveToNext());
//        }
//        return pass;

        //ORM:
        //        mDataBase = new TaskBaseHelper(mContext).getReadableDatabase();

        String pass = "not found";
        DaoSession daoSession = (App.getApp()).getDaoSession();
        contactDao = daoSession.getContactDao();
        List<Contact> contacts = contactDao.queryBuilder()
                .where(ContactDao.Properties.Username.eq(username))
                .list();

        for (int i = 0; i < contacts.size(); i++){
//            if (contacts.get(i).getUsername().equals(username)){
                pass = contacts.get(i).getPassword();
//            }
        }

//        pass = contacts.get(0).getPassword();
        return pass;
    }//searchContactPassword

    public void setContactID(long conID){
        contactID = conID;
    }

    public long getContactId(){
//        return contactRowInserted;
        return contactID;
    }//getContactId

    //ORM
    public boolean isUsernameExist(String username){
        //SQLite:
//        String UsernameInDB = null;
//        String whereClause = TaskDbSchema.ContactsTable.Cols.USERNAME + " = ?";
//        String[] whereArgs = new String[]{username};
//        ContactCursorWrapper contactCursorWrapper = queryContact(whereClause, whereArgs);
//
////        try {
////            if (contactCursorWrapper.getCount() == 0){
////                return UsernameInDB;
////            }
//            if (contactCursorWrapper.getCount()<=0){
//                contactCursorWrapper.close();
//                return false;
//            }
//            contactCursorWrapper.close();
//            return true;


        //ORM:
        String pass = "not found";
        DaoSession daoSession = (App.getApp()).getDaoSession();
        contactDao = daoSession.getContactDao();
        List<Contact> contacts = contactDao.queryBuilder()
                .where(ContactDao.Properties.Username.eq(username))
                .list();
        if (contacts.size()>0){
            return true;
        }
        return false;

    }//isUsernameExist

    public File getPhotoFile(WorksModel workmodel){
        File filesDir = mContext.getFilesDir(); // in data>data>appname>files ra bar migardune
        File photoFile = new File(filesDir, workmodel.getPhotoName());
        return photoFile;
    }//getPhotoFile
}
