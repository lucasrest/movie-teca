package rest.com.br.movieteca.service;

import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.domain.Movie;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LUCAS RODRIGUES on 05/10/2017.
 */

public interface FilmeService {

    String API_PATH = Constants.API_KEY_MOVIEDB + Constants.API_MOVIEDB_LANGUAGE;

    String nowPlaying = "now_playing" + API_PATH;

    String searchMovie = "movie" + API_PATH;

    @GET(nowPlaying)
    public Observable<Movie> findAll(@Query("page") int page);

    @GET(searchMovie)
    public Observable<Movie> search(@Query("page") int page, @Query("query") String query);

}


