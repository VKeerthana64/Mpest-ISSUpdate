package com.amana.MpestISS.quiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.PhotoAdapter;
import com.amana.MpestISS.model.quiz.Datum;
import com.amana.MpestISS.utils.AppConstants;
import com.amana.MpestISS.utils.AppPreferences;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<Datum> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;
    QuizAdapter(Context context, ArrayList<Datum> arrayDatum) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayDatum= arrayDatum;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_quiz, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Datum datum = arrayDatum.get(position);

        holder.tvQuestion.setText(datum.getQuestion().toString());


    /*    RadioGroup.LayoutParams rprms;

        for(int i=0;i<datum.getQuizOptions().size();i++){
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(datum.getQuizOptions().get(i).getOption());
            radioButton.setId(i);
            rprms= new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.rgAnswer.addView(radioButton, rprms);
        }*/


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvQuestion;
        public RadioGroup rgAnswer;
        public LinearLayout lnrRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.Question_txt);
            rgAnswer = (RadioGroup) itemView.findViewById(R.id.rg_answers);
            lnrRoot= (LinearLayout) itemView.findViewById(R.id.rg_answers);


          /*  lnrRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), AppConstants.TYPE_CLCIK_REMARKS);
                }
            });*/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return arrayDatum.get(id).getId();
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}


