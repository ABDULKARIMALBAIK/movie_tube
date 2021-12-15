package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.paperdb.Paper;
import io.reactivex.disposables.CompositeDisposable;
import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Model.User;
import com.abdalkarimalbiekdev.movietube.Model.UserResponse;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.Utils.DialogInfo;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;
import com.sanojpunchihewa.glowbutton.GlowButton;

import java.io.UnsupportedEncodingException;

public class SignIn extends AppCompatActivity {

    TextView  txtSignUp , txtSignInLabel , txtVerify;
    MaterialEditText edtEmail , edtPassword;
    CheckBox ckbRemember;
    GlowButton btnLogin;
    ShimmerLayout shimmerLogin;

    MovieAPI api;
    CompositeDisposable compositeDisposable;

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

        setContentView(R.layout.activity_sign_in);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initViews();


        Typeface typefaceTitle = Typeface.createFromAsset(getAssets() , "fonts/giddyupstd.otf");
        txtSignInLabel.setTypeface(typefaceTitle);


        Paper.init(this);
        api = Common.getAPI();
        compositeDisposable = new CompositeDisposable();


        //Check Remember Check Box
        String email = Paper.book().read("EMAIL");
        String pwd = Paper.book().read("PASSWORD");

        if (email != null && pwd != null){
            if (!email.isEmpty() && !pwd.isEmpty()){
                if (Common.isConnectionToNetwork(getApplicationContext())){
                    startLogin(email,pwd);
                }
                else
                    Toast.makeText(getApplicationContext() , "Check your connection internet !" , Toast.LENGTH_LONG).show();

            }

        }


        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this , SignUp.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save email & password
                if (ckbRemember.isChecked()) {

                    try {
                        Paper.book().write("EMAIL", AES.encrypt(edtEmail.getText().toString()));
                        Paper.book().write("PASSWORD", AES.encrypt(edtPassword.getText().toString()));

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                if (!edtEmail.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                        if (Common.isConnectionToNetwork(getApplicationContext())) {

                            try {
                                startLogin(
                                        AES.encrypt(edtEmail.getText().toString()) ,
                                        AES.encrypt(edtPassword.getText().toString()));
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else
                            Toast.makeText(SignIn.this, "Check your connection Network !", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SignIn.this, "Please enter your current email !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(SignIn.this, "Please fill all the fields !", Toast.LENGTH_SHORT).show();

            }
        });


        txtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAccount();
            }
        });

    }

    private void verifyAccount() {

        DialogInfo dialogInfo = new DialogInfo(SignIn.this,
                R.drawable.ic_security_yellow_24dp,
                "Enter verify code here :",
                "Verify",
                "OK",
                "CANCEL",
                "000000");
        dialogInfo.showDialog();

        dialogInfo.btnYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editVerify = dialogInfo.edtInfo.getText().toString();

                if (!editVerify.isEmpty()){

                    if (Common.isConnectionToNetwork(SignIn.this)){

                        try {
                            api.verify(AES.encrypt(editVerify)).enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                                    if (response.body().getErrorMessage().isEmpty()){

                                        Common.currentUser = response.body().getResult();
                                        startActivity(new Intent(SignIn.this, Category.class));
                                    }
                                    else {
                                        try {
                                            Toast.makeText(SignIn.this,AES.decrypt(response.body().getErrorMessage()) , Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<UserResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();                                    }
                            });
                        }
                        catch (Exception e) {
                            dialogInfo.dialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        dialogInfo.dialog.dismiss();
                        Toast.makeText(SignIn.this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    dialogInfo.dialog.dismiss();
                    Toast.makeText(SignIn.this, "Please fill the field first !", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        shimmerLogin.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();

        shimmerLogin.stopShimmerAnimation();
    }

    private void startLogin(String email, String pwd) {

        final ProgressDialog dialog = new ProgressDialog(SignIn.this , R.style.MyProgressDialogTheme);
        dialog.setMessage("Please Waiting...");
        dialog.show();
        dialog.setCancelable(false);


        api.login(email , pwd)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        dialog.dismiss();

                        try {

                            if (response.body().getErrorMessage().isEmpty()){

                                Claims value = Jwts.parser()
                                        .setSigningKey(Common.JWT_AUTH_KEY.getBytes())
                                        .parseClaimsJws(response.body().getResult().getToken())
                                        .getBody();

                                if (AES.decrypt(Common.SECRET_KEYWORD).equals(AES.decrypt(value.get("secretKeyword" , String.class)))){

                                    Common.currentUser = response.body().getResult();
                                    startActivity(new Intent(SignIn.this, Category.class));
                                }
                                else
                                    Toast.makeText(SignIn.this, "Token isn't from server !!!!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                try {
                                    Toast.makeText(SignIn.this,AES.decrypt(response.body().getErrorMessage()) , Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        catch (Exception e){
                            Log.d("SomeError" , e.getMessage());
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    private void initViews() {

        txtSignInLabel = findViewById(R.id.txtSignInLabel);
        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        ckbRemember = findViewById(R.id.ckbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        shimmerLogin = findViewById(R.id.shimmerLogin);
        txtVerify = findViewById(R.id.txtVerify);

        shimmerLogin.startShimmerAnimation();
    }
}
