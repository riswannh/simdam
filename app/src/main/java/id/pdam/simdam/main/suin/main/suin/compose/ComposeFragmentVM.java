package id.pdam.simdam.main.suin.main.suin.compose;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

public class ComposeFragmentVM extends ViewModel {
    Context mContext;

    public ComposeFragmentVM(Context mContext) {
        this.mContext = mContext;
    }

    // TODO: Implement the ViewModel
    public void onClickKirim(View v){
        ComposeActivity.startThisActivity(mContext,0,1);
    }
}
