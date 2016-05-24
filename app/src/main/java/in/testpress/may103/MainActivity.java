package in.testpress.may103;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.StaticLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SwipeRefreshLayout swipeContainer;

    private ListAdapter listAdapter ;

    public static final String ROOT_URL = "https://aqueous-cove-75719.herokuapp.com";
    private ListView listView;

    //List of type books this list will store type Book which is our data model
    private List<TodoModel> tasks = new ArrayList<>();


    public void addTaskAction(MenuItem mi) {

//        "Token " + getIntent().getStringExtra("token")

        Intent intent = new Intent(this, Pop.class);

        intent.putExtra("token" ,getIntent().getStringExtra("token"));
        startActivity(intent);
//        startActivity(new Intent(this, Pop.class));
//        startActivities(new Intent[]{new Intent(MainActivity.this, Pop.class)});
    }

    public static final String Model_ID = "task_id";
    public static final String Model_task= "task";
    private String auToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

//        Intent intent = getIntent();
//        auToken.(intent.getStringExtra(Login2Activity.AUTHENTICATION));

//        deletebtn = (ImageButton) findViewById(R.id.imageButton);
//        deletebtn.setOnClickListener(this);

        //Initializing the listview
        listView = (ListView) findViewById(R.id.tasklist);
        listAdapter =new ListAdapter(this,R.layout.list_layout, tasks);

        listView.setAdapter(listAdapter);

        //Calling the method that will fetch data
        getTodos();

        listView.setOnItemClickListener(this);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoModel todo = (TodoModel) parent.getAdapter().getItem(position);
//                Toast.makeText(getApplicationContext(), "id" + todo.getId() , Toast.LENGTH_SHORT)
//                        .show();
                deletetask(todo);

                return false;
            }
        });

//        listView.setOnItemClickListener(this);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTodos();
                swipeContainer.setRefreshing(false);

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void deletetask(final TodoModel todo) {

        final int ID= todo.getId();
        final String Task = todo.getTask();

        AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this task?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint(ROOT_URL)
                        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                        .build();
                TodoAPI api = adapter.create(TodoAPI.class);

                api.deletetask("Token " + getIntent().getStringExtra("token") ,ID, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {

                        tasks.remove(todo);
                        getTodos();
//                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
                alert.setView(R.layout.edit_task);
                alert.show();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void getTodos(){
        //final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        TodoAPI api = adapter.create(TodoAPI.class);

        api.getTodos("Token " + getIntent().getStringExtra("token"), new Callback<List<TodoModel>>() {
            @Override
            public void success(List<TodoModel> list, Response response) {
                //loading.dismiss();
                tasks = list;
                listAdapter.clear();
                listAdapter.addAll(tasks);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, Edit_task.class);

        TodoModel task = tasks.get(position);

        intent.putExtra(Model_task, task.getTask());
        intent.putExtra(Model_ID, task.getId());

        startActivity(intent);

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        Snackbar.make(findViewById(R.id.myCoordinatorLayout), getTaskId(),
//                Snackbar.LENGTH_SHORT)
//                .show();
//
//    }
        public class ListAdapter extends ArrayAdapter<TodoModel> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<TodoModel> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_layout, null);

            TodoModel p = getItem(position);
            TextView tt1 = (TextView) v.findViewById(R.id.showtask);
            tt1.setText(p.getTask());
            return v;

        }

    }

}









