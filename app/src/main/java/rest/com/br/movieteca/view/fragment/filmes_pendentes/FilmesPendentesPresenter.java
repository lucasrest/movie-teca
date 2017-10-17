package rest.com.br.movieteca.view.fragment.filmes_pendentes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.view.fragment.shared.FilmesContract;
import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public class FilmesPendentesPresenter implements FilmesContract.Presenter {

    Retrofit retrofit;
    FilmesContract.View view;
    FilmeDAO filmeDAO;

    @Inject
    public FilmesPendentesPresenter(Retrofit retrofit, FilmesContract.View view, FilmeDAO filmeDAO){
        this.retrofit = retrofit;
        this.view = view;
        this.filmeDAO = filmeDAO;
    }

    @Override
    public void findAll() {
        view.showLoad();
        List<Filme> filmes = filmeDAO.findAllAssistirDepois();
        App.filmesPendentes = new ArrayList<>(filmes);
        view.loadList(filmes);
        view.hideLoad();
    }

    @Override
    public void onDestroy() {
        filmeDAO.close();
    }
}
