package com.t2xm.application.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.entity.Item;
import com.t2xm.utils.PermissionUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.values.RequestCode;
import com.t2xm.utils.valuesConverter.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private final Integer MAX_REVIEW_LENGTH = 500;
    private Integer itemId;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private TextView tv_reviewCharacterCount;
    private EditText et_reviewText;
    private RecyclerView rv_uploadImage;
    private CheckBox cb_recommend;
    private Button btn_submitReview;

    private Item item;
    private List<Bitmap> bitmapList;

    private AlertDialog.Builder uploadImageBuilder;
    private String[] uploadImageItems = {"Select from gallery", "Take photo", "Cancel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        tv_reviewCharacterCount = findViewById(R.id.tv_review_character_count);
        et_reviewText = findViewById(R.id.et_review_text);
        rv_uploadImage = findViewById(R.id.rv_reviews);
        cb_recommend = findViewById(R.id.cb_recommend);
        btn_submitReview = findViewById(R.id.btn_submit_review);

        bitmapList = new ArrayList<>();
        Activity activity = this;

        uploadImageBuilder = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(uploadImageItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                if (!PermissionUtil.checkReadExternalStoragePermission(getApplicationContext())) {
                                    PermissionUtil.grantReadExternalStoragePermission(activity);
                                }
                                else {
                                    startGalleryIntent();
                                }
                                break;
                            case 1:
                                if (!PermissionUtil.checkCameraPermission(getApplicationContext())) {
                                    PermissionUtil.grantCameraPermission(activity);
                                }
                                else {
                                    startCameraIntent();
                                }
                                break;
                            default:
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                });

        itemId = getIntent().getIntExtra("itemId", 0);
        if (itemId <= 0) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error");
            onBackPressed();
            return;
        }

        //set item attributes
        item = ItemDao.getItemByItemId(itemId);
        if (item == null) {
            ToastUtil.createAndShowToast(getApplicationContext(), "Error");
            onBackPressed();
            return;
        }
        try {
            String img = String.valueOf(JsonUtil.mapJsonToObject(item.image, List.class).get(0));
            int resource = getResources().getIdentifier(img, "drawable", getPackageName());
            iv_itemImage.setImageResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_itemName.setText(item.itemName);

        //set listeners
        et_reviewText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_reviewCharacterCount.setText(et_reviewText.getText().length() + "/" + MAX_REVIEW_LENGTH);
            }
        });

        findViewById(R.id.btn_upload_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageBuilder.create().show();
            }
        });

        btn_submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO submit review
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.USE_CAMERA) {
            //TODO camera action
        } else if (requestCode == RequestCode.READ_EXTERNAL_STORAGE) {
            //TODO read external storage action
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(getApplicationContext()).setMessage("Denying some access may affect some functions of this application");
                return;
            }
        }
        if (requestCode == RequestCode.PICK_IMAGE_FROM_GALLERY) {
            startGalleryIntent();
        }
    }

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RequestCode.PICK_IMAGE_FROM_GALLERY);
    }

    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RequestCode.SNAP_PHOTO_FROM_CAMERA);
    }
}