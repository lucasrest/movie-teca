package rest.com.br.movieteca.view.activity.main;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */
@Module
public class MainModule {

    private final MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    MainContract.View providesView(){
        return view;
    }

    @Provides
    MainContract.Presenter providesPresenter(Retrofit retrofit){
        return new MainPresenter( retrofit, view);
    }
}
