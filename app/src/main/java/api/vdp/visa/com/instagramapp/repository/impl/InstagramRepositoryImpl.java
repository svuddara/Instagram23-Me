package api.vdp.visa.com.instagramapp.repository.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.vdp.visa.com.instagramapp.repository.webservices.InstagramApiService;
import api.vdp.visa.com.instagramapp.repository.InstagramRepository;
import api.vdp.visa.com.instagramapp.repository.dao.ImageDAO;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserImagesDAO;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramPost;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramImagesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramRepositoryImpl implements InstagramRepository {

    private static String BASE_URL = "https://api.instagram.com";
    InstagramApiService instagramApiService;

    public InstagramRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        instagramApiService = retrofit.create(InstagramApiService.class);
    }

    @Override
    public LiveData<InstagramUserImagesDAO> getUserImages(String accessToken) {

        final MutableLiveData<InstagramUserImagesDAO> userImagesLiveData = new MutableLiveData<>();

        Map<String, String> queryParamsMap = new HashMap<String, String>();
        queryParamsMap.put(OAuthConstants.ACCESS_TOKEN,accessToken);
        Call<InstagramImagesResponse> userImagesResponse = instagramApiService.getUserImages(queryParamsMap);
        userImagesResponse.enqueue(new Callback<InstagramImagesResponse>() {
            @Override
            public void onResponse(Call<InstagramImagesResponse> call, Response<InstagramImagesResponse> response) {
                InstagramImagesResponse instagramImagesResponse = response.body();
                userImagesLiveData.setValue(getUserImagesDAO(instagramImagesResponse));
            }

            @Override
            public void onFailure(Call<InstagramImagesResponse> call, Throwable t) {
                userImagesLiveData.setValue(new InstagramUserImagesDAO.InstagramUserImagesDAOBuilder().error(t).build());
            }
        });

        return userImagesLiveData;
    }

    private InstagramUserImagesDAO getUserImagesDAO(InstagramImagesResponse instagramImagesResponse){
        List<InstagramPost> imageDetailsList = instagramImagesResponse.getData();
        List<ImageDAO> imageDAOList = new ArrayList<>();
        if(imageDetailsList != null){
            for(InstagramPost imageDetail:imageDetailsList){
                ImageDAO imageDAO = getImageDAO(imageDetail);
                imageDAOList.add(imageDAO);
            }
        }
        return new InstagramUserImagesDAO.InstagramUserImagesDAOBuilder().images(imageDAOList)
                .build();
    }

    private ImageDAO getImageDAO(InstagramPost imageDetail){
        return new ImageDAO.ImageDAOBuilder()
                .id(imageDetail.getId())
                .likeCount(imageDetail.getLikes().getCount())
                .imageUrl(imageDetail.getImages().getStandardResolution().getUrl())
                .height(imageDetail.getImages().getStandardResolution().getHeight())
                .width(imageDetail.getImages().getStandardResolution().getWidth())
                .caption(imageDetail.getCaption().getText())
                .createdTime(imageDetail.getCreatedTime())
                .userName(imageDetail.getUser().getUsername())
                .profilePicture(imageDetail.getUser().getProfilePicture())
                .build();
    }


}
