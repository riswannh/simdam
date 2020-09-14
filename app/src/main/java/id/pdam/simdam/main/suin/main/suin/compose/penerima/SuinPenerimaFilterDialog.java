package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.pdam.simdam.databinding.SuinFilterItemBinding;
import id.pdam.simdam.databinding.SuinFilterPenerimaDialogBinding;
import id.pdam.simdam.main.suin.api.dao.FilterItemDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;

public class SuinPenerimaFilterDialog extends DialogFragment implements FilterDialogAdapter.AdapterFilterItemListener {
    SuinFilterPenerimaDialogBinding binding;
    SuinService mService;
    public String[] item = {"Jabatan", "Departemen", "Divisi"};
    RecyclerView recyclerView;
    FilterDialogAdapter dialogAdapter;
    protected LinearLayoutManager linearLayoutManager;
    public ArrayList<FilterItemDao> filterItemDao = new ArrayList<>();

    private int totalItem = 0;
    private int offset = 0;
    private int limit = 10;
    boolean isRefresh = false;
    boolean isLoad = false;
    boolean isCallApi = false;
    int jenis = 0;
    String keyword;

    public SuinPenerimaFilterDialog(){}


    public static SuinPenerimaFilterDialog newInstance(int jenis) {
        SuinPenerimaFilterDialog frag = new SuinPenerimaFilterDialog();
        Bundle args = new Bundle();
        args.putInt("jenis",jenis);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SuinFilterPenerimaDialogBinding.inflate(inflater,container,false);
        mService = ApiClient.getClient().create(SuinService.class);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,item);
        binding.spinnerJenisKpd.setAdapter(adapter);
        binding.spinnerJenisKpd.setOnItemSelectedListener(onSelectListener);
        configRv();
        return binding.getRoot();
    }

    private void configRv() {
        recyclerView = binding.rvListJenis;
        dialogAdapter = new FilterDialogAdapter(getActivity(), filterItemDao, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(dialogAdapter);
        recyclerView.addOnScrollListener(scrolFilterItemListener);
        dialogAdapter.setFooterVisible(true);
        dialogAdapter.setErrorFooter(0);
    }


    @Override
    public void onClick(int position, FilterItemDao itemDao) {

    }

    private RecyclerView.OnScrollListener scrolFilterItemListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (totalItem == linearLayoutManager.findLastCompletelyVisibleItemPosition() && !isRefresh && isLoad && !isCallApi) {
                dialogAdapter.setFooterVisible(true);
                limit += 10;
                offset++;
//                callFilterApi(keyword, offset, limit, jenis);
            }
        }
    };

    private AdapterView.OnItemSelectedListener onSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            binding.layoutSearch.setVisibility(View.GONE);
            filterItemJabatan();
        }else if (position == 1){
            binding.layoutSearch.setVisibility(View.VISIBLE);
        }else if (position == 2){
            binding.layoutSearch.setVisibility(View.VISIBLE);
        }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void filterItemJabatan() {
        ArrayList<FilterItemDao> listItem = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            FilterItemDao item = new FilterItemDao();
            item.ID = String.valueOf(i);
            if (i == 1) {
                item.NAMA = "Direksi";
            } else if (i == 2) {
                item.NAMA = "Senior Manager";
            } else if (i == 3) {
                item.NAMA = "Manager";
            } else if (i == 4) {
                item.NAMA = "Supervisor";
            } else if (i == 5) {
                item.NAMA = "Staf";
            } else if (i == 6) {
                item.NAMA = "Semua";
            }
            listItem.add(item);
        }
        filterItemDao.clear();
        filterItemDao.addAll(listItem);
        dialogAdapter.notifyDataSetChanged();
        dialogAdapter.setFooterVisible(false);
    }
}
