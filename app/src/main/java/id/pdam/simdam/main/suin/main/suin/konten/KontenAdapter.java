package id.pdam.simdam.main.suin.main.suin.konten;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.pdam.simdam.BR;
import id.pdam.simdam.databinding.ItemMassageReceivedBinding;
import id.pdam.simdam.databinding.ItemMassageSentBinding;
import id.pdam.simdam.databinding.SuinFooterBinding;
import id.pdam.simdam.databinding.SuinItemBinding;
import id.pdam.simdam.main.suin.api.dao.KontenSuinDao;
import id.pdam.simdam.main.suin.main.suin.SuinFooterVM;
import id.pdam.simdam.main.suin.main.suin.inbox.InboxAdapter;

public class KontenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<KontenSuinDao> dataList;
    private Context context;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM_SENT = 2;
    private static final int TYPE_ITEM_RECEIVED = 3;
    private boolean isFooterVisible;
    private int errorFooter;

    public KontenAdapter(Context context,ArrayList<KontenSuinDao> dataList){
    this.dataList = dataList;
    this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        String idUser = "405";
        if (position == dataList.size()) {
            return TYPE_FOOTER;
        }
        if (dataList.get(position).ID_PEG_PENGIRIM.equals(idUser)){
            return TYPE_ITEM_SENT;
        }else {
            return  TYPE_ITEM_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM_SENT) {
            ItemMassageSentBinding binding = ItemMassageSentBinding.inflate(layoutInflater, parent, false);
            return new SentItemHolder(binding);
        } else if (viewType == TYPE_ITEM_RECEIVED) {
            ItemMassageReceivedBinding binding = ItemMassageReceivedBinding.inflate(layoutInflater, parent, false);
            return new ReceivedItemHolder(binding);
        }else if (viewType == TYPE_FOOTER) {
            SuinFooterBinding binding = SuinFooterBinding.inflate(layoutInflater, parent, false);
            return new FooterHolder(binding);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SentItemHolder) {
            String bodyData = dataList.get(position).ISI_SURAT.replaceAll("\\n","");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                ((SentItemHolder) holder).getBinding().txtBody.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY));
            } else {
                ((SentItemHolder) holder).getBinding().txtBody.setText(Html.fromHtml(bodyData));
            }
            ((SentItemHolder) holder).getBinding().txtTime.setText(dataList.get(position).TGL_SURAT);
        }else if (holder instanceof ReceivedItemHolder){
            String bodyData = dataList.get(position).ISI_SURAT.replaceAll("\\n","");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                ((ReceivedItemHolder) holder).getBinding().txtBody.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY));
            } else {
                ((ReceivedItemHolder) holder).getBinding().txtBody.setText(Html.fromHtml(bodyData));
            }
            ((ReceivedItemHolder) holder).getBinding().txtTime.setText(dataList.get(position).TGL_SURAT);
            ((ReceivedItemHolder) holder).getBinding().txtName.setText(dataList.get(position).PENGIRIM);
        }else if (holder instanceof FooterHolder){
            SuinFooterVM viewVM = new SuinFooterVM(errorFooter,isFooterVisible);
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

    public void setErrorFooter(int error) {
        errorFooter = error;
        notifyItemChanged(dataList.size());
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    public class SentItemHolder extends RecyclerView.ViewHolder {
        private ItemMassageSentBinding binding;

        public SentItemHolder(ItemMassageSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemMassageSentBinding getBinding() {
            return binding;
        }
    }

    public class ReceivedItemHolder extends RecyclerView.ViewHolder {
        private ItemMassageReceivedBinding binding;

        public ReceivedItemHolder(ItemMassageReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemMassageReceivedBinding getBinding() {
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

}
