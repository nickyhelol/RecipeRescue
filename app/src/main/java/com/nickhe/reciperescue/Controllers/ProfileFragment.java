package com.nickhe.reciperescue.Controllers;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nickhe.reciperescue.Models.ImageProcessor;
import com.nickhe.reciperescue.R;
import com.nickhe.reciperescue.Models.RecipeRepository;
import com.nickhe.reciperescue.Models.UserDataManager;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment implements TextWatcher {

    private View view;
    private ImageView profileImageView, menuImageView;
    private ImageView sideImageView, dinnerImageView, drinkImageView, dessertImageView, breakfastImageView;
    private EditText nameEditView, descriptionEditView;
    private TextView breakfastTextView, sidesTextView, dinnerTextView, drinkTextView, dessertTextView;
    private RecipeRepository recipeRepository;

    public final int READ_IMAGE_PERMISSION = 0;
    public final int WRITE_IMAGE_PERMISSION = 2;
    public final int PICK_IMAGE_RESULT = 1;
    private FirebaseAuth firebaseAuth;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        askReadExternalStoragePermission();
        initialize();
        updateUserProfileView();
        updateCollectionViews();
        setMenuImageViewOnClickListener();
        setProfileImageViewListener();
        nameEditView.addTextChangedListener(this);
        descriptionEditView.addTextChangedListener(this);
        setBreakfastImageViewOnClickListener();
        setSideImageViewOnClickListener();
        setDinnerImageViewOnClickListener();
        setDrinkImageViewOnClickListener();
        setDessertImageViewOnClickListener();
    }

    /**
     * Initialize views and other fields
     */
    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        profileImageView = view.findViewById(R.id.profileImageView);
        nameEditView = view.findViewById(R.id.nameEditText);
        descriptionEditView = view.findViewById(R.id.descriptionEditText);
        recipeRepository = RecipeRepository.getRecipeRepository();
        menuImageView = view.findViewById(R.id.menuImageView);
        sideImageView = view.findViewById(R.id.sideImageView);
        breakfastImageView = view.findViewById(R.id.breakfastImageView);
        dinnerImageView = view.findViewById(R.id.dinnerImageView);
        drinkImageView = view.findViewById(R.id.drinkImageView);
        dessertImageView = view.findViewById(R.id.dessertImageView);
        breakfastTextView = view.findViewById(R.id.breakfastTextView);
        sidesTextView = view.findViewById(R.id.sideTextView);
        dinnerTextView = view.findViewById(R.id.dinnerTextView);
        drinkTextView = view.findViewById(R.id.drinkTextView);
        dessertTextView = view.findViewById(R.id.dessertTextView);
    }

    /**
     * Set OnClickListener for menuImageView to allow user to log out
     */
    private void setMenuImageViewOnClickListener(){
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    /**
     * This method will log out the user from the firebase and finish the current activity which is main menu activity and
     * goes back to the main login activity once log out menu is clicked.
     */
    private void logout() {
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), MainLoginActivity.class));
    }

    /**
     * Set onClick listener for breakfastImageView
     */
    private void setBreakfastImageViewOnClickListener(){
        breakfastImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startCollectionActivity(1);
            }
        });
    }

    /**
     * Set onClick listener for sideImageView
     */
    private void setSideImageViewOnClickListener(){
        sideImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionActivity(2);
            }
        });
    }

    /**
     * Set onClick listener for dinnerImageView
     */
    private void setDinnerImageViewOnClickListener(){
        dinnerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionActivity(3);
            }
        });
    }

    /**
     * Set onClick listener for drinkImageView
     */
    private void setDrinkImageViewOnClickListener(){
        drinkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionActivity(4);
            }
        });
    }

    /**
     * Set onClick listener for dessertImageView
     */
    private void setDessertImageViewOnClickListener(){
        dessertImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCollectionActivity(5);
            }
        });
    }

    /**
     * Start CollectionActivity
     * @param index
     */
    private void startCollectionActivity(int index){
        Intent i = new Intent(getActivity(), CollectionActivity.class);
        i.putExtra("collection", index);
        startActivity(i);
    }

    /**
     * Update the collection views
     */
    private void updateCollectionViews(){
        if(UserDataManager.getUser().getBreakfastsRepo() != null){
            breakfastTextView.setText(String.valueOf(UserDataManager.getUser().getBreakfastsRepo().size()));
        }

        if(UserDataManager.getUser().getSidesRepo() != null){
            sidesTextView.setText(String.valueOf(UserDataManager.getUser().getSidesRepo().size()));
        }

        if(UserDataManager.getUser().getDinnersRepo() != null){
            dinnerTextView.setText(String.valueOf(UserDataManager.getUser().getDinnersRepo().size()));
        }

        if(UserDataManager.getUser().getDrinksRepo() != null){
            drinkTextView.setText(String.valueOf(UserDataManager.getUser().getDrinksRepo().size()));
        }

        if(UserDataManager.getUser().getDessertsRepo() != null){
            dessertTextView.setText(String.valueOf(UserDataManager.getUser().getDessertsRepo().size()));
        }
    }

    /**
     * Set clickListener to allow users to select image from their phone as the profile image
     */
    private void setProfileImageViewListener() {
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askWriteExternalStoragePermission();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Ask READ_IMAGE_PERMISSION if does not have permission
     */
    private void askReadExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_IMAGE_PERMISSION);
        }
    }

    private void askWriteExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_IMAGE_PERMISSION);
            }
        }
    }

    /**
     * Update view when userInfo retrieved
     */
    private void updateUserProfileView() {

        if(UserDataManager.getUser()!=null){
            Uri uri = Uri.parse(UserDataManager.getUser().getProfileImage());
            Bitmap bitmap = ImageProcessor.convertUriToBitmap(getActivity(), uri);
            profileImageView.setImageBitmap(bitmap);
            String userName = UserDataManager.getUser().getName();
            String description = UserDataManager.getUser().getDescription();

            nameEditView.setText(userName);
            descriptionEditView.setText(description);
        }


    }

    /**
     * The result action after requesting permissions
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case READ_IMAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_IMAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * The activity when selected an image from gallery
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PICK_IMAGE_RESULT:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImg = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver()
                            .query(selectedImg, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    System.out.println("Bitmap: " + bitmap);
                    getImageUri(bitmap);
                    profileImageView.setImageBitmap(bitmap);
                }
        }
    }

    /**
     * Get the path string of the selected image
     *
     * @param inImage
     * @return
     */
    private void getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        System.out.println("Path: " + path);
        updateUserImage(path);
    }

    /**
     * Update userImage data on firebase
     *
     * @param path
     */
    private void updateUserImage(String path) {
        UserDataManager.getUser().setProfileImage(path);
        UserDataManager.setUser(UserDataManager.getUser());
        UserDataManager.updateUserToFirebase(firebaseAuth);

    }

    /**
     * Update user name to firebase
     */
    private void updateUserName(String name) {
        UserDataManager.getUser().setName(name);
        UserDataManager.setUser(UserDataManager.getUser());
        UserDataManager.updateUserToFirebase(firebaseAuth);
    }

    /**
     * Update user description to firebase
     */
    private void updateUserDescription(String description) {
        UserDataManager.getUser().setDescription(description);
        UserDataManager.setUser(UserDataManager.getUser());
        UserDataManager.updateUserToFirebase(firebaseAuth);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null && s.hashCode() != "".hashCode()) {
            if (s.hashCode() == nameEditView.getEditableText().hashCode()) {
                updateUserName(s.toString());
            } else if (s.hashCode() == descriptionEditView.getEditableText().hashCode()) {
                updateUserDescription(s.toString());
            }
        }
    }

}
