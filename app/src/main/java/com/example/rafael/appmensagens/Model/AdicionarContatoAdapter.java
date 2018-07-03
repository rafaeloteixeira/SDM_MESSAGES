package com.example.rafael.appmensagens.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafael.appmensagens.R;

import java.util.List;

public class AdicionarContatoAdapter extends RecyclerView.Adapter<AdicionarContatoAdapter.ViewHolder> {

    private List<Contato> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public AdicionarContatoAdapter(Context context, List<Contato> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contato contato = mData.get(position);
        holder.tvId.setText(contato.getId());
        holder.tvNome.setText(contato.getNomeCompleto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvId;
        TextView tvNome;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvContatoID);
            tvNome = itemView.findViewById(R.id.tvContatoNome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Contato getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}