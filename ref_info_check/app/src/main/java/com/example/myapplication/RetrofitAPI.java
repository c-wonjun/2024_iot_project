package com.example.myapplication;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    @GET("/bluetooth_data_list") // 서버에 GET 요청을 할 주소를 입력
    Call<JsonObject> getTodoList(); // MainActivity에서 사용할 JSON 파일 가져오는 메서드
}
