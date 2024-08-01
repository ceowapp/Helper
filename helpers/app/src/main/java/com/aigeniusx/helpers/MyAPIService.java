package com.aigeniusx.helpers;

public interface MyApiService {
    @POST("api/v1/users/login")
    Call<UserResponse> loginUser(@Body UserRequest userRequest);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://your-api-base-url.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    MyApiService apiService = retrofit.create(MyApiService.class);

    UserRequest userRequest = new UserRequest(username, password);
    Call<UserResponse> call = apiService.loginUser(userRequest);

    call.enqueue(new Callback<UserResponse>() {
        @Override
        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
            if (response.isSuccessful()) {
                // Handle a successful response
                UserResponse userResponse = response.body();
            } else {
                // Handle an error response
            }
        }

        @Override
        public void onFailure(Call<UserResponse> call, Throwable t) {
            // Handle network or other errors
        }
    });


}


