package rest.com.br.movieteca.view.activity.filme_detalhe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.domain.Filme;

public class FilmeDetalheActivity extends AppCompatActivity implements FilmeDetalheContract.View {
    @BindView(R.id.img_capa) ImageView imgCapa;
    @BindView(R.id.txt_titulo) TextView txtTitulo;
    @BindView(R.id.txt_descricao) TextView txtDescricao;
    @BindView(R.id.app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.collapsing) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject
    FilmeDetalheContract.Presenter presenter;
    String titulo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);

        ButterKnife.bind( this );

        DaggerFilmeDetalheComponent
                    .builder()
                    .filmeDetalheModule( new FilmeDetalheModule( this, this ))
                    .build()
                    .inject( this );

        presenter.loadFilme( getIntent() );

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean show = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if( scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle( titulo );
                    show = true;
                }else {
                    collapsingToolbarLayout.setTitle("");
                    show = false;
                }
            }
        });
        collapsingToolbarLayout.setTitle("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void loadFilme(Bitmap bitmap, Filme filme) {
        imgCapa.setImageBitmap(bitmap);
        txtTitulo.setText( filme.getTitulo() );
        txtDescricao.setText( filme.getSinopse() );
        titulo = filme.getTitulo();
    }
}
