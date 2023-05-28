package co.edu.udea.kplus1.appuntesmobile.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://181.133.33.188:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
