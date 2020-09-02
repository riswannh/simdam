package id.pdam.simdam.main.suin.main.suin.konten;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.KontenActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.KontenSuinDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KontenActivity extends AppCompatActivity {
    KontenActivityBinding binding;
    private RecyclerView recyclerView;
    private KontenAdapter adapter;
    private ArrayList<KontenSuinDao> dataList = new ArrayList<>();
    protected LinearLayoutManager linearLayoutManager;
    Context context;
    SuinService mService;
    String idSuin;

    private int totalItem = 0;
    private int min = 0;
    private int max = 10;
    boolean isLoad = false;
    boolean isCallApi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konten_activity);
        idSuin = getIntent().getStringExtra("idSuin");
        mService = ApiClient.getClient().create(SuinService.class);
        config();
        new Task().execute();
    }

    private void config() {
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.konten_activity);
        KontenVM viewModel = new KontenVM(this);
        binding.setVm(viewModel);
        recyclerView = binding.rvKonten;
        adapter = new KontenAdapter(this, dataList);
        linearLayoutManager = new LinearLayoutManager(KontenActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setHasFixedSize(true);
        adapter.setFooterVisible(true);
        adapter.setErrorFooter(0);
    }

    public void callApi(String idSuin, int min, int max) {
        String idUser = "405";
        isCallApi = true;
        isLoad = true;
        Call<BaseDao<List<KontenSuinDao>>> callInbox = mService.getBaca(idSuin, min, max);
        callInbox.enqueue(new Callback<BaseDao<List<KontenSuinDao>>>() {
            @Override
            public void onResponse(Call<BaseDao<List<KontenSuinDao>>> call, Response<BaseDao<List<KontenSuinDao>>> response) {
                Log.d("TAG",response.raw().request().toString());
                adapter.setFooterVisible(false);
                isCallApi = false;
                if (response.body().DATA.isEmpty()) {
                    dataList.clear();
                    adapter.setErrorFooter(1);
                    isLoad = false;
                } else {
                    dataList.addAll(response.body().DATA);
                    totalItem = dataList.size();
                    if (totalItem % 10 == 0)
                        isLoad = true;
                    else
                        isLoad = false;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BaseDao<List<KontenSuinDao>>> call, Throwable t) {

            }
        });
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition() && isLoad && !isCallApi) {
                adapter.setFooterVisible(true);
                max += 10;
                min++;
                new Task().execute();

            }
        }
    };

    public static void startThisActivity(Context context,String idSuin) {
        Intent intent = new Intent(context, KontenActivity.class);
        intent.putExtra("idSuin",idSuin);
        context.startActivity(intent);
    }

    private class Task extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            callApi(idSuin,min,max);
            return null;
        }
    }
}
