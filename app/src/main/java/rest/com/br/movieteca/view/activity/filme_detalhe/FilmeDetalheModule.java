package rest.com.br.movieteca.view.activity.filme_detalhe;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LUCAS RODRIGUES on 17/10/2017.
 */

@Module
public class FilmeDetalheModule {

    final FilmeDetalheContract.View view;
    final Context context;

    public FilmeDetalheModule(FilmeDetalheContract.View view, Context context) {

        this.view = view;
        this.context = context;
    }

    @Provides
    FilmeDetalheContract.View providesView(){
        return view;
    }

    @Provides
    FilmeDetalheContract.Presenter providesPresenter(){
        return new FilmeDetalhePresenter( view, context );
    }

}
