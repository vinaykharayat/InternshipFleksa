package com.androboot.internshipwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androboot.internshipwork.Objects.MenuItem;
import com.androboot.internshipwork.Objects.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddOrder extends AppCompatActivity {
    private String customerName, customerAddress, customerPhoneNumber, orderTime, orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Button addItem = findViewById(R.id.buttonAddMenuItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                EditText etCustomerName = findViewById(R.id.editTextCustomerName);
                customerName = etCustomerName.getText().toString();
                EditText etCustomerPhoneNumber = findViewById(R.id.editTextPhoneNumber);
                customerPhoneNumber = etCustomerPhoneNumber.getText().toString();
                EditText etCustomerAddress = findViewById(R.id.editTextAddress);
                customerAddress = etCustomerAddress.getText().toString();
                order.setCustomerName(customerName);
                order.setOrderAddress(customerAddress);
                order.setPhoneNumber(customerPhoneNumber);
                order.setOrderStatus(getString(R.string.order_pending));
                //Getting current date and time
                String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Frinday", "Saturday"};
                Calendar calendar = Calendar.getInstance();
                String weekday = days[calendar.get(Calendar.DAY_OF_WEEK)-1];
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                String orderDate = date + " " + weekday + ", " + hour+ ":" + minute;
                order.setOrderTime(orderDate);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                dbRef.push().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddOrder.this, "Order Added Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddOrder.this, AdminActivity.class));
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddOrder.this, "Failed:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
