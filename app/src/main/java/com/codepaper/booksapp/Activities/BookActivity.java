package com.codepaper.booksapp.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepaper.booksapp.Adapter.BookSellAdapter;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.github.ybq.android.spinkit.SpinKitView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BookActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgAdd;
    UserResponse userResponse;
    int Userid;
    String curDate;
    private static final String IMAGE_DIRECTORY = "/BOOK_IMAGE";
    private final String imageInSD = "/BOOK_IMAGE/1_5142.jpg";
    private int GALLERY = 1, CAMERA = 2;
    Bitmap bitmap;
    ImageView imgUpload;
    File wallpaperDirectory;
    String imgName = "No_image";
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<Post> postList;
    BookDatabase dataBase;
    PostDao db;
    BookSellAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler;
    SpinKitView progressBar;
    TextView txtEmptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.book_sale_toolbar);
        imgAdd = findViewById(R.id.book_sale_imgAdd);
        recyclerView = findViewById(R.id.book_sale_recyclerView);
        swipeRefreshLayout = findViewById(R.id.book_sale_swipeRefresh);
        progressBar = findViewById(R.id.book_sale_spinKit);
        txtEmptyView = findViewById(R.id.book_sale_txtEmptyView);
        layoutManager = new LinearLayoutManager(this);
        implementView();
    }

    private void implementView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        handler = new Handler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        dataBase = Room.databaseBuilder(this, BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getPostDao();

        userResponse = SharedPrefManager.getInstance(this).getUserId();
        Userid = userResponse.getUser_id();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddBookDialog();
            }
        });

        fillRecyclerView();
    }

    private void fillRecyclerView() {

        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                postList = db.getPostByType(Userid,"sell");
                if(postList.size()>0) {
                    Log.d("SIZE", String.valueOf(postList.size()));

                    adapter = new BookSellAdapter(BookActivity.this, postList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }
            }
        },1000);

    }

    private void OpenAddBookDialog() {

        Button btnNext;
        ImageView imgBtnClose;
        final EditText edtTitle,edtAuthor,edtEdition,edtISBN,edtPrice;
        final Spinner spnCondition;
        final Dialog dialog=new Dialog(BookActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_sale_book);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.dialog_sale_book_imgClose);
        btnNext = dialog.findViewById(R.id.dialog_sale_book_btnNext);
        edtTitle = dialog.findViewById(R.id.dialog_sale_book_edtTitle);
        edtAuthor = dialog.findViewById(R.id.dialog_sale_book_edtAuthor);
        edtEdition = dialog.findViewById(R.id.dialog_sale_book_edtEdition);
        edtISBN = dialog.findViewById(R.id.dialog_sale_book_edtISBN);
        edtPrice = dialog.findViewById(R.id.dialog_sale_book_edtPrice);
        spnCondition = dialog.findViewById(R.id.dialog_sale_book_spnCondition);
        imgUpload = dialog.findViewById(R.id.dialog_sale_book_imgUpload);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermissions();
                showPictureDialog();
            }
        });

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                curDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String postType = "sell";
                String title = edtTitle.getText().toString().trim();
                String author = edtAuthor.getText().toString().trim();
                String edition = edtEdition.getText().toString().trim();
                String isbn = edtISBN.getText().toString().trim();
                String condition = spnCondition.getSelectedItem().toString();

                if(title.equals("")) {
                    edtTitle.setError("Title is Required");
                    edtTitle.requestFocus();
                    return;
                }else if(author.equals("")) {
                    edtAuthor.setError("Author is Required");
                    edtAuthor.requestFocus();
                    return;
                }else if(edition.equals("")) {
                    edtEdition.setError("Edition is Required");
                    edtEdition.requestFocus();
                    return;
                }else if(isbn.equals("")) {
                    edtISBN.setError("ISBN is Required");
                    edtISBN.requestFocus();
                    return;
                }else if(condition.equals("Select Condition")) {
                    TextView errorText = (TextView)spnCondition.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please Select Condition");
                    return;
                }else if(edtPrice.getText().toString().equals("")) {
                    edtPrice.setError("Price is Required");
                    edtPrice.requestFocus();
                    return;
                }else{
                    double price = Double.parseDouble(edtPrice.getText().toString().trim());
                    Post post = new Post(Userid,postType,title,author,edition,isbn,condition,price,"null","null","null","sell",imgName,curDate);
                    db.insertPost(post);
                    Toast.makeText(BookActivity.this, "Post Saved SuccessFully!!", Toast.LENGTH_SHORT).show();
                    fillRecyclerView();
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    imgUpload.setImageBitmap(bitmap);
                    saveImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(BookActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgUpload.setImageBitmap(bitmap);

            saveImage(bitmap);
            Toast.makeText(BookActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            final int min = 2000;
            final int max = 8000;
            final int random = new Random().nextInt((max - min) + 1) + min;
            imgName = Userid + "_" + random;
            File f = new File(wallpaperDirectory, imgName + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
