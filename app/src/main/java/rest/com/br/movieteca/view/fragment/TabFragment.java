package rest.com.br.movieteca.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.view.fragment.filmes_assistidos.FilmesAssistidosFragment;
import rest.com.br.movieteca.view.fragment.filmes_pendentes.FilmesPendentesFragment;
import rest.com.br.movieteca.view.fragment.filmes_recentes.FilmesRecentesFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private static final int tabCount = 3;

    @BindView(R.id.tab) TabLayout tabLayout;

    @BindView(R.id.vpContent) ViewPager vpContent;

    public TabFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        ButterKnife.bind( this, view );

        vpContent.setAdapter( new TabAdapter( getFragmentManager() ));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager( vpContent );
            }
        });

        return view;
    }

    private class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch ( position ){

                case 0: return new FilmesPendentesFragment();
                case 1: return new FilmesRecentesFragment();
                case 2: return new FilmesAssistidosFragment();
                default: return null;

            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch ( position ){

                case 0: return "Pendentes";
                case 1: return "Recentes";
                case 2: return "Assistidos";
            }

            return super.getPageTitle(position);
        }
    }

}
