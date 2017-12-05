package id.web.wachid.pms.fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.wachid.pms.R;
import id.web.wachid.pms.activity.MatkulDetailActivity;
import id.web.wachid.pms.adapter.MatkulAdapter;
import id.web.wachid.pms.model.ResponseMatkul;
import id.web.wachid.pms.model.SemuamatkulItem;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @BindView(R.id.tvBelumMatkul)
    TextView tvBelumMatkul;
    @BindView(R.id.rvMatkul)
    RecyclerView rvMatkul;
    ProgressDialog loading;

    Context mContext;
    List<SemuamatkulItem> semuamatkulItemList = new ArrayList<>();
    MatkulAdapter matkulAdapter;
    BaseApiService mApiService;

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Server List");

        ButterKnife.bind(this, view);
        mApiService = UtilsApi.getAPIService();
        mContext = getActivity();

        matkulAdapter = new MatkulAdapter(getActivity(), semuamatkulItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvMatkul.setLayoutManager(mLayoutManager);
        rvMatkul.setItemAnimator(new DefaultItemAnimator());

        getDataMatkul();

        return view;

        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mata Kuliah");
    }

    private void getDataMatkul(){

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaMatkul().enqueue(new Callback<ResponseMatkul>() {
            @Override
            public void onResponse(Call<ResponseMatkul> call, Response<ResponseMatkul> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (response.body().isError()) {
                        tvBelumMatkul.setVisibility(View.VISIBLE);
                    } else {
                        final List<SemuamatkulItem> semuamatkulItems = response.body().getSemuamatkul();
                        rvMatkul.setAdapter(new MatkulAdapter(mContext, semuamatkulItems));
                        matkulAdapter.notifyDataSetChanged();

                        initDataIntent(semuamatkulItems);
                    }
                } else {
                 //   loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mata kuliah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMatkul> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<SemuamatkulItem> matkulList){
        rvMatkul.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String id = matkulList.get(position).getId();
                        String namadosen = matkulList.get(position).getNamaDosen();
                        String matkul = matkulList.get(position).getMatkul();

                        Intent detailMatkul = new Intent(mContext, MatkulDetailActivity.class);
                        detailMatkul.putExtra(Constant.KEY_ID_MATKUL, id);
                        detailMatkul.putExtra(Constant.KEY_NAMA_DOSEN, namadosen);
                        detailMatkul.putExtra(Constant.KEY_MATKUL, matkul);
                        startActivity(detailMatkul);
                    }
                }));
    }
}