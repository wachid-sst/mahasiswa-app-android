package id.web.wachid.pms.fragment;

import id.web.wachid.pms.R;
import id.web.wachid.pms.activity.MatkulDetailActivity;
import id.web.wachid.pms.adapter.RvServerAdapter;
import id.web.wachid.pms.listener.RecyclerTouchListener;
import id.web.wachid.pms.model.ResponseServer;
import id.web.wachid.pms.model.SemuaServerItem;
import id.web.wachid.pms.util.Constant;
import id.web.wachid.pms.util.RecyclerItemClickListener;
import id.web.wachid.pms.util.api.BaseApiService;
import id.web.wachid.pms.util.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {

   // @BindView(R.id.tvBelumMatkul)
    TextView tvBelumServer;
   // @BindView(R.id.rvMatkul)
    RecyclerView rvServer;
    ProgressDialog loading;

    Context mContext;
   // ArrayList<SemuaServerItem> semuaServerItem = new ArrayList<>();

    private List<SemuaServerItem> semuaServerItem;
    private RvServerAdapter serverAdapter;
    BaseApiService mApiService;


    private SearchView searchView;

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // toolbar fancy stuff
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("SemuaServerItem List");

       // ButterKnife.bind(this, view);

        rvServer = (RecyclerView) v.findViewById(R.id.rvServer);
        tvBelumServer = (TextView) v.findViewById(R.id.tvBelumMatkul);

        mApiService = UtilsApi.getAPIService();
        mContext = getActivity();

        semuaServerItem = new ArrayList<>();
       // serverAdapter = new RvServerAdapter(mContext, semuaServerItem,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvServer.setLayoutManager(mLayoutManager);
        rvServer.setItemAnimator(new DefaultItemAnimator());
       // rvServer.setAdapter(serverAdapter);
       // serverAdapter.setOnClick((RvServerAdapter.OnItemClicked) mContext);


        /*rvServer.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvServer, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               Log.d("log",  semuaServerItem.get(position).getNamaServer());


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/
        getDataServer();





        return v;



        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mata Kuliah");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // findViews();
        setHasOptionsMenu(true);
    }


    private void getDataServer(){

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaServer().enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (response.body().isError()) {
                        tvBelumServer.setVisibility(View.VISIBLE);
                    } else {
                        final List<SemuaServerItem> semuaServerItems = response.body().getSemuamatkul();
                        serverAdapter = new RvServerAdapter(mContext, semuaServerItems);
                        rvServer.setAdapter(serverAdapter);
                        serverAdapter.notifyDataSetChanged();

                        //initDataIntent(semuaServerItems);

                        Log.d("log", String.valueOf(semuaServerItems));
                        Log.d("log", String.valueOf(semuaServerItem));

                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<SemuaServerItem> serverList){
        rvServer.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String id = serverList.get(position).getId();
                        String namadosen = serverList.get(position).getNamaServer();
                        String matkul = serverList.get(position).getIpServer();

                        Intent detailMatkul = new Intent(mContext, MatkulDetailActivity.class);
                        detailMatkul.putExtra(Constant.KEY_ID_MATKUL, id);
                        detailMatkul.putExtra(Constant.KEY_NAMA_DOSEN, namadosen);
                        detailMatkul.putExtra(Constant.KEY_MATKUL, matkul);
                        startActivity(detailMatkul);
                    }
                }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
       // return true;


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                serverAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                serverAdapter.getFilter().filter(newText);
                Log.d("logging search ",newText);
                return true;
            }
        });
    }
}