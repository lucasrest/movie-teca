package rest.com.br.movieteca.view.fragment.filmes_pendentes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.adapter.FilmeAdapter;
import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.data.listiner.FilmeListener;
import rest.com.br.movieteca.util.FragmentEnum;
import rest.com.br.movieteca.view.fragment.shared.DaggerFilmeComponent;
import rest.com.br.movieteca.view.fragment.shared.FilmeModule;
import rest.com.br.movieteca.view.fragment.shared.FilmesContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmesPendentesFragment extends Fragment implements FilmesContract.View{

    @Inject FilmesContract.Presenter presenter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progress_wheel_center)ProgressWheel progressWheel;
    private FilmeAdapter filmeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmes_pendentes, container, false);

        ButterKnife.bind( this, view);

        DaggerFilmeComponent
                .builder()
                .netComponent(App.getComponent() )
                .filmeModule( new FilmeModule(this, FragmentEnum.PENDENTES))
                .build()
                .inject( this );

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );

        presenter.findAll();

        return view;
    }

    @Override
    public void showLoad() {
        progressWheel.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideLoad() {
        progressWheel.setVisibility( View.GONE );
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        Log.i("FilmesPendentes", message);
    }

    @Override
    public void loadList(List<Filme> filmes) {
        filmeAdapter = new FilmeAdapter(getActivity(), filmes, FragmentEnum.PENDENTES );
        recyclerView.setAdapter( filmeAdapter );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register( FilmesPendentesFragment.this );
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister( FilmesPendentesFragment.this );
    }

    @Subscribe
    public void onEvent(FilmeListener filmeListener){
        if(filmeListener.getFilme().isAssistirDepois()){
            presenter.findAll();
        }
    }
}
