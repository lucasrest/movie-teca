package rest.com.br.movieteca.data.listiner;


import rest.com.br.movieteca.data.domain.Filme;

/**
 * Created by LUCAS RODRIGUES on 07/10/2017.
 */

public class FilmeListener {

    private Filme filme;

    public FilmeListener( Filme filme ){
        this.filme = filme;
    }

    public Filme getFilme() {
        return filme;
    }
}
