package ir.applinkfinder.hw6.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorksRepository {
    public List<WorksModel> mWorksList;
    public List<WorksModel> mWorksListDone;

    private static WorksRepository instance;
//    private static WorksRepository instanceDone;
    private static WorksRepository instanceUndone;

    private ArrayList<WorksModel> DoneWork;
    // ----------------------------------------------------------------------------------------------
    private WorksRepository(){ // 1 constructor e private misazim ke dg kasi natune new sh kone
        mWorksList = new ArrayList<>();
        mWorksListDone = new ArrayList<>();
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

    public static WorksRepository getInstance() {
        if (instance == null){ // baraye ine ke faqat 1 bar new she
            instance = new WorksRepository();
        }
        return instance;
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

    public WorksModel getWork(UUID id){
        for (WorksModel worksModel: mWorksList){
            if (worksModel.getId().equals(id))
                return worksModel;
        }
        return null;
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
        WorksRepository.getInstance().getmWorksList().add(worksModel);
    }

    public void addWorkDone(WorksModel worksModel){
        WorksRepository.getInstance().getmWorkListDone().add(worksModel);
    }

    public void deleteWork(UUID id){
        int delNum = -1;
        WorksModel wm = null;
        for(int i=0;i<WorksRepository.getInstance().getmWorksList().size();i++) {
            if(WorksRepository.getInstance().getmWorksList().get(i).getId().equals(id)) {
                delNum = i;
                wm = WorksRepository.getInstance().getmWorksList().get(i);
                WorksRepository.getInstance().getmWorksList().remove(i);
                Log.d("MyTag", "remove from all tasks");
            }
        }

        WorksRepository.getInstance().getmWorkListDone().remove(wm);
    } //deleteWork

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