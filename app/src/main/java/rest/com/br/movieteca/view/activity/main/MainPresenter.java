package rest.com.br.movieteca.view.activity.main;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by LUCAS RODRIGUES on 13/10/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    Retrofit retrofit;
    MainContract.View view;

    @Inject
    public MainPresenter(Retrofit retrofit, MainContract.View view) {
        this.retrofit = retrofit;
        this.view = view;
    }
}
