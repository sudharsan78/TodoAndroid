package in.testpress.may103;

import java.util.List;
import java.util.Observable;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by sudharsan on 10/5/16.
 */
public interface TodoAPI {
    @GET("/api/v1/todo/")
    public void getTodos(Callback<List<TodoModel>> response);

    @FormUrlEncoded
    @POST("/api/v1/todo/")
    //public void createTodos(Callback<TodoModel> response);

    public void createTodos(@Field("task") String task, Callback<Response> callback);

    @DELETE("/api/v1/todo/{id}")
    public void deletetask(@Path("id") int id, Callback<Response> callback);


}