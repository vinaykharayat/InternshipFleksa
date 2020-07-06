package com.androboot.internshipwork.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androboot.internshipwork.Objects.Order;
import com.androboot.internshipwork.R;

import java.util.ArrayList;

public class OrderListAdaptor extends RecyclerView.Adapter<OrderListAdaptor.myViewHolder>{
    private Context context;
    private ArrayList<Order> orderList;
    private String orderId;
    private OnOrderClickListner mOrderClickListner;

    public OrderListAdaptor(Context context, ArrayList<Order> orderList, OnOrderClickListner mOrderClickListner) {
        this.context = context;
        this.orderList = orderList;
        this.mOrderClickListner = mOrderClickListner;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item, parent,false);
        return new myViewHolder(view, mOrderClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.orderAddress.setText(orderList.get(position).getOrderAddress());
        holder.orderStatus.setText(orderList.get(position).getOrderStatus());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView orderAddress, orderStatus;
        OnOrderClickListner onOrderClickListner;

        myViewHolder(@NonNull View itemView, OnOrderClickListner mOrderClickListner) {
            super(itemView);
            orderAddress = itemView.findViewById(R.id.textViewAddress);
            orderStatus = itemView.findViewById(R.id.textViewOrderStatus);
            this.onOrderClickListner = mOrderClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onOrderClickListner.onTaskClick(getAdapterPosition());
        }
    }

    public interface OnOrderClickListner{
        void onTaskClick(int position);
    }
}
