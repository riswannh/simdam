package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.pdam.simdam.BR;
import id.pdam.simdam.databinding.SuinLampiranItemBinding;
import id.pdam.simdam.main.suin.api.dao.PenerimaDao;
import id.pdam.simdam.main.suin.main.suin.compose.SuinLampiranItemVM;

public class SuinPenerimaSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<PenerimaDao> dataList;
    Context context;
    AdapterListener listener;

    public SuinPenerimaSelectedAdapter(Context context,ArrayList<PenerimaDao> dataList,AdapterListener listener){
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SuinLampiranItemBinding binding = SuinLampiranItemBinding.inflate(layoutInflater, parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        SuinLampiranItemVM viewVM = new SuinLampiranItemVM(context, dataList.get(position).NAMA);
        ((ItemHolder) holder).getBinding().setVariable(BR.vm, viewVM);
        ((ItemHolder) holder).getBinding().btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        SuinLampiranItemBinding binding;

        public ItemHolder(SuinLampiranItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public SuinLampiranItemBinding getBinding() {
            return binding;
        }
    }

    public interface AdapterListener {
        void onClick(int position);
    }
}
