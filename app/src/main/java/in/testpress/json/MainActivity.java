package in.testpress.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHit = (Button)findViewById(R.id.btnHit);
        tvData = (TextView)findViewById(R.id.tvJsonItem);

        assert btnHit != null;
        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new JSONTask().execute("https://aqueous-cove-75719.herokuapp.com/api/v1/todo/");
            }
        });


    }
    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream=connection.getInputStream();
                reader= new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line="";

                while ((line=reader.readLine())!=null){
                    buffer.append(line);

                }

                String finalJson = buffer.toString();
                JSONArray parentarray = new JSONArray(finalJson);

                StringBuffer tasklist = new StringBuffer();

                for (int i=0;i<parentarray.length();i++) {
                    JSONObject finalObject = parentarray.getJSONObject(i);

                    String task = finalObject.getString("task");
                    tasklist.append(task +"\n");
                }

                return tasklist.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection !=null){
                    connection.disconnect();
                }
                try {
                    if(reader!=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);
        }
    }
}
