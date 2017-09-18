package api.vdp.visa.com.instagramapp.repository;

import android.arch.lifecycle.LiveData;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserImagesDAO;

/**
 * Created by svuddara on 9/16/17.
 */

public interface InstagramRepository {

     public LiveData<InstagramUserImagesDAO> getUserImages(String accessToken);

     //add function to post for like

}
