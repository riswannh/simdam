package id.pdam.simdam.main.suin.api.service;

import java.util.List;

import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.FilterItemDao;
import id.pdam.simdam.main.suin.api.dao.KontenSuinDao;
import id.pdam.simdam.main.suin.api.dao.PenerimaDao;
import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;
import id.pdam.simdam.main.suin.shared.Constant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
}
