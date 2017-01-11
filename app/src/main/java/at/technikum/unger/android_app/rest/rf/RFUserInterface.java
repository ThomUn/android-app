package at.technikum.unger.android_app.rest.rf;

import at.technikum.unger.android_app.rest.pojo.ErrorMessage;
import at.technikum.unger.android_app.rest.pojo.LogoutRequest;
import at.technikum.unger.android_app.rest.pojo.LogoutResponse;
import at.technikum.unger.android_app.rest.pojo.RegisterRequest;
import at.technikum.unger.android_app.rest.pojo.RegisterResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Thomas on 06.11.2016.
 */

public interface RFUserInterface {
    @POST("/login")
    public Call<RegisterResponse> login(@Body RegisterRequest request);

    @POST("/logout")
    public Call<LogoutResponse> logout(@Body LogoutRequest request);

    @GET("/test")
    public Call<ErrorMessage> test();
}
