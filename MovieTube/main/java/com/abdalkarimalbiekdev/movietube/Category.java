package com.abdalkarimalbiekdev.movietube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.abdalkarimalbiekdev.movietube.Adapter.BestMoviesSliderAdapter;
import com.abdalkarimalbiekdev.movietube.Adapter.CategoryAdapter;
import com.abdalkarimalbiekdev.movietube.Model.BestMovieImageResponse;
import com.abdalkarimalbiekdev.movietube.Model.CategoryResponse;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Security.AES;
import com.abdalkarimalbiekdev.movietube.Utils.Common;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class Category extends AppCompatActivity {

    RecyclerView recyclerCategory;
    SliderView bestMoviesSlider;

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
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        //Change soft-key Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }


        api = Common.getAPI();
        compositeDisposable = new CompositeDisposable();


        initViews();


        if (Common.isConnectionToNetwork(getApplicationContext())){

            loadCategories();
            loadBestMoviesImages();
        }
        else
            Toast.makeText(this, "Please check your connection Network !", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        bestMoviesSlider.startAutoCycle();

    }

    private void initViews() {
        recyclerCategory = findViewById(R.id.recyclerCategory);
        bestMoviesSlider = findViewById(R.id.bestMoviesSlider);
    }

    private void loadCategories() {

        api.getCategory(Common.getToken())
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            CategoryAdapter adapter = new CategoryAdapter(Category.this , response.body().getResult());
                            recyclerCategory.setAdapter(adapter);
                            startRecyclerViewAnimation();
                        }
                        else {
                            try {
                                Toast.makeText(Category.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(Category.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
                        Toast.makeText(Category.this, "Server is close\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(api.getCategory(Common.currentUser.getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<com.abdalkarimalbiekdev.movietube.Model.Category>>() {
//                    @Override
//                    public void accept(List<com.abdalkarimalbiekdev.movietube.Model.Category> listResponse) throws Exception {
//
//                        CategoryAdapter adapter = new CategoryAdapter(Category.this , listResponse);
//                        recyclerCategory.setAdapter(adapter);
//                        startRecyclerViewAnimation();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(Category.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }));


    }

    private void startRecyclerViewAnimation() {

        Context context = recyclerCategory.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                context , R.anim.layout_fall_down);

        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setItemViewCacheSize(20);
        recyclerCategory.setDrawingCacheEnabled(true);
        //recyclerHotel.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerCategory.setLayoutManager(new GridLayoutManager(this , 2));

        //Set Animation
        recyclerCategory.setLayoutAnimation(controller);
        recyclerCategory.getAdapter().notifyDataSetChanged();
        recyclerCategory.scheduleLayoutAnimation();
    }

    private void loadBestMoviesImages() {

        api.getBesMovies(Common.getToken())
                .enqueue(new Callback<BestMovieImageResponse>() {
                    @Override
                    public void onResponse(Call<BestMovieImageResponse> call, Response<BestMovieImageResponse> response) {

                        if (response.body().getErrorMessage().isEmpty()){

                            bestMoviesSlider.setSliderAdapter(new BestMoviesSliderAdapter(getApplicationContext() , response.body().getResult()));

                            bestMoviesSlider.setIndicatorAnimation(IndicatorAnimations.DROP);
                            bestMoviesSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
                            bestMoviesSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            bestMoviesSlider.setIndicatorSelectedColor(Color.WHITE);
                            bestMoviesSlider.setIndicatorUnselectedColor(Color.GRAY);
                            bestMoviesSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
                            bestMoviesSlider.startAutoCycle();

                        }
                        else {
                            try {
                                Toast.makeText(Category.this, AES.decrypt(response.body().getErrorMessage()), Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(Category.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BestMovieImageResponse> call, Throwable t) {
                        Toast.makeText(Category.this, "Server is close\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        compositeDisposable.add(api.getBesMovies(Common.currentUser.getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<BestMovieImage>>() {
//                    @Override
//                    public void accept(List<BestMovieImage> images) throws Exception {
//
//                        bestMoviesSlider.setSliderAdapter(new BestMoviesSliderAdapter(getApplicationContext() , images));
//
//                        bestMoviesSlider.setIndicatorAnimation(IndicatorAnimations.DROP);
//                        bestMoviesSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
//                        bestMoviesSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//                        bestMoviesSlider.setIndicatorSelectedColor(Color.WHITE);
//                        bestMoviesSlider.setIndicatorUnselectedColor(Color.GRAY);
//                        bestMoviesSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
//                        bestMoviesSlider.startAutoCycle();
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(Category.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }));


    }
}
