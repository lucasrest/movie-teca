package rest.com.br.movieteca.view.fragment.filmes_recentes;

import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import rest.com.br.movieteca.adapter.FilmeAdapter;
import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.data.domain.Movie;
import rest.com.br.movieteca.data.domain.Result;
import rest.com.br.movieteca.service.FilmeService;
import rest.com.br.movieteca.util.FragmentEnum;
import rest.com.br.movieteca.view.fragment.shared.FilmesContract;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rest.com.br.movieteca.R.id.recyclerView;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public class FilmesRecentesPresenter implements FilmesRecentesContract.Presenter {

    Retrofit retrofit;
    FilmesRecentesContract.View view;
    FilmeDAO filmeDAO;
    FilmeService filmeService;
    int totalPages = 0;

    @Inject
    public FilmesRecentesPresenter(Retrofit retrofit, FilmesRecentesContract.View view, FilmeDAO filmeDAO){
        this.retrofit = retrofit;
        this.view = view;
        this.filmeDAO = filmeDAO;
    }

    @Override
    public void findByQuery(final int page, String query) {
        makeRetrofit(Constants.API_MOVIEDB_BASE_SEARCH_URL);
        final int posicaoFinal = App.filmesRecentes.size() - 2;
        retrofit.create( FilmeService.class )
                .search(page, query)
                .subscribeOn( Schedulers.newThread() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoad();
                        int nextPage = page + 1;
                        view.loadList( App.filmesRecentes, posicaoFinal, nextPage, totalPages);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoad();
                        view.showError( e.getMessage() );
                    }

                    @Override
                    public void onNext(Movie movie) {
                        for (Result result: movie.getResults()){
                            addFilme( result );
                        }
                        totalPages = movie.getTotal_pages();
                    }
                });
    }

    @Override
    public void findAll(final int page) {
        makeRetrofit(Constants.API_MOVIEDB_BASE_URL);
        view.showLoad();
        final int posicaoFinal = App.filmesRecentes.size() - 2;
        retrofit.create( FilmeService.class )
                .findAll( page )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoad();
                        int nextPage = page + 1;
                        view.loadList( App.filmesRecentes, posicaoFinal, nextPage, totalPages);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoad();
                        view.showError( e.getMessage() );
                    }

                    @Override
                    public void onNext(Movie movie) {
                        for (Result result: movie.getResults())
                            addFilme( result );
                    }
                });
    }

    @Override
    public void findAll() {

    }

    @Override
    public void onDestroy() {
        filmeDAO.close();
    }

    private void addFilme (Result result) {
        result.setAssistido(filmeDAO.isAssistido(result.getId()));
        result.setAssistirMaisTarde(filmeDAO.isAssistirDepois(result.getId()));
        App.filmesRecentes.add( new Filme( result ));
    }

    @Override
    public void limparLista(){
        int tamanho = App.filmesRecentes.size();
        if(tamanho > 0){
            for(int i = 0; i < tamanho; i++){
                App.filmesRecentes.remove(0);
            }
            view.notifyItemRangeRemoved(0, tamanho);
        }
    }

    private void makeRetrofit(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create() )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        filmeService = retrofit.create( FilmeService.class );
    }
}
