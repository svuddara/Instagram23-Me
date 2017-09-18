package api.vdp.visa.com.instagramapp.repository.webservices;

import java.util.Map;

import api.vdp.visa.com.instagramapp.repository.vo.InstagramImagesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by svuddara on 9/16/17.
 */


public interface InstagramApiService {


    @GET("/v1/users/self/media/recent")
    Call<InstagramImagesResponse> getUserImages(@QueryMap Map<String,String> queryParam);

}
