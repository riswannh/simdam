package id.pdam.simdam.main.suin.main.suin.compose.penerima;

import android.content.Context;

import androidx.databinding.ObservableField;

public class SuinPenerimaItemVM {
    Context context;
    public ObservableField<String> nama = new ObservableField<>();

    public SuinPenerimaItemVM(Context context,String nama) {
        this.context = context;
        this.nama.set(nama);
    }
}
