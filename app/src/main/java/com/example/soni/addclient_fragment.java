package com.example.soni;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class addclient_fragment extends Fragment {

    EditText pdfname;
    static int total;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    List<PDFDoc> pdfDocs = new ArrayList<>();
    Uri finaluri;
    GridView gv;//= (GridView) getView().findViewById(R.id.gv);
    MyAdapter myAdapter;
    Button savebtn;
    public ArrayList<Uri> uploadlist = new ArrayList<Uri>();
    EditText name, mobileno, email, gst,vat,pan;
RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_add_clients, container, false);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setNestedScrollingEnabled(true);
        }*/
        //   super.onViewCreated(view, savedInstanceState);
        gv = (GridView) view.findViewById(R.id.gridview);
        gv.setVerticalScrollBarEnabled(false);
//relativeLayout = view.findViewById(R.id.nested);
        savebtn = view.findViewById(R.id.saveclient);

        myAdapter = new MyAdapter(this.getContext(), pdfDocs);
        gv.setAdapter(new MyAdapter(this.getContext(), pdfDocs));
        final int size = pdfDocs.size();
        //savebtn.setOnClickListener(this);
        Spinner spinner = view.findViewById(R.id.selectyear);

       /*spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        try {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    toast(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (Exception e){
            toast("Exception");
        }*/
         savebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                databaseReference = FirebaseDatabase.getInstance().getReference("clients");

                name = getView().findViewById(R.id.nameid);
                email = getView().findViewById(R.id.emailid);
                mobileno = getView().findViewById(R.id.mobilenumber);
                gst = getView().findViewById(R.id.gstnumber);
                vat = getView().findViewById(R.id.vatnumber);
                pan = getView().findViewById(R.id.pannumber);

                String nameofclient, emailid, mobilenumber, gstno, vatno, panno;

                nameofclient = name.getText().toString();
                emailid = email.getText().toString();
                mobilenumber = mobileno.getText().toString();
                gstno = gst.getText().toString();
                vatno = vat.getText().toString();
                panno = pan.getText().toString();

                if (!((TextUtils.isEmpty(nameofclient)) && (TextUtils.isEmpty(emailid)) && (TextUtils.isEmpty(mobilenumber)) && (TextUtils.isEmpty(gstno)) && (TextUtils.isEmpty(vatno)) && (TextUtils.isEmpty(panno)))) {
                    databaseReference.child(name.getText().toString()).child("Details").child("Name").setValue(name.getText().toString());
                    databaseReference.child(name.getText().toString()).child("Details").child("Email").setValue(email.getText().toString());
                    databaseReference.child(name.getText().toString()).child("Details").child("Mobile Number").setValue(mobileno.getText().toString());
                    databaseReference.child(name.getText().toString()).child("Details").child("Gst number").setValue(gst.getText().toString());
                    databaseReference.child(name.getText().toString()).child("Details").child("Pan Number").setValue(pan.getText().toString());
                    databaseReference.child(name.getText().toString()).child("Details").child("Vat number").setValue(vat.getText().toString());
                    error=0;
                } else {
                    if (TextUtils.isEmpty(nameofclient))
                        name.setError("Enter Name of client");
                    if (TextUtils.isEmpty(emailid))
                        email.setError("Enter email address");
                    if (TextUtils.isEmpty(mobilenumber))
                        mobileno.setError("Enter Mobile number");
                    if (TextUtils.isEmpty(gstno))
                        gst.setError("Enter GST Number");
                    if (TextUtils.isEmpty(panno))
                        pan.setError("Enter PAN Number");
                    if (TextUtils.isEmpty(vatno))
                        vat.setError("Enter VAT Number");

                    error++;
                }

                if(error==0)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    StorageReference reference = storageReference.child("clients" + "/" + name.getText().toString());

                    for (int i = 0; i < total; i++) {
//                        toast(myAdapter.getYear(i));

                        String filename = "noname";
                        final Uri file = uploadlist.get(i);
                        File fileeeee = new File(file.toString());
                        //  toast(String.valueOf(total));
                        if (file.toString().startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = getActivity().getContentResolver().query(file, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (file.toString().startsWith("file://")) {
                            filename = fileeeee.getName();
                        }
                        gv.getItemAtPosition(i);
                        gv.getAdapter().getItem(i);
                        //  toast(myAdapter.getItem(i).getItem(i).getYear().toString());


                        final int finalI = i + 1;
                        final String finalFilename = filename;//.child(myAdapter.getYear().toString())

                        // toast(filename+ " " +myAdapter.getYear(i+1)+" " + myAdapter.getPDFtype(i+1)+" ");

                        final String finalFilename1 = filename;
                        reference.child(myAdapter.getYear(i)).child(myAdapter.getPDFtype(i)).child(filename)
                                .putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                while ((!uri.isComplete())) ;
                                Uri url = uri.getResult();
                                PDFDoc uploadpdf = new PDFDoc(finalFilename, url.toString());                    //name.getText().toString()
                                databaseReference.child(name.getText().toString()).child("documents").push().setValue(uploadpdf);  //finalFilename.replaceAll("[^a-zA-Z]+", " ")
                                toast("Uploaded to database");
                                progressDialog.dismiss();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//toast(("Progress: %5.2f MB transferred"+taskSnapshot.getBytesTransferred()/1024.0/1024.0));
                           /* Log.i(TAG, String.format("onProgress: %5.2f MB transferred",
                                    taskSnapshot.getBytesTransferred() / 1024.0 / 1024.0));
*/
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded : " + progress + " %\ncompleted" + finalI + "/" + total);
                                // toast("Uploading " + finalI + "th file");
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(true);
                                progressDialog.show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                toast("failed");
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Failed to upload " + finalFilename1, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        return view;


    }




    public void selectPDFfile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
//        gv.setFocusable(false);
        // getActivity().finish();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_layout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.upload) {
            selectPDFfile();
        }

        if (item.getItemId() == R.id.signOutMenuId) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            addPDFfile(data);
            //  addPDFfile(data.getData());
            savebtn = getView().findViewById(R.id.saveclient);
             /*   savebtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
toast("Inside onactivity result");
                    }
                });*/


        }
        //getActivity().finish();
    }

    public void addPDFfile(Intent intent) {
        ClipData clipData;
        clipData = intent.getClipData();
        //   toast(intent.getClipData().toString());
        // toast(String.valueOf(intent.getClipData().getItemCount()));
        if (clipData == null) {

            setadapterbyuri(intent.getData());
            uploadlist.add(intent.getData());
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) gv.getLayoutParams();
            params.height += 275;
            gv.setLayoutParams(params);
            toast("height "+ params.height + " width "+params.width);
            total++;
        } else {
            int count = intent.getClipData().getItemCount();

            for (int i = 0; i < count; i++) {
                Uri file = intent.getClipData().getItemAt(i).getUri();
                total++;
               /* ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) gv.getLayoutParams();
                params.height += 500;
                gv.setLayoutParams(params);*/
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) gv.getLayoutParams();
                layoutParams.height += 275;
                gv.setLayoutParams(layoutParams);

             /* LinearLayout linearLayout = getView().findViewById(R.id.relativelay);
                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.height += 300;
                linearLayout.setLayoutParams(layoutParams);
*/

              /* RelativeLayout relativeLayout = getView().findViewById(R.id.relativelay);
               RelativeLayout.LayoutParams layoutParams = () relativeLayout.getLayoutParams();
               layoutParams.height += 100;
               relativeLayout.setLayoutParams(layoutParams);*/

            //    toast("height "+ layoutParams.height + " width "+layoutParams.width);
                uploadlist.add(file);
            }
            toast(String.valueOf(count));
            for (int i = 0; i < intent.getClipData().getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                Uri uri = item.getUri();
                setadapterbyuri(uri);
            }
        }
    }

    public void setadapterbyuri(Uri data) {
        // Uri data= intent.getData();
        String displayName = null;
//        myAdapter = new MyAdapter(getContext(),pdfDocs);
        PDFDoc pdfDoc;
        File file = new File(data.toString());
        Log.v(TAG, "xxxxxxxxxxxxxxxxxxxxx" + file.getPath());

        if (data.toString().startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(data, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        } else if (data.toString().startsWith("file://")) {
            displayName = file.getName();
        }

        if (file.getPath().endsWith("pdf")) {
            //  toast(displayName);
            pdfDocs.add(new PDFDoc(displayName, file.getPath()));
            myAdapter = new MyAdapter(this.getContext(), pdfDocs);
            gv.setAdapter(myAdapter);

               /* for (int i = 0; i < pdfDocs.size(); i++) {
                    Log.i("Arraylist", pdfDocs.get(i).toString());
                    Log.i("Arraylist", pdfDocs.get(i).getName().toString());
                }*/
            //myAdapter = new MyAdapter(addclient_fragment.this,pdfDocs);
        }
        myAdapter.notifyDataSetChanged();
        //return pdfDocs;
        //  StorageReference reference = storageReference.child()

    }


    public void toast(String x) {
        Toast.makeText(getActivity(), x, Toast.LENGTH_SHORT).show();
    }



}

/*

    private ArrayList<PDFDoc> getPDFs()
    {
        ArrayList<PDFDoc> pdfDocs=new ArrayList<>();
        //TARGET FOLDER
        File downloadsFolder= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        PDFDoc pdfDoc;
        if(downloadsFolder.exists())
        {
            //GET ALL FILES IN DOWNLOAD FOLDER
            File[] files=downloadsFolder.listFiles();

            //LOOP THRU THOSE FILES GETTING NAME AND URI
            for (int i=0;i<files.length;i++)
            {
                File file=files[i];

                if(file.getPath().endsWith("pdf"))
                {
                    pdfDoc=new PDFDoc();
                    pdfDoc.setName(file.getName());
                    pdfDoc.setPath(file.getAbsolutePath());

                    pdfDocs.add(pdfDoc);
                }

            }
        }

        return pdfDocs;
    }
*/

/*2020-05-02 20:27:55.983 8194-8194/? E/om.example.son: Unknown bits set in runtime_flags: 0x8000
2020-05-02 20:28:04.659 8194-8194/com.example.soni E/DecorView: mWindow.mActivityCurrentConfig is null
2020-05-02 20:28:04.680 8194-8194/com.example.soni E/DecorView: mWindow.mActivityCurrentConfig is null
2020-05-02 20:28:12.445 8194-8194/com.example.soni E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.soni, PID: 8194
    java.lang.IllegalStateException: Task is not yet complete
        at com.google.android.gms.common.internal.Preconditions.checkState(Unknown Source:29)
        at com.google.android.gms.tasks.zzu.zzb(Unknown Source:121)
        at com.google.android.gms.tasks.zzu.getResult(Unknown Source:12)
        at com.example.soni.addclient_fragment$1$3.onSuccess(addclient_fragment.java:108)
        at com.example.soni.addclient_fragment$1$3.onSuccess(addclient_fragment.java:100)
        at com.google.firebase.storage.StorageTask.lambda$new$0(com.google.firebase:firebase-storage@@19.1.1:123)
        at com.google.firebase.storage.StorageTask$$Lambda$1.raise(Unknown Source:6)
        at com.google.firebase.storage.TaskListenerImpl.lambda$onInternalStateChanged$2(com.google.firebase:firebase-storage@@19.1.1:90)
        at com.google.firebase.storage.TaskListenerImpl$$Lambda$3.run(Unknown Source:6)
        at android.os.Handler.handleCallback(Handler.java:883)
        at android.os.Handler.dispatchMessage(Handler.java:100)
        at android.os.Looper.loop(Looper.java:237)
        at android.app.ActivityThread.main(ActivityThread.java:7814)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1075)
*/