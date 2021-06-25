package Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sidray.live_rute_10.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener{
    private List<ListFavoritos> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public ListAdapter(List<ListFavoritos> list, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = list;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= mInflater.inflate(R.layout.list_element, null);

        view.setOnClickListener(this);

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListFavoritos> items){
        mData=items;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView immg;
        TextView ruuta;

        ViewHolder(View itemView){
            super(itemView);
            immg = itemView.findViewById(R.id.img);
            ruuta = itemView.findViewById(R.id.tvrutta);
        }

        void bindData(final ListFavoritos item){
            ruuta.setText(item.getRuta());
        }

    }

}
