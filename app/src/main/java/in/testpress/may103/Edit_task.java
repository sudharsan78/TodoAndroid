package in.testpress.may103;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sudharsan on 17/5/16.
 */
public class Edit_task extends AppCompatActivity implements View.OnClickListener {
    public static final String ROOT_URL = "https://aqueous-cove-75719.herokuapp.com";

    private EditText edit_task;
    private Button ok;
    private Button cancel;
    private TextView tid;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        edit_task = (EditText) findViewById(R.id.editText);
        ok = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.button2);
        tid = (TextView) findViewById(R.id.taskid);

        ok.setOnClickListener(this);

        Intent intent = getIntent();
        edit_task.setText(intent.getStringExtra(MainActivity.Model_task));
//        tid.setText(String.valueOf(intent.getIntExtra(MainActivity.Model_ID,0)));

    }

    @Override
    public void onClick(View v) {

        updatetask();

//        updatetask(Integer.parseInt(tid.getText().toString()));
    }
    public void updatetask(){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        TodoAPI api = adapter.create(TodoAPI.class);

        api.updatetask("Token " + getIntent().getStringExtra("token"), getIntent().getIntExtra(MainActivity.Model_ID,0), edit_task.getText().toString(), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Intent intent = new Intent(Edit_task.this, MainActivity.class);
                intent.putExtra("token" ,getIntent().getStringExtra("token"));
                startActivity(intent);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


    public void cancelbtn(View view) {
        Intent intent = new Intent(Edit_task.this, MainActivity.class);
        intent.putExtra("token" ,getIntent().getStringExtra("token"));
        startActivity(intent);

    }
}
