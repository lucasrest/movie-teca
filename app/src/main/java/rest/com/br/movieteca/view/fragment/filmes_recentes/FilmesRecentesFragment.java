package rest.com.br.movieteca.view.fragment.filmes_recentes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.adapter.FilmeAdapter;
import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.data.listiner.FilmeRemovidoListener;
import rest.com.br.movieteca.util.FragmentEnum;
import rest.com.br.movieteca.view.fragment.filmes_pendentes.FilmesPendentesFragment;
import rest.com.br.movieteca.view.fragment.shared.FilmeModule;

public class FilmesRecentesFragment extends Fragment implements FilmesRecentesContract.View {

    @Inject
    FilmesRecentesContract.Presenter presenter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    //@BindView(R.id.progress_wheel) ProgressWheel progressWheelBottom;
    @BindView(R.id.progress_wheel_center) ProgressWheel progressWheelCenter;
    @BindView(R.id.progress_wheel) ProgressWheel progressWheel;
    private FilmeAdapter filmeAdapter;
    private int page = 1;
    private int totalPage = 0;
    private String query = "";

    private final String TAG = "FilmesRecentesFragment";

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmes_recentes, container, false);

        ButterKnife.bind( this, view );

        App.filmesRecentes = new ArrayList<>();


        DaggerFilmesRecentesComponent
                .builder()
                .netComponent(App.getComponent())
                .filmesRecentesModule( new FilmesRecentesModule( this ))
                .build()
                .inject( this );

        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );

        presenter.findAll( page );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( dy > 0 ){
                    if ( !recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) ) {
                        if( query.isEmpty() )
                            presenter.findAll(page);
                        else{
                            if (page <= totalPage)
                                presenter.findByQuery(page, query);
                        }
                    }
                }
            }
        });

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
    }

    @Override
    public void loadList(List<Filme> filmes) {

    }

    @Override
    public void loadList(List<Filme> filmes, int posicaoFinal, int nextPage, int totalPages) {
        filmeAdapter = new FilmeAdapter(getActivity(), filmes, FragmentEnum.RECENTES );
        recyclerView.scrollToPosition(posicaoFinal);
        recyclerView.setAdapter( filmeAdapter );
        this.page = nextPage;
        this.totalPage = totalPages;
    }

    @Override
    public void notifyItemRangeRemoved(int position, int size) {
        filmeAdapter.notifyItemRangeRemoved(position, size);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView( menu.findItem( R.id.search ));
        final MenuItem serachMenuItem = menu.findItem( R.id.search );

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    serachMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                Log.i(TAG, "onSubmit: " + q);
                page = 1;
                query = q;
                if(!q.isEmpty()){
                    presenter.limparLista();
                    presenter.findByQuery( page, q );
                }else {
                    presenter.findAll( page);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!query.isEmpty() && newText.isEmpty()){
                    query = "";
                    page = 1;
                    presenter.limparLista();
                    presenter.findAll( page );
                }
                return false;
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register( FilmesRecentesFragment.this );
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister( FilmesRecentesFragment.this );
    }

    @Subscribe
    public void onEvent(FilmeRemovidoListener filmeRemovidoListener){
        filmeAdapter.notifyDataSetChanged();
    }
}
