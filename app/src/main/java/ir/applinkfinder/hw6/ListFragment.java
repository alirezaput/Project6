package ir.applinkfinder.hw6;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.List;

import ir.applinkfinder.hw6.model.DaoSession;
import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksModelDao;
import ir.applinkfinder.hw6.model.WorksRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private static final String ARG_TAB_TYPE = "arg_tab_type";
    private static final String ARG_CONTACT_ID = "arg_contact_id";

    public RecyclerView mRecyclerView;
    private MyAdapter mCrimeAdapter;
    private List<WorksModel> mWorksList;
    private FloatingActionButton mFloatingActionButtonAdd;
    private ImageView mImageViewEmptyList;
    private String firstLetter;
    private long contactId;
//    private SQLiteDatabase mDataBase;
    private WorksModelDao worksModelDao;

    private WorksModel mTask;

    private int tabNum = 0;
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        DaoSession daoSession = (App.getApp()).getDaoSession();
        WorksModelDao worksModelDao = daoSession.getWorksModelDao();
        mCrimeAdapter = new MyAdapter(worksModelDao.loadAll());
        // ------------- Toolbar -----------
        ActionBar myActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        RelativeLayout myLayout = new RelativeLayout(getActivity());
        TextView textview = new TextView(getActivity());
        textview.setText(R.string.actionbar_listfragment_title);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_task:
                Intent intent = AddItemToListActivity.newIntent(getActivity(), contactId);
                startActivity(intent);
                return true;

            case R.id.delete_all_tasks: // ezafe kardan e subtitle be menu
                // ORM
                DaoSession daoSession = (App.getApp()).getDaoSession();
                WorksModelDao worksModelDao = daoSession.getWorksModelDao();
                worksModelDao.deleteAll();

                // SQLite
//                mDataBase = new TaskBaseHelper(getActivity()).getWritableDatabase(); // qabl az getWritableDatabase, onCreate e CrimeBaseHelper ejra mishe
//                mDataBase.delete(TaskDbSchema.TaskTable.NAME, null, null);
                return true;

            case R.id.log_off:
//                Intent intent1 = MainActivity.newIntent(getActivity());
//                startActivity(intent1);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                Intent intent = AddItemToListActivity.newIntent(getActivity(), contactId);
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
        contactId = getArguments().getLong(ARG_CONTACT_ID);
//        mTask = WorksRepository.getInstance(getActivity()).getWork(UUID?);
        WorksRepository worksRepository = WorksRepository.getInstance(getActivity());
        if (tabNum == 0){

//            mWorksList = worksRepository.getAllWork();
            mWorksList = worksRepository.getmWorksList();

            if(mWorksList != null){
                if (mWorksList.size()==0){
                    mRecyclerView.setVisibility(View.GONE);
                    mImageViewEmptyList.setVisibility(View.VISIBLE);
                }
                else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mImageViewEmptyList.setVisibility(View.GONE);
                }
            }
        }
        else if (tabNum == 1){
////            mWorksList = worksRepository.getDoneWork();
            mWorksList = worksRepository.getmWorkListDone();
//            mWorksList = worksRepository.getmWorksList();

            if (mWorksList.size()==0){
                mRecyclerView.setVisibility(View.GONE);
                mImageViewEmptyList.setVisibility(View.VISIBLE);
            }
            else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mImageViewEmptyList.setVisibility(View.GONE);
            }
        }
        //setAdapter
        MyAdapter myAdapter = new MyAdapter(mWorksList);
        mRecyclerView.setAdapter(myAdapter);
//        myAdapter(mWorksList);
    }//onResume

    public void updateUI() {
        WorksRepository worksRepository = WorksRepository.getInstance(getActivity());
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
        private Button mButtonShareTask;

        private WorksModel mWorkModel;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTextView      = itemView.findViewById(R.id.list_item_crime_title);
            mDateTextView       = itemView.findViewById(R.id.list_item_crime_date);
            mDetailTextView     = itemView.findViewById(R.id.list_item_crimed_detail);
            mImageViewGmailIcon = itemView.findViewById(R.id.gmailitem_letter);
            mButtonShareTask    = itemView.findViewById(R.id.button_share_task);

//            mCrimeAdapter.notifyDataSetChanged();
//             Edit - Delete - Done
//            Log.i("UUID", String.valueOf(mWorkModel.getId())+"");

            itemView.setOnClickListener(new View.OnClickListener() { // har jaye view click kardim, listener kari anjam dahad
                @Override
                public void onClick(View v) {
                    Intent intent = EditDeleteDoneActivity.newIntent(getActivity(), mWorkModel.getId());
                    startActivity(intent);
                }
            });


            mButtonShareTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //                Intent reportIntent = new Intent();
//                reportIntent.setAction(Intent.ACTION_SEND);

//                Intent reportIntent = new Intent(Intent.ACTION_SEND);
//                reportIntent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
//                reportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject)); //subject mizare
//                reportIntent.setType("text/plain");
////                startActivity(reportIntent);
//                startActivity(Intent.createChooser(reportIntent, getString(R.string.send_report))); // createChooser(intent, title)


//                String title = "Example title";

                    Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                            .setChooserTitle(getString(R.string.task_share_title))
                            .setType("text/plain")
                        .setText(getTaskReport())
                            .getIntent();
                    if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null){
                        startActivity(shareIntent);
                    }
                }
            });
        }

    private String getTaskReport(){
        String report = null;
        String dateString = new SimpleDateFormat("yyyy/MM/dd").format(mWorkModel.getDate());

        String solvedString = null;

        if (mWorkModel.getDone()){
            solvedString = getString(R.string.task_report_done);
        }
        else{
            solvedString = getString(R.string.task_report_undone);
        }

        String titleString = null;
        if (mWorkModel.getTitle() == null) {
            titleString = getString(R.string.crime_report_no_suspect);
        }
        else {
            titleString = getString(R.string.crime_report_suspect, mWorkModel.getTitle());
        }
//        report = getString(R.string.task_report, mWorkModel.getTitle(), dateString, solvedString, suspectString);
        report = getString(R.string.task_report, mWorkModel.getTitle(), mWorkModel.getDetail(), dateString, solvedString);
        return report;
    }//getTaskReport


        public void bind(WorksModel worksModel) {
//            String date = new Date().toString();

            mWorkModel = worksModel;
            mTitleTextView.setText(worksModel.getTitle());
            mDetailTextView.setText(worksModel.getDetail());
//            mDateTextView.setText(date);
            mDateTextView.setText(worksModel.getDate().toString());

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

    // ----------------------------- Adapter -----------------------------------
    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<WorksModel> mCrimes;

//        ColorGenerator generator = ColorGenerator.MATERIAL;

        public MyAdapter(List<WorksModel> crimes) {
            mCrimes = crimes;
        }

        public void setCrimes(List<WorksModel> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            CrimeHolder crimeHolder = new CrimeHolder(view);
            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            WorksModel worksModel = mCrimes.get(position);
//            holder.bind(worksModel);
            Log.i("UUID", worksModel.getId()+"");
            ((CrimeHolder) holder).bind(worksModel);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
//            return (int) worksModelDao.count();
        }
    }
    //---------------------------------- Adapter ------------------------------------

    public static ListFragment newInstance(int tabType, long contactId){ //@NonNull yani crimeId nemitune Null bashe
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TAB_TYPE, tabType);
        args.putLong(ARG_CONTACT_ID, contactId);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

}//class