package rest.com.br.movieteca.data.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;

import io.realm.Realm;
import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.domain.Filme;

import static rest.com.br.movieteca.R.layout.filme;

/**
 * Created by LUCAS RODRIGUES on 07/10/2017.
 */

public class FilmeDAO {

    Realm realm;

    public FilmeDAO(){
        realm = Realm.getDefaultInstance();
    }

    public void save(final Filme filme, final boolean assistir, Context context) {

        if (context != null) {
            Picasso.with( context ).load(Constants.API_MOVIEDB_IMG_BASE_URL + filme.getImgCapa()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    filme.setCapa(stream.toByteArray());
                    realm.beginTransaction();
                    if ( assistir ){
                        filme.setAssistido( true );
                        filme.setAssistirDepois( false );
                    }else {
                        filme.setAssistido( false );
                        filme.setAssistirDepois( true );
                    }
                    if(!existFilme(filme.getId())){
                        realm.copyToRealm(filme);
                    }
                    realm.commitTransaction();
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }else {
            realm.beginTransaction();
            if ( assistir ){
                filme.setAssistido( true );
                filme.setAssistirDepois( false );
            }else {
                filme.setAssistido( false );
                filme.setAssistirDepois( true );
            }
            if(existFilme(filme.getId()))
                realm.copyToRealm(filme);
            realm.commitTransaction();
        }
    }

    public List<Filme> findAllAssistirDepois(){
        return realm.where( Filme.class ).equalTo("assistirDepois", true).findAll();
    }

    public List<Filme> findAllAssistidos(){
        return realm.where( Filme.class ).equalTo("assistido", true).findAll();
    }
    
    public void delete(Filme filme){
        realm.beginTransaction();
        filme.deleteFromRealm();
        realm.commitTransaction();
    }

    public void close(){
        realm.close();
    }

    public boolean isAssistido(int id) {
        return (realm.where( Filme.class ).equalTo( "id", id).equalTo("assistido", true).findFirst() != null ? true : false);
    }

    public boolean isAssistirDepois ( int id ){
        return (realm.where( Filme.class ).equalTo( "id", id ).equalTo("assistirDepois", true).findFirst() != null ? true : false);
    }

    public boolean existFilme( int id ){
        return (realm.where( Filme.class ).equalTo("id", id).findFirst() != null);
    }

}
