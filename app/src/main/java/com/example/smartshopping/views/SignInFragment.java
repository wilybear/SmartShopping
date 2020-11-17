package com.example.smartshopping.views;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.smartshopping.R;
import com.example.smartshopping.viewmodel.SignInViewModel;
import com.example.smartshopping.viewmodel.SignUpViewModel;
import com.github.nikartm.button.FitButton;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private EditText emailEditText;
    private EditText pwdEditText;
    private FitButton signInText;
    private SignInViewModel signInViewModel;
    private FitButton signUpText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        signInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_itemListFragment);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in,container,false);
        emailEditText = view.findViewById(R.id.emailInput);
        pwdEditText = view.findViewById(R.id.pwdInput);
        signInText = view.findViewById(R.id.signInBtt);
        signUpText = view.findViewById(R.id.signUpBtt);
        RelativeLayout signInRootLayout = view.findViewById(R.id.signInRootLayout);
        signInRootLayout.setBackgroundResource(R.drawable.gradient_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) signInRootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        signInText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = pwdEditText.getText().toString();
                if(email.length()>0 && password.length()>0) {
                    signInViewModel.signIn(email,password);
                }
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_signUpFragment2);
            }
        });

        return view;
    }
}
