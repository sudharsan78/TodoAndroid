package in.testpress.may103;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeRefreshLayout swipeContainer;

    public static final String ROOT_URL = "https://aqueous-cove-75719.herokuapp.com";
    private ListView listView;

    //List of type books this list will store type Book which is our data model
    private List<TodoModel> tasks;

    private EditText editText;
    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);
        addbtn = (Button) findViewById(R.id.add_task);
        addbtn.setOnClickListener(this);

        //Initializing the listview
        listView = (ListView) findViewById(R.id.tasklist);

        //Calling the method that will fetch data
        getTodos();


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

    private void getTodos(){

        //final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        TodoAPI api = adapter.create(TodoAPI.class);

        api.getTodos(new Callback<List<TodoModel>>() {
            @Override
            public void success(List<TodoModel> list, Response response) {

                //loading.dismiss();

                tasks = list;

                showList();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void showList(){

        String[] items = new String[tasks.size()];

        for(int i=0; i<tasks.size(); i++){
            items[i] = tasks.get(i).getTask();
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.simple_list,items);

        listView.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        createTodos();

    }

    private void createTodos() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        TodoAPI api = adapter.create(TodoAPI.class);

        api.createTodos(
                editText.getText().toString(), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader=null;
                        String output="";

                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                       // Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        );

    }
}









