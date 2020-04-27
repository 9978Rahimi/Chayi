package ir.coleo.alexa.chayi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChayiInterface {

    @GET("{url}")
    Call<ResponseBody> get(@Path("url") String url);

    @POST("{url}")
    Call<ResponseBody> post(@Path("url") String url, @Body Chayi chayi);

    @PUT("{url}")
    Call<ResponseBody> put(@Path("url") String url, @Body Chayi chayi);

    @DELETE("{url}")
    Call<ResponseBody> delete(@Path("url") String url);

}
