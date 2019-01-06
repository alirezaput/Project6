package ir.applinkfinder.hw6;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeleteDoneFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_DETAIL = "arg_detail";
//    private static final String ARG_WORKMODEL = "arg_workmodel";
    private static final String ARG_WORK_ID = "arg_work_id";

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private TextView mTextViewTitle;
    private TextView mTextViewDetail;
    private TextView mTextViewDate;
    private TextView mTextHour;

    private ImageButton mImageButtonDone;
    private ImageButton mImageButtonDelete;
    private ImageButton mImageButtonEdit;

//    private String title;
//    private String detail;
//    private WorksModel mWorksModel;
    private UUID work_id;

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
//            title = getArguments().getString(ARG_TITLE);
//            detail = getArguments().getString(ARG_DETAIL);
//            mWorksModel = (WorksModel) getArguments().getSerializable(ARG_WORKMODEL);
            work_id = (UUID) getArguments().getSerializable(ARG_WORK_ID);
        }

        // ------------- Toolbar -----------
        ActionBar myActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        RelativeLayout myLayout = new RelativeLayout(getActivity());
        TextView textview = new TextView(getActivity());
        textview.setText(R.string.actionbar_editdeletedone_title);
        textview.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams textviewDetails =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        textview.setLayoutParams(textviewDetails);
        textview.setGravity(Gravity.RIGHT);
        textview.setTextSize(25);
        textview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/BNazanin_0.ttf"));

        myActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        myActionBar.setCustomView(textview);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_delete_done, container, false);
        mTextViewTitle     = view.findViewById(R.id.edittext_edit_done_remove_title);
        mTextViewDetail    = view.findViewById(R.id.edittext_edit_done_remove_detail);
        mImageButtonDone   = view.findViewById(R.id.imagebutton_done);
        mImageButtonDelete = view.findViewById(R.id.imagebutton_delete);
        mImageButtonEdit   = view.findViewById(R.id.imagebutton_edit);

//        mEditTextTitle.setText(title);
//        mEditTextDetail.setText(detail);

        WorksModel wm = WorksRepository.getInstance().getWork(work_id);
        String s = wm.getTitle();
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        mTextViewTitle.setText(WorksRepository.getInstance().getWork(work_id).getTitle());
        mTextViewDetail.setText(WorksRepository.getInstance().getWork(work_id).getDetail());
//        mEditTextTitle.setText(mWorksModel.getTitle());
//        mEditTextDetail.setText(mWorksModel.getDetail());

        final FragmentManager fragmentManager = getFragmentManager();
        final MyDialogFragment myDialogFragment = new MyDialogFragment();


        mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myDialogFragment.newInstance(title, detail).show(fragmentManager, "Custom Dialog Manager");
                myDialogFragment.newInstance(work_id).show(fragmentManager, "Custom Dialog Manager");
            }
        });


        mImageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditFragment editFragment = EditFragment.newInstance(work_id);
//                AddItemToListFragment editFragment = new AddItemToListFragment();
                fragmentTransaction.replace(R.id.container_edit_delete_done, editFragment);
                fragmentTransaction.commit();
//                editItem();
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

//    // --------------------------- editItem
//    public void editItem(){
//        mListFragment = new ListFragment();
//
//        WorksRepository mWorksRepository = WorksRepository.getInstance();
//        List<WorksModel> list = mWorksRepository.getmWorksList();
//        List<WorksModel> listDone = mWorksRepository.getmWorkListDone();
//
//        mAdapter = mListFragment.new MyAdapter(list);
//        mAdapterDone = mListFragment.new MyAdapter(listDone);
//
//        String newTitle  = mTextViewTitle.getText().toString();
//        String newDetail = mEditTextDetail.getText().toString();
////        Date newDate = mEditTextDate
//
//        mWorksRepository.editWork(work_id, newTitle, newDetail);
//        mWorksRepository.editWorkDone(work_id, newTitle, newDetail);
//
////        mAdapter.notifyItemRemoved(0);
//        if (mAdapter == null) {
//            mListFragment.mRecyclerView.setAdapter(mAdapter);
//        }
//        else{
//            mAdapter.notifyDataSetChanged();
//        }
//
//        if (mAdapterDone == null) {
//            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
//        }
//        else{
//            mAdapterDone.notifyDataSetChanged();
//        }
//    }
    // ------------------------------------------------------------

    public void changeToDone(){

        mListFragment = new ListFragment();

        List<WorksModel> list = WorksRepository.getInstance().getmWorksList();
        mAdapter = mListFragment.new MyAdapter(list);

        List<WorksModel> listDone = WorksRepository.getInstance().getmWorkListDone();
        mAdapterDone = mListFragment.new MyAdapter(listDone);

        WorksRepository.getInstance().getWork(work_id).setTitle(mTextViewTitle.getText().toString());
        WorksRepository.getInstance().getWork(work_id).setDetail(mTextViewDetail.getText().toString());

        WorksModel currentWorksModel = WorksRepository.getInstance().getWork(work_id);

        WorksRepository.getInstance().addWorkDone(currentWorksModel);

        Toast.makeText(getActivity(), "Done Number: " + String.valueOf(WorksRepository.getInstance().getmWorkListDone().size()), Toast.LENGTH_SHORT).show();

        mAdapter.notifyItemInserted(0);

        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
//            mAdapter.notifyItemInserted(0);
            mAdapter.notifyDataSetChanged();
        }

        if (mAdapterDone == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
        }
        else{
//            mAdapter.notifyItemInserted(0);
            mAdapterDone.notifyDataSetChanged();
        }

//        Toast.makeText(getActivity(), String.valueOf(worksRepositoryDone.getmWorkListDone().size()), Toast.LENGTH_SHORT).show();
    }//changeToDone

    public static EditDeleteDoneFragment newInstance(UUID id){ //@NonNull yani crimeId nemitune Null bashe
        EditDeleteDoneFragment fragment = new EditDeleteDoneFragment();

        Bundle args = new Bundle();
//        args.putString(ARG_TITLE, title);
//        args.putString(ARG_DETAIL, detail);
//        args.putSerializable(ARG_WORKMODEL, worksModel);
        args.putSerializable(ARG_WORK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }//newInstance
}
