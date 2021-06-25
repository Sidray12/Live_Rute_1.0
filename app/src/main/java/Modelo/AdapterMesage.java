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

public class AdapterMesage extends RecyclerView.Adapter<AdapterMesage.ViewHolder> implements View.OnClickListener{
    private List<ListMesage> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public AdapterMesage(List<ListMesage> list, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = list;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public AdapterMesage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= mInflater.inflate(R.layout.list_comentarios, null);

        view.setOnClickListener(this);

        return new AdapterMesage.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterMesage.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListMesage> items){
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
        TextView cor, men;

        ViewHolder(View itemView){
            super(itemView);
            immg = itemView.findViewById(R.id.imgm);
            cor = itemView.findViewById(R.id.tvcorreo);
            men = itemView.findViewById(R.id.mesaje);
        }

        void bindData(final ListMesage item){
            cor.setText(item.getCorreo());
            men.setText(item.getMensaje());
        }

    }
}
