package com.example.chating_app.api;

import com.example.chating_app.api_entities.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendApi {
    @POST("contacts/transfer")
    Call<String> transfer(@Body Api_Transfer transfer);

    @POST("contacts/invitations")
    Call<String> invitations(@Body Api_Invite invite);
}
