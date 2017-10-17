package rest.com.br.movieteca.app;

import android.app.Application;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rest.com.br.movieteca.data.component.DaggerNetComponent;
import rest.com.br.movieteca.data.component.NetComponent;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.data.module.NetModule;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public class App extends Application {

    private static NetComponent netComponent;

    public static List<Filme> filmesPendentes;

    public static List<Filme> filmesAssistidos;

    public static List<Filme> filmesRecentes;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init( this );
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name( "movie-teca")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration( realmConfiguration );


        netComponent = DaggerNetComponent
                .builder()
                .appModule( new AppModule( this ))
                .netModule( new NetModule( Constants.API_MOVIEDB_BASE_URL ) )
                .build();
    }

    public static NetComponent getComponent(){
        return netComponent;
    }
}
