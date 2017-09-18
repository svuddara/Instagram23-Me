package api.vdp.visa.com.instagramapp.repository.dao;

import java.util.List;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramUserImagesDAO {


    private List<ImageDAO> imageDAOs;

    private Throwable error;

    private InstagramUserImagesDAO(InstagramUserImagesDAOBuilder builder){

        this.imageDAOs = builder.imageDAOs;
        this.error = builder.error;
    }


    public List<ImageDAO> getImageDAOs() {
        return imageDAOs;
    }

    public void setImageDAOs(List<ImageDAO> imageDAOs) {
        this.imageDAOs = imageDAOs;
    }

    public Throwable getError() {
        return error;
    }

    public static class InstagramUserImagesDAOBuilder{

        private Throwable error;
        private List<ImageDAO> imageDAOs;


        public InstagramUserImagesDAOBuilder images(List<ImageDAO> imageDAOs){
            this.imageDAOs = imageDAOs;
            return this;
        }

        public InstagramUserImagesDAOBuilder error(Throwable error){
            this.error = error;
            return this;
        }

        public InstagramUserImagesDAO build(){
            return new InstagramUserImagesDAO(this);
        }
    };
}
