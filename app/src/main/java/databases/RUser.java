package databases;

import io.realm.RealmObject;

/**
 * Created by SHAShi on 14-03-2018.
 */

public class RUser extends RealmObject {

    private String user_id;
    private String user_name;
    private String user_mobile;
    private String user_password;





    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}