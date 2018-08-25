package com.example.admin.collegenoti.api;


import com.example.admin.collegenoti.models.GetManagementUtilsInput;
import com.example.admin.collegenoti.models.GetManagementUtilsResult;
import com.example.admin.collegenoti.models.ManagementUploadInput;
import com.example.admin.collegenoti.models.ManagementUploadResult;
import com.example.admin.collegenoti.models.UserLoginInput;
import com.example.admin.collegenoti.models.UserLoginResult;
import com.example.admin.collegenoti.models.UserRegisterInput;
import com.example.admin.collegenoti.models.UserRegisterResult;
import com.example.admin.collegenoti.models.WhoInput;
import com.example.admin.collegenoti.models.WhoResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Shashi on 16/03/2017.
 */
public interface WebServices {
    @POST("TUserLogin_c/userLogin")
    Call<UserLoginResult> userLogin(@Body UserLoginInput input);

    @POST("TUserRegister_c/userRegister")
    Call<UserRegisterResult> userRegister(@Body UserRegisterInput input);

    @POST("UploadManagementUtil_c/uploadManagementUtil")
    Call<ManagementUploadResult> management(@Body ManagementUploadInput input);

    @POST("GetManagementUtils_c/getManagementUtils")
    Call<GetManagementUtilsResult> getManagementUtils (@Body GetManagementUtilsInput input);

    @POST("GetManagementUtilsWho_c/getManagementUtilsWho")
    Call<WhoResult> who (@Body WhoInput input);
}


