package in.testpress.may103;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudharsan on 10/5/16.
 */
public class TodoModel implements Parcelable {
    private int id;
    private String task;
    private Boolean completed;

    protected TodoModel(Parcel in) {
        id = in.readInt();
        task = in.readString();
        completed = in.readByte()!=0;
    }

    public static final Creator<TodoModel> CREATOR = new Creator<TodoModel>() {
        @Override
        public TodoModel createFromParcel(Parcel in) {
            return new TodoModel(in);
        }

        @Override
        public TodoModel[] newArray(int size) {
            return new TodoModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(task);
        if (completed == null) {
            dest.writeByte((byte) (0));
        } else {
            dest.writeByte((byte) (completed ? 1 : 0));
        }
    }
}
