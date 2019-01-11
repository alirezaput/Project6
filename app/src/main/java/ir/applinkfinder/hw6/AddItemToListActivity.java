package ir.applinkfinder.hw6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class AddItemToListActivity extends AppCompatActivity {

    private static final String EXTRA_TITLE = "ir.applinkfinder.hw6v3.extra_title";
    private static final String EXTRA_DETAIL = "ir.applinkfinder.hw6v3.extra_detail";
    private static final String EXTRA_CONTACT_ID = "ir.applinkfinder.hw6.extra_contact_id";

    private int contact_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_list);

        getContactIdInAddItem();

        // Begin the transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new AddItemToListFragment();
        if (fragmentManager.findFragmentById(R.id.container_add_item_to_list) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_add_item_to_list, fragment)
                    .commit();
        }
    }//onCreate

    public int getContactIdInAddItem(){
        contact_Id = getIntent().getIntExtra(EXTRA_CONTACT_ID, -2);
        Toast.makeText(this, "AddItemToListActivity Welcome User: " + contact_Id, Toast.LENGTH_SHORT).show();
        return contact_Id;
    }

    //add
    public static Intent newIntent(Context context, int contactId){
        Intent intent = new Intent(context, AddItemToListActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactId);
        return intent;
    }//newIntent
}