package ir.applinkfinder.hw6.model;

import java.util.Date;
import java.util.UUID;

public class WorksModel {

    private UUID Id;
    private String mTitle;
    private String mDetail;
    private Date mDate;
    private String mHour;
    private boolean mDone;

    public WorksModel() {
//        this(UUID.randomUUID());
    Id = UUID.randomUUID();
    }

//    public WorksModel(UUID id) {
//        Id = id;
////        mDate = RandomData.randomDate();
//    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public UUID getId() {
        return Id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }
}
