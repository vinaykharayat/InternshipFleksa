package com.androboot.internshipwork;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.androboot.internshipwork.Objects.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetails extends Fragment {
    private String orderId;
    private TextView orderDate, customerName, customerAddress, customerPhone, orderStatus;

    public OrderDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderId = getArguments().getString("orderId");
        orderDate = view.findViewById(R.id.textViewOrderTime);
        customerName = view.findViewById(R.id.textViewCustomerName);
        customerAddress = view.findViewById(R.id.textViewCustomerAddress);
        customerPhone = view.findViewById(R.id.textViewCustomerPhone);
        orderStatus = view.findViewById(R.id.textViewOrderStatus);
        final Button accept = view.findViewById(R.id.buttonAccept);
        final Button reject = view.findViewById(R.id.buttonReject);

        fetchOrderDetails(new FirebaseCallback() {
            @Override
            public void Callback(final Order order) {
                orderDate.setText(order.getOrderTime());
                customerName.setText(order.getCustomerName());
                customerAddress.setText(order.getCustomerAddress());
                customerPhone.setText(order.getPhoneNumber());
                orderStatus.setText(order.getOrderStatus());
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("Orders").child(orderId).child("orderStatus").setValue("Accepted")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getContext(), AdminActivity.class));
                                getActivity().finishAffinity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), AdminActivity.class));
                                getActivity().finishAffinity();
                            }
                        });
                    }
                });

                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("Orders").child(orderId).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getContext(), AdminActivity.class));
                                getActivity().finishAffinity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), AdminActivity.class));
                                getActivity().finishAffinity();
                            }
                        });
                    }
                });
            }
        });
    }

    private void fetchOrderDetails(final FirebaseCallback firebaseCallback) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        dbRef.child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order order = new Order();
                order = snapshot.getValue(Order.class);
                firebaseCallback.Callback(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private interface FirebaseCallback{
        void Callback(Order order);
    }
}
