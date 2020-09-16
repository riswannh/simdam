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
    private Dialog filterDialog;
    private Context context;

    public ArrayList<FilterItemDao> filterItemDao = new ArrayList<>();
    SuinFilterPenerimaDialogBinding dialogBinding;
    private RecyclerView rvJenisPenerima;
    SuinService mService;
    FilterDialogAdapter dialogAdapter;
    protected LinearLayoutManager linearLayoutManager;

    SuinPenerimaFilterDialog dialog;
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

        dialogConfig();

    }

    public void dialogConfig() {
//
//        dialogBinding.spinnerJenisKpd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        dialogBinding.etSearch.setVisibility(View.GONE);
//                        dialogBinding.btnSearch.setVisibility(View.GONE);
//                        jenis = 0;
//                        callFilterApi("", 0, 0, 0);
//                        break;
//                    case 1:
//                        dialogBinding.etSearch.setVisibility(View.VISIBLE);
//                        dialogBinding.btnSearch.setVisibility(View.VISIBLE);
//                        jenis = 1;
//                        callFilterApi("", 0, 10, 1);
//                        break;
//                    case 2:
//                        dialogBinding.etSearch.setVisibility(View.VISIBLE);
//                        dialogBinding.btnSearch.setVisibility(View.VISIBLE);
//                        jenis = 2;
//                        callFilterApi("", 0, 10, 2);
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = SuinPenerimaFilterDialog.newInstance(0);
                dialog.show(fm,"TEST");
            }
        });
    }

    public void filterDialogConfig() {
        //todo jenis: 0=jabatan,1 = departemen,2=divisi
        rvJenisPenerima = dialogBinding.rvListJenis;
        dialogAdapter = new FilterDialogAdapter(this, filterItemDao, this);
        linearLayoutManager = new LinearLayoutManager(SearchPenerimaActivity.this, LinearLayoutManager.VERTICAL, false);
        rvJenisPenerima.setLayoutManager(linearLayoutManager);
        rvJenisPenerima.setAdapter(dialogAdapter);
        rvJenisPenerima.addOnScrollListener(scrolFilterItemListener);
        dialogAdapter.setFooterVisible(true);
        dialogAdapter.setErrorFooter(0);
        rvJenisPenerima.setHasFixedSize(true);
    }

    private RecyclerView.OnScrollListener scrolFilterItemListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition() && !isRefresh && isLoad && !isCallApi) {
                dialogAdapter.setFooterVisible(true);
                limit += 10;
                offset++;
                callFilterApi(keyword, offset, limit, jenis);
            }
        }
    };

    @Override
    public void onClick(int position, FilterItemDao itemDao) {

    }

//    public ArrayList<FilterItemDao> filterItemJabatan() {
//        ArrayList<FilterItemDao> listItem = new ArrayList<>();
//        for (int i = 1; i <= 6; i++) {
//            FilterItemDao item = new FilterItemDao();
//            item.ID = String.valueOf(i);
//            if (i == 1) {
//                item.NAMA = "Direksi";
//            } else if (i == 2) {
//                item.NAMA = "Senior Manager";
//            } else if (i == 3) {
//                item.NAMA = "Manager";
//            } else if (i == 4) {
//                item.NAMA = "Supervisor";
//            } else if (i == 5) {
//                item.NAMA = "Staf";
//            } else if (i == 6) {
//                item.NAMA = "Semua";
//            }
//            listItem.add(item);
//        }
//
//        return listItem;
//    }

    public void callFilterApi(String keyword, int offset, int limit, int jenis) {
        isCallApi = true;
        isLoad = true;
        if (jenis == 0) {
//            filterItemDao.addAll(filterItemJabatan());
            totalItem = filterItemDao.size();
            if (totalItem % 10 == 0)
                isLoad = true;
            else
                isLoad = false;
            dialogAdapter.notifyDataSetChanged();
        } else {
            Call<BaseDao<List<FilterItemDao>>> callApi = mService.getJenisPenerima(offset, limit, keyword, jenis);
            callApi.enqueue(new Callback<BaseDao<List<FilterItemDao>>>() {
                @Override
                public void onResponse(Call<BaseDao<List<FilterItemDao>>> call, Response<BaseDao<List<FilterItemDao>>> response) {
                    if (response.body().DATA.isEmpty()) {
                        filterItemDao.clear();
                        dialogAdapter.setErrorFooter(1);
                        isLoad = false;
                    } else {
                        filterItemDao.addAll(response.body().DATA);
                        totalItem = filterItemDao.size();
                        if (totalItem % 10 == 0)
                            isLoad = true;
                        else
                            isLoad = false;
                        dialogAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BaseDao<List<FilterItemDao>>> call, Throwable t) {

                }
            });
        }
    }
}