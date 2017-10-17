package rest.com.br.movieteca.data.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LUCAS RODRIGUES on 06/10/2017.
 */

public class Result implements Parcelable{


    private int id;
    private double vote_average;
    private String title;
    private String poster_path;
    private String overview;
    private String release_date;
    private boolean assistido;
    private boolean assistirMaisTarde;

    public Result() {
    }

    public Result(int id, double vote_average, String title, String poster_path, String overview, String release_date, boolean assistido, boolean assistirMaisTarde) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.assistido = assistido;
        this.assistirMaisTarde = assistirMaisTarde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public boolean isAssistirMaisTarde() {
        return assistirMaisTarde;
    }

    public void setAssistirMaisTarde(boolean assistirMaisTarde) {
        this.assistirMaisTarde = assistirMaisTarde;
    }

    protected Result(Parcel in) {
        id = in.readInt();
        vote_average = in.readDouble();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        assistido = in.readByte() != 0;
        assistirMaisTarde = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeByte((byte) (assistido ? 1 : 0));
        dest.writeByte((byte) (assistirMaisTarde ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
