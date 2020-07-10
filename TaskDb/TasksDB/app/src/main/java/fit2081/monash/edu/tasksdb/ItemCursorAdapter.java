package fit2081.monash.edu.tasksdb;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fit2081.monash.edu.tasksdb.provider.TaskScheme;
import fit2081.monash.edu.tasksdb.provider.TasksDBHelper;

public class ItemCursorAdapter extends CursorAdapter {
    TasksDBHelper db;
    public ItemCursorAdapter(Context context, Cursor c, int flags, TasksDBHelper _db) {
        super(context, c, flags);
        db=_db;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView task_name=view.findViewById(R.id.list_task_name_header);
        TextView task_desc=view.findViewById(R.id.list_task_desc_header);
        TextView task_date=view.findViewById(R.id.list_task_date_header);

        task_name.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.TASK_NAME)));
        task_date.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.TASK_DATE)));
        task_desc.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.TASK_DESC)));
        final String id = cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.ID));


        Button removeBtn = view.findViewById(R.id.list_delete_task);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShape(id);
            }
        });

    }

    public void deleteShape(String id) {
        db.deleteTask(Integer.parseInt(id));
        changeCursor(db.getAllTasks());
    }

}
