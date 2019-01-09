package ir.applinkfinder.hw6.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.contacts.Contact;
import ir.applinkfinder.hw6.database.TaskBaseHelper;
import ir.applinkfinder.hw6.database.TaskCursorWrapper;
import ir.applinkfinder.hw6.database.TaskDbSchema;

public class WorksRepository {

    private SQLiteDatabase mDataBase;
    private Context mContext;
    List<WorksModel> mWorksListDone;// = new ArrayList<>();
    private static WorksRepository instance;
//    private static WorksRepository instanceDone;
    private static WorksRepository instanceUndone;

    // ----------------------------------------------------------------------------------------------
    private WorksRepository(Context context){ // 1 constructor e private misazim ke dg kasi natune new sh kone
//        mWorksList = new ArrayList<>();
        mWorksListDone = new ArrayList<>();

        mContext = context.getApplicationContext();
        mDataBase = new TaskBaseHelper(mContext).getWritableDatabase(); // qabl az getWritableDatabase, onCreate e CrimeBaseHelper ejra mishe
    }//WorksRepository Constructor

//    public List<WorksModel> getDoneWork(){
//        WorksRepository worksRepository = WorksRepository.getInstance();
//        List<WorksModel> DoneWorkList = new ArrayList<>();
//        for (int i = 0; i < worksRepository.getmWorkListDone().size(); i++){
//            if (worksRepository.getmWorksList().get(i).isDone()){
//                DoneWorkList.add(worksRepository.getmWorksList().get(i));
//            }
//        }
//        return DoneWorkList;
//    }

//    public List<WorksModel> getUndoneWork(){
//        WorksRepository worksRepository = WorksRepository.getInstanceUndone();
//        List<WorksModel> UndoneWorkList = new ArrayList<>();
//        for (int i = 0; i < worksRepository.getmWorksList().size(); i++){
//            if (!worksRepository.getmWorksList().get(i).isDone()){
//                UndoneWorkList.add(worksRepository.getmWorksList().get(i));
//            }
//        }
//        return UndoneWorkList;
//    }

    public static WorksRepository getInstance(Context context) {
        if (instance == null){ // baraye ine ke faqat 1 bar new she
            instance = new WorksRepository(context);
        }
        return instance;
    }

    public static WorksRepository getInstanceUndone(Context context) {
        if (instanceUndone == null){ // baraye ine ke faqat 1 bar new she
            instanceUndone = new WorksRepository(context);
        }
        return instanceUndone;
    }

    // method e "query" select mizane...
    // select operation with query syntax
    public List<WorksModel> getmWorksList() {

        List<WorksModel> mWorksList = new ArrayList<>();
        TaskCursorWrapper taskCursorWrapper = queryTask(null, null);

//        // in khat Title va Id hameye crime ha ra barmigardune
//        mDataBase.query(CrimeDbSchema.CrimeTable.NAME, new String[]{CrimeDbSchema.CrimeTable.Cols.TITLE, CrimeDbSchema.CrimeTable.Cols.DATE}, null, null, null, null, null);

        try {
            if (taskCursorWrapper.getCount() == 0){
                return mWorksList;
            }
            // cursor ruye db peymayesh mikone
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){ // age isLast bezarim akhario dar nazar nemigire
                mWorksList.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }//while
        }// try
        finally {
            taskCursorWrapper.close();
        }
        return mWorksList;
    }//getmWorksList

    public List<WorksModel> getmWorkListDone(){
//        List<WorksModel> mWorksListDone = new ArrayList<>();

        String whereClause = TaskDbSchema.TaskTable.Cols.DONE + " > 0 ";
        TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, null);

        try {
            if (taskCursorWrapper.getCount() == 0){
                return mWorksListDone;
            }
            // cursor ruye db peymayesh mikone
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()){ // age isLast bezarim akhario dar nazar nemigire
                mWorksListDone.add(taskCursorWrapper.getDoneTask()); //getDoneTask()
                Log.d("Tag22", "getmWorkListDone"); //test
                taskCursorWrapper.moveToNext();
            }//while
        }// try
        finally {
            taskCursorWrapper.close();
        }
        return mWorksListDone;
    }

    public WorksModel getWork(UUID id){
//        for (WorksModel worksModel: mWorksList){
//            if (worksModel.getId().equals(id))
//                return worksModel;
//        }
//        return null;
            String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
            String[] whereArgs = new String[]{id.toString()};
            TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, whereArgs);

            try {
                if (taskCursorWrapper.getCount() == 0) {
                    return null;
                }
                // cursor ruye db peymayesh mikone
                taskCursorWrapper.moveToFirst();
                return taskCursorWrapper.getTask();
            }// try
            finally {
                taskCursorWrapper.close();
            }
        }//getCrime

    // raw haye DB
    public ContentValues getContentValues(WorksModel worksModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDbSchema.TaskTable.Cols.UUID, worksModel.getId().toString());
        contentValues.put(TaskDbSchema.TaskTable.Cols.TITLE, worksModel.getTitle());
        contentValues.put(TaskDbSchema.TaskTable.Cols.DETAIL, worksModel.getDetail());
//        contentValues.put(TaskDbSchema.TaskTable.Cols.DATE, worksModel.getDate().getTime()); //getTime haman TimeStamp ra bar migardune
//        contentValues.put(TaskDbSchema.TaskTable.Cols.HOUR, worksModel.getHour().getTime()); //getTime haman TimeStamp ra bar migardune

        contentValues.put(TaskDbSchema.TaskTable.Cols.DATE, ""); //getTime haman TimeStamp ra bar migardune
        contentValues.put(TaskDbSchema.TaskTable.Cols.HOUR, ""); //getTime haman TimeStamp ra bar migardune

        contentValues.put(TaskDbSchema.TaskTable.Cols.DONE, worksModel.isDone() ? 1 : 0); //if ebarat 1, true ---- else false
        return contentValues;
    }

    public ContentValues getContentValuesContacts(Contact contact){
//        String query = "select * from " + TaskDbSchema.ContactsTable.NAME;
//        Cursor cursor = mDataBase.rawQuery(query, null);
//        int numOfContacts = cursor.getCount();

        ContentValues contentValues = new ContentValues();
//        contentValues.put(TaskDbSchema.ContactsTable.Cols.ID, numOfContacts);
        contentValues.put(TaskDbSchema.ContactsTable.Cols.NAME, contact.getName());
        contentValues.put(TaskDbSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
        contentValues.put(TaskDbSchema.ContactsTable.Cols.USERNAME, contact.getUsername());
        contentValues.put(TaskDbSchema.ContactsTable.Cols.PASSWORD, contact.getPassword());

        return contentValues;
    }


    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDataBase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }


//    public WorksModel getWorkDone(UUID id){
//        for(int i=0;i<WorksRepository.getInstance().getmWorkListDone().size();i++) {
//            if(WorksRepository.getInstance().getmWorkListDone().get(i).getId().equals(id)) {
//                return WorksRepository.getInstance().getmWorkListDone().get(i);
//            }
//        }
//        return null;
//    }//getWorkDone

    public void addWork(WorksModel worksModel){
//        WorksRepository.getInstance().getmWorksList().add(worksModel);
        mDataBase.insert(TaskDbSchema.TaskTable.NAME, null, getContentValues(worksModel));
    }

    public void addWorkDone(WorksModel worksModel){
        WorksRepository.getInstance(mContext).getmWorkListDone().add(worksModel);
//        mDataBase.insert(TaskDbSchema.TaskTable.NAME, null, getContentValuesDone(worksModel));
    }

    public void deleteWork(UUID id){
//        int delNum = -1;
//        WorksModel wm = null;
//        for(int i=0;i<WorksRepository.getInstance().getmWorksList().size();i++) {
//            if(WorksRepository.getInstance().getmWorksList().get(i).getId().equals(id)) {
//                delNum = i;
//                wm = WorksRepository.getInstance().getmWorksList().get(i);
//                WorksRepository.getInstance().getmWorksList().remove(i);
//                Log.d("MyTag", "remove from all tasks");
//            }
//        }
//        WorksRepository.getInstance().getmWorkListDone().remove(wm);
        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
        mDataBase.delete(TaskDbSchema.TaskTable.NAME, whereClause, new String[] {id.toString()});
    } //deleteWork

    public void deleteWorkDone(UUID id){
        WorksModel wm = null;
        for(int i=0;i<WorksRepository.getInstance(mContext).getmWorksList().size();i++) {
            if(WorksRepository.getInstance(mContext).getmWorksList().get(i).getId().equals(id)) {
                wm = WorksRepository.getInstance(mContext).getmWorkListDone().get(i);
                WorksRepository.getInstance(mContext).getmWorksList().remove(i);
                Log.d("MyTag", "remove from all tasks");
            }
        }
        WorksRepository.getInstance(mContext).getmWorkListDone().remove(wm);
    }

    public void update(WorksModel worksModel){
        ContentValues contentValues = getContentValues(worksModel);
        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});

////         //estefade az place holder jahate jelogiri az injection
//        String whereClause = CrimeDbSchema.CrimeTable.Cols.UUID + " = ? AND _id > ?";
//        mDataBase.update(CrimeDbSchema.CrimeTable.NAME, contentValues, whereClause, new String[] {crime.getId().toString(), "8"});
    }

    public void editWork(UUID id, WorksModel worksModel){
//        for(int i=0;i<WorksRepository.getInstance().getmWorksList().size();i++) {
//            if(WorksRepository.getInstance().getmWorksList().get(i).getId().equals(id)) {
//                Log.d("MyTag2", "edit");
//                WorksRepository.getInstance().getmWorksList().set(i, worksModel);
//            }
//        }
        ContentValues contentValues = getContentValues(worksModel);
        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
//        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});
        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {id.toString()});

    }//editWork

    public void editWorkDone(UUID id, WorksModel worksModel){
        ContentValues contentValues = getContentValues(worksModel);
        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? " + " And " + TaskDbSchema.TaskTable.Cols.DONE + " = 1";
        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {id.toString()});

//        for(int i=0;i<WorksRepository.getInstance().getmWorkListDone().size();i++) {
//            if(WorksRepository.getInstance().getmWorkListDone().get(i).getId().equals(id)) {
//                Log.d("MyTag2", "edit done");
//                WorksRepository.getInstance().getmWorkListDone().set(i, worksModel);
//            }
//        }
    }//editWorkDone

    public void insertContact(Contact contact){
//        String query = "select * from " + TaskDbSchema.ContactsTable.NAME;
//        Cursor cursor = mDataBase.rawQuery(query, null);
//        int numOfContacts = cursor.getCount();
        mDataBase = new TaskBaseHelper(mContext).getWritableDatabase();
        mDataBase.insert(TaskDbSchema.ContactsTable.NAME, null, getContentValuesContacts(contact));
    }

    public String searchContactPassword(String username){
//        mDataBase = new TaskBaseHelper(mContext).getReadableDatabase();

        String[] columns = new String[] {TaskDbSchema.ContactsTable.Cols.USERNAME, TaskDbSchema.ContactsTable.Cols.PASSWORD};
        Cursor cursor = mDataBase.query(TaskDbSchema.ContactsTable.NAME,
                columns,
                null,
                null,
                null,
                null,
                null);

        String user;
        String pass = "not found";
        if (cursor.moveToFirst()){
            do {
                user = cursor.getString(0);
                if (user.equals(username)){
                    pass = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return pass;
    }//searchContactPassword
}
