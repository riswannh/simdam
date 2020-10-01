package id.pdam.simdam.main.suin.main.menu;

import android.view.View;

import id.pdam.simdam.main.suin.main.suin.compose.ComposeActivity;
import id.pdam.simdam.main.suin.main.suin.inbox.InboxActivity;

public class MenuVM {
    MenuSuinActivity mActitvity;

    public MenuVM(MenuSuinActivity mActitvity) {
        this.mActitvity = mActitvity;
    }

    public void onClickSuratInternal(View v){
        ComposeActivity.startThisActivity(mActitvity,0,null);
    }

    public void onClickKotakSurat(View v){
        InboxActivity.startThisActivity(mActitvity);
    }
}
