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
import com.amana.MpestISS.model.quiz.Datum;
import com.amana.MpestISS.model.quiz.QuizDetail;
import com.amana.MpestISS.utils.AppPreferences;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<QuizDetail> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;
    QuizAdapter(Context context, ArrayList<QuizDetail> arrayDatum) {
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
        final QuizDetail datum = arrayDatum.get(position);


        ArrayList<String> mQuesList = new ArrayList<>();
        holder.tvQuestion.setText(datum.getQuestion().toString());

        mQuesList.add(datum.getOptionA().toString());
        mQuesList.add(datum.getOptionB().toString());
        mQuesList.add(datum.getOptionC().toString());
        mQuesList.add(datum.getOptionD().toString());
        mQuesList.add(datum.getOptionE().toString());


        RadioGroup.LayoutParams rprms;

        for(int i=0;i<mQuesList.size();i++){
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(mQuesList.get(i).toString());
            radioButton.setId(i);
            rprms= new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.rgAnswer.addView(radioButton, rprms);
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;
        public RadioGroup rgAnswer;
        public LinearLayout lnrRoot;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.Question_txt);
            rgAnswer = itemView.findViewById(R.id.rg_answers);
            lnrRoot= itemView.findViewById(R.id.rg_answers);

            rgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedId=rgAnswer.getCheckedRadioButtonId();
                     RadioButton radioSexButton= itemView.findViewById(selectedId);
                    if (mClickListener != null) mClickListener.onItemClick(radioSexButton.getText().toString(),checkedId, getAdapterPosition());
                }
            });

          /*  lnrRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), AppConstants.TYPE_CLCIK_REMARKS);
                }
            });*/
           // itemView.setOnClickListener(this);
        }

      /*  @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }*/
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
        void onItemClick(String view, int SelectedPos, int position);
    }



}


