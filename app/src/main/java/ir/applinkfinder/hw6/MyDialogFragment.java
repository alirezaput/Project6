package ir.applinkfinder.hw6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import ir.applinkfinder.hw6.model.WorksModel;
import ir.applinkfinder.hw6.model.WorksRepository;

public class MyDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "args_title";
    private static final String ARG_DETAIL = "args_detail";
//    private static final String ARG_WORKMODEL = "args_workmodel";
    private static final String ARG_WORKMODEL_ID = "args_workmodel_id";

    private Button mButtonOk;
    private Button mButtonCancel;

    ListFragment mListFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;

    private UUID workId;

    public MyDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workId = (UUID) getArguments().getSerializable(ARG_WORKMODEL_ID);
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
                deleteItem(workId);
//                deleteItem(title, detail);
                dismiss();
            }
        });
        return view;

    }//onCreateView

    // ----------------------------- deleteItem Method: ----------------------------
    public void deleteItem(UUID id){
        mListFragment = new ListFragment();

        List<WorksModel> list = WorksRepository.getInstance(getActivity()).getmWorksList();
        List<WorksModel> listDone = WorksRepository.getInstance(getActivity()).getmWorkListDone();

        mAdapter = mListFragment.new MyAdapter(list);
        mAdapterDone = mListFragment.new MyAdapter(listDone);

//        WorksRepository.getInstance(getActivity()).deleteWorkDone(id);
        WorksRepository.getInstance(getActivity()).deleteWork(id);

        Toast.makeText(getActivity(), "Done Number: " + String.valueOf(WorksRepository.getInstance(getActivity()).getmWorkListDone().size()), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "All Works: "   + String.valueOf(WorksRepository.getInstance(getActivity()).getmWorksList().size()), Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity(), R.string.toast_delete_task, Toast.LENGTH_SHORT).show();

        mAdapterDone.notifyItemRemoved(0);
        if (mAdapter == null) {
            mListFragment.mRecyclerView.setAdapter(mAdapter);
            mListFragment.mRecyclerView.setAdapter(mAdapterDone);
        }
        else{
            mAdapter.notifyDataSetChanged();
            mAdapterDone.notifyDataSetChanged();
        }
    }//deleteItem
    // --------------------------------------------------------------------------------------

    // Alert Dialog Instance
    public static MyDialogFragment newInstance(UUID workId) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKMODEL_ID, workId);
        frag.setArguments(args);
        return frag;
    }//newInstance
}