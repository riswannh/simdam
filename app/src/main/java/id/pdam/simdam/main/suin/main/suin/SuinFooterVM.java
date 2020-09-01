package id.pdam.simdam.main.suin.main.suin;

import androidx.databinding.ObservableField;

public class SuinFooterVM {
    public ObservableField<String> pesan = new ObservableField<>();
    public ObservableField<Boolean> isVisible = new ObservableField<>(false);

    public SuinFooterVM(String pesan, boolean isVisible) {
        this.pesan.set(pesan);
        this.isVisible.set(isVisible);
    }
}
