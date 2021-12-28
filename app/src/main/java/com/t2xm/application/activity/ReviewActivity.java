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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t2xm.R;
import com.t2xm.dao.ItemDao;
import com.t2xm.dao.ReviewDao;
import com.t2xm.dao.ReviewImageDao;
import com.t2xm.dao.UserDao;
import com.t2xm.entity.Item;
import com.t2xm.entity.Review;
import com.t2xm.entity.ReviewImage;
import com.t2xm.utils.PermissionUtil;
import com.t2xm.utils.SharedPreferenceUtil;
import com.t2xm.utils.ToastUtil;
import com.t2xm.utils.adapter.ReviewImageAdapter;
import com.t2xm.utils.values.RequestCode;
import com.t2xm.utils.valuesConverter.DateUtil;
import com.t2xm.utils.valuesConverter.ImageUtil;
import com.t2xm.utils.valuesConverter.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private Activity activity;

    private final Integer MAX_REVIEW_LENGTH = 500;
    private Integer itemId;

    private ImageView iv_itemImage;
    private TextView tv_itemName;
    private Integer rating;
    private List<ImageView> iv_starList;
    private TextView tv_reviewCharacterCount;
    private EditText et_reviewText;
    private TextView tv_numberOfImages;
    private RecyclerView rv_uploadImage;
    private CheckBox cb_recommend;
    private Button btn_submitReview;

    private Item item;
    public List<Bitmap> bitmapList;
    public ReviewImageAdapter imageAdapter;

    private AlertDialog.Builder uploadImageBuilder;
    private String[] uploadImageItems = {"Select from gallery", "Take photo", "Cancel"};

    private Integer numberOfImages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        activity = this;

        setupActivityComponents();
        setupActivityListeners();
        setupItemAttributes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        if (requestCode == RequestCode.SNAP_PHOTO_FROM_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmapList.add(bitmap);
                imageAdapter.notifyDataSetChanged();
                increaseNumberOfImages();
            }
        } else if (requestCode == RequestCode.PICK_IMAGE_FROM_GALLERY) {
            if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    bitmapList.add(bitmap);
                    imageAdapter.notifyDataSetChanged();
                    increaseNumberOfImages();
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

    private void startGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RequestCode.PICK_IMAGE_FROM_GALLERY);
    }

    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RequestCode.SNAP_PHOTO_FROM_CAMERA);
    }

    private void setupItemAttributes() {
        //get itemId from intent
        itemId = getIntent().getIntExtra("itemId", 0);
        if (itemId <= 0) {
            ToastUtil.createAndShowToast(activity, "Error");
            onBackPressed();
            return;
        }

        //set item attributes
        item = ItemDao.getItemByItemId(itemId);
        tv_itemName.setText(item.itemName);
        if (item == null) {
            ToastUtil.createAndShowToast(activity, "Error");
            onBackPressed();
            return;
        }
        try {
            //TODO set multiple item image
            String img = String.valueOf(JsonUtil.mapJsonToObject(item.image, List.class).get(0));
            int resource = getResources().getIdentifier(img, "drawable", getPackageName());
            iv_itemImage.setImageResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupActivityComponents() {
        iv_itemImage = findViewById(R.id.iv_item_image);
        tv_itemName = findViewById(R.id.tv_item_name);
        tv_reviewCharacterCount = findViewById(R.id.tv_review_character_count);
        et_reviewText = findViewById(R.id.et_review_text);
        rv_uploadImage = findViewById(R.id.rv_upload_image);
        tv_numberOfImages = findViewById(R.id.tv_number_of_images);
        cb_recommend = findViewById(R.id.cb_recommend);
        btn_submitReview = findViewById(R.id.btn_submit_review);

        iv_starList = new ArrayList<>();
        iv_starList.add(findViewById(R.id.iv_star_1));
        iv_starList.add(findViewById(R.id.iv_star_2));
        iv_starList.add(findViewById(R.id.iv_star_3));
        iv_starList.add(findViewById(R.id.iv_star_4));
        iv_starList.add(findViewById(R.id.iv_star_5));

        bitmapList = new ArrayList<>();
        imageAdapter = new ReviewImageAdapter(ReviewActivity.this, bitmapList);
        Activity activity = this;
        rv_uploadImage.setAdapter(imageAdapter);
        rv_uploadImage.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        uploadImageBuilder = new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(uploadImageItems, new DialogInterface.OnClickListener() {
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
                            default:
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                });
    }

    private void setupActivityListeners() {
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

        for (int i = 0; i < 5; i++) {
            final int final_i = i;
            ImageView iv_star = iv_starList.get(i);
            iv_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rating = final_i + 1;
                    for (int j = 0; j < 5; j++) {
                        if (j <= final_i) {
                            iv_starList.get(j).setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                        } else {
                            iv_starList.get(j).setImageDrawable(getDrawable(R.drawable.ic_baseline_star_border_24));
                        }
                    }
                }
            });
        }

        findViewById(R.id.btn_upload_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOfImages >= 5) {
                    ToastUtil.createAndShowToast(activity, "You have uploaded the maximum number of images");
                }
                else {
                    uploadImageBuilder.create().show();
                }
            }
        });

        btn_submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(et_reviewText.getText()).trim().equals("") || bitmapList.size() <= 0) {
                    ToastUtil.createAndShowToast(activity, "Please provide some contents before submitting your review");
                }

                Integer userId = UserDao.getUserIdByUsername(SharedPreferenceUtil.getUsername(activity));
                String reviewText = String.valueOf(et_reviewText.getText());
                Boolean recommend = cb_recommend.isChecked();

                Review review = new Review(null, itemId, userId, reviewText, rating,
                        recommend, DateUtil.getCurrentTimestamp());
                long reviewId = ReviewDao.insertReviewAndGetReviewId(review);

                if (bitmapList != null && bitmapList.size() > 0) {
                    for (Bitmap bitmap : bitmapList) {
                        ReviewImage reviewImage = new ReviewImage(null, Math.toIntExact(reviewId), ImageUtil.bitmapToByteArray(bitmap));
                        if (!ReviewImageDao.insertReviewImage(reviewImage)) {
                            ToastUtil.createAndShowToast(activity, "Error: some image was not uploaded successfully");
                        }
                    }
                }
                ToastUtil.createAndShowToast(activity, "Your review has been posted");
                onBackPressed();
                finish();
            }
        });
    }

    public void increaseNumberOfImages() {
        numberOfImages += 1;
        updateNumberOfImagesText();
    }

    public void reduceNumberOfImages() {
        numberOfImages -= 1;
        updateNumberOfImagesText();
    }

    private void updateNumberOfImagesText() {
        tv_numberOfImages.setText(numberOfImages + "/5 images");
    }
}