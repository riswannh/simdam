package id.pdam.simdam.main.suin.api.service;

import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.LoginDao;
import id.pdam.simdam.main.suin.shared.Constant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @FormUrlEncoded
    @POST(Constant.API.URL_LOGIN)
    Call<BaseDao<LoginDao>> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
