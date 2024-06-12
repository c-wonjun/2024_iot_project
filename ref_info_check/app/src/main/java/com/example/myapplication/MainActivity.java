package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG_MainActivity";

    private Retrofit mRetrofit;
    private RetrofitAPI mRetrofitAPI;
    private Call<JsonObject> mCallTodoList;

    private TextView textView1, textView2, textView3, textView4;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            callTodoList();
            handler.postDelayed(this, 1000); //1초마다 실행
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);

        setRetrofit();

        // 주기적인 작업을 시작
        handler.post(runnable);
    }

    private void setRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitAPI = mRetrofit.create(RetrofitAPI.class);
    }

    private void callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList();
        mCallTodoList.enqueue(new Callback<JsonObject>() {
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "에러입니다. => " + t.getMessage());
                textView1.setText("에러\n" + t.getMessage());
            }

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject result = response.body();
                Log.d(TAG, "결과는 => " + result);

                Gson mGson = new Gson();
                DataModel.TodoInfo1 dataParsed1 = mGson.fromJson(result, DataModel.TodoInfo1.class);
                DataModel.TodoInfo2 dataParsed2 = mGson.fromJson(result, DataModel.TodoInfo2.class);
                DataModel.TodoInfo3 dataParsed3 = mGson.fromJson(result, DataModel.TodoInfo3.class);

                textView1.setText("냉장고 데이터");
                textView2.setText("현재 온도 : " + dataParsed3.getTodo3().getTask() + "C");
                textView3.setText("현재 습도 : " + dataParsed2.getTodo2().getTask() + "%");
                textView4.setText("현재 가스 농도 : " + dataParsed1.getTodo1().getTask() + "ppm");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 액티비티가 파괴될 때, 주기적인 작업을 중지
    }
}
