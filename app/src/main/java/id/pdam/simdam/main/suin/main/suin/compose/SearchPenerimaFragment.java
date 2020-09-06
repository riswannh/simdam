package id.pdam.simdam.main.suin.main.suin.compose;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.pdam.simdam.R;

public class SearchPenerimaFragment extends Fragment {

    private SearchPenerimaVM mViewModel;

    public static SearchPenerimaFragment newInstance() {
        return new SearchPenerimaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.suin_search_penerima_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchPenerimaVM.class);
        // TODO: Use the ViewModel
    }

}