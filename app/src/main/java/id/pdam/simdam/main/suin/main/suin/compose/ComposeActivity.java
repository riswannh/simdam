package id.pdam.simdam.main.suin.main.suin.compose;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinComposeActivityBinding;
import id.pdam.simdam.main.suin.api.dao.BaseDao;
import id.pdam.simdam.main.suin.api.param.LampiranParam;
import id.pdam.simdam.main.suin.api.param.SuinBalasParam;
import id.pdam.simdam.main.suin.api.param.SuinParam;
import id.pdam.simdam.main.suin.api.pdamapi.ApiClient;
import id.pdam.simdam.main.suin.api.service.SuinService;
import id.pdam.simdam.main.suin.main.suin.compose.penerima.SearchPenerimaActivity;
import id.pdam.simdam.main.suin.shared.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeActivity extends AppCompatActivity implements ComposeAtcAdapter.AdapterListener {
    int jenis = 0;
    String idSuin;
    SuinComposeActivityBinding binding;
    Context context;
    public static final int REQUEST_CODE_LAMPIRAN = 41;
    private ArrayList<LampiranParam> lampiranParam = new ArrayList<>();
    ComposeAtcAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String idPegawai = "405";
    SuinService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_compose_activity);
        jenis = 0;
        idSuin = "test";
        mService = ApiClient.getClient().create(SuinService.class);
//        jenis = getIntent().getIntExtra("jenis", 0);
//        idSuin = getIntent().getStringExtra("idSuin");
        config();
    }

    private void config() {
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.suin_compose_activity);
        ComposeActivityVM viewModel = new ComposeActivityVM(this, jenis, idSuin);
        binding.setVm(viewModel);

        if (jenis == 0) {
            binding.txJudul.setVisibility(View.VISIBLE);
            binding.etJudul.setVisibility(View.VISIBLE);
        } else {
            binding.txJudul.setVisibility(View.GONE);
            binding.etJudul.setVisibility(View.GONE);
        }
        recyclerView = binding.rvAttachment;
        adapter = new ComposeAtcAdapter(this, lampiranParam, this);
        linearLayoutManager = new LinearLayoutManager(ComposeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        binding.btnAtc.setOnClickListener(clickLampiran);

        binding.btnPenerima.setOnClickListener(onClickPenerima);

    }


    public View.OnClickListener clickLampiran = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takeLampiran();
        }
    };


    @AfterPermissionGranted(REQUEST_CODE_LAMPIRAN)
    void takeLampiran() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, REQUEST_CODE_LAMPIRAN);
        } else {
            EasyPermissions.requestPermissions(this, this.getString(R.string.txt_storage), REQUEST_CODE_LAMPIRAN, perms);
        }

    }

    public View.OnClickListener onClickPenerima = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (jenis == 0) {
//                SuinParam param = new SuinParam();
//                param.idPegawai = idPegawai;
//                param.judul = "test";
//                param.pesan = binding.etPesan.getText().toString();
//                SearchPenerimaActivity.startThisActiviy(context, jenis, 0, "",param,null);
//            } else {
//                SuinBalasParam param = new SuinBalasParam();
//                param.idPegawai = idPegawai;
//                param.pesan = binding.etPesan.getText().toString();
//                SearchPenerimaActivity.startThisActiviy(context,jenis, 1, "",null,param);
//            }


            //todo event ini ketika selesai mendapatkan id konten suin dari megirim suin nya
            for (final LampiranParam param : lampiranParam) {

                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), param.lampiran);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("lampiran", param.lampiran.getName(), requestBody);
                Call<BaseDao> postLampiran = mService.uploadLampiran("220", body);
                postLampiran.enqueue(new Callback<BaseDao>() {
                    @Override
                    public void onResponse(Call<BaseDao> call, Response<BaseDao> response) {
                        Log.d("TAG", response.raw().request().toString());
                    }

                    @Override
                    public void onFailure(Call<BaseDao> call, Throwable t) {
                    }
                });
            }

        }
    };

    public static void startThisActivity(Context context, int jenis, String idSuin) {
        //TODO jenis 0= tulis baru , 1= balas
        Intent intent = new Intent(context, ComposeActivity.class);
        intent.putExtra("jenis", jenis);
        intent.putExtra("idSuin", idSuin);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LAMPIRAN && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                String path = FileUtil.getPath(context, uri);
                File file = new File(path);
                LampiranParam param = new LampiranParam();
                param.namaFile = file.getName();
                param.lampiran = file;
                lampiranParam.add(param);
                adapter.notifyItemInserted(lampiranParam.size());
            }
        }
    }

    @Override
    public void onClick(int position) {
        lampiranParam.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
