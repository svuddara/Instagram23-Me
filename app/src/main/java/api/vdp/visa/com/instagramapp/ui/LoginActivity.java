package api.vdp.visa.com.instagramapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import api.vdp.visa.com.instagramapp.R;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;
import api.vdp.visa.com.instagramapp.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = this;
    }

    @OnClick (R.id.login)
    public void onLogin(){
        /**
         * if access_token is in shared preference send to posts activity
         * else send to instagram login
         */
        String accessToken = SharedPreferencesUtils.getAccessTokenFromSharedPreferences(getApplicationContext(), OAuthConstants.ACCESS_TOKEN);
        if(accessToken == null || accessToken.isEmpty()){
            startActivity(new Intent(context,InstagramHomeActivity.class));
        }else{
            startActivity(new Intent(context,PhotosListActivity.class));
        }

    }


}
