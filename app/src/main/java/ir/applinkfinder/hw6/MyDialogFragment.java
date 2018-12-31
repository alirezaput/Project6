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
import android.widget.Toast;

import java.util.List;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "args_title";
    private static final String ARG_DETAIL = "args_detail";
    private Button mButtonOk;
    private Button mButtonCancel;

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private String title;
    private String detail;

    public MyDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            detail = getArguments().getString(ARG_DETAIL);
        }
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_dialog, container, false);

        getDialog().setTitle("برنامه موردنظر حذف شود؟");
        mButtonOk = view.findViewById(R.id.button_ok);
        mButtonCancel = view.findViewById(R.id.button_cancel);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(title, detail);
                dismiss();
            }
        });
        return view;

    }//onCreateView

    // ----------------------------- deleteItem Method: ----------------------------
    public void deleteItem(String title, String detail){
        mListFragment = new ListFragment();

        WorksRepository mWorksRepository = WorksRepository.getInstance();
        WorksRepository mWorksRepositoryDone = WorksRepository.getInstanceDone();

        List<WorksModel> list = mWorksRepository.getmWorksList();
        List<WorksModel> listDone = mWorksRepositoryDone.getmWorkListDone();

        mAdapter = mListFragment.new MyAdapter(list);
        mAdapterDone = mListFragment.new MyAdapter(listDone);


        mWorksRepository.deleteWork(title, detail);
        mWorksRepositoryDone.deleteWorkDone(title, detail);
//        Toast.makeText(getActivity(), String.valueOf(mWorksRepository.getmWorksList().size()), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), R.string.toast_delete_task, Toast.LENGTH_SHORT).show();

        mAdapter.notifyItemRemoved(0);
        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
        }
        else{
            mAdapter.notifyDataSetChanged();
            mAdapterDone.notifyDataSetChanged();
        }

//        Toast.makeText(getActivity(), String.valueOf(mWorksRepository.getmWorksList().size()), Toast.LENGTH_SHORT).show();

    }//deleteItem
    // --------------------------------------------------------------------------------------

    // Alert Dialog Instance
    public static MyDialogFragment newInstance(String title, String detail) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DETAIL, detail);
        frag.setArguments(args);
        return frag;
    }//newInstance
}