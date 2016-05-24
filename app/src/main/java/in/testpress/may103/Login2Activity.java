package in.testpress.may103;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class Login2Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String AUTHENTICATION = "authToken";

    public static final String ROOT_URL = "https://aqueous-cove-75719.herokuapp.com";

    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;

    private AccountManager accountManager;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        accountManager = AccountManager.get(this);

        usernameText = (EditText) findViewById(R.id.user_name);
        passwordText = (EditText) findViewById(R.id.user_password);
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        login();
    }

    private void login() {
        final String USERNAME = usernameText.getText().toString().trim();
        final String PASSWORD = passwordText.getText().toString().trim();

        final ProgressDialog loading = ProgressDialog.show(this,"Loging In","Please wait...",false,false);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
        TodoAPI api = adapter.create(TodoAPI.class);
        final HashMap<String, String> credentials = new HashMap<String, String>();
        credentials.put("username", USERNAME);
        credentials.put("password", PASSWORD);
//Log.e("ssssssssssssss", USERNAME+PASSWORD);
        api.login(credentials, new Callback<AuthToken>() {
            @Override
            public void success(AuthToken authToken, Response response2) {

//                final Account account = new Account(authToken.getToken(), SyncStateContract.Constants.ACCOUNT_NAME);
//                Bundle extraData = new Bundle();
//                extraData.putString("username", USERNAME);
//                accountManager.addAccountExplicitly(account, PASSWORD, extraData);
//                accountManager.setAuthToken(account, SyncStateContract.Constants.DATA, authToken.getToken());
                Intent intent = new Intent(Login2Activity.this, MainActivity.class);
                intent.putExtra("token", authToken.getToken());
                intent.putExtra("credentials", credentials.toString());
                loading.dismiss();
                startActivity(intent);
//                AccountManager manager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
//                Account[] account = manager.getAccountsByType(Constants.Auth.MOCKBANK_ACCOUNT_TYPE);
//                if(account.length > 0) {
//                    hasUserAuthenticated = true;
//                }
//                Log.e("ssssssssssssss", authToken.getToken());
            }

            @Override
            public void failure(RetrofitError error) {

                loading.dismiss();

                AlertDialog.Builder alert= new AlertDialog.Builder(Login2Activity.this);
                alert.setTitle("Login Error");
                alert.setMessage("Incorrect username or password");

                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
//                Log.e("ssssssssssssss", USERNAME+PASSWORD+error.toString());
            }
        });

    }
}
