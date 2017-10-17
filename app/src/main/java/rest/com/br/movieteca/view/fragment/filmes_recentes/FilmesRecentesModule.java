package rest.com.br.movieteca.view.fragment.filmes_recentes;

import dagger.Module;
import dagger.Provides;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 14/10/2017.
 */

@Module
public class FilmesRecentesModule {

    private final FilmesRecentesContract.View view;

    public FilmesRecentesModule(FilmesRecentesContract.View view) {
        this.view = view;
    }

    @Provides
    FilmesRecentesContract.View providesView(){
        return this.view;
    }

    @Provides
    FilmeDAO providesFilmeDAO() { return new FilmeDAO(); }

    @Provides
    FilmesRecentesContract.Presenter providesRecentesPresenter(Retrofit retrofit, FilmeDAO filmeDAO){
        return new FilmesRecentesPresenter( retrofit, view, filmeDAO);
    }

}
