package rest.com.br.movieteca.view.fragment.shared;

import java.util.List;

import rest.com.br.movieteca.data.domain.Filme;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public interface FilmesContract {

    interface View{
        void showLoad();
        void hideLoad();
        void showError(String message);
        void loadList(List<Filme> filmes);
    }

    interface Presenter{
        void findAll();
        void onDestroy();
    }
}
