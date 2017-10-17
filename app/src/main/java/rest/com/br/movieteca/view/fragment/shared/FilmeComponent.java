package rest.com.br.movieteca.view.fragment.shared;

import dagger.Component;
import rest.com.br.movieteca.data.component.NetComponent;
import rest.com.br.movieteca.util.CustomScope;
import rest.com.br.movieteca.view.fragment.filmes_assistidos.FilmesAssistidosFragment;
import rest.com.br.movieteca.view.fragment.filmes_pendentes.FilmesPendentesFragment;
import rest.com.br.movieteca.view.fragment.filmes_recentes.FilmesRecentesFragment;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

@CustomScope
@Component( dependencies = NetComponent.class, modules = FilmeModule.class)
public interface FilmeComponent {
    void inject(FilmesPendentesFragment filmesPendentesFragment);
    void inject(FilmesAssistidosFragment filmesAssistidosFragment);
}
