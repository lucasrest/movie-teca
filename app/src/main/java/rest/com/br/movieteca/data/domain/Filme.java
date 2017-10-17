package rest.com.br.movieteca.data.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LUCAS RODRIGUES on 04/10/2017.
 */

public class Filme extends RealmObject implements Parcelable{

    public static final String ID = "rest.com.br.movieteca.data.domain.Filme.ID";

    @PrimaryKey
    private int id;
    @Ignore
    private String imgCapa;
    private String titulo;
    private double avaliacao;
    private String anoLancamento;
    private String categoria;
    private String sinopse;
    private boolean assistido;
    private boolean assistirDepois;
    private byte[] capa;

    public Filme(){};

    public Filme(Result result){
        this.id = result.getId();
        this.imgCapa = result.getPoster_path();
        this.titulo = result.getTitle();
        if(!result.getRelease_date().isEmpty())
            this.anoLancamento = result.getRelease_date().substring(0,4);
        else
            this.anoLancamento = "";
        this.avaliacao = result.getVote_average();
        this.sinopse = result.getOverview();
        this.assistido = result.isAssistido();
        this.assistirDepois = result.isAssistirMaisTarde();
    }

    protected Filme(Parcel in) {
        id = in.readInt();
        imgCapa = in.readString();
        titulo = in.readString();
        avaliacao = in.readDouble();
        anoLancamento = in.readString();
        categoria = in.readString();
        sinopse = in.readString();
        assistido = in.readByte() != 0;
        assistirDepois = in.readByte() != 0;
        capa = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imgCapa);
        dest.writeString(titulo);
        dest.writeDouble(avaliacao);
        dest.writeString(anoLancamento);
        dest.writeString(categoria);
        dest.writeString(sinopse);
        dest.writeByte((byte) (assistido ? 1 : 0));
        dest.writeByte((byte) (assistirDepois ? 1 : 0));
        dest.writeByteArray(capa);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgCapa() {
        return imgCapa;
    }

    public void setImgCapa(String imgCapa) {
        this.imgCapa = imgCapa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public boolean isAssistirDepois() {
        return assistirDepois;
    }

    public void setAssistirDepois(boolean assistirDepois) {
        this.assistirDepois = assistirDepois;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", imgCapa='" + imgCapa + '\'' +
                ", titulo='" + titulo + '\'' +
                ", avaliacao=" + avaliacao +
                ", anoLancamento='" + anoLancamento + '\'' +
                ", categoria='" + categoria + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", assistido=" + assistido +
                ", assistirDepois=" + assistirDepois +
                ", capa=" + Arrays.toString(capa) +
                '}';
    }
}
