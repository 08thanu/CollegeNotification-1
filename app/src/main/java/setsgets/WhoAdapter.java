package setsgets;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.collegenoti.R;
import com.example.admin.collegenoti.logic.P;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WhoAdapter extends RecyclerView.Adapter<WhoAdapter.MyViewHolder> {
    private List<Who> who = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sem, type;
        public ImageView img;

        public LinearLayout row_details;


        public MyViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
            sem = (TextView) view.findViewById(R.id.sem);
            type = (TextView) view.findViewById(R.id.type);

            row_details = (LinearLayout) view.findViewById(R.id.who);

        }
    }

    public WhoAdapter(Context context, List<Who> chats) {
        this.who = chats;
        this.context = context;
    }

    @Override
    public WhoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.who_row, parent, false);

        return new WhoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Who Who = who.get(position);


        holder.sem.setText("Sem : "+Who.getM_sem());
        holder.type.setText(Who.getM_util_type());
        Glide.with(context).load(P.IMAGE_URL + Who.getM_util_image()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return who.size();
    }
}



