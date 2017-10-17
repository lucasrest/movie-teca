package rest.com.br.movieteca.data.component;

import javax.inject.Singleton;

import dagger.Component;
import rest.com.br.movieteca.app.AppModule;
import rest.com.br.movieteca.data.module.NetModule;
import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class
})
public interface NetComponent {
    Retrofit retrofit();
}
