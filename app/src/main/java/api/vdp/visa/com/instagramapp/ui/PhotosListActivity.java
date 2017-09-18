package api.vdp.visa.com.instagramapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import api.vdp.visa.com.instagramapp.R;

import api.vdp.visa.com.instagramapp.utils.CookieUtils;
import api.vdp.visa.com.instagramapp.utils.SharedPreferencesUtils;
import api.vdp.visa.com.instagramapp.viewmodel.InstagramViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserImagesDAO;
import api.vdp.visa.com.instagramapp.repository.dao.ImageDAO;

public class PhotosListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_list);
        ButterKnife.bind(this);
        context = this;
        setSupportActionBar(toolbar);
        populatePostsView();
    }

    private void populatePostsView(){
        String accessToken =SharedPreferencesUtils.getAccessTokenFromSharedPreferences(this,OAuthConstants.ACCESS_TOKEN);
        InstagramViewModel instagramViewModel = ViewModelProviders.of(this).get(InstagramViewModel.class);
        LiveData<InstagramUserImagesDAO> imagesDAOLiveData = instagramViewModel.getUserImages(accessToken);
        imagesDAOLiveData.observe(this, new Observer<InstagramUserImagesDAO>() {
            @Override
            public void onChanged(@Nullable InstagramUserImagesDAO instagramUserImagesDAO) {
                if(instagramUserImagesDAO.getError() != null){
                    Toast.makeText(getApplicationContext(), "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
                }else{
                    List<ImageDAO> imageDAOList = instagramUserImagesDAO.getImageDAOs();
                    PhotosAdapter photosAdapter = new PhotosAdapter(context,imageDAOList);
                    recyclerView.setAdapter(photosAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Logout")){
            clearSharedPreferences();
            clearBrowserCookies();
            MoveToLoginScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearBrowserCookies(){
        CookieUtils.clearCookies(getApplicationContext());
    }

    private void clearSharedPreferences(){
        SharedPreferencesUtils.clearPreference(context);
    }

    private void MoveToLoginScreen(){
        Intent loginIntent = new Intent(this,LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(new Intent(context,LoginActivity.class));
    }
}
