package com.androboot.internshipwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser==null){
            setContentView(R.layout.activity_authentication);
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            NavInflater inflater = navHostFragment.getNavController().getNavInflater();
            NavGraph graph = inflater.inflate(R.navigation.nav_auth);
            graph.setStartDestination(R.id.register);
        }else{
            startActivity(new Intent(this, AdminActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
