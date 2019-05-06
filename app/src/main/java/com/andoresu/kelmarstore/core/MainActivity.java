package com.andoresu.kelmarstore.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.client.ErrorResponse;
import com.andoresu.kelmarstore.core.product.ProductFragment;
import com.andoresu.kelmarstore.core.products.ProductsContract;
import com.andoresu.kelmarstore.core.products.ProductsFragment;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.utils.BaseActivity;
import com.andoresu.kelmarstore.utils.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainContract.View,
        ProductsContract.InteractionListener {

    public static final String TAG = BASE_TAG + MainActivity.class.getSimpleName();

    private MainContract.ActionsListener actionsListener;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private HeaderViewHolder headerViewHolder;

    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        setTitle(R.string.app_name);

        headerViewHolder = new HeaderViewHolder(navigationView.getHeaderView(0));
        setUserNameToMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        actionsListener = new MainPresenter(this, this, "");

        setProductsFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void setUserNameToMenu(){
        headerViewHolder.navHeaderNameTextView.setText("Kelly");
        headerViewHolder.navHeaderEmailTextView.setText("La mejor del mundo");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.navProducts:
                setProductsFragment();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setProductsFragment(){
        ProductsFragment fragment = ProductsFragment.newInstance(this);
        changeFragment(fragment);
    }

    @Override
    public void goToProductDetail(Product product) {
        ProductFragment fragment = ProductFragment.newInstance(product);
        changeFragment(fragment);
    }

    private void changeFragment(BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setTitle(fragment.getTitle());
        currentFragment = fragment;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        progressBar.setVisibility( active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public void onLogoutFinish() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }



    protected static class HeaderViewHolder {

        @BindView(R.id.navHeaderNameTextView)
        TextView navHeaderNameTextView;

        @BindView(R.id.navHeaderEmailTextView)
        TextView navHeaderEmailTextView;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
