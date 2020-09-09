package id.pdam.simdam.main.suin.main.suin.compose;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import id.pdam.simdam.R;
import id.pdam.simdam.databinding.SuinComposeActivityBinding;
import id.pdam.simdam.main.suin.api.param.LampiranParam;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ComposeActivity extends AppCompatActivity implements ComposeAtcAdapter.AdapterListener {
    int jenis;
    String idSuin;
    SuinComposeActivityBinding binding;
    Context context;
    public static final int REQUEST_CODE = 41;
    private ArrayList<LampiranParam> lampiranParam = new ArrayList<>();
    ComposeAtcAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suin_compose_activity);
        jenis = getIntent().getIntExtra("jenis", 0);
        idSuin = getIntent().getStringExtra("idSuin");
        config();
    }

    private void config() {
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.suin_compose_activity);
        ComposeActivityVM viewModel = new ComposeActivityVM(this);
        binding.setVm(viewModel);

        recyclerView = binding.rvAttachment;
        adapter = new ComposeAtcAdapter(this, lampiranParam, this);
        linearLayoutManager = new LinearLayoutManager(ComposeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        binding.btnAtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

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
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                File file = new File(uri.getPath());
                LampiranParam param = new LampiranParam();
                param.namaFile = file.getName().replace("primary:","");
                param.file = file;
                lampiranParam.add(param);
                adapter.notifyItemInserted(lampiranParam.size());
//                RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)),file);
//                Log.d("TAG",file.getName());
            }
        }
    }

    @Override
    public void onClick(int position) {
        lampiranParam.remove(position);
        adapter.notifyDataSetChanged();
    }
}
