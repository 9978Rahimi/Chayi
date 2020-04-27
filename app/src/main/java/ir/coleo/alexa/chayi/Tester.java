package ir.coleo.alexa.chayi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Tester {

    @GET("https://jsonplaceholder.typicode.com/posts/42")
    Call<Chayi> test();


}
