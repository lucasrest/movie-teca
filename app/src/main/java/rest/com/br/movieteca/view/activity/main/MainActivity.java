package rest.com.br.movieteca.view.activity.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rest.com.br.movieteca.R;
import rest.com.br.movieteca.app.App;
import rest.com.br.movieteca.data.component.DaggerNetComponent;
import rest.com.br.movieteca.data.dao.FilmeDAO;
import rest.com.br.movieteca.data.domain.Filme;
import rest.com.br.movieteca.view.fragment.TabFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.main_drawer) NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind( this );

        DaggerMainComponent
                .builder()
                .netComponent( App.getComponent() )
                .mainModule( new MainModule( this ) )
                .build()
                .inject( this );

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir, R.string.fechar);

        if( toolbar != null ){
            setSupportActionBar( toolbar );
        }
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked( true );
                drawerLayout.closeDrawers();

                if(item.getItemId() == R.id.menu_home) {
                    Log.i( TAG, "Home");
                }
                else if (item.getItemId() == R.id.menu_sair){
                    finish();
                }
                return false;
            }
        });

        //add();

    }

    private void add() {
        Filme filme = new Filme();
        filme.setTitulo("nemsei");
        filme.setAssistido(false);
        filme.setId(2);
        filme.setSinopse("sdsfsdfsdfsdf wwerwr wr");
        FilmeDAO filmeDAO = new FilmeDAO();
        filmeDAO.save(filme, false, null);
        filme = new Filme();
        filme.setTitulo("nemsei");
        filme.setAssistido(false);
        filme.setId(3);
        filme.setSinopse("sdsfsdfsdfsdf wwerwr wr");
        filmeDAO.save(filme, false, null);
        filme = new Filme();
        filme.setTitulo("nemsei");
        filme.setAssistido(false);
        filme.setId(4);
        filme.setSinopse("sdsfsdfsdfsdf wwerwr wr");
        filmeDAO.save(filme, false, null);
        filme = new Filme();
        filme.setTitulo("nemsei");
        filme.setAssistido(false);
        filme.setId(5);
        filme.setSinopse("sdsfsdfsdfsdf wwerwr wr");
        filmeDAO.save(filme, false, null);
        filmeDAO.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( toggle.onOptionsItemSelected( item ) ){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
