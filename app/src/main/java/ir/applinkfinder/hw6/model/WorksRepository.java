package ir.applinkfinder.hw6.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.database.TaskBaseHelper;
import ir.applinkfinder.hw6.database.TaskCursorWrapper;
import ir.applinkfinder.hw6.database.TaskDbSchema;

public class WorksRepository {
    public List<WorksModel> mWorksList;
    public List<WorksModel> mWorksListDone;

    private SQLiteDatabase mDataBase;
    private Context mContext;

    private static WorksRepository instance;
//    private static WorksRepository instanceDone;
    private static WorksRepository instanceUndone;

    private ArrayList<WorksModel> DoneWork;
    // ----------------------------------------------------------------------------------------------
    private WorksRepository(Context context){ // 1 constructor e private misazim ke dg kasi natune new sh kone
//        mWorksList = new ArrayList<>();
//        mWorksListDone = new ArrayList<>();
        mContext = context.getApplicationContext();
        mDataBase = new TaskBaseHelper(mContext).getWritableDatabase(); // qabl az getWritableDatabase, onCreate e CrimeBaseHelper ejra mishe
    }//WorksRepository Constructor

    public List<WorksModel> getDoneWork(){
        WorksRepository worksRepository = WorksRepository.getInstance();
        List<WorksModel> DoneWorkList = new ArrayList<>();
        for (int i = 0; i < worksRepository.getmWorkListDone().size(); i++){
            if (worksRepository.getmWorksList().get(i).isDone()){
                DoneWorkList.add(worksRepository.getmWorksList().get(i));
            }
        }
        return DoneWorkList;
    }

    public List<WorksModel> getUndoneWork(){
        WorksRepository worksRepository = WorksRepository.getInstanceUndone();
        List<WorksModel> UndoneWorkList = new ArrayList<>();
        for (int i = 0; i < worksRepository.getmWorksList().size(); i++){
            if (!worksRepository.getmWorksList().get(i).isDone()){
                UndoneWorkList.add(worksRepository.getmWorksList().get(i));
            }
        }
        return UndoneWorkList;
    }

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
//        return mWorksList;
        List<WorksModel> mWorkList = new ArrayList<>();
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
                mWorkList.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }//while
        }// try
        finally {
            taskCursorWrapper.close();
        }

        return mWorksList;
    }

    public List<WorksModel> getmWorkListDone(){
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
        contentValues.put(TaskDbSchema.TaskTable.Cols.DATE, worksModel.getDate().getTime()); //getTime haman TimeStamp ra bar migardune
        contentValues.put(TaskDbSchema.TaskTable.Cols.DONE, worksModel.isDone() ? 1 : 0); //if ebarat 1, true ---- else 0
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

    }//getWork



    public WorksModel getWorkDone(UUID id){
        for(int i=0;i<WorksRepository.getInstance().getmWorkListDone().size();i++) {
            if(WorksRepository.getInstance().getmWorkListDone().get(i).getId().equals(id)) {
                return WorksRepository.getInstance().getmWorkListDone().get(i);
            }
        }
        return null;
    }//getWorkDone

    public void addWork(WorksModel worksModel){
//        WorksRepository.getInstance().getmWorksList().add(worksModel);
        mDataBase.insert(TaskDbSchema.TaskTable.NAME, null, getContentValues(worksModel));
    }

    public void addWorkDone(WorksModel worksModel){
        WorksRepository.getInstance().getmWorkListDone().add(worksModel);
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

    public void update(WorksModel worksModel){
        ContentValues contentValues = getContentValues(worksModel);
        String whereClause = TaskDbSchema.TaskTable.Cols.UUID + " = ? ";
        mDataBase.update(TaskDbSchema.TaskTable.NAME, contentValues, whereClause, new String[] {worksModel.getId().toString()});

////         //estefade az place holder jahate jelogiri az injection
//        String whereClause = CrimeDbSchema.CrimeTable.Cols.UUID + " = ? AND _id > ?";
//        mDataBase.update(CrimeDbSchema.CrimeTable.NAME, contentValues, whereClause, new String[] {crime.getId().toString(), "8"});
    }

    public void editWork(UUID id, WorksModel worksModel){

        for(int i=0;i<WorksRepository.getInstance().getmWorksList().size();i++) {
            if(WorksRepository.getInstance().getmWorksList().get(i).getId().equals(id)) {
                Log.d("MyTag2", "edit");
                WorksRepository.getInstance().getmWorksList().set(i, worksModel);
            }
        }
    }//editWork

    public void editWorkDone(UUID id, WorksModel worksModel){

        for(int i=0;i<WorksRepository.getInstance().getmWorkListDone().size();i++) {
            if(WorksRepository.getInstance().getmWorkListDone().get(i).getId().equals(id)) {
                Log.d("MyTag2", "edit done");
                WorksRepository.getInstance().getmWorkListDone().set(i, worksModel);
            }
        }
    }//editWorkDone
}