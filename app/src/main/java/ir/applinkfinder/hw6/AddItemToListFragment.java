package ir.applinkfinder.hw6;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;

public class AddItemToListFragment extends DialogFragment {

    private static final String BUNDLE_CONTACT_ID = "bundle_contact_id";
    private ArrayList<WorksModel> mWorksList;
    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;
    private EditText mEditTextAddTitle;
    private EditText mEditTextAddDetail;
    private EditText mEditTextAddDate;
    private EditText mEditTextAddHour;
    private ImageButton mImageButtonAdd;
    private int contactId;

    public AddItemToListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // set Dialog Fragment Size and Title
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().setTitle("اضافه کردن برنامه جدید");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        contactId = ((MainActivity) getActivity()).getContactIDinMainActivity();

        // ------------- Toolbar -----------
        ActionBar myActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        RelativeLayout myLayout = new RelativeLayout(getActivity());
        TextView textview = new TextView(getActivity());
        textview.setText(R.string.actionbar_add_item_to_list_title);
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
                dismiss();
            }
        });

        // ------------- ActionBar -----------

        // --------------- ActionBar----------

        return view;
    }//onCreateView

    // ----------------------------- insertItem Method: ----------------------------
    public void insertItem(){
        mListFragment = new ListFragment();

//        WorksRepository mWorksRepository = WorksRepository.getInstance();
        WorksRepository mWorksRepository = WorksRepository.getInstance(getActivity());
        List<WorksModel> list = mWorksRepository.getmWorksList();
        mAdapter = mListFragment.new MyAdapter(list);

        int contactId = ((AddItemToListActivity) getActivity()).getContactIdInAddItem();
        WorksModel worksModel = new WorksModel();
        worksModel.setContactId(contactId);
        worksModel.setTitle(mEditTextAddTitle.getText().toString());
        worksModel.setDetail(mEditTextAddDetail.getText().toString());
//        worksModel.setDate(new Date());
//        worksModel.setHour(mEditTextAddHour.getText().toString());
        mWorksRepository.addWork(worksModel);

        mAdapter.notifyItemInserted(0);

        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
        }
        else{
//            mAdapter.notifyItemInserted(0);
            mAdapter.notifyDataSetChanged();
        }
        Toast.makeText(getActivity(), R.string.toast_insert_item, Toast.LENGTH_SHORT).show();

    }//insertItem
    // --------------------------------------------------------------------------------------
}