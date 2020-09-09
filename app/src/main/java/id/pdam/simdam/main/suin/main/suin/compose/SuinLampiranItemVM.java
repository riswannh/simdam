package id.pdam.simdam.main.suin.main.suin.compose;

import android.content.Context;

import androidx.databinding.ObservableField;

public class SuinLampiranItemVM {
    public ObservableField<String> txtFile = new ObservableField<>();
    Context context;

    public SuinLampiranItemVM(Context context, String txtFile) {
        this.txtFile.set(txtFile);
        this.context = context;
    }
}
