package com.androboot.internshipwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        final EditText email = view.findViewById(R.id.editTextEmail);
        final EditText password = view.findViewById(R.id.editTextPassword);
        Button login = view.findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDetails(email,password)) {
                    logInUser(email, password);
                }
            }
        });
        Button register = view.findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterScreen();
            }
        });
    }

    static boolean checkDetails(EditText email, EditText password){
        if(email.getText().toString().isEmpty()){
            email.setError("Enter email");
            return false;
        }else if(password.getText().toString().isEmpty()){
            password.setError("Enter password");
            return false;
        }else return true;
    }

    private void forgotPassword() {
        NavHostFragment.findNavController(this).navigate(R.id.action_login_to_forgotPassword);
    }

    private void openRegisterScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.action_login_to_register);
    }

    private void logInUser(EditText email, EditText password) {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        upDateUI();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void upDateUI() {
        startActivity(new Intent(getContext(), AdminActivity.class));
        getActivity().finishAffinity();
    }
}
