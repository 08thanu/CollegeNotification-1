package databases;

import android.content.Context;

import com.example.admin.collegenoti.models.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by SHASHI on 14-03-2018.
 */

public class RealmU {
    private Context context;
    private Realm realm;

    public RealmU(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    public boolean insertUserDetails(final List<User> u) {
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RUser rUser = realm.createObject(RUser.class);
//                    rUser.setUser_id(u.user_id);
//                    rUser.setUser_name(u.user_name);
//                    rUser.setUser_mobile(u.user_mobile);
//                    rUser.setUser_password(u.user_password);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


//
//    public boolean insertUserDetails(final User u) {
//        try {
//            realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    RUser rUser = realm.createObject(RUser.class);
//                    rUser.setUser_id(u.user_id);
//                    rUser.setUser_name(u.user_name);
//                    rUser.setUser_mobile(u.user_mobile);
//                    rUser.setUser_password(u.user_password);
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public boolean truncateUserTable() {
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(RUser.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public RUser getUserdetails() {
        try {
            RUser user = realm.where(RUser.class).findFirst();
            return user;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public boolean isUserLoggedIn() {
        try {
            RealmResults<RUser> user = realm.where(RUser.class).findAll();
            if (user.size() > 0) return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
