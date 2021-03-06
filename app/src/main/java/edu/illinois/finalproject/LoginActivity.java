package edu.illinois.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

//https://firebase.google.com/docs/auth/android/password-auth
public class LoginActivity extends AppCompatActivity {

    public static final String SIGN_UP = "Sign Up";
    public static final String SIGN_IN = "Sign in";
    public static final String LOADING = "Please wait...";
    public static final String EMPTY_STRING = "";
    Button emailButton;
    Button signInButton;
    Button signUpButton;
    Button anonymousButton;
    Button backButton;
    EditText emailEditText;
    EditText passwordEditText;
    TextView title;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        title = (TextView) findViewById(R.id.title);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        emailButton = (Button) findViewById(R.id.emailButton);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        anonymousButton = (Button) findViewById(R.id.anonymousButton);
        backButton = (Button) findViewById(R.id.backButton);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        // set the default user interface
        defaultUI();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailButton.setText(SIGN_IN);
                updateUI();
                emailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = emailEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        signIn(email, password);
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailButton.setText(SIGN_UP);
                updateUI();
                emailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        registerUser();
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultUI();
            }
        });

        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(
                        "edu.illinois.finalproject.MainActivity");
                startActivity(mainActivity);
            }
        });
    }

    /**
     * Default User Interface
     */
    private void defaultUI() {
        emailEditText.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);
        emailButton.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);
        anonymousButton.setVisibility(View.VISIBLE);
    }

    /**
     * Register the user to the Firebase database
     */
    private void registerUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(LoginActivity.this, "Registration success!",
                            Toast.LENGTH_SHORT).show();
                    defaultUI();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }});
    }

    /**
     * Sign in to the application
     * @param email user email
     * @param password user password
     */
    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START sign_in_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, move to main activity
                            Intent mainActivity = new Intent(
                                    "edu.illinois.finalproject.MainActivity");
                            startActivity(mainActivity);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication" +
                                    " failed.", Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }
                });
    }

    /**
     * To check if the input in the email, password, and name field are valid
     * @return valid or invalid input
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    /**
     * Showing progress dialog
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.setMessage(LOADING);
        progressDialog.show();
    }

    /**
     * Hide the progress dialog
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Update the UI
     */
    public void updateUI() {
        emailEditText.setVisibility(View.VISIBLE);
        passwordEditText.setVisibility(View.VISIBLE);
        emailButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
        anonymousButton.setVisibility(View.GONE);
        emailEditText.setText(EMPTY_STRING);
        passwordEditText.setText(EMPTY_STRING);
    }
}
