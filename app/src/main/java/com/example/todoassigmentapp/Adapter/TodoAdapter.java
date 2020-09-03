package com.example.todoassigmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoassigmentapp.Model.TodoModel;
import com.example.todoassigmentapp.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.CustomViewHolder> {
    List<TodoModel> todoModelList;
    Context context;

    public TodoAdapter(List<TodoModel> todoModels,Context ctx)
    {
        this.todoModelList=todoModels;
        this.context=ctx;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customtodorow,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
       final TodoModel tm=todoModelList.get(position);
        if(tm!=null)
        {
            // Limit to 20 charaters pending
            holder.txttitle.setText(tm.getId()+ " - "+ tm.getTitle() + "...");
            holder.txtdetails.setText( tm.getTitle());
            boolean isEnabled=tm.getExpanded();
            holder.cns_expandeddetails.setVisibility(isEnabled?View.VISIBLE:View.GONE);

            holder.cns_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tm.setExpanded(!tm.getExpanded());
                    notifyItemChanged(position);
                }
            });

            if(tm.getCompleted()==true)
            {
                holder.img_complete.setImageResource(R.drawable.correct2);
            }

        }

    }

    @Override
    public int getItemCount() {
        return todoModelList.size();
    }

    public class CustomViewHolder  extends  RecyclerView.ViewHolder{
        TextView txttitle,txtdetails;
        ConstraintLayout cns_layout,cns_expandeddetails;
        ImageView img_complete,img_arrow;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txt_title);
            txtdetails = itemView.findViewById(R.id.txtdetails);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            img_complete = itemView.findViewById(R.id.img_complete);
            cns_layout = itemView.findViewById(R.id.cns_layout);
            cns_expandeddetails = itemView.findViewById(R.id.cns_detailsExpanded);

        }
    }
}
