package id.pdam.simdam.main.suin.main.suin.inbox;

import android.view.View;
import android.widget.Toast;

public class InboxActivityVM {
    public InboxActivity mActivity;

    public InboxActivityVM(InboxActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void onClickSearch(View v){
        Toast.makeText(mActivity,"search",Toast.LENGTH_LONG).show();
    }
}
