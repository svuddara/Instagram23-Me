package api.vdp.visa.com.instagramapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserImagesDAO;
import api.vdp.visa.com.instagramapp.repository.InstagramRepository;
import api.vdp.visa.com.instagramapp.repository.impl.InstagramRepositoryImpl;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramViewModel extends ViewModel{

    private MediatorLiveData<InstagramUserImagesDAO> userImagesLiveData;
    private InstagramRepository instagramRepository;

    public InstagramViewModel(){
        userImagesLiveData = new MediatorLiveData<>();
        instagramRepository = new InstagramRepositoryImpl();
    }


    public LiveData<InstagramUserImagesDAO> getUserImages(String accessToken){
        userImagesLiveData.addSource(
                instagramRepository.getUserImages(accessToken),
                new Observer<InstagramUserImagesDAO>() {
                    @Override
                    public void onChanged(@Nullable InstagramUserImagesDAO instagramUserImagesDAO) {
                        userImagesLiveData.setValue(instagramUserImagesDAO);
                    }
                }
        );

        return userImagesLiveData;
    }

}
