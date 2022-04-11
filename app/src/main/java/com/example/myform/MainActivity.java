package com.example.myform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myform.databinding.ActivityMainBinding;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


//  Material Design TextInput Layout
    TextInputLayout inputLayoutname, inputLayoutemail, inputLayoutnumber, inputLayoutpassword;
    EditText name, email, mobilenumber, password;
    TextView showdate,showtime,showdoe,spinnervalue;

//  DatePicker and TimePicker Get from the xml its self with spinner
    DatePicker dateofbirth, dateofevent;
    TimePicker timeofevent;
    Spinner formspinner;


//  Custom Button Used for loading
    CircularProgressButton submitbtn;

//  Validation Of Email with the help of Regular Expression
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+",text = "MANJUR";

//  Database
    FirebaseDatabase rootnode;
    DatabaseReference reference;


    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int mHour,mMin;

    NotificationHelper notificationHelper;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Used For Hide The Status Bar
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//      Input Layout Of Material Design
        inputLayoutemail = findViewById(R.id.inputlayoutemail);
        inputLayoutname = findViewById(R.id.inputlayoutname);
        inputLayoutnumber = findViewById(R.id.inputlayoutnumber);
        inputLayoutpassword = findViewById(R.id.inputlayoutpassword);

//      Text View
        showdoe = findViewById(R.id.showdateofevent);
        showdate = findViewById(R.id.showdate);
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.edittextemail);
        mobilenumber = findViewById(R.id.edittextmobilenumber);
        password = findViewById(R.id.edittextpassword);
        dateofevent =findViewById(R.id.dateofevent);

        timeofevent = findViewById(R.id.timeofevent);
        timeofevent.setIs24HourView(true);
        showtime = findViewById(R.id.showtime);

        dateofbirth = findViewById(R.id.datePicker);
        spinnervalue = findViewById(R.id.spinnervalue);

        submitbtn = findViewById(R.id.submitbutton);
        formspinner = findViewById(R.id.form_spinner);



//        Used This Listener For Taking the input in Hour and Minute
        timeofevent.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
          public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        mHour = hourOfDay;
        mMin = minute;

        }
      });



        submitbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                showdate.setText("Selected Date: "+ dateofbirth.getDayOfMonth()+"/"+ (dateofbirth.getMonth() + 1)+"/"+dateofbirth.getYear());
                showdoe.setText("Selected Date: "+ dateofevent.getDayOfMonth()+"/"+ (dateofevent.getMonth() + 1)+"/"+dateofevent.getYear());
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = timeofevent.getHour();
                    minute = timeofevent.getMinute();
                }
                else{
                    hour = timeofevent.getCurrentHour();
                    minute = timeofevent.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }
                showtime.setText("Selected Date: "+ hour +":"+ minute+" "+am_pm);


                formvalidation(); //Method For Validation Of the Form

                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("myFormDetails");
                String username = name.getText().toString();
                String emailvalue = email.getText().toString();
                String passwordvalue = password.getText().toString();
                String mobilenumbervalue = mobilenumber.getText().toString();
                String dobvalue = showdate.getText().toString();
                String spinnerdata = spinnervalue.getText().toString();
                String doevalue = showdoe.getText().toString();
                String toevalue = showtime.getText().toString();



                getuserdata get = new getuserdata(username,emailvalue,passwordvalue,mobilenumbervalue,dobvalue,spinnerdata,doevalue,toevalue);

                reference.setValue(get);


                setnotification(); //Method For Notification







            }




        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnervalues, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formspinner.setAdapter(adapter);
        formspinner.setOnItemSelectedListener(this) ;




    }




    private void setnotification()

    {



        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,mHour);
        calendar.set(Calendar.MINUTE, mMin);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra("place", text);



        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }



    private void formvalidation()

    {

        if(name.getText().toString().isEmpty())
        {
            inputLayoutname.setError("Name is Mandatory");
            inputLayoutname.requestFocus();
        }


        if(email.getText().toString().isEmpty()) {
            inputLayoutemail.setError("enter email address");
            inputLayoutemail.requestFocus();
        }

        else if (email.getText().toString().trim().matches(emailPattern)) {

        }

        else {
            inputLayoutemail.setError("Invalid email address");
            inputLayoutemail.requestFocus();
        }


        if(password.getText().toString().isEmpty() || password.length()<8){
            inputLayoutpassword.setError("Enter Proper Password and lenght should be less than 8");
            inputLayoutpassword.requestFocus();
        }

        else {

            Intent intent = new Intent(MainActivity.this,doneactivity.class);
            startActivity(intent);

        }







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          text = parent.getItemAtPosition(position).toString();



         spinnervalue.setText(text);



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}



