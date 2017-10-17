package rest.com.br.movieteca.view.fragment.filmes_recentes;

import java.util.List;

import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.view.fragment.shared.FilmesContract;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public interface FilmesRecentesContract {

    interface Presenter extends FilmesContract.Presenter{
        void findByQuery(int page, String query);
        void findAll(int page);
        void limparLista();
    }
    interface View extends  FilmesContract.View {
        void loadList(List<Filme> filmes, int posicaoFinal, int nextPage, int totalPages);
        void notifyItemRangeRemoved(int position, int size);
    }

}
