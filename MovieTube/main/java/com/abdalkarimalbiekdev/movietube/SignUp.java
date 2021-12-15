package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import io.supercharge.shimmerlayout.ShimmerLayout;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.abdalkarimalbiekdev.movietube.Model.ServerResponse;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.ipaulpro.afilechooser.utils.FileUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Interface.UploadCallBack;
import com.abdalkarimalbiekdev.movietube.Model.User;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.Utils.ProgressRequestBody;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sanojpunchihewa.glowbutton.GlowButton;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 4444;
    ShimmerLayout shimmerSignUp;
    TextView txtSignUp;
    CircleImageView imgPerson;
    MaterialEditText edtEmail , edtName , edtPassword;
    GlowButton btnSignUp;

    MovieAPI api;
    CompositeDisposable compositeDisposable;
    private Uri selectedFileUri;
    String fileName = "";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Note: add this code before setContentView method
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/teko.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_sign_up);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initViews();

        Typeface typefaceTitle = Typeface.createFromAsset(getAssets() , "fonts/giddyupstd.otf");
        txtSignUp.setTypeface(typefaceTitle);


        api = Common.getAPI();
        compositeDisposable = new CompositeDisposable();
        
        
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtEmail.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty() && !edtName.getText().toString().isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                        if (Common.isConnectionToNetwork(getApplicationContext())) {

//                            uploadFile();
                            saveData();

                        } else
                            Toast.makeText(SignUp.this, "Check your connection Network !", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SignUp.this, "Please enter your current email !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(SignUp.this, "Please fill all the fields !", Toast.LENGTH_SHORT).show();

            }
        });



        imgPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startUpload();

            }
        });

    }

    private void startUpload() {

        Dexter.withActivity(SignUp.this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){

                            startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent() , "Select a Picture"),
                                    PICK_FILE_REQUEST);
                        }
                        else
                            Toast.makeText(getApplicationContext() , "You can't use access to gallery !" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                })
                .check();
    }

    private void uploadFile() {

        if (selectedFileUri != null){

            final File file = FileUtils.getFile(this , selectedFileUri);

            fileName = new StringBuilder(UUID.randomUUID().toString().substring(0 , 8))
                    .append("_User_")
                    .append(Common.currentUser.getName())
                    .append(FileUtils.getExtension(file.toString())).toString();

//            ProgressRequestBody requestFile = new ProgressRequestBody(file, new UploadCallBack() {
//                @Override
//                public void onProgressUpdate(int pertantage) {
//                }
//            });
//
//            final MultipartBody.Part body =  MultipartBody.Part.createFormData("uploaded_file" , fileName , requestFile);
//            final MultipartBody.Part id =  MultipartBody.Part.createFormData("user_id" , String.valueOf(Common.currentUser.getId()));
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    api.uploadImage(
//                            id ,
//                            body).enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            Toast.makeText(getApplicationContext() , response.body() , Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                }
//            }).start();
        }
    }

    private void saveData() {

        final ProgressDialog progressDialog = new ProgressDialog(this , R.style.MyProgressDialogTheme);
        progressDialog.setMessage("Please waiting...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        try {
            api.insertUserData(
                    AES.encrypt(edtEmail.getText().toString()),
                    AES.encrypt(edtPassword.getText().toString()),
                    AES.encrypt(edtName.getText().toString()))
                    .enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                            progressDialog.dismiss();

                            try {
                                if (response.body().getErrorMessage().isEmpty())
                                    Toast.makeText(SignUp.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();

                                else {
                                    Toast.makeText(SignUp.this,AES.decrypt(response.body().getErrorMessage()) , Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }



                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {

        shimmerSignUp = findViewById(R.id.shimmerSignUp);
        imgPerson = findViewById(R.id.imgPerson);
        shimmerSignUp = findViewById(R.id.shimmerSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignUp = findViewById(R.id.txtSignUp);

        shimmerSignUp.startShimmerAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST){
            if (resultCode == RESULT_OK){
                if (data != null){

                    selectedFileUri = data.getData();
                    if (selectedFileUri != null && !selectedFileUri.getPath().isEmpty()){

                        ///////////////////////kenBurnsView.setImageURI(selectedFileUri);
                        imgPerson.setImageURI(selectedFileUri);
                        Toast.makeText(this, "The image is picked up", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this, "OOps , there is problem !!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        shimmerSignUp.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();

        shimmerSignUp.stopShimmerAnimation();
    }

}
