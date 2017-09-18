package api.vdp.visa.com.instagramapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import api.vdp.visa.com.instagramapp.R;
import api.vdp.visa.com.instagramapp.repository.dao.ImageDAO;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by svuddara on 9/16/17.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<ImageDAO> imageDAOList;
    private Context context;

    public PhotosAdapter(Context context,List<ImageDAO> imageDAOList){
        this.imageDAOList = imageDAOList;
        this.context = context;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_adapter_row, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        ImageDAO imageDAO = imageDAOList.get(position);
        holder.imgPicture.setImageResource(0);
        Picasso.with(context).load(imageDAO.getImageUrl()).into(holder.imgPicture);
        holder.txtCaption.setText(imageDAO.getCaption());
        holder.imgProfilePicture.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(20)
                .oval(true)
                .build();

        Picasso.with(context)
                .load(imageDAO.getProfilePicture())
                .transform(transformation)
                .into(holder
                        .imgProfilePicture);

       Long createdTime = Long.valueOf(imageDAO.getCreatedTime()) * 1000;
       String time = new String(DateUtils.getRelativeTimeSpanString(context,createdTime).toString());
       holder.txtCreatedTime.setText(time);
       holder.txtLikes.setText(String.valueOf(imageDAO.getLikeCount()) + " Like(s)");
       holder.txtUserName.setText(imageDAO.getUserName());
    }

    @Override
    public int getItemCount() {
        return imageDAOList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProfilePicture)
        public ImageView imgProfilePicture;

        @BindView(R.id.txtUserName)
        public TextView txtUserName;

        @BindView(R.id.imgPicture)
        public  ImageView imgPicture;

        @BindView(R.id.txtCaption)
        public TextView txtCaption;

        @BindView(R.id.txtLikes)
        public  TextView txtLikes;

        @BindView(R.id.txtTime)
        public TextView txtCreatedTime;

        public PhotoViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
