package id.web.wachid.pms.util.api;

/**
 * Created by command center on 12/6/2017.
 */

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}