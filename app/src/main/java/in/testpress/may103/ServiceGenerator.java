package in.testpress.may103;

import android.util.Base64;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by sudharsan on 20/5/16.
 */
public class ServiceGenerator {

//    public static final String API_BASE_URL = "https://aqueous-cove-75719.herokuapp.com";
//
//    private static RestAdapter.Builder builder = new RestAdapter.Builder()
//            .setEndpoint(API_BASE_URL)
//            .setClient(new OkClient(new OkHttpClient()));
//
//    public static <S> S createService(Class<S> serviceClass) {
//        return createService(serviceClass, null, null);
//    }
//
//    public static <S> S createService(Class<S> serviceClass, String username, String password) {
//        if (username != null && password != null) {
//            String credentials = username + ":" + password;
//            final String basic =
//                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//
//            builder.setRequestInterceptor(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade request) {
//                    request.addHeader("Authorization", basic);
//                    request.addHeader("Accept", "application/json");
//                }
//            });
//        }
//
//        RestAdapter adapter = builder.build();
//        return adapter.create(serviceClass);
//    }
//
//    public static void main(String... args) {
//        // Create a very simple REST adapter which points the GitHub API endpoint.
//        TodoAPI client = ServiceGenerator.createService(TodoAPI.class);
//
//        // Fetch and print a list of the contributors to this library.
//        List<TodoModel>  ownertasks=
//                client.ownertasks("owner", id);
//
//        for (TodoModel ownertask : ownertasks) {
//            System.out.println(
//                    ownertask.login + " (" + ownertask.ownertasks + ")");
//        }
//    }

}
