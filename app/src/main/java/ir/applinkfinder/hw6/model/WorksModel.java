package ir.applinkfinder.hw6.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;
import java.util.UUID;

@Entity
public class WorksModel {

    // SQLite Fields:
//    private UUID Id;
//    private String mTitle;
//    private String mDetail;
//    private Date mDate;
//    private Date mHour;
//    private boolean mDone;
//    private int mContactId;

    @org.greenrobot.greendao.annotation.Id(autoincrement = true)
    private Long ORMId;

    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID Id;

    private String title;
    private String detail;
    private Date date;
    private Date hour;
    private boolean done;
    private Long contactId; // int bud be long tabdil kardam

//    @ToOne(joinProperty = "contactId")
//    private Contact contact; // in field ra tebqe mesal e kashfi ezafe kardam

    public String getPhotoName(){
        return "IMG" + Id.toString() + ".jpg";
    }

    @Generated(hash = 1152772578)
    public WorksModel(Long ORMId, UUID Id, String title, String detail, Date date,
            Date hour, boolean done, Long contactId) {
        this.ORMId = ORMId;
        this.Id = Id;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.hour = hour;
        this.done = done;
        this.contactId = contactId;
    }

    @Keep
    public WorksModel() {
        this(UUID.randomUUID());
    }
    @Keep
    public WorksModel(UUID id) {
        Id = id;
    }

//    @Generated(hash = 1820215979)
//    public WorksModel() {
//    }

    public Long getORMId() {
        return this.ORMId;
    }

    public void setORMId(Long ORMId) {
        this.ORMId = ORMId;
    }

    public UUID getId() {
        return this.Id;
    }

    public void setId(UUID Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHour() {
        return this.hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public static class UuidConverter implements PropertyConverter<UUID, String> {

        @Override
        public UUID convertToEntityProperty(String databaseValue) {
            return UUID.fromString(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(UUID entityProperty) {
            return entityProperty.toString();
        }
    }


    // --------------------------------------------------------------
    // SQLite Fields:
//    private UUID Id;
//    private String mTitle;
//    private String mDetail;
//    private Date mDate;
//    private Date mHour;
//    private boolean mDone;
//    private int mContactId;


    // SQLIite
//    public int getContactId() {
//        return mContactId;
//    }
//
//    public void setContactId(int contactId) {
//        mContactId = contactId;
//    }
//
//    public WorksModel() {
//        this(UUID.randomUUID());
////    Id = UUID.randomUUID();
//    }
//
//    public WorksModel(UUID id) {
//        Id = id;
////        mDate = RandomData.randomDate();
//    }
//
//    public boolean isDone() {
//        return mDone;
//    }
//
//    public void setDone(boolean done) {
//        mDone = done;
//    }
//
//    public UUID getId() {
//        return Id;
//    }
//
//    public String getTitle() {
//        return mTitle;
//    }
//
//    public void setTitle(String title) {
//        mTitle = title;
//    }
//
//    public String getDetail() {
//        return mDetail;
//    }
//
//    public void setDetail(String detail) {
//        mDetail = detail;
//    }
//
//    public Date getDate() {
//        return mDate;
//    }
//
//    public void setDate(Date date) {
//        mDate = date;
//    }
//
//    public Date getHour() {
//        return mHour;
//    }
//
//    public void setHour(Date hour) {
//        mHour = hour;
//    }
}