package com.abdalkarimalbiekdev.movietube.Remote;

import com.abdalkarimalbiekdev.movietube.Model.ActorItem;
import com.abdalkarimalbiekdev.movietube.Model.ActorItemResponse;
import com.abdalkarimalbiekdev.movietube.Model.BestMovieImage;
import com.abdalkarimalbiekdev.movietube.Model.BestMovieImageResponse;
import com.abdalkarimalbiekdev.movietube.Model.Category;
import com.abdalkarimalbiekdev.movietube.Model.CategoryResponse;
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
import com.abdalkarimalbiekdev.movietube.Model.MovieModel;
import com.abdalkarimalbiekdev.movietube.Model.MovieResponse;
import com.abdalkarimalbiekdev.movietube.Model.ServerResponse;
import com.abdalkarimalbiekdev.movietube.Model.User;
import com.abdalkarimalbiekdev.movietube.Model.UserResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MovieAPI {

    @FormUrlEncoded
    @POST("api/users/login")
    Call<UserResponse> login(@Field("email") String email , @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/register")
    Call<ServerResponse> insertUserData(@Field("email") String email , @Field("password") String password,
                                        @Field("name") String name);

    @FormUrlEncoded
    @POST("api/users/verify")
    Call<UserResponse> verify(@Field("verifyCode") String verifyCode);

    @Multipart
    @POST("uploadImage")
    Call<String> uploadImage(@Part MultipartBody.Part id , @Part MultipartBody.Part file);

    @GET("api/categories/")
    Call<CategoryResponse> getCategory(@Header("Authorization") String token);

    @GET("api/movies/get_best_movies")
    Call<BestMovieImageResponse> getBesMovies(@Header("Authorization") String token);

    @GET("api/categories/get_movies_by_category_Id")
    Call<MovieResponse> getMovies(@Header("Authorization") String token , @Query("categoryId") int category_id);

    @POST("api/favourites/")
    Call<ServerResponse> addUserFav(@Header("Authorization") String token ,
                            @Query("movieId") int movie_id , @Query("userId") int user_id);

    @DELETE("api/favourites/")
    Call<ServerResponse> deleteUserFav(@Header("Authorization") String token ,
                            @Query("movieId") int movie_id , @Query("userId") int user_id);

    @FormUrlEncoded
    @POST("api/users/add_update_like")
    Call<ServerResponse> addUpdateUserLiked(@Header("Authorization") String token ,
                                    @Field("movieId") int movie_id ,
                                    @Field("userId") int user_id ,
                                    @Field("liked") String liked);

    @FormUrlEncoded
    @POST("api/users/add_update__rating")
    Call<ServerResponse> addUpdateUserRating(@Header("Authorization") String token ,
                                    @Field("movieId") int movie_id ,
                                    @Field("userId") int user_id ,
                                    @Field("userRatingEntertament") String rating_entertament ,
                                    @Field("userRatingPerformActor") String rating_perform_actors ,
                                    @Field("userRatingResolution") String rating_resolution);

    @FormUrlEncoded
    @POST("api/users/add_update_comment")
    Call<ServerResponse> addUpdateUserComment(@Header("Authorization") String token ,
                                    @Field("movieId") int movie_id ,
                                    @Field("userId") int user_id ,
                                    @Field("userComment") String comment);

    @GET("api/movies/get_movie_comments")
    Call<CommentItemResponse> getMovieComments(@Header("Authorization") String token , @Query("movieId") int movie_id);

    @GET("api/movies/get_movie_count_comments_likes_ratings")
    Call<LikeRateCommentCountResponse> getMovieCountCommentsLikesRatings(@Header("Authorization") String token ,
                                                                         @Query("movieId") int movie_id);

    @GET("api/movies/get_movie_actors")
    Call<ActorItemResponse> getMovieActors(@Header("Authorization") String token ,
                                           @Query("movieId") int movie_id);

    @GET("api/movies/get_movie_images")
    Call<MovieImagesResponse> getMovieImages(@Header("Authorization") String token ,
                                             @Query("movieId") int movie_id);

    @GET("api/favourites/get_favourite_Id")
    Call<ServerResponse> getUserFavouriteId(@Header("Authorization") String token ,
                                         @Query("movieId") int movie_id ,
                                         @Query("userId") int user_id);

    @GET("api/users/get_like_ratings_comment")
    Call<LikeRateCommentResponse> getUserLikedRatingComment(@Header("Authorization") String token ,
                                                            @Query("movieId") int movie_id ,
                                                            @Query("userId") int user_id);

    @GET("api/movies/get_movie_description")
    Call<MovieDetailsModelResponse> getMovieDetails(@Header("Authorization") String token ,
                                                    @Query("movieId") int movie_id);
}
