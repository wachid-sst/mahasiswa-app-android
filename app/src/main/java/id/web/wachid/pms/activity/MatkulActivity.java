package id.web.wachid.pms.activity;

/**
 * Created by command center on 12/6/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.wachid.pms.R;
import id.web.wachid.pms.adapter.RvServerAdapter;
import id.web.wachid.pms.model.ResponseServer;
import id.web.wachid.pms.model.SemuaServerItem;
import id.web.wachid.pms.util.Constant;
import id.web.wachid.pms.util.RecyclerItemClickListener;
import id.web.wachid.pms.util.api.BaseApiService;
import id.web.wachid.pms.util.api.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatkulActivity extends AppCompatActivity {

    @BindView(R.id.btnTambahMatkul)
    Button btnTambahMatkul;
    @BindView(R.id.tvBelumMatkul)
    TextView tvBelumMatkul;
    @BindView(R.id.rvServer)
    RecyclerView rvMatkul;
    ProgressDialog loading;

    Context mContext;
    ArrayList<SemuaServerItem> semuaServerItem = new ArrayList<>();
    RvServerAdapter serverAdapter;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul);

        getSupportActionBar().setTitle("Mata Kuliah");

        ButterKnife.bind(this);
        mApiService = UtilsApi.getAPIService();
        mContext = this;

        serverAdapter = new RvServerAdapter(this, semuaServerItem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvMatkul.setLayoutManager(mLayoutManager);
        rvMatkul.setItemAnimator(new DefaultItemAnimator());

        getDataMatkul();

        btnTambahMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatkulActivity.this, TambahMatkulActivity2.class));
            }
        });
    }

    private void getDataMatkul(){
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

        mApiService.getSemuaServer().enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    if (response.body().isError()) {
                        tvBelumMatkul.setVisibility(View.VISIBLE);
                    } else {
                        final List<SemuaServerItem> semuaServerItems = response.body().getSemuamatkul();
                        rvMatkul.setAdapter(new RvServerAdapter(mContext, semuaServerItem));
                        serverAdapter.notifyDataSetChanged();

                      //  initDataIntent(semuaServerItems);
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mata kuliah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<SemuaServerItem> matkulList){
        rvMatkul.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String id = matkulList.get(position).getId();
                        String namadosen = matkulList.get(position).getNamaServer();
                        String matkul = matkulList.get(position).getIpServer();

                        Intent detailMatkul = new Intent(mContext, MatkulDetailActivity.class);
                        detailMatkul.putExtra(Constant.KEY_ID_MATKUL, id);
                        detailMatkul.putExtra(Constant.KEY_NAMA_DOSEN, namadosen);
                        detailMatkul.putExtra(Constant.KEY_MATKUL, matkul);
                        startActivity(detailMatkul);
                    }
                }));
    }
}