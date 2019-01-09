package ir.applinkfinder.hw6.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.Date;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;

public class TaskCursorWrapper extends CursorWrapper {

    // constructor
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public WorksModel getTask() {
        UUID uuid = UUID.fromString(getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID)));
        String title = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
        String detail = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DETAIL));
        Date date = new Date(getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.DATE)));
        Date hour = new Date(getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.HOUR)));
        boolean isDone = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.DONE)) != 0;

        WorksModel worksModel = new WorksModel(uuid);
        worksModel.setTitle(title);
        worksModel.setDetail(detail);
        worksModel.setDate(date);
        worksModel.setHour(hour);
        worksModel.setDone(isDone);

        return worksModel;
    }//getTask

    public WorksModel getDoneTask() {
        Log.d("Tag22", "getDoneTask isDone"); //test
        UUID uuid = UUID.fromString(getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID)));
        String title = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
        String detail = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DETAIL));
        Date date = new Date(getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.DATE)));
        Date hour = new Date(getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.HOUR)));
//        boolean isDone = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.DONE)) != 0;
        boolean isDone = true;
        WorksModel worksModel = new WorksModel(uuid);
        worksModel.setTitle(title);
        worksModel.setDetail(detail);
        worksModel.setDate(date);
        worksModel.setHour(hour);
        worksModel.setDone(isDone);
        return worksModel;
    }//getDoneTask
}