package id.pdam.simdam.main.suin.api.service;

import java.util.List;

import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.FilterItemDao;
import id.pdam.simdam.main.suin.api.dao.KontenSuinDao;
import id.pdam.simdam.main.suin.api.dao.PenerimaDao;
import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;
import id.pdam.simdam.main.suin.api.param.LampiranParam;
import id.pdam.simdam.main.suin.shared.Constant;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SuinService {
    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @GET(Constant.API.URL_GET_SUIN_INBOX)
    Call<BaseDao<List<SuinInboxDao>>> getInbox(
            @Query("idPegawai") String idPegawai,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @GET(Constant.API.URL_GET_SUIN_KONTEN)
    Call<BaseDao<List<KontenSuinDao>>> getBaca(
            @Query("idSuin") String idSuin,
            @Query("min") int min,
            @Query("max") int max
    );

    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @GET(Constant.API.URL_GET_SUIN_PENERIMA)
    Call<BaseDao<List<PenerimaDao>>> getPenerima(
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("keyword") String keyword
    );

    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @Multipart
    @POST(Constant.API.URL_POST_LAMPIRAN)
    Call<BaseDao> uploadLampiran(
            @Query("idKontenSuin") String idKontenSuin,
            @Part("lampiran\"") RequestBody lampiran
    );

    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @FormUrlEncoded
    @POST(Constant.API.URL_POST_SUIN)
    Call<BaseDao> postSuin(
            @Field("idPegawai") String idPegawai,
            @Field("judul") String judul,
            @Field("pesan") String pesan,
            @Field("tembusan") String tembusan,
            @Field("sms") String sms,
            @Field("wa") String wa,
            @Field("cekTembusan") String cekTembusan

    );

    @Headers({"X-Auth-Token: " + Constant.HEADER.token, "X-Auth-User: " + Constant.HEADER.auth})
    @FormUrlEncoded
    @POST(Constant.API.URL_POST_SUIN_BALAS)
    Call<BaseDao> postSuinBalas(
            @Field("idPegawai") String idPegawai,
            @Field("tembusan") String tembusan,
            @Field("cekTembusan") String cekTembusan,
            @Field("sms") String sms,
            @Field("wa") String wa,
            @Field("pesan") String pesan
    );
}
