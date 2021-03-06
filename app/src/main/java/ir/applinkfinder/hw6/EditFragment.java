package ir.applinkfinder.hw6;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends DialogFragment {

    private static final String ARG_WORK_ID_EDIT_FRAG = "arg_work_id_edit_frag";
    private EditText mEditTextTitle;
    private EditText mEditTextDetail;
    private Button mButtonEditDone;

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private UUID work_ID;

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // set Dialog Fragment Size
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().setTitle("ویرایش برنامه");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            work_ID = (UUID) getArguments().getSerializable(ARG_WORK_ID_EDIT_FRAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        mEditTextTitle = view.findViewById(R.id.edittext_edit_title);
        mEditTextDetail = view.findViewById(R.id.edittext_edit_detail);
        mButtonEditDone = view.findViewById(R.id.button_edit_done);

        mEditTextTitle.setText(WorksRepository.getInstance(getActivity()).getWork(work_ID).getTitle());
        mEditTextDetail.setText(WorksRepository.getInstance(getActivity()).getWork(work_ID).getDetail());

        mButtonEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorksModel worksModel = WorksRepository.getInstance(getActivity()).getWork(work_ID);
                worksModel.setTitle(mEditTextTitle.getText().toString());
                worksModel.setDetail(mEditTextDetail.getText().toString());
                editItem(worksModel);
                Toast.makeText(getActivity(), "Task Edited", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }//onCreateView

    @Override
    public void onPause() {
        super.onPause();
        WorksModel worksModel = WorksRepository.getInstance(getActivity()).getWork(work_ID);
        WorksRepository.getInstance(getActivity()).update(worksModel);
    }

    // --------------------------- editItem
    public void editItem(WorksModel worksModel){
        mListFragment = new ListFragment();

        WorksRepository mWorksRepository = WorksRepository.getInstance(getActivity());
        List<WorksModel> list = mWorksRepository.getmWorksList();
        List<WorksModel> listDone = mWorksRepository.getmWorkListDone();

        mAdapter = mListFragment.new MyAdapter(list);
        mAdapterDone = mListFragment.new MyAdapter(listDone);

        WorksRepository.getInstance(getActivity()).editWork(worksModel);

        mWorksRepository.editWorkDone(worksModel);

//        mAdapter.notifyItemRemoved(0);
        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

        if (mAdapterDone == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
        }
        else{
            mAdapterDone.notifyDataSetChanged();
        }
    }
    // ------------------------------------------------------------

    public static EditFragment newInstance(UUID id){ //@NonNull yani crimeId nemitune Null bashe
        EditFragment fragment = new EditFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_WORK_ID_EDIT_FRAG, id);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

}
