package rest.com.br.movieteca.view.activity.filme_detalhe;

import android.content.Intent;
import android.graphics.Bitmap;

import rest.com.br.movieteca.data.domain.Filme;

/**
 * Created by LUCAS RODRIGUES on 17/10/2017.
 */

public interface FilmeDetalheContract {

    interface View {
        void loadFilme(Bitmap bitmap, Filme filme);
    }

    interface Presenter {
        void loadFilme(Intent intent);
    }
}
