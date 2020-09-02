package id.pdam.simdam.main.suin.main.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinMenuActivityBinding;

public class MenuSuinActivity extends AppCompatActivity {
SuinMenuActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_menu_activity);
        config();
    }

    private void config() {
        binding = DataBindingUtil.setContentView(this, R.layout.suin_menu_activity);
        MenuVM viewModel = new MenuVM(this);
        binding.setVm(viewModel);
    }
}
