package rest.com.br.movieteca.view.fragment.filmes_recentes;

import dagger.Component;
import rest.com.br.movieteca.data.component.NetComponent;
import rest.com.br.movieteca.util.CustomScope;
import rest.com.br.movieteca.view.fragment.shared.FilmeModule;

/**
 * Created by LUCAS RODRIGUES on 14/10/2017.
 */

@CustomScope
@Component( dependencies = NetComponent.class, modules = {
        FilmesRecentesModule.class
})
public interface FilmesRecentesComponent {
    void inject(FilmesRecentesFragment filmesRecentesFragment);
}
