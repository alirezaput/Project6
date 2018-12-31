package ir.applinkfinder.hw6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemToListFragment extends Fragment {

    private ArrayList<WorksModel> mWorksList;
    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private EditText mEditTextAddTitle;
    private EditText mEditTextAddDetail;
    private EditText mEditTextAddDate;
    private EditText mEditTextAddHour;
    private ImageButton mImageButtonAdd;

    private ImageButton mImageButtonDone;
    private ImageButton mImageButtonEdit;
    private ImageButton mImageButtonDelete;


    public AddItemToListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item_to_list, container, false);

        mEditTextAddTitle   = view.findViewById(R.id.edittext_add_title);
        mEditTextAddDetail  = view.findViewById(R.id.edittext_add_details);
        mEditTextAddDate    = view.findViewById(R.id.edittext_add_date);
        mEditTextAddHour    = view.findViewById(R.id.edittext_add_hour);
        mImageButtonAdd     = view.findViewById(R.id.imagebutton_add_done);

        mImageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });

        // ------------- ActionBar -----------

        // --------------- ActionBar----------

        return view;
    }//onCreateView

    // ----------------------------- insertItem Method: ----------------------------
    public void insertItem(){
        mListFragment = new ListFragment();

        WorksRepository mWorksRepository = WorksRepository.getInstance();
        List<WorksModel> list = mWorksRepository.getmWorksList();
        mAdapter = mListFragment.new MyAdapter(list);

        WorksModel worksModel = new WorksModel();
        worksModel.setTitle(mEditTextAddTitle.getText().toString());
        worksModel.setDetail(mEditTextAddDetail.getText().toString());
//        worksModel.setDate(new Date());
        worksModel.setHour(mEditTextAddHour.getText().toString());

        mWorksRepository.addWork(worksModel);

        mAdapter.notifyItemInserted(0);

        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
//            mAdapter.notifyItemInserted(0);
            mAdapter.notifyDataSetChanged();
        }
//        Toast.makeText(getActivity(), String.valueOf(mWorksRepository.getmWorksList().size()), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), R.string.toast_insert_item, Toast.LENGTH_SHORT).show();

    }//insertItem
    // --------------------------------------------------------------------------------------
}