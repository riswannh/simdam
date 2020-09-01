package id.pdam.simdam.main.suin.main.suin.inbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinInboxActivityBinding;
import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;

public class InboxActivity extends AppCompatActivity implements InboxAdapter.AdapterListener {

    SuinInboxActivityBinding binding;
    private RecyclerView recyclerView;
    private InboxAdapter adapter;
    private ArrayList<SuinInboxDao> dataList = new ArrayList<>();
    protected LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    private int totalItem = 0;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_inbox_activity);
        config();
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
        adapter.notifyDataSetChanged();
        adapter.setFooterVisible(true);

        swipeRefreshLayout = binding.srInbox;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(context, "Refresh", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, InboxActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(int position, SuinInboxDao item) {

    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition()) {
                adapter.setFooterVisible(true);
                adapter.notifyDataSetChanged();
            }
        }
    };
}