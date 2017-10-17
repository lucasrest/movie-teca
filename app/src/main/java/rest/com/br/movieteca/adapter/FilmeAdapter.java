package rest.com.br.movieteca.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.app.Constants;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.data.listiner.FilmeListener;
import rest.com.br.movieteca.data.listiner.FilmeRemovidoListener;
import rest.com.br.movieteca.util.FragmentEnum;
import rest.com.br.movieteca.view.activity.filme_detalhe.FilmeDetalheActivity;


/**
 * Created by LUCAS RODRIGUES on 04/10/2017.
 */

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.ViewHolder> {

    private List<Filme> filmes;
    private FragmentEnum fragmentEnum;
    private Context context;
    private Fragment fragment;
    private String TAG = "FilmeAdapter";

    public FilmeAdapter(Context context, List<Filme> filmes, FragmentEnum fragmentEnum){
        this.context = context;
        this.filmes = filmes;
        this.fragmentEnum = fragmentEnum;
    }

    public void setFragment( Fragment fragment ){
        this.fragment = fragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgCapa) ImageView imgCapa;
        @BindView(R.id.imgAssistirMaisTarde) ImageView imgAssistirMaisTarde;
        @BindView(R.id.imgAssistido) ImageView imgAssistido;
        @BindView(R.id.imgExcluir) ImageView imgExcluir;
        @BindView(R.id.tvTitulo) TextView tvTitulo;
        @BindView(R.id.tvAvaliacao) TextView tvAvaliacao;
        @BindView(R.id.tvAnoLancamento) TextView tvAnoLancamento;
        @BindView(R.id.tvCategorias) TextView tvCategorias;
        @BindView(R.id.tvSinopse) TextView tvSinopse;
        @BindView(R.id.tvMaisInformacoes) TextView tvMaisInformacoes;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind( this, view );
        }
    }

    @Override
    public FilmeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.filme, parent, false);

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final FilmeAdapter.ViewHolder holder, final int position) {

        final Filme filme = filmes.get(position);
        try {
            if (fragmentEnum == FragmentEnum.RECENTES) {
                holder.imgExcluir.setVisibility( View.GONE );
                Picasso.with(context)
                        .load(Constants.API_MOVIEDB_IMG_BASE_URL + filme.getImgCapa())
                        .placeholder(R.drawable.img_default)
                        .into(holder.imgCapa);
            }else {
                if (filme.getCapa() != null) {
                    byte[] outImage = filme.getCapa();
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    holder.imgCapa.setImageBitmap(bitmap);
                } else {
                    holder.imgCapa.setImageResource( R.drawable.img_default );
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            holder.imgCapa.setImageResource(R.drawable.img_default);
        }
        holder.tvTitulo.setText( filme.getTitulo() );
        holder.tvAvaliacao.setText( String.valueOf( filme.getAvaliacao() ));
        holder.tvAnoLancamento.setText( filme.getAnoLancamento() );
        holder.tvCategorias.setText( filme.getCategoria() );
        holder.tvSinopse.setText( filme.getSinopse() );
        if( filme.isAssistido() ) {
            holder.imgAssistido.setImageResource( R.drawable.ic_play_circle_filled );
        }else {
            holder.imgAssistido.setImageResource( R.drawable.ic_play_circle_outline );
        }

        if( filme.isAssistirDepois() ){
            holder.imgAssistirMaisTarde.setImageResource( R.drawable.ic_watch_later );
        }else {
            holder.imgAssistirMaisTarde.setImageResource( R.drawable.ic_action_clock);
        }

        holder.imgAssistido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if( fragmentEnum == FragmentEnum.PENDENTES) {
                        movimentarFilme(App.filmesPendentes, App.filmesAssistidos, position, true);
                    }else if ( fragmentEnum == FragmentEnum.RECENTES ){
                        movimentarFilme(App.filmesRecentes, App.filmesAssistidos, position, true);
                    }
                }catch ( Exception e ){
                    Log.e( TAG, "Error: " + e.getMessage());
                }

            }
        });

        holder.imgAssistirMaisTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if ( fragmentEnum == FragmentEnum.ASSISTIDOS ){
                        movimentarFilme(App.filmesAssistidos, App.filmesPendentes, position, false);
                    }else if ( fragmentEnum == FragmentEnum.RECENTES ){
                        movimentarFilme(App.filmesRecentes, App.filmesPendentes, position, false);
                    }
                }catch ( Exception e ){
                    Log.e( TAG, "Error: " + e.getMessage());
                }
            }
        });

        holder.imgExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    desmarcarAcoes(filmes.get( position ));
                    FilmeDAO filmeDAO = new FilmeDAO();
                    filmeDAO.delete(filmes.get( position ));
                    notifyItemRemoved( position );
                    notifyItemRangeRemoved( position, filmes.size());
                    EventBus.getDefault().post( new FilmeRemovidoListener( position ));
                }catch ( Exception e ){
                    Log.e(TAG, "Error:" + e.getMessage());
                }
            }
        });

        holder.tvMaisInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, FilmeDetalheActivity.class );
                Filme f = filmes.get( position );
                intent.putExtra(Filme.ID, f);
                context.startActivity( intent );
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    private void movimentarFilme(List<Filme> origem, List<Filme> destino, int position, boolean assistir) {
        Filme f = origem.get( position );
        FilmeDAO filmeDAO = new FilmeDAO();
        if(fragmentEnum == FragmentEnum.RECENTES){
            filmeDAO.save(f, assistir, context);
            filmeDAO.close();
        }else {
            filmeDAO.save(f, assistir, null);
            filmeDAO.close();
        }
        destino.add(f);
        if(fragmentEnum != FragmentEnum.RECENTES){
            origem.remove( position );
            notifyItemRemoved( position );
            notifyItemRangeRemoved( position, origem.size());
        }else {
            if( assistir )
                origem.get( position ).setAssistido( true );
            else
                origem.get( position ).setAssistirDepois( true );
            notifyDataSetChanged();
        }
        EventBus.getDefault().post( new FilmeListener(f));
    }

    private void desmarcarAcoes( Filme filme ){
        for (Filme f: App.filmesRecentes){
            if (f.getId() == filme.getId()){
                f.setAssistido( false );
                f.setAssistirDepois( false );
                break;
            }
        }
    }
}
