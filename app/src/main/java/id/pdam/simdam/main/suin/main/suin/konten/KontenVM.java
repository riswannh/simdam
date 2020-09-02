package id.pdam.simdam.main.suin.main.suin.konten;

import android.view.View;
import android.widget.Toast;

public class KontenVM  {
    public KontenActivity mActivity;

    public KontenVM(KontenActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void onClickBtnReplie(View v){
        Toast.makeText(mActivity,"balas",Toast.LENGTH_LONG).show();
    }

    public void onClickBtnDelete(View v){
        Toast.makeText(mActivity,"delete",Toast.LENGTH_LONG).show();
    }
}
