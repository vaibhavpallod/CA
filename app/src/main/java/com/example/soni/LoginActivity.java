package com.example.soni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private static final String SHARED_PEFS ="sharedPreds" ;
    private static final String TAG = "Hello";
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;
    private EditText mFieldID;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Firebase mRef;
    FirebaseDatabase database ;
    int posofspinner=0 ;
    public int error=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Firebase.setAndroidContext(this);
       // error=0;
        mAuth = FirebaseAuth.getInstance();
        mEmailField = (EditText)findViewById(R.id.emailField);
        mPasswordField= (EditText)findViewById(R.id.passwordField);
      //  mLoginBtn = (Button)findViewById(R.id.loginBtn);
        mRef = new Firebase("https://soni-database.firebaseio.com/");
        database = FirebaseDatabase.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
/*
        Spinner spinner = findViewById(R.id.loginBtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spineer_list,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                if(position==1)
                {   posofspinner = position;
                    error=0;
                    startSignIn();

                */
/*
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            }else
                            {
                                Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
*//*

                }
                else if(position == 2)
                { posofspinner = position;
                    error=0;
                    startSignIn();
                    */
/*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);



                            }else
                            {
                                Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*//*

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);

                    startActivity(i);


                    /* if(posofspinner==1) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(i);

                       // saveData(studentID);

                    }
                    if(posofspinner==2)
                    {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        //saveData(studentID);

                    }*/
                }
            }
        };

/*

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String studentID = mFieldID.getText().toString();
                            Firebase mRefchild = mRef.child("StudentID").child(studentID);
                            String token = task.getResult().getToken();
                            mRefchild.child("ID").setValue(studentID);
                            mRefchild.child("Token").setValue(token);


                            sharedPreferences = getSharedPreferences("SaveData",MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("Value",studentID);
                            editor.apply();


                            Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                            startActivity(intent);


                        }else
                        {
                            Toast.makeText(getBaseContext(),"Token not generated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                startSignIn();
            }
        });
*/



    }

/*

    public void saveData(String StudenID){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PEFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT,StudenID);

    }
*/

   @Override
    protected void onStart(){
      /*  String studentID = mFieldID.getText().toString();
        Toast.makeText(getApplicationContext(),studentID,Toast.LENGTH_LONG).show();
        Intent i =new Intent(LoginActivity.this,StudentActivity.class);
        i.putExtra("Value",studentID);
        startActivity(i);
        startActivity(new Intent(LoginActivity.this,StudentActivity.class));
       */
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }




    private int startSignIn() {
            error=0;
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            if(TextUtils.isEmpty(email))
                mEmailField.setError("Enter mail address");
            else
                mPasswordField.setError("Enter Password");
            error++;
          //  Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_LONG  ).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        error++;
                        Toast.makeText(LoginActivity.this, "Sign in Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        Log.v(TAG, "value : " + error);
        if(error==0)
            return 0;
        else
            return 1;

    }



    public void signup_click(View view)
    {
        Intent intent = new Intent(LoginActivity.this,Signupform.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(),"signup clicked",Toast.LENGTH_LONG).show();
    }

}
