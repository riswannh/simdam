package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinFilterPenerimaDialogBinding;
import id.pdam.simdam.databinding.SuinSearchPenerimaActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.FilterItemDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPenerimaActivity extends AppCompatActivity implements FilterDialogAdapter.AdapterFilterItemListener {
    SuinSearchPenerimaActivityBinding binding;
    private Context context;

    private RecyclerView recyclerView;
    SuinService mService;
    protected LinearLayoutManager linearLayoutManager;

    FragmentManager fm = getSupportFragmentManager();
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


    }



    private RecyclerView.OnScrollListener scrolFilterItemListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition() && !isRefresh && isLoad && !isCallApi) {
                limit += 10;
                offset++;
                callFilterApi(keyword, offset, limit, jenis);
            }
        }
    };

    @Override
    public void onClick(int position, FilterItemDao itemDao) {

    }


    public void callFilterApi(String keyword, int offset, int limit, int jenis) {
        isCallApi = true;
        isLoad = true;
    }
}