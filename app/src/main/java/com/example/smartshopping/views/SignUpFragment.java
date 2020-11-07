package com.example.smartshopping.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.smartshopping.R;
import com.example.smartshopping.viewmodel.SignUpViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private EditText emailEditText;
    private EditText pwdEditText;
    private TextView signUpText;
    private EditText birthdayText;
    private Button backBtt;
    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        signUpViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    Toast.makeText(getContext(),"User created", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_signInFragment2);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up,container,false);
        emailEditText = view.findViewById(R.id.signUpEmailInput);
        pwdEditText = view.findViewById(R.id.signUpPwdInput);
        signUpText = view.findViewById(R.id.signUpBtt);
        birthdayText = view.findViewById(R.id.signUpBirthdayInput);
        backBtt = view.findViewById(R.id.backBtt);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = pwdEditText.getText().toString();
                if(email.length()>0 && password.length()>0) {
                    signUpViewModel.signUp(email,password);
                }
            }
        });

        backBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_signInFragment2);
            }
        });

        return view;
    }
}
