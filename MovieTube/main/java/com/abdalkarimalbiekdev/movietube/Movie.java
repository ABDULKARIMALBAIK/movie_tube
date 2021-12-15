package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Adapter.CategoryAdapter;
import com.abdalkarimalbiekdev.movietube.Adapter.MovieAdapter;
import com.abdalkarimalbiekdev.movietube.Model.MovieModel;
import com.abdalkarimalbiekdev.movietube.Model.MovieResponse;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;

import java.util.List;

public class Movie extends AppCompatActivity {

    RecyclerView recyclerMovies;

    MovieAPI api;
    CompositeDisposable compositeDisposable;

    int category_id;

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
        setContentView(R.layout.activity_movie);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }


        if (getIntent().getExtras().getInt("category_id") != -1)
            category_id = getIntent().getExtras().getInt("category_id");

        api = Common.getAPI();
        compositeDisposable = new CompositeDisposable();
        recyclerMovies = findViewById(R.id.recyclerMovies);



        if (Common.isConnectionToNetwork(getApplicationContext())){
            loadMovies();
        }
        else
            Toast.makeText(this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();
    }

    private void loadMovies() {

        api.getMovies(Common.getToken() , category_id)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            MovieAdapter adapter = new MovieAdapter(Movie.this , response.body().result);
                            recyclerMovies.setAdapter(adapter);
                            startRecyclerViewAnimation();
                        }
                        else {
                            try {
                                Toast.makeText(Movie.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(Movie.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(Movie.this,"Server is close\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(
//                api.getMovies(Common.currentUser.getToken() , category_id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<MovieModel>>() {
//                    @Override
//                    public void accept(List<MovieModel> listResponse) throws Exception {
//
//                        MovieAdapter adapter = new MovieAdapter(Movie.this , listResponse);
//                        recyclerMovies.setAdapter(adapter);
//                        startRecyclerViewAnimation();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(Movie.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }));
    }

    private void startRecyclerViewAnimation() {

        Context context = recyclerMovies.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                context , R.anim.layout_slide_right);

        recyclerMovies.setHasFixedSize(true);
        recyclerMovies.setItemViewCacheSize(20);
        recyclerMovies.setDrawingCacheEnabled(true);
        //recyclerHotel.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));

        //Set Animation
        recyclerMovies.setLayoutAnimation(controller);
        recyclerMovies.getAdapter().notifyDataSetChanged();
        recyclerMovies.scheduleLayoutAnimation();
    }
}
