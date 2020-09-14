package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.pdam.simdam.BR;
import id.pdam.simdam.databinding.SuinFilterItemBinding;
import id.pdam.simdam.databinding.SuinFooterBinding;
import id.pdam.simdam.main.suin.api.dao.FilterItemDao;
import id.pdam.simdam.main.suin.main.suin.SuinFooterVM;
import id.pdam.simdam.main.suin.main.suin.inbox.InboxAdapter;

public class FilterDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<FilterItemDao> dataList;
    private Context context;
    private AdapterFilterItemListener listener;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private boolean isFooterVisible;
    private int errorFooter;

    public FilterDialogAdapter(Context context, ArrayList<FilterItemDao> dataList, AdapterFilterItemListener listener){
        this.listener = listener;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void setFooterVisible(boolean footerVisible){
        if (isFooterVisible){
            isFooterVisible = footerVisible;
            notifyItemChanged(dataList.size() +1);
        }else {
            isFooterVisible = footerVisible;
            notifyItemChanged(dataList.size());
        }
    }

    public void setErrorFooter(int error) {
        errorFooter = error;
        notifyItemChanged(dataList.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            SuinFilterItemBinding binding = SuinFilterItemBinding.inflate(layoutInflater, parent, false);
            return new ItemHolder(binding);
        } else if (viewType == TYPE_FOOTER) {
            SuinFooterBinding binding = SuinFooterBinding.inflate(layoutInflater, parent, false);
            return new FooterHolder(binding);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            final FilterItemDao inbox = dataList.get(position);
            FilterDialogItemVM viewVM = new FilterDialogItemVM(context, dataList.get(position).NAMA);
            ((ItemHolder) holder).getBinding().setVariable(BR.vm, viewVM);
            ((ItemHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position,inbox);
                }
            });
        }else if (holder instanceof FooterHolder){
            SuinFooterVM viewVM = new SuinFooterVM(errorFooter,isFooterVisible);
            ((FooterHolder)holder).getBinding().setVariable(BR.vm, viewVM);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        SuinFilterItemBinding binding;

        public ItemHolder(SuinFilterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public SuinFilterItemBinding getBinding() {
            return binding;
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        private SuinFooterBinding binding;

        public FooterHolder(SuinFooterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public SuinFooterBinding getBinding() {
            return binding;
        }
    }

    public interface AdapterFilterItemListener {
        void onClick(int position, FilterItemDao itemDao);
    }
}
