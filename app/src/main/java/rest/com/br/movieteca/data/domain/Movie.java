package rest.com.br.movieteca.data.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by LUCAS RODRIGUES on 06/10/2017.
 */

public class Movie implements Parcelable{

    private int total_pages;

    private List<Result> results;

    protected Movie(Parcel in) {
        total_pages = in.readInt();
        results = in.createTypedArrayList(Result.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total_pages);
        parcel.writeTypedList(results);
    }
}
