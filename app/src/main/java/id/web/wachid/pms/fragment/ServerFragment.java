package id.web.wachid.pms.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.web.wachid.pms.R;
import id.web.wachid.pms.adapter.DataAdapter;
import id.web.wachid.pms.adapter.ServerAdapter;
import id.web.wachid.pms.model.AndroidVersion;
import id.web.wachid.pms.model.ResponseServer;
import id.web.wachid.pms.model.SemuaServerItem;
import id.web.wachid.pms.util.api.BaseApiService;
import id.web.wachid.pms.util.api.JSONResponse;
import id.web.wachid.pms.util.api.RequestInterface;
import id.web.wachid.pms.util.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerFragment extends Fragment {

    public static final String BASE_URL = "https://api.learn2crack.com";
    private RecyclerView mRecyclerView;
    private ArrayList<SemuaServerItem> mArrayList;
    private DataAdapter mAdapter;
    ProgressDialog loading;
    BaseApiService mApiService;
    Context mContext;
    ArrayList<SemuaServerItem> semuaServerItem = new ArrayList<>();

    public static ServerFragment newInstance() {

        return new ServerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("SemuaServerItem List");

        mApiService = UtilsApi.getAPIService();

        //mAdapter = new ServerAdapter(this, semuaServerItem);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

       // initViews();
      //  loadJSON();

        getDataServer();

        return view;

    }

 /*   private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                mAdapter = new DataAdapter(mArrayList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });


    }*/

    private void getDataServer(){

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaServer().enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (response.body().isError()) {
                     //   tvBelumServer.setVisibility(View.VISIBLE);
                    } else {
                        final List<SemuaServerItem> semuaServerItems = response.body().getSemuamatkul();
                        Log.d("log", String.valueOf(semuaServerItems));
                        mRecyclerView.setAdapter(new ServerAdapter(mContext, mArrayList));
                        mAdapter.notifyDataSetChanged();

                      //  initDataIntent(semuaServerItems);


                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
            //    loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // findViews();
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);

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

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}