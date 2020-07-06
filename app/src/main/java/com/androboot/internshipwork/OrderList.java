package com.androboot.internshipwork;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androboot.internshipwork.Objects.Order;
import com.androboot.internshipwork.adaptors.OrderListAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderList extends Fragment implements OrderListAdaptor.OnOrderClickListner {


    private RecyclerView recyclerView;
    private ArrayList<Order> ordersArrayList;
    private OrderListAdaptor adaptor;
    private Order order;
    private OrderListAdaptor.OnOrderClickListner mOrderClickListner;


    public OrderList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        mOrderClickListner = this;
        fetchOrders(new FirebaseCallback() {
            @Override
            public void Callback(Order ordersList) {
                ordersArrayList.add(ordersList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adaptor = new OrderListAdaptor(getContext(), ordersArrayList, mOrderClickListner);
                recyclerView.getRecycledViewPool().clear();
                recyclerView.setAdapter(adaptor);
            }
        });
    }

    private void fetchOrders(final FirebaseCallback firebaseCallback) {
        ordersArrayList = new ArrayList<>();
        order = new Order();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    order = dataSnapshot1.getValue(Order.class);
                    order.setOrderId(dataSnapshot1.getKey());
                    firebaseCallback.Callback(order);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onTaskClick(int position) {
        String orderID = ordersArrayList.get(position).getOrderId();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderID);
        NavHostFragment.findNavController(this).navigate(R.id.action_orderList_to_orderDetails, bundle);
    }

    private interface FirebaseCallback{
        void Callback(Order ordersList);
    }
}
