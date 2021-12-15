package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Adapter.ActorAdapter;
import com.abdalkarimalbiekdev.movietube.Adapter.CategoryAdapter;
import com.abdalkarimalbiekdev.movietube.Adapter.CommentAdapter;
import com.abdalkarimalbiekdev.movietube.Adapter.PhotoAdapter;
import com.abdalkarimalbiekdev.movietube.Model.ActorItem;
import com.abdalkarimalbiekdev.movietube.Model.ActorItemResponse;
import com.abdalkarimalbiekdev.movietube.Model.CommentItem;
import com.abdalkarimalbiekdev.movietube.Model.CommentItemResponse;
import com.abdalkarimalbiekdev.movietube.Model.FavouriteId;
import com.abdalkarimalbiekdev.movietube.Model.LikeRateComment;
import com.abdalkarimalbiekdev.movietube.Model.LikeRateCommentCount;
import com.abdalkarimalbiekdev.movietube.Model.LikeRateCommentCountResponse;
import com.abdalkarimalbiekdev.movietube.Model.LikeRateCommentResponse;
import com.abdalkarimalbiekdev.movietube.Model.MovieDetailsModel;
import com.abdalkarimalbiekdev.movietube.Model.MovieDetailsModelResponse;
import com.abdalkarimalbiekdev.movietube.Model.MovieImagesResponse;
import com.abdalkarimalbiekdev.movietube.Model.ServerResponse;
import com.abdalkarimalbiekdev.movietube.Model.User;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.abdalkarimalbiekdev.movietube.Utils.DialogInfo;
import com.abdalkarimalbiekdev.movietube.Utils.DialogRating;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.github.abdularis.piv.VerticalScrollParallaxImageView;

import java.io.File;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    TextView txtName , txtDescription , txtCountLikes , txtCountDisLikes , txtFav;
    ImageView imgShare , imgPlayTrailer , imgPoster , imgLike , imgDisLike , imgFav , imgWatch , imgRating , imgComment;
    RecyclerView recyclerPhotos , recyclerActors , recyclerComments;
    RatingBar ratingEntertainment , ratingPerformingArtists , ratingResolution , ratingMovie;
    MaterialEditText edtComment;
    VerticalScrollParallaxImageView imgBanner;

    RatingBar ratingEntertainmentDialog , ratingPerformingArtistsDialog , ratingResolutionDialog;

    int movieId;
    MovieDetailsModel movie;
    LikeRateComment likeRateComment;
    String favouriteId;
    LikeRateCommentCount likeRateCommentCount;

    MovieAPI api;
    CompositeDisposable compositeDisposable;

    PhotoAdapter photoAdapter;
    ActorAdapter actorAdapter;
    CommentAdapter commentAdapter;

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
        setContentView(R.layout.activity_movie_details);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        initViews();
        api = Common.getAPI();
        compositeDisposable = new CompositeDisposable();


        if (getIntent().getExtras().getInt("movie_id") != -1)
            movieId = getIntent().getExtras().getInt("movie_id");


        if (Common.isConnectionToNetwork(getApplicationContext())){

            loadDetailsMovie();
            loadUserLikeRateComment();
            loadFavourite();
            loadRatingCommentLikesCount();

            loadPhotos();
            loadActors();
            loadComments();

        }
        else
            Toast.makeText(this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();

        clickShare();
        clickTrailer();
        clickWatch();
        clickComment();
        clickRating();
        clickLike();
        clickDislike();
        clickFav();

    }

    private void clickFav() {
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                Log.d("FavouriteId" , favouriteId != null ? favouriteId : "fav is null");

                if (favouriteId != null){
                    if (favouriteId.length() != 0){

                        imgFav.startAnimation(animation);
                        imgFav.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        txtFav.setTextColor(getResources().getColor(android.R.color.white));
                        deleteUserFav();

                    }
                }
                else {
                    imgFav.startAnimation(animation);
                    imgFav.setImageResource(R.drawable.ic_favorite_yallow_24dp);
                    txtFav.setTextColor(getResources().getColor(R.color.colorAccent));
                    insertUserFav();
                }

            }
        });
    }

    private void insertUserFav() {

        if (Common.isConnectionToNetwork(getApplicationContext())){

            api.addUserFav(
                    Common.getToken(),
                    movieId,
                    Common.currentUser.getId())
                    .enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                            try{
                                if (response.body().getErrorMessage().isEmpty()){
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();
                                    //Update Fav
                                    loadFavourite();
                                }
                                else
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
            Toast.makeText(MovieDetails.this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();


    }

    private void deleteUserFav() {

        Log.d("movieId" , String.valueOf(movieId));

        if (Common.isConnectionToNetwork(getApplicationContext())){

            api.deleteUserFav(
                    Common.getToken(),
                    movieId,
                    Common.currentUser.getId())
                    .enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                            try{
                                if (response.body().getErrorMessage().isEmpty()){
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();
                                    //Update Fav
                                    loadFavourite();
                                }
                                else
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
            Toast.makeText(MovieDetails.this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();



    }

    private void clickDislike() {
        imgDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                imgDisLike.startAnimation(animation);

                if (likeRateComment == null){
                    insertUpdateUser_Dis_Like(2);
                    return;
                }


                try {
                    switch (Integer.parseInt(AES.decrypt(likeRateComment.getLiked()))){
                        case 0:{

//                        imgDisLike.setAnimation(animation);
//                        imgDisLike.setImageResource(R.drawable.ic_dislike_red_24dp);
                            insertUpdateUser_Dis_Like(2);
                            break;
                        }
                        case 2:{
//                        imgDisLike.setAnimation(animation);
//                        imgDisLike.setImageResource(R.drawable.ic_dislike_white_24dp);
                            insertUpdateUser_Dis_Like(0);
                            break;
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void clickLike() {
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                imgLike.startAnimation(animation);

                if (likeRateComment == null){
                    insertUpdateUser_Dis_Like(1);
                    return;
                }


                try {
                    switch (Integer.parseInt(AES.decrypt(likeRateComment.getLiked()))){
                        case 0:{

//                        imgLike.setAnimation(animation);
//                        imgLike.setImageResource(R.drawable.ic_like_green_24dp);
                            insertUpdateUser_Dis_Like(1);
                            break;
                        }
                        case 1:{
//                        imgLike.setAnimation(animation);
//                        imgLike.setImageResource(R.drawable.ic_like_white_24dp);
                            insertUpdateUser_Dis_Like(0);
                            break;
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void insertUpdateUser_Dis_Like(int dis_like) {

        if (Common.isConnectionToNetwork(getApplicationContext())){

            try {
                api.addUpdateUserLiked(Common.getToken(),
                        movieId,
                        Common.currentUser.getId(),
                        AES.encrypt(String.valueOf(dis_like)))
                        .enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                                try{
                                    if (response.body().getErrorMessage().isEmpty()){

                                        Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();
                                        //Update ratings , comments , likes
                                        loadUserLikeRateComment();
                                        loadRatingCommentLikesCount();

                                    }
                                    else
                                        Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            @Override
                            public void onFailure(Call<ServerResponse> call, Throwable t) {

                                Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
        else
            Toast.makeText(MovieDetails.this, "Please check your connection internet !", Toast.LENGTH_SHORT).show();



    }

    private void clickRating() {
        imgRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogRating dialogInfo = null;
                try {
                    dialogInfo = new DialogRating(MovieDetails.this,
                            R.drawable.ic_star_white_24dp,
                            "Choose your ratings Here :",
                            "Rating",
                            "OK",
                            "CANCEL",
                            Float.parseFloat(String.valueOf((Float.parseFloat(AES.decrypt(likeRateComment.getRating_entertament())) > 0 ? AES.decrypt(likeRateComment.getRating_entertament()) : 0))),
                            Float.parseFloat(String.valueOf((Float.parseFloat(AES.decrypt(likeRateComment.getRating_perform_actors())) > 0 ? AES.decrypt(likeRateComment.getRating_perform_actors()) : 0))),
                            Float.parseFloat(String.valueOf((Float.parseFloat(AES.decrypt(likeRateComment.getRating_resolution())) > 0 ? AES.decrypt(likeRateComment.getRating_resolution()) : 0)))
                    );
                }
                catch (Exception e) {
                    dialogInfo.dialog.dismiss();
                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                dialogInfo.showDialog();


                DialogRating finalDialogInfo = dialogInfo;
                dialogInfo.btnYES.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        float ratingEntertainment = finalDialogInfo.ratingEntertainmentDialog.getRating();
                        float ratingPerformingArtists = finalDialogInfo.ratingPerformingArtistsDialog.getRating();
                        float ratingResolution = finalDialogInfo.ratingResolutionDialog.getRating();


                        if (ratingEntertainment > 0.0 && ratingPerformingArtists > 0.0 && ratingResolution > 0.0){

                            if (Common.isConnectionToNetwork(MovieDetails.this)){

                                insertUpdateUserRating(ratingEntertainment, ratingPerformingArtists, ratingResolution);
                                finalDialogInfo.dialog.dismiss();
                            }
                            else{
                                finalDialogInfo.dialog.dismiss();
                                Toast.makeText(MovieDetails.this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            finalDialogInfo.dialog.dismiss();
                            Toast.makeText(MovieDetails.this, "Please enter your ratings first !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
    }

    private void insertUpdateUserRating(float ratingEntertainment, float ratingPerformingArtists , float ratingResolution) {

        try {
            api.addUpdateUserRating(Common.getToken(),
                    movieId,
                    Common.currentUser.getId(),
                    AES.encrypt(String.valueOf(ratingEntertainment)),
                    AES.encrypt(String.valueOf(ratingPerformingArtists)),
                    AES.encrypt(String.valueOf(ratingResolution)))
                    .enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                            try {
                                if (response.body().getErrorMessage().isEmpty()){

                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();
                                    //Update ratings , comments , likes
                                    loadUserLikeRateComment();
                                    loadRatingCommentLikesCount();
                                }
                                else {
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void clickComment() {
        imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInfo dialogInfo = new DialogInfo(MovieDetails.this,
                        R.drawable.ic_mode_comment_white_24dp,
                        "Write your comment Here :",
                        "Comment",
                        "OK",
                        "CANCEL",
                        "Comment");
                dialogInfo.showDialog();

                if (likeRateComment != null){
                    if (likeRateComment.getComment() != null) {
                        try {
                            dialogInfo.edtInfo.setText(likeRateComment.getComment().length() > 0 ? AES.decrypt(likeRateComment.getComment()) : "");
                        } catch (Exception e) {
                            Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }


                dialogInfo.edtInfo.setMaxCharacters(80);
                dialogInfo.edtInfo.setInputType(InputType.TYPE_CLASS_TEXT);
                dialogInfo.edtInfo.setFloatingLabelText("Comment");

                dialogInfo.btnYES.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String editVerify = dialogInfo.edtInfo.getText().toString();
                        //asdasdasdsda animation

                        if (!editVerify.isEmpty()){

                            if (Common.isConnectionToNetwork(MovieDetails.this)){

                                insertUpdateUserComment(editVerify);
                                dialogInfo.dialog.dismiss();
                            }
                            else{
                                dialogInfo.dialog.dismiss();
                                Toast.makeText(MovieDetails.this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            dialogInfo.dialog.dismiss();
                            Toast.makeText(MovieDetails.this, "Please fill the field first !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

//                AlertDialog alertDialog;
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(MovieDetails.this , R.style.AlertDialogCustom)
//                        .setMessage("Write your comment Here :")
//                        .setTitle("Comment")
//                        .setIcon(R.drawable.ic_mode_comment_white_24dp)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                if (likeRateComment.getLiked() != 0)
//
//                                if (Common.isConnectionToNetwork(MovieDetails.this)){
//
//                                    insertUpdateUserComment();
//                                }
//                                else
//                                    Toast.makeText(MovieDetails.this, "Please check your connection internet !", Toast.LENGTH_SHORT).show();
//
//
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                View view = getLayoutInflater().inflate(R.layout.comment_dialog_layout , null);
//                dialog.setView(view);
//                alertDialog = dialog.show();
//
//                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.white));
//                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.white));
//
//                edtComment = view.findViewById(R.id.edtComment);
//
//                edtComment.setText(likeRateComment.getComment());

            }
        });
    }

    private void insertUpdateUserComment(String comment) {

        try {
            api.addUpdateUserComment(Common.getToken(),
                    movieId,
                    Common.currentUser.getId(),
                    AES.encrypt(comment))
                    .enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                            try{
                                if (response.body().errorMessage.isEmpty()){

                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getResult()), Toast.LENGTH_SHORT).show();
                                    //Update ratings , comments , likes
                                    loadUserLikeRateComment();
                                    loadRatingCommentLikesCount();
                                    loadComments();

                                }
                                else {
                                    Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                            Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void loadComments() {

        api.getMovieComments(Common.getToken() , movieId)
                .enqueue(new Callback<CommentItemResponse>() {
                    @Override
                    public void onResponse(Call<CommentItemResponse> call, Response<CommentItemResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            commentAdapter = new CommentAdapter(MovieDetails.this , response.body().result);
                            recyclerComments.setAdapter(commentAdapter);
                            startRecyclerViewAnimationVirtual(recyclerComments);
                        }
                        else {
                            try {
                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<CommentItemResponse> call, Throwable t) {
                        Toast.makeText(MovieDetails.this, "Server is close\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(
//                api.getMovieComments(Common.getToken() , movieId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<CommentItem>>() {
//                            @Override
//                            public void accept(List<CommentItem> listResponse) throws Exception {
//
//                                commentAdapter = new CommentAdapter(MovieDetails.this , listResponse);
//                                recyclerComments.setAdapter(commentAdapter);
//                                startRecyclerViewAnimationVirtual(recyclerComments);
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText(MovieDetails.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }));

    }

    private void loadRatingCommentLikesCount() {

        api.getMovieCountCommentsLikesRatings(Common.getToken() , movieId)
                .enqueue(new Callback<LikeRateCommentCountResponse>() {
                    @Override
                    public void onResponse(Call<LikeRateCommentCountResponse> call, Response<LikeRateCommentCountResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            likeRateCommentCount = response.body().getResult().size() > 0 ? response.body().getResult().get(0) : null;

                            if (likeRateCommentCount != null){

                                try {
                                    txtCountLikes.setText(AES.decrypt(likeRateCommentCount.getCount_like()));
                                    txtCountDisLikes.setText(AES.decrypt(likeRateCommentCount.getCount_dislike()));
                                    ratingEntertainment.setRating(Float.parseFloat(AES.decrypt(likeRateCommentCount.getAvg_rating_entertament())));
                                    ratingPerformingArtists.setRating(Float.parseFloat(AES.decrypt(likeRateCommentCount.getRating_perform_actors())));
                                    ratingResolution.setRating(Float.parseFloat(AES.decrypt(likeRateCommentCount.getRating_resolution())));

                                }
                                catch (Exception e) {
                                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }


                            }

                        }
                        else {
                            try {
                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }


                    }
                    @Override
                    public void onFailure(Call<LikeRateCommentCountResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadActors() {

        api.getMovieActors(Common.getToken() , movieId)
                .enqueue(new Callback<ActorItemResponse>() {
                    @Override
                    public void onResponse(Call<ActorItemResponse> call, Response<ActorItemResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            actorAdapter = new ActorAdapter(MovieDetails.this , response.body().getResult());
                            recyclerActors.setAdapter(actorAdapter);
                            startRecyclerViewAnimationHorizontal(recyclerActors);
                        }
                        else {
                            try {
                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ActorItemResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(
//                api.getMovieActors(Common.getToken() , movieId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<ActorItem>>() {
//                            @Override
//                            public void accept(List<ActorItem> listResponse) throws Exception {
//
//                                actorAdapter = new ActorAdapter(MovieDetails.this , listResponse);
//                                recyclerActors.setAdapter(actorAdapter);
//                                startRecyclerViewAnimationHorizontal(recyclerActors);
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText(MovieDetails.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }));

    }

    private void loadPhotos() {

        api.getMovieImages(Common.getToken() , movieId)
                .enqueue(new Callback<MovieImagesResponse>() {
                    @Override
                    public void onResponse(Call<MovieImagesResponse> call, Response<MovieImagesResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            photoAdapter = new PhotoAdapter(MovieDetails.this , response.body().getResult());
                            recyclerPhotos.setAdapter(photoAdapter);
                            startRecyclerViewAnimationHorizontal(recyclerPhotos);
                        }
                        else {
                            try {
                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MovieImagesResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(
//                api.getMovieImages(Common.getToken() , movieId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<String>>() {
//                            @Override
//                            public void accept(List<String> listResponse) throws Exception {
//
//                                photoAdapter = new PhotoAdapter(MovieDetails.this , listResponse);
//                                recyclerPhotos.setAdapter(photoAdapter);
//                                startRecyclerViewAnimationHorizontal(recyclerPhotos);
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText(MovieDetails.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }));
    }

    private void clickWatch() {
        imgWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                imgWatch.startAnimation(animation);

                Intent intent = new Intent(MovieDetails.this , StreamVideo.class);

                try {
                    intent.putExtra("video_path" , Common.BASE_URL + "....................." + AES.decrypt(movie.getMovie_path()));
                }
                catch (Exception e) {
                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                intent.putExtra("is_trailer" , 0);
                startActivity(intent);
            }
        });
    }

    private void clickTrailer() {
        imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                imgPlayTrailer.startAnimation(animation);

                Intent intent = new Intent(MovieDetails.this , StreamVideo.class);

                try {
                    intent.putExtra("video_path" , Common.BASE_URL + "............." + AES.decrypt(movie.getTrailer_path()));
                }
                catch (Exception e) {
                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                intent.putExtra("is_trailer" , 1);
                startActivity(intent);
            }
        });
    }

    private void clickShare() {
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.click_img);
                imgShare.startAnimation(animation);

                if (Common.isConnectionToNetwork(getApplicationContext())){

                    Dexter.withActivity(MovieDetails.this)
                            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {

                                    if (report.areAllPermissionsGranted()){

                                        Picasso.get()
                                                .load(Common.BASE_URL + "................" + movie.getPoster())
                                                .resize(1000 ,1000)
                                                .into(new Target() {
                                                    @Override
                                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                                        try {
                                                            String imagePath =
                                                                    Common.getPathImages() +
                                                                            "/" + AES.decrypt(movie.getName()) + "_poster.png";
                                                        }
                                                        catch (Exception e) {
                                                            Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            e.printStackTrace();
                                                        }

                                                        Uri data;
                                                        File file = Common.createFile(bitmap);

                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

                                                            data = FileProvider.getUriForFile(MovieDetails.this, "com.abdalkarimalbiekdev.movietube.myprovider", file);
                                                            MovieDetails.this.grantUriPermission(MovieDetails.this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                        }
                                                        else
                                                            data = Uri.fromFile(file);

                                                        //Send poster
                                                        Intent share = new Intent(Intent.ACTION_SEND);
                                                        share.setType("image/*");
                                                        share.putExtra(Intent.EXTRA_STREAM, data);
                                                        share.putExtra(Intent.EXTRA_TEXT, movie.getName());
                                                        startActivity(Intent.createChooser(share, "Share Movie's poster!"));
                                                    }

                                                    @Override
                                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                                    }

                                                    @Override
                                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                    }
                                                });
                                    }
                                    else
                                        Toast.makeText(MovieDetails.this, "You can't share the poster !", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                }
                            })
                            .check();



                }
                else
                    Toast.makeText(MovieDetails.this, "Please check your connection internet !", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void loadFavourite() {

        api.getUserFavouriteId(Common.getToken() , movieId , Common.currentUser.getId())
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            try {
                                favouriteId = AES.decrypt(response.body().getResult());

//                                Log.d("FavouriteResponse" ,favouriteId);

                                if (favouriteId != null){
                                    if (favouriteId.length() != 0){

                                        imgFav.setImageResource(R.drawable.ic_favorite_yallow_24dp);
                                        txtFav.setTextColor(getResources().getColor(R.color.colorAccent));
                                    }

                                }
                                else {
                                    imgFav.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                                    txtFav.setTextColor(getResources().getColor(android.R.color.white));
                                }

                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                        else {
                            try {
//                                Toast.makeText(MovieDetails.this,AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();

                                favouriteId = null;
                                imgFav.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                                txtFav.setTextColor(getResources().getColor(android.R.color.white));
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadUserLikeRateComment() {

        api.getUserLikedRatingComment(Common.getToken() , movieId , Common.currentUser.getId())
                .enqueue(new Callback<LikeRateCommentResponse>() {
                    @Override
                    public void onResponse(Call<LikeRateCommentResponse> call, Response<LikeRateCommentResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            likeRateComment = response.body().getResult();

                            if (likeRateComment != null){

                                //Check like
                                try{
                                    switch (Integer.parseInt(AES.decrypt(likeRateComment.getLiked()))){
                                        case 0:{  //He doesn't like or dislike the movie
                                            imgLike.setImageResource(R.drawable.ic_like_white_24dp);
                                            imgDisLike.setImageResource(R.drawable.ic_dislike_white_24dp);
                                            txtCountLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            txtCountDisLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            break;
                                        }
                                        case 1:{  //He like the movie
                                            imgLike.setImageResource(R.drawable.ic_like_green_24dp);
                                            imgDisLike.setImageResource(R.drawable.ic_dislike_white_24dp);
                                            txtCountLikes.setTextColor(Color.parseColor("#53F042"));
                                            txtCountDisLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            break;
                                        }
                                        case 2:{  //He dislike the movie
                                            imgLike.setImageResource(R.drawable.ic_like_white_24dp);
                                            imgDisLike.setImageResource(R.drawable.ic_dislike_red_24dp);
                                            txtCountLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            txtCountDisLikes.setTextColor(Color.parseColor("#F03230"));
                                            break;
                                        }
                                        default:{
                                            imgLike.setImageResource(R.drawable.ic_like_white_24dp);
                                            imgDisLike.setImageResource(R.drawable.ic_dislike_white_24dp);
                                            txtCountLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            txtCountDisLikes.setTextColor(getResources().getColor(android.R.color.white));
                                            break;
                                        }
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }

                        }
                        else {
                            try {
//                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<LikeRateCommentResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadDetailsMovie() {

        api.getMovieDetails(Common.getToken() , movieId)
                .enqueue(new Callback<MovieDetailsModelResponse>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModelResponse> call, Response<MovieDetailsModelResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            movie = response.body().getResult();

                            try {
                                txtName.setText(AES.decrypt(movie.getName()));

                                txtDescription.setText(AES.decrypt(movie.getDescription()));

                                Glide.with(MovieDetails.this)
                                        .load(Common.BASE_URL + "..............." + AES.decrypt(movie.getBanner()))
                                        .centerCrop()
                                        .skipMemoryCache(true)
                                        .into(imgBanner);

                                Glide.with(MovieDetails.this)
                                        .load(Common.BASE_URL + ".................." + AES.decrypt(movie.getPoster()))
                                        .centerCrop()
                                        .skipMemoryCache(true)
                                        .into(imgPoster);

                                ratingMovie.setRating(Float.parseFloat(AES.decrypt(movie.getRate())));
                            }

                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                        else {
                            try {
                                Toast.makeText(MovieDetails.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(MovieDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }


                    }
                    @Override
                    public void onFailure(Call<MovieDetailsModelResponse> call, Throwable t) {

                        Toast.makeText(getApplicationContext() ,"Server is close !\n" +  t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startRecyclerViewAnimationHorizontal(RecyclerView recyclerView) {

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                context , R.anim.layout_slide_right);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        //recyclerHotel.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        //Set Animation
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void startRecyclerViewAnimationVirtual(RecyclerView recyclerView) {

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                context , R.anim.layout_slide_right);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        //recyclerHotel.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Set Animation
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void initViews() {

        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtCountLikes = findViewById(R.id.txtCountLikesss);
        txtCountDisLikes = findViewById(R.id.txtCountDisLikes);
        txtFav = findViewById(R.id.txtFav);
        imgShare = findViewById(R.id.imgShare);
        imgBanner = findViewById(R.id.imgBanner);
        imgPlayTrailer = findViewById(R.id.imgPlayTrailer);
        imgPoster = findViewById(R.id.imgPoster);
        imgLike = findViewById(R.id.imgLike);
        imgDisLike = findViewById(R.id.imgDisLike);
        imgFav = findViewById(R.id.imgFav);
        imgWatch = findViewById(R.id.imgWatch);
        imgRating = findViewById(R.id.imgRating);
        imgComment = findViewById(R.id.imgComment);
        recyclerPhotos = findViewById(R.id.recyclerPhotos);
        recyclerActors = findViewById(R.id.recyclerActors);
        recyclerComments = findViewById(R.id.recyclerComments);
        ratingEntertainment = findViewById(R.id.ratingEntertainment);
        ratingPerformingArtists = findViewById(R.id.ratingPerformingArtists);
        ratingResolution = findViewById(R.id.ratingResolution);
        ratingMovie = findViewById(R.id.ratingMovie);

    }
}
