package id.pdam.simdam.main.suin.main.suin;

import androidx.databinding.ObservableField;

public class SuinFooterVM {
    public ObservableField<String> pesan = new ObservableField<>();
    public ObservableField<Boolean> isVisible = new ObservableField<>(false);

    public SuinFooterVM(int error, boolean isVisible) {
        if (error == 0){
            this.pesan.set("Loading..");
            this.isVisible.set(isVisible);
        }else if (error == 1){
            this.pesan.set("Suin Kosong");
            this.isVisible.set(isVisible);
        }

    }
}
