package id.pdam.simdam.main.suin.main.suin;

import android.content.Context;

import androidx.databinding.ObservableField;

import id.pdam.simdam.main.suin.api.dao.SuinInboxDao;

public class SuinListViewVM {
    public ObservableField<String> pengirim = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> judul = new ObservableField<>();

    public SuinListViewVM(Context context, SuinInboxDao dataDao) {
        this.pengirim.set(dataDao.PENGIRIM);
        this.time.set(dataDao.BALASAN_TERAKHIR);
        this.judul.set(dataDao.JUDUL);
    }
}
