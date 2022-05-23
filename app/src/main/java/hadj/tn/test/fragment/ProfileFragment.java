package hadj.tn.test.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hadj.tn.test.Model.User;
import hadj.tn.test.R;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    ImageView imageViewCam,imageProfile;

    private Bitmap bitmap;
    private String path;

    String email = getArguments().getString("User");
    RetrofitClient retrofitClient = new RetrofitClient();
    API userApi = retrofitClient.getRetrofit().create(API.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){

            Call<User> call = userApi.getUser(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User user = response.body();

                    assert user != null;
                    String personName = user.getUsername();
                    String personEmail = user.getEmail();

                    TextView textViewUsername = view.findViewById(R.id.usernameProfile);
                    TextView textViewEmail = view.findViewById(R.id.emailProfile);
                    textViewUsername.setText(personName);
                    textViewEmail.setText(personEmail);

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Log error here since request failed
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = data.getData();
        imageProfile.setImageURI(selectedImageUri);
    }

}