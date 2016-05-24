package in.testpress.may103;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sudharsan on 12/5/16.
 */
public class Pop extends AppCompatActivity implements View.OnClickListener {
    public static final String ROOT_URL = "https://aqueous-cove-75719.herokuapp.com";

    private EditText editText;
    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();

        setContentView(R.layout.pop_up_window);

        editText = (EditText) findViewById(R.id.Addtask);
        addbtn = (Button) findViewById(R.id.Addbtn);
        addbtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        createTodos();

        editText.setText(" ");

    }

    private void createTodos() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(ROOT_URL)
                .build();

        TodoAPI api = adapter.create(TodoAPI.class);

        api.createTodos("Token " + getIntent().getStringExtra("token"),
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
                        Intent intent = new Intent(Pop.this, MainActivity.class);
                        intent.putExtra("token" ,getIntent().getStringExtra("token"));
                        startActivity(intent);
                        createTodos();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        );

    }
}
