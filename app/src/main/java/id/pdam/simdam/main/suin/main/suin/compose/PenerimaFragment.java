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

public class PenerimaFragment extends Fragment {

    private PenerimaVM mViewModel;
    private int idSuin;
    private int typeCompose;


    public static PenerimaFragment newInstance() {
        return new PenerimaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.suin_penerima_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PenerimaVM.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        idSuin = getArguments().getInt("idSuin");
        typeCompose = getArguments().getInt("typeCompose");

    }

    public static PenerimaFragment newInstance(int idSuin,int typeCompose){
        PenerimaFragment fragment = new PenerimaFragment();
        Bundle args = new Bundle();
        args.putInt("idSuin",idSuin);
        args.putInt("typeCompose",typeCompose);
        fragment.setArguments(args);
        return fragment;
    }

}
