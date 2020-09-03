package id.pdam.simdam.main.suin.main.suin.compose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.Array;

import id.pdam.simdam.R;

public class ComposeActivity extends AppCompatActivity {
    int idSuin;
    int mFragment;
    int typeCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_compose_activity);

        getIntent().getIntExtra("idSuin",this.idSuin);
        getIntent().getIntExtra("mfragment",this.mFragment);
        if (idSuin == 0){
            typeCompose = 0;
        }else {
            typeCompose = 1;
        }

        changeForm(mFragment);
    }

    private void changeForm(int mFragment) {
        if (mFragment == 0){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ComposeFragment composeFragment = ComposeFragment.newInstance(idSuin,typeCompose);
            ft.replace(R.id.flCompose,composeFragment);
            ft.addToBackStack(null);
            ft.commit();
        }else if (mFragment == 1){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flCompose,new PenerimaFragment());
            ft.addToBackStack(null);
            ft.commit();
        }else if(mFragment == 2){

        }
    }

    public static void startThisActivity(Context context, int idSuin,int mFragment){
        Intent intent = new Intent(context,ComposeActivity.class);
        intent.putExtra("idSuin",idSuin);
        intent.putExtra("mFragment",mFragment);
        context.startActivity(intent);
    }
}
