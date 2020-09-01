package id.pdam.simdam.main.suin.main.suin.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.pdam.simdam.BR;
import id.pdam.simdam.databinding.SuinFooterBinding;
import id.pdam.simdam.databinding.SuinItemBinding;
import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;
import id.pdam.simdam.main.suin.main.suin.SuinFooterVM;
import id.pdam.simdam.main.suin.main.suin.SuinListViewVM;

public class InboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SuinInboxDao> dataList;
    private Context context;
    private AdapterListener listener;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private boolean isFooterVisible;

    public InboxAdapter(Context context,ArrayList<SuinInboxDao> dataList,AdapterListener listener){
    this.listener = listener;
    this.dataList = dataList;
    this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            SuinItemBinding binding = SuinItemBinding.inflate(layoutInflater, parent, false);
            return new ItemHolder(binding);
        } else if (viewType == TYPE_FOOTER) {
            SuinFooterBinding binding = SuinFooterBinding.inflate(layoutInflater, parent, false);
            return new FooterHolder(binding);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            final SuinInboxDao inbox = dataList.get(position);
            SuinListViewVM viewVM = new SuinListViewVM(context, inbox);
            ((ItemHolder) holder).getBinding().setVariable(BR.vm, viewVM);
            ((ItemHolder) holder).getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position, inbox);
                }
            });
        }else if (holder instanceof FooterHolder){
            SuinFooterVM viewVM = new SuinFooterVM("loading",isFooterVisible);
            ((FooterHolder)holder).getBinding().setVariable(BR.vm, viewVM);
        }
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

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private SuinItemBinding binding;

        public ItemHolder(SuinItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public SuinItemBinding getBinding() {
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

    public interface AdapterListener {
        void onClick(int position, SuinInboxDao item);
    }
}
