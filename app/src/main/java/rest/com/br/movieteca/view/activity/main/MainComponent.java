package rest.com.br.movieteca.view.activity.main;

import dagger.Component;
import rest.com.br.movieteca.data.component.NetComponent;
import rest.com.br.movieteca.util.CustomScope;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

@CustomScope
@Component( dependencies = NetComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
