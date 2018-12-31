package ir.applinkfinder.hw6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class EditDeleteDoneActivity extends AppCompatActivity {

    private static final String EXTRA_TITLE = "ir.applinkfinder.hw6v3.title";
    private static final String EXTRA_DETAIL = "ir.applinkfinder.hw6v3.detail";

    private String title;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_done);

        title  = getIntent().getStringExtra(EXTRA_TITLE);
        detail = getIntent().getStringExtra(EXTRA_DETAIL);

        // Begin the transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditDeleteDoneFragment fragment = EditDeleteDoneFragment.newInstance(title, detail);
        if (fragmentManager.findFragmentById(R.id.container_edit_delete_done) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_edit_delete_done, fragment)
                    .commit();
        }

//        EditDeleteDoneFragment alertDialog = EditDeleteDoneFragment.newInstance("Title of Dialog");
//        alertDialog.show(fragmentManager, "fragment_alert");

    }//onCreate

    //edit
    public static Intent newIntent(Context context, String title, String detail){
        Intent intent = new Intent(context, EditDeleteDoneActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DETAIL, detail);
        return intent;
    }//newIntent
}