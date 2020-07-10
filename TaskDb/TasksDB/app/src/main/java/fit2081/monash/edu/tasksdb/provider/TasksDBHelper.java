package fit2081.monash.edu.tasksdb.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksDBHelper extends SQLiteOpenHelper {
    // Database name and version
    public final static String DB_NAME = "TasksDB.db";
    public final static int DB_VERSION = 1;
    Context myContext;

    private final static String TASKS_TABLE_CREATE =
            "CREATE TABLE " +
                    TaskScheme.TABLE_NAME + " (" +
                    TaskScheme.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    TaskScheme.TASK_NAME + " TEXT, " +
                    TaskScheme.TASK_DATE + " TEXT," +
                    TaskScheme.TASK_DESC + " TEXT);";


    public TasksDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASKS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskScheme.TABLE_NAME);
        onCreate(db);
    }


    public void addTask(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TaskScheme.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor tasks = db.rawQuery("select * from " + TaskScheme.TABLE_NAME, null);

        return tasks;
    }

    public boolean deleteTask(int taskId) {
        boolean result;
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {Integer.toString(taskId)};
        result=db.delete(TaskScheme.TABLE_NAME, "_id=?", args) > 0;
        return result;

    }
}
