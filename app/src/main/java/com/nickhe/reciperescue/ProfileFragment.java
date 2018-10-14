package com.nickhe.reciperescue;

import android.Manifest;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.nio.channels.spi.AbstractSelectionKey;

public class ProfileFragment extends Fragment implements TextWatcher {

    private User user;
    private View view;
    private ImageView profileImageView, addNewRecipeIncon;
    private EditText nameEditView, descriptionEditView;
    private ListView listView;
    private RecipeRepository recipeRepository;
    public final int READ_IMAGE_PERMISSION = 0;
    public final int WRITE_IMAGE_PERMISSION = 2;
    public final int PICK_IMAGE_RESULT = 1;
    private FirebaseAuth firebaseAuth;
    private TextWatcher textWatcher;

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
        updateView();

        setProfileImageViewListener();
        setListViewClickListener();
        setAddNewRecipeInconClickListener();
        nameEditView.addTextChangedListener(this);
        descriptionEditView.addTextChangedListener(this);

    }

    /**
     * Set nameEditText textChanged listener
     */
    private void setNameEditViewOnClickListener() {
        nameEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //updateUserName(s.toString());
            }
        });
    }

    /**
     * Set descriptionEditText textChanged listener
     */
    private void setDescriptionEditViewOnClickListener() {
        descriptionEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //updateUserDescription(s.toString());
            }
        });
    }


    /**
     * To all users to be able to submit a new recipe
     */
    private void setAddNewRecipeInconClickListener() {
        addNewRecipeIncon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateRecipeActivity.class));
            }
        });
    }

    /**
     * To allow users to be able to open a recipe and review that
     */
    private void setListViewClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe recipe = recipeRepository.getRecipeRepo().get(position);
                sendDataToRecipeViewActivity(recipe);
            }
        });
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
     * Initialize views and other fields
     */
    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = UserDataManager.getUser();
        profileImageView = view.findViewById(R.id.profileImageView);
        addNewRecipeIncon = view.findViewById(R.id.addNewRecipeIcon);
        nameEditView = view.findViewById(R.id.nameEditText);
        descriptionEditView = view.findViewById(R.id.descriptionEditText);
        listView = view.findViewById(R.id.profile_recipeList);
        recipeRepository = RecipeRepository.getRecipeRepository();
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getActivity(), recipeRepository.getRecipeRepo());
        listView.setAdapter(recipeListAdapter);
        ListViewProcessor.setListViewHeightBasedOnChildren(listView);
    }

    /**
     * Update view when userInfo retrieved
     */
    private void updateView() {
        Uri uri = Uri.parse(user.getProfileImage());
        Bitmap bitmap = ImageProcessor.convertUriToBitmap(getActivity(), uri);
        String userName = user.getName();
        String description = user.getDescription();

        profileImageView.setImageBitmap(bitmap);
        nameEditView.setText(userName);
        descriptionEditView.setText(description);
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
        user.setProfileImage(path);
        UserDataManager.setUser(user);
        UserDataManager.updateUserToFirebase(firebaseAuth);

    }

    /**
     * Update user name to firebase
     */
    private void updateUserName(String name) {
        user.setName(name);
        UserDataManager.setUser(user);
        UserDataManager.updateUserToFirebase(firebaseAuth);
    }

    /**
     * Update user description to firebase
     */
    private void updateUserDescription(String description) {
        user.setDescription(description);
        UserDataManager.setUser(user);
        UserDataManager.updateUserToFirebase(firebaseAuth);
    }

    /**
     * Start RecipeViewActivity when recipe clicked
     *
     * @param recipe
     */
    private void sendDataToRecipeViewActivity(Recipe recipe) {
        Intent i = new Intent(getActivity().getBaseContext(), RecipeViewActivity.class);
        i.putExtra("recipe", recipe);
        startActivity(i);
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
