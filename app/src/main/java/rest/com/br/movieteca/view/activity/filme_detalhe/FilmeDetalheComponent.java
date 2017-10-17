package rest.com.br.movieteca.view.activity.filme_detalhe;

import dagger.Component;
import rest.com.br.movieteca.util.CustomScope;

/**
 * Created by LUCAS RODRIGUES on 17/10/2017.
 */

@CustomScope
@Component(modules = FilmeDetalheModule.class)
public interface FilmeDetalheComponent {
    void inject(FilmeDetalheActivity filmeDetalheActivity);
}
