package ir.applinkfinder.hw6;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private static final String ARG_TAB_TYPE = "arg_tab_type";
    public RecyclerView mRecyclerView;
    private MyAdapter mCrimeAdapter;
    private List<WorksModel> mWorksList;
    private FloatingActionButton mFloatingActionButtonAdd;
    private ImageView mImageViewEmptyList;
    private String firstLetter;

    private int tabNum = 0;
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mImageViewEmptyList = view.findViewById(R.id.imageView_empty_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFloatingActionButtonAdd = view.findViewById(R.id.floatingActionButton_add);

        //Add
        mFloatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "AAA", Toast.LENGTH_SHORT).show();
                Intent intent = AddItemToListActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }//onCreateView

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setVisibility(View.VISIBLE);
        mImageViewEmptyList.setVisibility(View.GONE);
        updateUI();
        tabNum = getArguments().getInt(ARG_TAB_TYPE);

        WorksRepository worksRepository = WorksRepository.getInstance();
        if (tabNum == 0){
//            mWorksList = worksRepository.getAllWork();
            mWorksList = worksRepository.getmWorksList();


            if (mWorksList.size()==0){
                mRecyclerView.setVisibility(View.GONE);
                mImageViewEmptyList.setVisibility(View.VISIBLE);
            }
            else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mImageViewEmptyList.setVisibility(View.GONE);
            }
        }
        else if (tabNum == 1){
//            mWorksList = worksRepository.getDoneWork();
            mWorksList = worksRepository.getmWorkListDone();

            if (mWorksList.size()==0){
                mRecyclerView.setVisibility(View.GONE);
                mImageViewEmptyList.setVisibility(View.VISIBLE);
            }
            else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mImageViewEmptyList.setVisibility(View.GONE);
            }
        }
//        else
//            mWorksList = worksRepository.getUndoneWork();

        //setAdapter
        MyAdapter myAdapter = new MyAdapter(mWorksList);
        mRecyclerView.setAdapter(myAdapter);
//        myAdapter(mWorksList);
    }

    private void updateUI() {
        WorksRepository worksRepository = WorksRepository.getInstance();
        List<WorksModel> works = worksRepository.getmWorksList();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new MyAdapter(works);
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimes(works);
            mCrimeAdapter.notifyDataSetChanged();
        }
    }//updateUI

    // View Holder
    private class CrimeHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDetailTextView;

        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private ImageView mImageViewGmailIcon;
//        private ImageView letterImageView;

        private WorksModel mWorkModel;

        public CrimeHolder(@NonNull final View itemView) {
            super(itemView);

            mTitleTextView      = itemView.findViewById(R.id.list_item_crime_title);
            mDateTextView       = itemView.findViewById(R.id.list_item_crime_date);
            mDetailTextView     = itemView.findViewById(R.id.list_item_crimed_detail);
            mImageViewGmailIcon = itemView.findViewById(R.id.gmailitem_letter);

//            Toast.makeText(getActivity(), String.valueOf(mCrimesss.getId()), Toast.LENGTH_SHORT).show();

//            mCrimeAdapter.notifyDataSetChanged();
//             Edit - Delete - Done
            itemView.setOnClickListener(new View.OnClickListener() { // har jaye view click kardim, listener kari anjam dahad
                @Override
                public void onClick(View v) {
                    Intent intent = EditDeleteDoneActivity.newIntent(getActivity(), mWorkModel.getId());
                    startActivity(intent);
                }
            });

        }

        public void bind(WorksModel worksModel) {
            mWorkModel = worksModel;
            mTitleTextView.setText(worksModel.getTitle());
            mDetailTextView.setText(worksModel.getDetail());
//            mDateTextView.setText(worksModel.getDate().toString());
//            mSolvedImageView.setVisibility(worksModel.isSolved() == true ? View.VISIBLE : View.GONE);

            if (worksModel.getTitle().matches("")) {
                firstLetter = " ";
            }
            else{
                firstLetter = String.valueOf(worksModel.getTitle().charAt(0));
            }
            //        Create a new TextDrawable for our image's background
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, generator.getRandomColor());

            mImageViewGmailIcon.setImageDrawable(drawable);
        }
    }
    // --------------------------------- View Holder


    // ----------------------------- Adapter
    public class MyAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<WorksModel> mCrimes;
        private String letter;
        ColorGenerator generator = ColorGenerator.MATERIAL;

        public MyAdapter(List<WorksModel> crimes) {
            mCrimes = crimes;
        }

        public void setCrimes(List<WorksModel> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            CrimeHolder crimeHolder = new CrimeHolder(view);
            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            WorksModel crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }//Adapter


    public static ListFragment newInstance(int tabType){ //@NonNull yani crimeId nemitune Null bashe
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TAB_TYPE, tabType);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

}//class