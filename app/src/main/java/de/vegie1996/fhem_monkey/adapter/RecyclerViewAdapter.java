package de.vegie1996.fhem_monkey.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.vegie1996.fhem_monkey.R;
import de.vegie1996.fhem_monkey.database.tables.LevelsTable;

/**
 * Created by lucienkerl on 22/01/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    List<LevelsTable.LevelsEntry> deviceList;

    public RecyclerViewAdapter(List<LevelsTable.LevelsEntry> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_card_view, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.title.setText(deviceList.get(position).getName());
        holder.tv1.setText(deviceList.get(position).getReadingTopLeft());
        holder.tv2.setText(deviceList.get(position).getReadingTopCenter());
        holder.tv3.setText(deviceList.get(position).getReadingTopRight());
        holder.tv4.setText(deviceList.get(position).getReadingBottomLeft());
        holder.tv5.setText(deviceList.get(position).getReadingBottomCenter());
        holder.tv6.setText(deviceList.get(position).getReadingBottomRight());
        //holder.icon.setImageResource;
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public interface MyViewHolderInterface {
        void onClick(int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        ImageView icon;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            title = (TextView) itemView.findViewById(R.id.textView_title);
            tv1 = (TextView) itemView.findViewById(R.id.textView1);
            tv2 = (TextView) itemView.findViewById(R.id.textView2);
            tv3 = (TextView) itemView.findViewById(R.id.textView3);
            tv4 = (TextView) itemView.findViewById(R.id.textView4);
            tv5 = (TextView) itemView.findViewById(R.id.textView5);
            tv6 = (TextView) itemView.findViewById(R.id.textView6);
            icon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
