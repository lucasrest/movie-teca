package rest.com.br.movieteca.view.fragment.shared;

import dagger.Module;
import dagger.Provides;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.util.FragmentEnum;
import rest.com.br.movieteca.view.fragment.filmes_assistidos.FilmesAssistidosPresenter;
import rest.com.br.movieteca.view.fragment.filmes_pendentes.FilmesPendentesPresenter;
import rest.com.br.movieteca.view.fragment.filmes_recentes.FilmesRecentesContract;
import rest.com.br.movieteca.view.fragment.filmes_recentes.FilmesRecentesPresenter;
import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

@Module
public class FilmeModule {

    private final FilmesContract.View view;
    private final FragmentEnum fragmentEnum;

    public FilmeModule(FilmesContract.View view, FragmentEnum fragmentEnum) {
        this.view = view;
        this.fragmentEnum = fragmentEnum;
    }

    @Provides
    FilmesContract.View providesView(){
        return view;
    }

    @Provides
    FilmeDAO providesFilmeDAO() { return new FilmeDAO(); }

    @Provides
    FilmesContract.Presenter providesPresenter(Retrofit retrofit, FilmesContract.View view, FilmeDAO filmeDAO){
        FilmesContract.Presenter presenter;
        if(fragmentEnum == FragmentEnum.ASSISTIDOS)
            presenter = new FilmesAssistidosPresenter(retrofit, view, filmeDAO);
        else
            presenter = new FilmesPendentesPresenter( retrofit, view, filmeDAO);
        return presenter;
    }

}
