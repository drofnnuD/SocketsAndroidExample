package com.example.mattdunn.anothersocketproject.ticketlistfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mattdunn.anothersocketproject.R;
import com.example.mattdunn.anothersocketproject.models.TicketModel;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder> {

    private List<TicketModel> ticketList;
    private Context context;
    private TicketListAdapterListener listener;

    TicketListAdapter(List<TicketModel> ticketList, Context context,
                      TicketListAdapterListener listener) {
        this.ticketList = ticketList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item, viewGroup,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.txt_barcode.setText(ticketList.get(i).getBarcode());
        holder.txt_ticket_name.setText(ticketList.get(i).getTicketName());
        holder.btn_redeem_unredeem.setText(ticketList.get(i).getRedeemed() ? "Unredeem" : "Redeem");

        holder.btn_redeem_unredeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticketList.get(holder.getAdapterPosition()).getRedeemed()){
                    listener.unRedeemTicket(ticketList.get(holder.getAdapterPosition()));
                    holder.btn_redeem_unredeem.setText(R.string.redeem);
                    ticketList.get(holder.getAdapterPosition()).setRedeemed(false);
                } else {
                    listener.redeemTicket(ticketList.get(holder.getAdapterPosition()));
                    holder.btn_redeem_unredeem.setText(R.string.un_redeem);
                    ticketList.get(holder.getAdapterPosition()).setRedeemed(true);
                }
            }
        });
    }

    void setTicketToRedeemed(TicketModel ticketModel){
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketModel.getBarcode().equals(ticketList.get(i).getBarcode())){
                ticketList.get(i).setRedeemed(true);
                break;
            }
        }
        notifyDataSetChanged();
    }

    void setTicketToUnRedeemed(TicketModel ticketModel){
        for(int i = 0; i < ticketList.size(); i++){
            if(ticketModel.getBarcode().equals(ticketList.get(i).getBarcode())){
                ticketList.get(i).setRedeemed(false);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_barcode, txt_ticket_name;
        private Button btn_redeem_unredeem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_barcode = itemView.findViewById(R.id.txt_barcode);
            txt_ticket_name = itemView.findViewById(R.id.txt_ticket_name);
            btn_redeem_unredeem = itemView.findViewById(R.id.btn_redeem_unredeem);
        }
    }

}
