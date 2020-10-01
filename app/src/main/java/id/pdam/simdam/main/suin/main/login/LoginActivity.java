package id.pdam.simdam.main.suin.main.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.LoginActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.dao.LoginDao;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.LoginService;
import id.pdam.simdam.main.suin.main.menu.MenuSuinActivity;
import id.pdam.simdam.main.suin.shared.Constant;
import id.pdam.simdam.main.suin.shared.PrefHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Context context;
    LoginActivityBinding binding;
    LoginService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        config();
    }

    private void config() {
        context = this;
        mService = ApiClient.getClient().create(LoginService.class);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        binding.btnLogin.setOnClickListener(onClickLogin);
    }

    public View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call<BaseDao<LoginDao>> callLogin = mService.login(binding.username.getText().toString(), binding.password.getText().toString());
            callLogin.enqueue(new Callback<BaseDao<LoginDao>>() {
                @Override
                public void onResponse(Call<BaseDao<LoginDao>> call, Response<BaseDao<LoginDao>> response) {
                    if (response.body().STATUS == false) {
                        Toast.makeText(context, "Username Atau Password Salah", Toast.LENGTH_LONG).show();
                    } else {
                        PrefHelper.savePref(context, Constant.PREF.USER_ID,response.body().DATA.ID);
                        MenuSuinActivity.startThisActivity(context);
                    }
                }

                @Override
                public void onFailure(Call<BaseDao<LoginDao>> call, Throwable t) {
                }
            });
        }
    };

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
