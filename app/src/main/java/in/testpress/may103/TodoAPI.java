package in.testpress.may103;

import android.telecom.Call;
import android.text.LoginFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by sudharsan on 10/5/16.
 */
public interface TodoAPI {

    @POST("/api-token-auth/")
    public void login(@Body HashMap<String, String> arguments, Callback<AuthToken> callback);

//    @Headers("Authorization: token  4ab57ea92cbd130709465d531772d5d17a90d56a")
    @GET("/api/v1/todo/users/tasks/")
    public void getTodos(@Header("Authorization") String authorization,
                         Callback<List<TodoModel>> response);

    @FormUrlEncoded
    @POST("/api/v1/todo/")
    public void createTodos(@Header("Authorization") String authorization,
                            @Field("task") String task,
                            Callback<Response> callback);

    @DELETE("/api/v1/todo/{id}")
    public void deletetask(@Header("Authorization") String authorization,
                           @Path("id") int id,
                           Callback<Response> callback);

    @PUT("/api/v1/todo/{id}")
    public void updatetask(@Path("id") int id, @Part("id") TodoModel todoModel);






}


