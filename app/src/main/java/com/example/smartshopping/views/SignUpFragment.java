package com.example.smartshopping.views;

import android.app.DatePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.android.play.core.splitinstall.c;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpFragment extends Fragment {

    private EditText emailEditText;
    private EditText pwdEditText;
    private TextView signUpText;
    private EditText pwdEditText2;
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
                if (firebaseUser != null) {
                    Toast.makeText(getContext(), "User created", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_signInFragment2);
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailEditText = view.findViewById(R.id.signUpEmailInput);
        pwdEditText = view.findViewById(R.id.signUpPwdInput);
        pwdEditText2 = view.findViewById(R.id.signUpPwdInput2);
        signUpText = view.findViewById(R.id.signUpBtt);
        birthdayText = view.findViewById(R.id.signUpBirthdayInput);
        backBtt = view.findViewById(R.id.backBtt);

        RelativeLayout signUpRootLayout = view.findViewById(R.id.signUpRootLayout);
        signUpRootLayout.setBackgroundResource(R.drawable.gradient_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) signUpRootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        // date picker
        Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                try {
                    Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year+"-"+(month+1)+"-"+dayOfMonth);
                    updateLabel(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);

                datePickerDialog.show();
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = pwdEditText.getText().toString();
                String password2 = pwdEditText2.getText().toString();
                if (!signUpViewModel.isEmail(emailEditText)) {
                    emailEditText.setError("Enter valid Email");
                }
                if (password.length() == 0) {
                    pwdEditText.setError("Enter password");
                }
                if (!password.equals(password2)) {
                    pwdEditText2.setError("Check password");
                }
                if (signUpViewModel.isEmail(emailEditText) && password.length() > 0
                        && password.equals(password2)) {
                    signUpViewModel.signUp(email, password);
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

    private void updateLabel(Date d) {
        birthdayText.setText(d.toString());
    }
}
