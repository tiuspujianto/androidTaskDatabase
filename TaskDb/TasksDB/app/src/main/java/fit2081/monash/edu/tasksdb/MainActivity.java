package fit2081.monash.edu.tasksdb;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import fit2081.monash.edu.tasksdb.provider.TaskScheme;
import fit2081.monash.edu.tasksdb.provider.TasksDBHelper;

public class MainActivity extends AppCompatActivity {

    TasksDBHelper db;
    ListView lvItems;
    ItemCursorAdapter itemAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Reference to the DB
        db=new TasksDBHelper(this,TasksDBHelper.DB_NAME,null,TasksDBHelper.DB_VERSION);
        itemAdaptor = new ItemCursorAdapter(this, null,2,db);

        //Reference to the listview
        lvItems =  findViewById(R.id.tasks_list);
        //Add a header to the listview
        lvItems.addHeaderView(View.inflate(this,  R.layout.list_header,null));
        //link the adapter and the listview
        lvItems.setAdapter(itemAdaptor);
        // this code shows the datepicker dialog if the date Edittext gets the focus
        EditText date=findViewById(R.id.task_date);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(null);
                }
            }
        });
        // this function is used to fetch all the tasks from the database and provide them to the adapter.
        loadTasks();
    }

    private void loadTasks() {
        //Cursor is a temporary buffer area which stores results from a SQLiteDataBase query.
        Cursor cursor = db.getAllTasks();
        itemAdaptor.changeCursor(cursor);
    }

    public void handleBtn(View view){

        ContentValues contentValues=new ContentValues();

        EditText task_name=findViewById(R.id.task_name);
        EditText task_date=findViewById(R.id.task_date);
        EditText task_desc=findViewById(R.id.task_desc);
        // save all the values of the three columns in a contentvalues object
        //ContentValues is a name value pair, used to insert or update values into database tables.
        // ContentValues object will be passed to SQLiteDataBase objects insert() and update() functions.
        contentValues.put(TaskScheme.TASK_NAME, task_name.getText().toString());
        contentValues.put(TaskScheme.TASK_DATE,task_date.getText().toString());
        contentValues.put(TaskScheme.TASK_DESC,task_desc.getText().toString());
        // The implementation of addTask function can be found in TasksDBHelper
        db.addTask(contentValues);
        loadTasks();
    }

    // The below code is merely for the datepicker dialog
    //https://developer.android.com/guide/topics/ui/controls/pickers#java

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText date=getActivity().findViewById(R.id.task_date);
            date.setText(day + "/" + (month+1) + "/" + year);
        }
    }

}
