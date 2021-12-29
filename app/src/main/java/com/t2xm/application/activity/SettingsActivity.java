package com.t2xm.application.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.t2xm.R;
import com.t2xm.dao.FavouriteItemDao;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.User;
import com.t2xm.utils.PermissionUtil;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.values.RequestCode;
import com.t2xm.utils.valuesConverter.ImageUtil;

public class SettingsActivity extends AppCompatActivity {

    private Activity activity;
    private ImageView iv_profileImage;

    private User user;
    private String username;

    private DialogInterface.OnClickListener logoutOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SharedPreferenceUtil.removeUsername(activity);
            startActivity(new Intent(activity, LoginActivity.class));
            finish();
        }
    };
    private DialogInterface.OnClickListener deleteAccountOnClickListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String username = SharedPreferenceUtil.getUsername(activity);
                    Integer userId = UserDao.getUserIdByUsername(username);
                    if (UserDao.deleteUserByUsername(username) == true) {
                        FavouriteItemDao.deleteFavouriteItemsByUserId(userId);
                        SharedPreferenceUtil.removeUsername(activity);
                        ToastUtil.createAndShowToast(activity, "Your account has been deleted");
                        startActivity(new Intent(activity, LoginActivity.class));
                        finish();
                    } else {
                        ToastUtil.createAndShowToast(activity, "Error: Please try again");
                    }
                }
            };

    private AlertDialog.Builder logoutBuilder;
    private AlertDialog.Builder deleteAccountBuilder;

    private AlertDialog.Builder uploadProfileImageBuilder;
    private String[] uploadProfileImage = {"Select from gallery", "Take photo", "Remove profile image", "Cancel"};

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        activity = this;
        iv_profileImage = findViewById(R.id.iv_profile_image);
        Activity activity = this;

        username = SharedPreferenceUtil.getUsername(activity);
        user = UserDao.getUserInfoByUsername(username);

        logoutBuilder = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Confirm to logout your account?")
                .setPositiveButton("Yes, Logout", logoutOnClickListener)
                .setNegativeButton("No", null);
        deleteAccountBuilder = new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Confirm to delete your account?")
                .setPositiveButton("Yes, Delete", deleteAccountOnClickListener)
                .setNegativeButton("No", null);
        uploadProfileImageBuilder = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(uploadProfileImage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                if (!PermissionUtil.readExternalStoragePermissionGranted(activity)) {
                                    PermissionUtil.grantReadExternalStoragePermission(activity);
                                } else {
                                    startGalleryIntent();
                                }
                                break;
                            case 1:
                                if (!PermissionUtil.cameraPermissionGranted(activity)) {
                                    PermissionUtil.grantCameraPermission(activity);
                                } else {
                                    startCameraIntent();
                                }
                                break;
                            case 2:
                                if(UserDao.removeUserProfileImageByUsername(SharedPreferenceUtil.getUsername(activity))) {
                                    ToastUtil.createAndShowToast(activity, "Profile image has been updated");
                                    updateUserProfileImage();
                                    setResult(RESULT_OK);
                                }
                                break;
                            default:
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                });

        String username = SharedPreferenceUtil.getUsername(activity);
        ((TextView) findViewById(R.id.tv_username)).setText(username);
        user = UserDao.getUserInfoByUsername(username);
        updateUserProfileImage();

        iv_profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfileImageBuilder.create().show();
            }
        });

        findViewById(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ChangePasswordActivity.class));
            }
        });

        findViewById(R.id.btn_change_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ChangeEmailActivity.class));
            }
        });

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutBuilder.create().show();
            }
        });

        findViewById(R.id.btn_delete_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccountBuilder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.SNAP_PHOTO_FROM_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                boolean result = UserDao.editUserProfileImage(SharedPreferenceUtil.getUsername(activity), ImageUtil.bitmapToByteArray(bitmap));
               if(result == true) {
                   updateUserProfileImage();
                   ToastUtil.createAndShowToast(activity, "Profile image has been updated");
                   setResult(RESULT_OK);
               }
                else {
                   ToastUtil.createAndShowToast(activity, "Error: Please try again");
                    setResult(RESULT_CANCELED);
               }
            }
        } else if (requestCode == RequestCode.PICK_IMAGE_FROM_GALLERY) {
            if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    boolean result = UserDao.editUserProfileImage(SharedPreferenceUtil.getUsername(activity), ImageUtil.bitmapToByteArray(bitmap));
                    if(result == true) {
                        updateUserProfileImage();
                        ToastUtil.createAndShowToast(activity, "Profile image has been updated");
                        setResult(RESULT_OK);
                    }
                    else {
                        ToastUtil.createAndShowToast(activity, "Error: Please try again");
                        setResult(RESULT_CANCELED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestCode.USE_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            }
        } else if (requestCode == RequestCode.READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryIntent();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RequestCode.PICK_IMAGE_FROM_GALLERY);
    }

    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RequestCode.SNAP_PHOTO_FROM_CAMERA);
    }

    private void updateUserProfileImage() {
        user = UserDao.getUserInfoByUsername(username);
        if (user.profileImg != null) {
            iv_profileImage.setColorFilter(null);
            iv_profileImage.setImageBitmap(ImageUtil.byteArrayToBitmap(user.profileImg));
        } else {
            iv_profileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_24));
            iv_profileImage.setColorFilter(R.color.black);
        }
    }
}