package ir.coleo.chayi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Tester {

    @GET("https://jsonplaceholder.typicode.com/posts/42")
    Call<Chayi> test();


}
