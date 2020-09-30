package id.pdam.simdam.main.suin.main.suin.inbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinInboxActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;
import id.pdam.simdam.main.suin.main.suin.konten.KontenActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity implements InboxAdapter.AdapterListener {

    SuinInboxActivityBinding binding;
    private RecyclerView recyclerView;
    private InboxAdapter adapter;
    private ArrayList<SuinInboxDao> dataList = new ArrayList<>();
    protected LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    SuinService mService;

    private int totalItem = 0;
    private int offset = 0;
    private int limit = 10;
    boolean isRefresh = false;
    boolean isLoad = false;
    boolean isCallApi = false;
    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_inbox_activity);
        config();
        mService = ApiClient.getClient().create(SuinService.class);

        new Task().execute();
    }

    private void config() {
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.suin_inbox_activity);
        InboxActivityVM viewModel = new InboxActivityVM(this);
        binding.setVm(viewModel);
        recyclerView = binding.rvInbox;
        adapter = new InboxAdapter(this, dataList, this);
        linearLayoutManager = new LinearLayoutManager(InboxActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        adapter.setFooterVisible(true);
        adapter.setErrorFooter(0);
        recyclerView.setHasFixedSize(true);
        binding.btnSearch.setOnClickListener(onClickSearch);

        swipeRefreshLayout = binding.srInbox;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                keyword = "";
                new Task().execute();
            }
        });
    }

    private View.OnClickListener onClickSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            keyword = binding.etSearch.getText().toString();
            dataList.clear();
            new Task().execute();
        }
    };

    public void callApi(int offset, int limit,String keyword) {
        String idUser = "405";
        isCallApi = true;
        isLoad = true;
        Call<BaseDao<List<SuinInboxDao>>> callInbox = mService.getInbox(idUser, offset, limit,keyword);
        callInbox.enqueue(new Callback<BaseDao<List<SuinInboxDao>>>() {
            @Override
            public void onResponse(Call<BaseDao<List<SuinInboxDao>>> call, Response<BaseDao<List<SuinInboxDao>>> response) {
                Log.d("TAG",response.raw().request().toString());
                adapter.setFooterVisible(false);
                isCallApi = false;
                if (isRefresh) {
                    stopRefreshing();
                    dataList.clear();
                    totalItem = 0;
                    isRefresh = false;
                }
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
            public void onFailure(Call<BaseDao<List<SuinInboxDao>>> call, Throwable t) {

            }
        });
    }

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, InboxActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(int position, String idSuin) {
        KontenActivity.startThisActivity(this,idSuin);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition() && !isRefresh && isLoad && !isCallApi) {
                adapter.setFooterVisible(true);
                limit += 10;
                offset++;
                new Task().execute();

            }
        }
    };

    public void stopRefreshing() {
        binding.srInbox.postDelayed(new Runnable() {

            @Override
            public void run() {
                binding.srInbox.setRefreshing(false);
            }
        }, 100);
    }

    private class Task extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            callApi(offset,limit,keyword);
            return null;
        }
    }
}