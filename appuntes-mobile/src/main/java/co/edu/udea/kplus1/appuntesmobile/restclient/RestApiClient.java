package co.edu.udea.kplus1.appuntesmobile.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sentimentanalysis.ddns.net/appuntes/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
