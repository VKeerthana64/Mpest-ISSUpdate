package com.amana.CLEANSolutions.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amana.CLEANSolutions.R;
import com.amana.CLEANSolutions.fragment.ServicesFragment;
import com.amana.CLEANSolutions.model.PestModel;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class PestAdapter extends RecyclerView.Adapter<PestAdapter.ViewHolder> {

    ArrayList<PestModel> arrayPest;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context mcontext;

    public PestAdapter(Context context, ArrayList<PestModel> arrayPest) {
        this.mcontext = context;
        this.mInflater = LayoutInflater.from(context);
        this.arrayPest= arrayPest;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_pests, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PestModel pestModel = arrayPest.get(position);

        if(position == pestModel.getSelectedPosition()){

            holder.btn_text.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.btn_fill_green));
            holder.btn_text.setTextColor(ContextCompat.getColor(mcontext,R.color.white));
        }else{

            holder.btn_text.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.btn_shape_green));
            holder.btn_text.setTextColor(ContextCompat.getColor(mcontext,R.color.black));

        }
        holder.btn_text.setText(pestModel.getTitle());

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayPest.size();
    }

    public PestModel getItem(int pos) {
        return arrayPest.get(pos);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button btn_text;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_text = (Button) itemView.findViewById(R.id.text_btn);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onPestItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onPestItemClick(View view, int position);
    }
}