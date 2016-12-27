package online.upcare.f4hupcaregroup.bestshopping.live;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import online.upcare.f4hupcaregroup.bestshopping.infrastructure.BestShoppingApplication;
import online.upcare.f4hupcaregroup.bestshopping.infrastructure.Utils;
import online.upcare.f4hupcaregroup.bestshopping.services.AccountServices;

public class LiveAccountService extends BaseLiveService {
    public LiveAccountService(BestShoppingApplication application) {
        super(application);
    }

    @Subscribe
    public void RegisterUser(final AccountServices.RegisterUserRequest request){
        AccountServices.RegisterUserResponse response = new AccountServices.RegisterUserResponse();

        if(request.userEmail.isEmpty()){
            response.setPropertyErrors("email", "Please put in your e-mail");
        }
        if(request.userName.isEmpty()){
            response.setPropertyErrors("userName", "Please put in your name");
        }
        if(response.didSucceed()){
            request.progressDialog.show();

            SecureRandom random = new SecureRandom();
            final String randomPassword = new BigInteger(32, random).toString();

            auth.createUserWithEmailAndPassword(request.userEmail, randomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                              auth.sendPasswordResetEmail(request.userEmail)
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if(!task.isSuccessful()){
                                                  request.progressDialog.dismiss();
                                                  Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                              } else {
                                                  Firebase reference = new Firebase(Utils.FIREBASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                                  HashMap<String, Object> timeJoined = new HashMap<>();
                                                  timeJoined.put("dateJoined", ServerValue.TIMESTAMP);
                                                  reference.child("email").setValue(request.userEmail);
                                                  reference.child("name").setValue(request.userName);
                                                  reference.child("hasLoggedInWithPassword").setValue(false);
                                                  reference.child("timeJoined").setValue(timeJoined);

                                                  Toast.makeText(application.getApplicationContext(), "Please check your email", Toast.LENGTH_LONG).show();
                                                  request.progressDialog.dismiss();
                                              }
                                          }
                                      });
                            }
                        }
                    });




        }

        bus.post(response);
    }

    @Subscribe
    public void LogInUser(final AccountServices.LogUserInRequest request){
        AccountServices.LogUserInResponse response = new AccountServices.LogUserInResponse();

        if(request.userEmail.isEmpty()){
            response.setPropertyErrors("email", "Please enter your email");
        }

        if(request.userPassword.isEmpty()){
            response.setPropertyErrors("password", "Please enter your password");
        }

        if(response.didSucceed()) {
            request.progressDialog.show();
            auth.signInWithEmailAndPassword(request.userEmail, request.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){

                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Firebase userLocation = new Firebase(Utils.FIREBASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                userLocation.child("hasLoggedInWithPassword").setValue(true);
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), "User has logged in", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        bus.post(response);
    }

    @Subscribe
    public void FacebookLogin(final AccountServices.LogUserInFacebookRequest request){
        request.progressDialog.show();

        AuthCredential authCredential = FacebookAuthProvider.getCredential(request.accessToken.getToken());

        auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    request.progressDialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    final Firebase reference = new Firebase(Utils.FIREBASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null){
                                HashMap<String, Object> timeJoined = new HashMap<>();
                                timeJoined.put("dateJoined", ServerValue.TIMESTAMP);
                                reference.child("email").setValue(request.userEmail);
                                reference.child("name").setValue(request.userName);
                                reference.child("hasLoggedInWithPassword").setValue(true);
                                reference.child("timeJoined").setValue(timeJoined);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            request.progressDialog.dismiss();
                            Toast.makeText(application.getApplicationContext(), firebaseError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                    request.progressDialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "User has logged in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
