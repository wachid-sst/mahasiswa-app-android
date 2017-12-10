package id.web.wachid.pms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Filter;
import android.widget.Filterable;

import com.amulyakhare.textdrawable.TextDrawable;

import id.web.wachid.pms.R;
import id.web.wachid.pms.fragment.HomeFragment;
import id.web.wachid.pms.model.SemuaServerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wachid.sst
 * website : www.wachid.web.id
 * github : https://github.com/wachid.sst
 */


public class RvServerAdapter extends RecyclerView.Adapter<RvServerAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private  List<SemuaServerItem> serverList;
    private  List<SemuaServerItem> serverListFiltered;
   // private ServerAdapterListener listener;


    public String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };



    public RvServerAdapter(Context context, List<SemuaServerItem> serverList ) {
        this.mContext = context;
       //   this.listener = listener;
        this.serverList = serverList;
        this.serverListFiltered = serverList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        // @BindView(R.id.ivTextDrawable)
        ImageView ivTextDrawable;
        // @BindView(R.id.tvNamaDosen)
        TextView tvNamaServer;
        // @BindView(R.id.tvNamaMatkul)
        TextView tvIpServer;

        public MyViewHolder(View itemView) {
            super(itemView);

            //  ButterKnife.bind(this, itemView);

            tvNamaServer = (TextView) itemView.findViewById(R.id.tvNamaDosen);
            tvIpServer = (TextView) itemView.findViewById(R.id.tvNamaMatkul);
            ivTextDrawable = (ImageView) itemView.findViewById(R.id.ivTextDrawable);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    // send selected contact in callback
                  //  listener.onServerSelected(serverListFiltered.get(getAdapterPosition()));

                    Log.d("log ", String.valueOf(getAdapterPosition()));


                    Log.d("log ", String.valueOf(serverListFiltered.get(getAdapterPosition())));
                }
            });


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_server, parent, false);
        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(RvServerAdapter.MyViewHolder viewHolder, final int position) {
        final SemuaServerItem server = serverListFiltered.get(position);
        viewHolder.tvNamaServer.setText(server.getNamaServer());
        viewHolder.tvIpServer.setText(server.getIpServer());

       // holder.tvNamaServer.setText(mFilteredList.get(position).getNamaServer());
      //  holder.tvIpServer.setText(mFilteredList.get(position).getIpServer());



        String namaServer = serverListFiltered.get(position).getNamaServer();
        String firstCharNamaServer = namaServer.substring(0,1);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstCharNamaServer, getColor());
        viewHolder.ivTextDrawable.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return serverListFiltered.size();
    }

    @Override
    public Filter getFilter() {

        Log.d("logging call filter ", "call filter");

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    serverListFiltered = serverList;
                    Log.d("logging filter ", "filter empty");

                } else {
                    Log.d("logging filter ", "else ! filter empty");

                    List<SemuaServerItem> filteredList = new ArrayList<>();
                    for (SemuaServerItem row : serverList) {
                        Log.d("logging filter ", "for if ...");
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNamaServer().toLowerCase().contains(charString.toLowerCase()) || row.getIpServer().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                            Log.d("logging semuaServer ", String.valueOf(row));
                        }
                    }

                    serverListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = serverListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                serverListFiltered = (ArrayList<SemuaServerItem>) filterResults.values;
                notifyDataSetChanged();
                Log.d("logging filter ", String.valueOf(filterResults.values));
            }
        };
    }




    public int getColor() {
        String color;

        // Randomly select a fact
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }


}
