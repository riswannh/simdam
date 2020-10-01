package id.pdam.simdam.main.suin.main.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinMenuActivityBinding;
import id.pdam.simdam.main.suin.main.login.LoginActivity;
import id.pdam.simdam.main.suin.main.suin.compose.ComposeActivity;
import id.pdam.simdam.main.suin.shared.Constant;
import id.pdam.simdam.main.suin.shared.PrefHelper;

public class MenuSuinActivity extends AppCompatActivity {
    SuinMenuActivityBinding binding;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_menu_activity);
        config();
    }

    private void config() {
        if (PrefHelper.getPref(this, Constant.PREF.USER_ID) == null) {
            LoginActivity.startThisActivity(this);
            finish();
        }
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.suin_menu_activity);
        MenuVM viewModel = new MenuVM(this);
        binding.setVm(viewModel);
    }

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, MenuSuinActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
