package ir.applinkfinder.hw6;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private static final String ARG_WORK_ID_EDIT_FRAG = "arg_work_id_edit_frag";
    private EditText mEditTextTitle;
    private EditText mEditTextDetail;
    private ImageButton mButtonEditDone;

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private UUID work_ID;

    public EditFragment() {
        // Required empty public constructor
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
        mButtonEditDone = view.findViewById(R.id.imagebutton_edit_done);

        mEditTextTitle.setText(WorksRepository.getInstance(getActivity()).getWork(work_ID).getTitle());
        mEditTextDetail.setText(WorksRepository.getInstance(getActivity()).getWork(work_ID).getDetail());

        mButtonEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
                Toast.makeText(getActivity(), "Task Edited", Toast.LENGTH_SHORT).show();
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
    public void editItem(){
        mListFragment = new ListFragment();

        WorksRepository mWorksRepository = WorksRepository.getInstance(getActivity());
        List<WorksModel> list = mWorksRepository.getmWorksList();
        List<WorksModel> listDone = mWorksRepository.getmWorkListDone();

        mAdapter = mListFragment.new MyAdapter(list);
        mAdapterDone = mListFragment.new MyAdapter(listDone);

        String newTitle  = mEditTextTitle.getText().toString();
        String newDetail = mEditTextDetail.getText().toString();
        WorksModel worksModel = new WorksModel();
        worksModel.setTitle(newTitle);
        worksModel.setDetail(newDetail);
//        Date newDate = mEditTextDate

//        mWorksRepository.editWork(work_ID, newTitle, newDetail);
//        mWorksRepository.editWorkDone(work_ID, newTitle, newDetail);

        mWorksRepository.editWork(work_ID, worksModel);
//        WorksRepository.getInstance(getActivity()).update(worksModel);

        mWorksRepository.editWorkDone(work_ID, worksModel);

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
