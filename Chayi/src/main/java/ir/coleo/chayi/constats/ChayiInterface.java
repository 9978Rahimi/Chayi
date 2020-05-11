package ir.coleo.chayi.constats;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface ChayiInterface {


    @GET
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token);

    @POST
    Call<ResponseBody> post(@Url String url, @Body RequestBody chayi, @Header("Authorization") String token);

    @POST
    Call<ResponseBody> post(@Url String url, @Body RequestBody chayi);

    @PUT
    Call<ResponseBody> put(@Url String url, @Body RequestBody body, @Header("Authorization") String token);

    @DELETE
    Call<ResponseBody> delete(@Url String url, @Header("Authorization") String token);


}
