package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinSearchPenerimaActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.PenerimaDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPenerimaActivity extends AppCompatActivity implements SearchPenerimaAdapter.AdapterListener,SuinPenerimaSelectedAdapter.AdapterListener {
    SuinSearchPenerimaActivityBinding binding;
    private Context context;

    private RecyclerView rvPenerima;
    SuinService mService;
    protected LinearLayoutManager lmPenerima;
    SearchPenerimaAdapter PenerimaAdapter;
    private ArrayList<PenerimaDao> dataListPenerima = new ArrayList<>();


    private ArrayList<PenerimaDao> dataListPenerimaSelected = new ArrayList<>();
    SuinPenerimaSelectedAdapter penerimaSelectedAdapter;
    private RecyclerView rvPenerimaSelected;
    protected LinearLayoutManager lmPenerimaSelected;

    private int totalItem = 0;
    private int offset = 0;
    private int limit = 10;
    boolean isRefresh = false;
    boolean isLoad = false;
    boolean isCallApi = false;
    int jenis = 0;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_search_penerima_activity);
        mService = ApiClient.getClient().create(SuinService.class);
        config();
    }

    private void config() {
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.suin_search_penerima_activity);
        SearchPenerimaActivityVM viewModel = new SearchPenerimaActivityVM(this);
        binding.setVm(viewModel);
        rvPenerima = binding.rvListPenerima;
        PenerimaAdapter = new SearchPenerimaAdapter(this, dataListPenerima, this);
        lmPenerima = new LinearLayoutManager(SearchPenerimaActivity.this, LinearLayoutManager.VERTICAL, false);
        rvPenerima.setLayoutManager(lmPenerima);
        rvPenerima.setAdapter(PenerimaAdapter);
        rvPenerima.addOnScrollListener(scrollListener);
        PenerimaAdapter.setErrorFooter(0);
        rvPenerima.setHasFixedSize(true);
        binding.btnSearch.setOnClickListener(clickSearch);

        rvPenerimaSelected = binding.rvListSelected;
        lmPenerimaSelected = new LinearLayoutManager(SearchPenerimaActivity.this,LinearLayoutManager.VERTICAL,false);
        penerimaSelectedAdapter = new SuinPenerimaSelectedAdapter(this,dataListPenerimaSelected,this);
        rvPenerimaSelected.setLayoutManager(lmPenerimaSelected);
        rvPenerimaSelected.setAdapter(penerimaSelectedAdapter);
    }


    private View.OnClickListener clickSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            keyword = binding.edtSearch.getText().toString();
            limit = 10;
            offset = 0;
            dataListPenerima.clear();
            callFilterApi(keyword,offset,limit);
        }
    };



    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == lmPenerima.findLastCompletelyVisibleItemPosition() && isLoad && !isCallApi) {
                PenerimaAdapter.setFooterVisible(true);
                limit += 10;
                offset++;
                callFilterApi(keyword, offset, limit);
            }
        }
    };



    public void callFilterApi(String keyword, int offset, int limit) {
        isCallApi = true;
        isLoad = true;
        Call<BaseDao<List<PenerimaDao>>> callPenerima = mService.getPenerima(limit,offset,keyword);
        callPenerima.enqueue(new Callback<BaseDao<List<PenerimaDao>>>() {
            @Override
            public void onResponse(Call<BaseDao<List<PenerimaDao>>> call, Response<BaseDao<List<PenerimaDao>>> response) {
                Log.d("TAG",response.raw().request().toString());
                PenerimaAdapter.setFooterVisible(false);
                isCallApi = false;
                if (response.body().DATA.isEmpty()) {
                    dataListPenerima.clear();
                    PenerimaAdapter.setErrorFooter(1);
                    isLoad = false;
                } else {
                    dataListPenerima.addAll(response.body().DATA);
                    totalItem = dataListPenerima.size();
                    if (totalItem % 10 == 0)
                        isLoad = true;
                    else
                        isLoad = false;
                    PenerimaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseDao<List<PenerimaDao>>> call, Throwable t) {

            }
        });
    }

    public static void startThisActiviy(Context context,int jenis,String idSuin){
        Intent intent = new Intent(context,SearchPenerimaActivity.class);
        intent.putExtra("jenis",jenis);
        intent.putExtra("idSuin",idSuin);
        context.startActivity(intent);
    }

    @Override
    public void onClick(int position, PenerimaDao dao) {
        if(!dataListPenerimaSelected.contains(dao)){
            dataListPenerimaSelected.add(dao);
            penerimaSelectedAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(context,"Penerima Sudah Ditambah",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(int position) {
        dataListPenerimaSelected.remove(position);
        penerimaSelectedAdapter.notifyDataSetChanged();
    }
}