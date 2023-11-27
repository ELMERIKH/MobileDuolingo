package com.example.quizappduolingo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class LeaderboardAdapter  extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private List<UserClass> users;

    public LeaderboardAdapter(List<UserClass> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserClass user = users.get(position);
        holder.usernameTextView.setText(user.getUsername());
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedFloat = df.format(user.getRank());
        int Position=position+1;
        holder.rankTextView.setText("Score : "+formattedFloat);
        holder.ranking.setText("# "+Position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView rankTextView;
        public TextView ranking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            ranking = itemView.findViewById(R.id.rank);
        }
    }
}

