package id.pdam.simdam.main.suin.main.suin.compose;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import id.pdam.simdam.main.suin.main.suin.compose.penerima.SearchPenerimaActivity;

public class ComposeActivityVM extends ViewModel {
    Context mContext;
    int jenis;
    String idSuin;
    private static final int REQUEST_CODE= 21;

    public ComposeActivityVM(Context mContext,int jenis,String idSuin) {
        this.mContext = mContext;
    }

    // TODO: Implement the ViewModel
    public void onClickKirim(View v){
        SearchPenerimaActivity.startThisActiviy(mContext,jenis,idSuin);
    }



}
