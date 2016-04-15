package com.leo.cattle.data.net;

import com.leo.cattle.data.entity.CattleEntity;
import com.leo.cattle.data.entity.ChatSessionEntity;
import com.leo.cattle.data.entity.CostEntity;
import com.leo.cattle.data.entity.EventEntity;
import com.leo.cattle.data.entity.MessageEntity;
import com.leo.cattle.data.entity.UserEntity;
import com.leo.cattle.data.entity.WeightEntity;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by leobui on 3/23/2016.
 */
public class ApiService {

    private static final String BASE_URI = "http://192.168.1.4/cattle_manager/api/v1/";
    private EndPoints mEndPoints;

    public ApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        mEndPoints = retrofit.create(EndPoints.class);
    }

    public EndPoints getApi() {

        return mEndPoints;
    }

    public interface EndPoints {

        @GET("me/users")
        public Observable<List<UserEntity>>
        userEntityList(@Header("X-AUTH") String token);

        @GET("me/{userId}")
        public Observable<UserEntity>
        userEntityById(@Path("userId") int userId);

        @GET("me/chatSessions")
        public Observable<List<ChatSessionEntity>>
        sessionEntityList(@Header("X-AUTH") String token);

        @FormUrlEncoded
        @POST("signup")
        public Observable<UserEntity>
        askForSignup(@Field("name") String userName,
                     @Field("email") String userEmail,
                     @Field("password") String userPassword);

        @FormUrlEncoded
        @POST("signin")
        public Observable<UserEntity>
        askForSignIn(@Field("name") String userName,
                     @Field("password") String userPassword);

        //@FormUrlEncoded
        @POST("signout")
        public Observable<MessageEntity> askForLogout(@Header("X-AUTH") String token);

        @FormUrlEncoded
        @POST("cattle/add")
        public Observable<CattleEntity>
        newCattle(@Header("X-AUTH") String token,
                  @Field("name") String userName,
                  @Field("description") String des,
                  @Field("blood") String blood,
                  @Field("weight") int weight,
                  @Field("month_old") int monthOld,
                  @Field("cost") int cost,
                  @Field("buy_date") String date,
                  @Field("user_id") int userId);

        @FormUrlEncoded
        @POST("cost/add")
        public Observable<CostEntity>
        newCost(@Header("X-AUTH") String token,
                @Field("name") String name,
                @Field("description") String des,
                @Field("cost") int cost,
                @Field("date") String date,
                @Field("user_id") int userId);

        @FormUrlEncoded
        @POST("event/adds")
        public Observable<EventEntity>
        newEvent(@Header("X-AUTH") String token,
                @Field("name") String name,
                @Field("description") String des,
                @Field("cost") int cost,
                @Field("date") String date,
                @Field("user_id") int userId,
                @Field("cattle_ids") String cattles);

        @FormUrlEncoded
        @POST("weight/add")
        public Observable<WeightEntity>
        newWeight(@Header("X-AUTH") String token,
                 @Field("name") String name,
                 @Field("description") String des,
                 @Field("weight") int weight,
                 @Field("date") String date,
                 @Field("user_id") int userId,
                 @Field("cattle_id") int cattleId);

        @GET("me/cattles")
        public Observable<List<CattleEntity>>
        cattleEntityList(@Header("X-AUTH") String token);
    }
}
