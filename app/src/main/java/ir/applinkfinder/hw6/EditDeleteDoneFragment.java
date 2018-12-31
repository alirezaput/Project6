package ir.applinkfinder.hw6;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;
import ir.applinkfinder.hw6v3.model.WorksModel;
import ir.applinkfinder.hw6v3.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeleteDoneFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_DETAIL = "arg_detail";

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private EditText mEditTextTitle;
    private EditText mEditTextDetail;
    private ImageButton mImageButtonDone;
    private ImageButton mImageButtonDelete;
    private ImageButton mImageButtonEdit;

    private String title;
    private String detail;

    public EditDeleteDoneFragment() {
        // Required empty public constructor
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        String title = getArguments().getString("title");
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setTitle(title);
//        alertDialogBuilder.setMessage("Are you sure?");
//
//        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // on success
//            }
//        });
//
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        return alertDialogBuilder.create();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            detail = getArguments().getString(ARG_DETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_delete_done, container, false);
        mEditTextTitle     = view.findViewById(R.id.edittext_edit_done_remove_title);
        mEditTextDetail    = view.findViewById(R.id.edittext_edit_done_remove_detail);
        mImageButtonDone   = view.findViewById(R.id.imagebutton_done);
        mImageButtonDelete = view.findViewById(R.id.imagebutton_delete);
        mImageButtonEdit   = view.findViewById(R.id.imagebutton_edit);

        mEditTextTitle.setText(title);
        mEditTextDetail.setText(detail);





        mImageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
            }
        });

        mImageButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToDone();
            }
        });
        return view;
    }//onCreateView

//    // ----------------------------- deleteItem Method: ----------------------------
//    public void deleteItem(String title, String detail){
//        mListFragment = new ListFragment();
//
//        WorksRepository mWorksRepository = WorksRepository.getInstance();
//        WorksRepository mWorksRepositoryDone = WorksRepository.getInstanceDone();
//
//        List<WorksModel> list = mWorksRepository.getmWorksList();
//        List<WorksModel> listDone = mWorksRepositoryDone.getmWorkListDone();
//
//        mAdapter = mListFragment.new MyAdapter(list);
//        mAdapterDone = mListFragment.new MyAdapter(listDone);
//
//
//        mWorksRepository.deleteWork(title, detail);
//        mWorksRepositoryDone.deleteWorkDone(title, detail);
////        Toast.makeText(getActivity(), String.valueOf(mWorksRepository.getmWorksList().size()), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), R.string.toast_delete_task, Toast.LENGTH_SHORT).show();
//
//        mAdapter.notifyItemRemoved(0);
//        if (mAdapter == null) {
//            mListFragment.mRecyclerView.setAdapter(mAdapter);
//            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
//        }
//        else{
//            mAdapter.notifyDataSetChanged();
//            mAdapterDone.notifyDataSetChanged();
//        }
//
////        Toast.makeText(getActivity(), String.valueOf(mWorksRepository.getmWorksList().size()), Toast.LENGTH_SHORT).show();
//
//    }//deleteItem
//    // --------------------------------------------------------------------------------------

    // --------------------------- editItem
    public void editItem(){
        mListFragment = new ListFragment();

        WorksRepository mWorksRepository = WorksRepository.getInstance();
        List<WorksModel> list = mWorksRepository.getmWorksList();
        mAdapter = mListFragment.new MyAdapter(list);

        String newTitle  = mEditTextTitle.getText().toString();
        String newDetail = mEditTextDetail.getText().toString();

        mWorksRepository.editWork(newTitle, newDetail);
//        Toast.makeText(getActivity(), "ویرایش", Toast.LENGTH_SHORT).show();

//        mAdapter.notifyItemRemoved(0);
        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }
    // ------------------------------------------------------------

    public void changeToDone(){
        WorksRepository worksRepositoryDone = WorksRepository.getInstanceDone();

        mListFragment = new ListFragment();

        List<WorksModel> list = worksRepositoryDone.getmWorkListDone();
        mAdapter = mListFragment.new MyAdapter(list);

        WorksModel worksModel = new WorksModel();
        worksModel.setTitle(mEditTextTitle.getText().toString());
        worksModel.setDetail(mEditTextDetail.getText().toString());
//        worksModel.setDate(new Date());
//        worksModel.setHour(mEditTextAddHour.getText().toString());

        worksRepositoryDone.addWorkDone(worksModel);

        mAdapter.notifyItemInserted(0);

        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
//            mAdapter.notifyItemInserted(0);
            mAdapter.notifyDataSetChanged();
        }
//        Toast.makeText(getActivity(), String.valueOf(worksRepositoryDone.getmWorkListDone().size()), Toast.LENGTH_SHORT).show();
    }//changeToDone

    public static EditDeleteDoneFragment newInstance(String title, String detail){ //@NonNull yani crimeId nemitune Null bashe
        EditDeleteDoneFragment fragment = new EditDeleteDoneFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DETAIL, detail);
        fragment.setArguments(args);
        return fragment;
    }//newInstance
}
