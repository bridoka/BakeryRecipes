package com.emanuellerizzuto.baking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emanuellerizzuto.baking.data.RecipeStepParcelable;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>
{
    private List<RecipeStepParcelable> steps;

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(RecipeStepParcelable stepParcelable);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView tv_step;
        public final TextView tv_id_step;
        public final MaterialCardView card_step;

        public StepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_step = itemView.findViewById(R.id.tv_step);
            tv_id_step = itemView.findViewById(R.id.tv_id_step);
            card_step = itemView.findViewById(R.id.card_step);
        }
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mCallback = (StepsAdapter.OnStepClickListener) context;
        int layoutIdItem = R.layout.row_step;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdItem, parent, false);
        return new StepsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
        RecipeStepParcelable step = steps.get(position);
        String shortDescription = step.getShortDescription();
        String idStep = String.valueOf(step.getId());
        holder.tv_step.setText(shortDescription);
        holder.tv_id_step.setText(idStep);
        holder.card_step.setTag(steps.get(position));
        holder.card_step.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepSelected((RecipeStepParcelable) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

    public void setSteps(List<RecipeStepParcelable> stepsList) {
        steps = stepsList;
        notifyDataSetChanged();
    }
}
