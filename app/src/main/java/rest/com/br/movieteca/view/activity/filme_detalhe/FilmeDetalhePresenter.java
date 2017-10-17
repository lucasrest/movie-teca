package rest.com.br.movieteca.view.activity.filme_detalhe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import rest.com.br.movieteca.R;
import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.domain.Filme;

/**
 * Created by LUCAS RODRIGUES on 17/10/2017.
 */

public class FilmeDetalhePresenter implements FilmeDetalheContract.Presenter {

    final FilmeDetalheContract.View view;
    final Context context;
    Filme filme = null;

    @Inject
    public FilmeDetalhePresenter(FilmeDetalheContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadFilme(Intent intent) {
        if (intent.getParcelableExtra( Filme.ID ) != null ){
            filme = (Filme) intent.getParcelableExtra( Filme.ID );
            Bitmap bitmap;
            if (filme.getCapa() != null ){
                byte[] outImage = filme.getCapa();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
                view.loadFilme(bitmap, filme);
            }else {
                Picasso.with( context )
                        .load(Constants.API_MOVIEDB_IMG_BASE_URL + filme.getImgCapa())
                        .placeholder(R.drawable.img_default)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                view.loadFilme(bitmap, filme);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
            }


        }
    }
}
