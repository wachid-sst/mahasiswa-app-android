package id.web.wachid.pms.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import id.web.wachid.pms.fragment.AddFragment;
import id.web.wachid.pms.fragment.FavoriteFragment;
import id.web.wachid.pms.fragment.HomeFragment;
import id.web.wachid.pms.R;
import id.web.wachid.pms.fragment.ProfileFragment;
import id.web.wachid.pms.fragment.SearchFragment;
import id.web.wachid.pms.util.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.ivSearh)
    ImageView ivSearch;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivFavorite)
    ImageView ivFavorite;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.frameFragment)
    FrameLayout frameFragment;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initFragment(new HomeFragment());


        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new HomeFragment());
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new SearchFragment());
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new AddFragment());
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new FavoriteFragment());
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new ProfileFragment());
            }
        });
    }

    private void initFragment(Fragment classFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameFragment, classFragment);
        transaction.commit();
    }
}