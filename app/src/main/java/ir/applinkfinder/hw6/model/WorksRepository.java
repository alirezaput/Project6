package ir.applinkfinder.hw6.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorksRepository {
    public List<WorksModel> mWorksList;
    public List<WorksModel> mWorksListDone;

    private static WorksRepository instance;
    private static WorksRepository instanceDone;
    private static WorksRepository instanceUndone;


    private ArrayList<WorksModel> DoneWork;

    ///////////////////////////////////////////////////////////
//    public static CustomListListener mListener;
//    public interface CustomListListener{
//        void onListChanged(List<WorksModel> myList);
//    }
//    public static void setOnListChangeListener(CustomListListener listener){
//        mListener = listener;
//    }

    //////////////////////////////////////////////////////////


    // ----------------------------------------------------------------------------------------------
    private WorksRepository(){ // 1 constructor e private misazim ke dg kasi natune new sh kone
        mWorksList = new ArrayList<>();
        mWorksListDone = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            WorksModel worksModel = new WorksModel();
//            worksModel.setTitle("Title: " + i);
//            worksModel.setDetail("Details: " + i);
////            worksModel.setDate(new Date());
//            worksModel.setHour("Hour: " + i);
////           contactsModel.setSolved(i % 2 == 0); // yeki dar mian true false midahad
//            mWorksList.add(worksModel);
//        }
    }//WorksRepository Constructor

    public List<WorksModel> getDoneWork(){
        WorksRepository worksRepository = WorksRepository.getInstanceDone();
        List<WorksModel> DoneWorkList = new ArrayList<>();
        for (int i = 0; i < worksRepository.getmWorksList().size(); i++){
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

    public List<WorksModel> getAllWork(){
        WorksRepository worksRepository = WorksRepository.getInstance();
        List<WorksModel> AllWorkList = new ArrayList<>();
        for (int i = 0; i < worksRepository.getmWorksList().size(); i++){
            AllWorkList.add(worksRepository.getmWorksList().get(i));
        }
        return AllWorkList;
    }


    public static WorksRepository getInstance() {
        if (instance == null){ // baraye ine ke faqat 1 bar new she
            instance = new WorksRepository();
        }
        return instance;
    }

    public static WorksRepository getInstanceDone() {
        if (instanceDone == null){ // baraye ine ke faqat 1 bar new she
            instanceDone = new WorksRepository();
        }
        return instanceDone;
    }

    public static WorksRepository getInstanceUndone() {
        if (instanceUndone == null){ // baraye ine ke faqat 1 bar new she
            instanceUndone = new WorksRepository();
        }
        return instanceUndone;
    }


    public List<WorksModel> getmWorksList() {
        return mWorksList;
    }

    public List<WorksModel> getmWorkListDone(){
        return mWorksListDone;
    }

//    public WorksModel getWork(UUID id){
//        for (WorksModel worksModel: mWorksList){
//            if (worksModel.getId().equals(id))
//                return worksModel;
//            }
//        return null;
//    }//getWork

    public WorksModel getWork(UUID id){
        for (WorksModel worksModel: mWorksList){
            if (worksModel.getId().equals(id))
                return worksModel;
        }
        return null;
    }//getWork

    public WorksModel getWorkDone(UUID id){
        for (WorksModel worksModel: mWorksListDone){
            if (worksModel.getId().equals(id))
                return worksModel;
        }
        return null;
    }//getWork

    public void addWork(WorksModel worksModel){
        WorksRepository.getInstance().getmWorksList().add(worksModel);
    }

    public void addWorkDone(WorksModel worksModel){
        WorksRepository.getInstanceDone().getmWorkListDone().add(worksModel);
    }

    public void deleteWork(String title, String detail){
        WorksRepository mWorksRepository = WorksRepository.getInstance();
//        WorksRepository.getInstance().getmWorksList().remove(worksModel);
        for(int i=0;i<mWorksRepository.getmWorksList().size();i++) {
            if(mWorksRepository.getmWorksList().get(i).getTitle().contains(title) &&
                    mWorksRepository.getmWorksList().get(i).getDetail().contains(detail))
            {
                mWorksRepository.getmWorksList().remove(i);
            }
        }
    } //deleteWork



    public void deleteWorkDone(String title, String detail){
        WorksRepository mWorksRepository = WorksRepository.getInstanceDone();
//        WorksRepository.getInstance().getmWorksList().remove(worksModel);
        for(int i=0;i<mWorksRepository.getmWorkListDone().size();i++) {
            if(mWorksRepository.getmWorkListDone().get(i).getTitle().contains(title) &&
                    mWorksRepository.getmWorkListDone().get(i).getDetail().contains(detail))
            {
                mWorksRepository.getmWorkListDone().remove(i);
            }
        }
    } //deleteWork



    public void editWork(String newTitle, String newDetail){
        WorksRepository mWorksRepository = WorksRepository.getInstance();
        for(int i=0;i<mWorksRepository.getmWorksList().size();i++) {
            if(mWorksRepository.getmWorksList().get(i).getTitle().contains(newTitle) &&
                    mWorksRepository.getmWorksList().get(i).getDetail().contains(newDetail))
            {
                WorksModel worksModel = new WorksModel();
                worksModel.setTitle(newTitle);
                worksModel.setDetail(newDetail);
                mWorksRepository.getmWorksList().set(i, worksModel);
            }
        }
    }//editWork

    public void editWorkDone(String newTitle, String newDetail){
        WorksRepository mWorksRepository = WorksRepository.getInstanceDone();
        for(int i=0;i<mWorksRepository.getmWorkListDone().size();i++) {
            if(mWorksRepository.getmWorkListDone().get(i).getTitle().contains(newTitle) &&
                    mWorksRepository.getmWorkListDone().get(i).getDetail().contains(newDetail))
            {
                WorksModel worksModel = new WorksModel();
                worksModel.setTitle(newTitle);
                worksModel.setDetail(newDetail);
                mWorksRepository.getmWorkListDone().set(i, worksModel);
            }
        }
    }//editWork

}