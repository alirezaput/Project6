package ir.applinkfinder.hw6;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;
import ir.applinkfinder.hw6.utils.PictureUtils;

public class EditDeleteDoneFragment extends DialogFragment {

    private static final String ARG_WORK_ID = "arg_work_id";
    public static final int REQUEST_PHOTOS = 1;

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private TextView mTextViewTitle;
    private TextView mTextViewDetail;
    private TextView mTextViewDate;
    private TextView mTextViewHour;

    private Button mButtonDone;
    private Button mButtonDelete;
    private Button mButtonEdit;

    private ImageView mImageViewTaskPic;
    private ImageButton mImageButtonAddTaskPic;
    private File mPhotoFile;

    private UUID work_id;

    public EditDeleteDoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        // set Dialog Fragment Size and Title
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().setTitle("اطلاعات برنامه");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            title = getArguments().getString(ARG_TITLE);
//            detail = getArguments().getString(ARG_DETAIL);
//            mWorksModel = (WorksModel) getArguments().getSerializable(ARG_WORKMODEL);
            work_id = (UUID) getArguments().getSerializable(ARG_WORK_ID);
            mPhotoFile = WorksRepository.getInstance(getActivity()).getPhotoFile(WorksRepository.getInstance(getActivity()).getWork(work_id));
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
        mTextViewTitle  = view.findViewById(R.id.edittext_edit_done_remove_title);
        mTextViewDetail = view.findViewById(R.id.edittext_edit_done_remove_detail);
        mTextViewDate   = view.findViewById(R.id.edittext_edit_done_remove_date);
        mTextViewHour   = view.findViewById(R.id.edittext_edit_done_remove_hour);
        mButtonDone     = view.findViewById(R.id.button_done);
        mButtonDelete   = view.findViewById(R.id.button_delete);
        mButtonEdit     = view.findViewById(R.id.button_edit);
        mImageButtonAddTaskPic = view.findViewById(R.id.imagebutton_pic_for_task);
        mImageViewTaskPic = view.findViewById(R.id.imageview_pic_for_task);

        mImageButtonAddTaskPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri uri = getPhotoFileUri();
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(captureIntent, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : activities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureIntent, REQUEST_PHOTOS);
            }
        });

        mTextViewTitle.setText(WorksRepository.getInstance(getActivity()).getWork(work_id).getTitle());
        mTextViewDetail.setText(WorksRepository.getInstance(getActivity()).getWork(work_id).getDetail());
//        mTextViewDate.setText(WorksRepository.getInstance(getActivity()).getWork(work_id).getDate().toString());
//        mTextViewHour.setText(WorksRepository.getInstance(getActivity()).getWork(work_id).getHour().toString());

        final FragmentManager fragmentManager = getFragmentManager();
        final MyDialogFragment myDialogFragment = new MyDialogFragment();

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myDialogFragment.newInstance(title, detail).show(fragmentManager, "Custom Dialog Manager");
                myDialogFragment.newInstance(work_id).show(fragmentManager, "Custom Dialog Manager");
                dismiss();
            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment:
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EditFragment editFragment = EditFragment.newInstance(work_id);
////                AddItemToListFragment editFragment = new AddItemToListFragment();
//                fragmentTransaction.replace(R.id.container_edit_delete_done, editFragment);
//                fragmentTransaction.commit();
////                editItem();

                // Dialog Fragment:
                FragmentManager fragmentManager = getFragmentManager();
                EditFragment editFragment = EditFragment.newInstance(work_id);
                editFragment.show(fragmentManager, "Custom Dialog Fragment");
                dismiss();
            }
        });

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToDone();
                dismiss();
            }
        });
        updatePhotoView();
        return view;
    }//onCreateView

    public void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            mImageViewTaskPic.setImageDrawable(null);
        }
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mImageViewTaskPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTOS) {
            Uri uri = getPhotoFileUri();
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private Uri getPhotoFileUri() {
        return FileProvider.getUriForFile(getActivity(),"ir.applinkfinder.hw6.fileprovider", mPhotoFile);
    }

    public void changeToDone(){

        mListFragment = new ListFragment();

        List<WorksModel> list = WorksRepository.getInstance(getActivity()).getmWorksList();
        mAdapter = mListFragment.new MyAdapter(list);

        List<WorksModel> listDone = WorksRepository.getInstance(getActivity()).getmWorkListDone();
        mAdapterDone = mListFragment.new MyAdapter(listDone);

//        WorksRepository.getInstance(getActivity()).getWork(work_id).setTitle(mTextViewTitle.getText().toString());
//        WorksRepository.getInstance(getActivity()).getWork(work_id).setDetail(mTextViewDetail.getText().toString());

        WorksModel currentWorksModel = WorksRepository.getInstance(getActivity()).getWork(work_id);

        currentWorksModel.setDone(true);

        WorksRepository.getInstance(getActivity()).addWorkDone(currentWorksModel);

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

    }//changeToDone

    public static EditDeleteDoneFragment newInstance(UUID id){ //@NonNull yani crimeId nemitune Null bashe
        EditDeleteDoneFragment fragment = new EditDeleteDoneFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_WORK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }//newInstance
}
