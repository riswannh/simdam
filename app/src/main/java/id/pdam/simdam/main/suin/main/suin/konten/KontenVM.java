package id.pdam.simdam.main.suin.main.suin.konten;

import android.view.View;
import android.widget.Toast;

import id.pdam.simdam.main.suin.main.suin.compose.ComposeActivity;

public class KontenVM  {
    public KontenActivity mActivity;
    String idSuin;

    public KontenVM(KontenActivity mActivity,String idSuin) {
        this.mActivity = mActivity;
        this.idSuin = idSuin;
    }

    public void onClickBtnReplie(View v){
            ComposeActivity.startThisActivity(mActivity,1,idSuin);

    }

    public void onClickBtnDelete(View v){
        Toast.makeText(mActivity,"delete",Toast.LENGTH_LONG).show();
    }
}
