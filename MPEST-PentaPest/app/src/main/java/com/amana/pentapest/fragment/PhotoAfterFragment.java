package com.amana.pentapest.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.amana.pentapest.R;
import com.amana.pentapest.adapter.DocsDataDisplayAdapter;
import com.amana.pentapest.adapter.FragmentLifecycle;
import com.amana.pentapest.adapter.PestAdapter;
import com.amana.pentapest.adapter.PhotoAdapter;
import com.amana.pentapest.components.MultiSelectionSpinner;
import com.amana.pentapest.model.PestModel;
import com.amana.pentapest.model.RemarskModel;
import com.amana.pentapest.model.realm.jobdetails.PhotoRemarkRMModel;
import com.amana.pentapest.myjob.MyJobActivity;
import com.amana.pentapest.utils.AppConstants;
import com.amana.pentapest.utils.AppLogger;
import com.amana.pentapest.utils.AppPreferences;
import com.amana.pentapest.utils.MasterDbLists;
import com.amana.pentapest.utils.PermissionUtils;
import com.amana.pentapest.utils.Utils;
import com.github.clans.fab.FloatingActionMenu;
import com.retropicker.FileUtils;
import com.retropicker.callback.CallbackPicker;
import com.retropicker.config.Retropicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoAfterFragment extends Fragment implements FragmentLifecycle, PhotoAdapter.ItemClickListener, PestAdapter.ItemClickListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener, DocsDataDisplayAdapter.ItemClickListener {
    AppPreferences _appPrefs;
    PhotoAdapter photoAdapter;
    PestAdapter mPestAdapter;
    DocsDataDisplayAdapter docsDataDisplayAdapter;

    String str_pestType = "";
    String TAG = this.getClass().getSimpleName();

    ArrayList<PhotoRemarkRMModel> arr_photoWithRemarks = new ArrayList<>();
    ArrayList<PestModel> arr_pest_list = new ArrayList<>();
    ArrayList<RemarskModel> arr_remarks = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();

    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;

    View myFragmentView;

    @BindView(R.id.picFrame)
    ImageView picFrame;
    @BindView(R.id.proceed_btn)
    Button bn_Proceed;
    @BindView(R.id.fab_camera)
    FloatingActionButton fb_Camera;
    @BindView(R.id.photoRemark_rv)
    RecyclerView rv_photoremark;
    @BindView(R.id.pests_rv)
    RecyclerView rv_pestTypes;
    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    EditText edt_remarks;

    @BindView(R.id.fab_Menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.fab_photo)
    com.github.clans.fab.FloatingActionButton fab_photo;
    @BindView(R.id.fab_gallery)
    com.github.clans.fab.FloatingActionButton fab_gallery;

    private Retropicker retropicker;

    Uri imageUri;
    String picturePath;
    private final int CAMERA_ACTIVITY_RESULT_CODE = 101;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_photo_before, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        init();

        updatePestType();

        updatePhotoList("AFTER", str_pestType);

        getRemarkList();

        //  Utils.dismissDialog();

        return myFragmentView;
    }

    private void init() {
        fab_menu.setClosedOnTouchOutside(true);
        //Camera onClick
        fab_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // ((MyJobActivity)getActivity()).switchFragment(0);

                fab_menu.toggle(true);

                Retropicker.Builder builder =  new Retropicker.Builder(getActivity())
                        .setTypeAction(Retropicker.CAMERA_PICKER)
                        //.setImageName("Mpest"+Utils.getCurrentTime()+".jpg")
                        .checkPermission(true);


                builder.enquee(new CallbackPicker() {
                    @Override
                    public void onSuccess(Bitmap bitmap, String imagePath) {
                        // imageView.setImageBitmap(bitmap);
                        Log.e("TAG", "File Path: " + imagePath);

                        MasterDbLists.saveImageDetails("AFTER",str_pestType,_appPrefs.getSERVICEID().toString(),imagePath);
                        updatePhotoList("AFTER",str_pestType);
                        photoAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e("TAG", "error: " + error.getMessage());
                        Log.e("TAG", "error toString: " + error.toString());
                    }
                });

                retropicker = builder.create();
                retropicker.open();


               /* if (PermissionUtils.isPermissionGranted(getActivity(), Manifest.permission.CAMERA) && PermissionUtils.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    launchCamera();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionUtils.PERMISSION_REQUEST_CODE_GALLERY);
                    }
                }*/


            }
        });

        fab_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_menu.toggle(true);

                Retropicker.Builder builder =  new Retropicker.Builder(getActivity())
                        .setTypeAction(Retropicker.GALLERY_PICKER)
                        //.setImageName("Mpest"+Utils.getCurrentTime()+".jpg")
                        .checkPermission(true);


                builder.enquee(new CallbackPicker() {
                    @Override
                    public void onSuccess(Bitmap bitmap, String imagePath) {


                        final String path = FileUtils.getPath(getContext(), Uri.parse(imagePath));
                        Log.e("TAG", "File Path: " + path);

                        File imgFile = new File(path);
                        if(imgFile.exists()){

                            MasterDbLists.saveImageDetails("AFTER",str_pestType,_appPrefs.getSERVICEID().toString(),path);
                            updatePhotoList("AFTER",str_pestType);
                            photoAdapter.notifyDataSetChanged();

                        }else{
                            Log.e("TAG", "File Path not Found");
                        }

                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e("TAG", "error: " + error.getMessage());
                        Log.e("TAG", "error toString: " + error.toString());
                    }
                });

                retropicker = builder.create();
                retropicker.open();


            }
        });

        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyJobActivity) getActivity()).switchFragment(4);

            }
        });

    }


    private void launchCamera() {
   /*     Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getImageUri();
        picturePath = imageUri.getPath();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_ACTIVITY_RESULT_CODE);*/

        Retropicker.Builder builder =  new Retropicker.Builder(getActivity())
                .setTypeAction(Retropicker.CAMERA_PICKER)
                //.setImageName("Mpest"+Utils.getCurrentTime()+".jpg")
                .checkPermission(false);


        builder.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, String imagePath) {
                // imageView.setImageBitmap(bitmap);
                Log.e("TAG", "File Path: " + imagePath);

                MasterDbLists.saveImageDetails("AFTER",str_pestType,_appPrefs.getSERVICEID().toString(),imagePath);
                updatePhotoList("AFTER",str_pestType);
                photoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "error: " + error.getMessage());
                Log.e("TAG", "error toString: " + error.toString());
            }
        });

        retropicker = builder.create();
        retropicker.open();
    };

    private Uri getImageUri() {
        // Store image in Kaakateeya folder
        Uri cameraImgUri = null;
        try {
            //String path = Environment.getExternalStorageDirectory() + "/Kaakateeya/pics";
            //String path = context.getFilesDir()
            String path = Environment.getExternalStorageDirectory()
                    + "/Android/data/" + getActivity().getPackageName() + "/profile/IMG_" + Calendar.getInstance().getTimeInMillis();

            // String path = Environment.getExternalStorageDirectory() + "/Solutions/pics/";
            String fileName = ".jpg";

            path = path + fileName;
            File actualImageFile = new File(path);
            cameraImgUri = Uri.fromFile(actualImageFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cameraImgUri;
    }



    private void updatePestType() {

        String[] arrayString = (_appPrefs.getPESTS().toString()).split(",");
        str_pestType = arrayString[0];
        arr_pest_list.clear();
        for (int i = 0; arrayString.length > i; i++) {
            arr_pest_list.add(new PestModel(0, arrayString[i]));
        }

        mPestAdapter = new PestAdapter(getContext(), arr_pest_list);
        rv_pestTypes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_pestTypes.setAdapter(mPestAdapter);
        rv_pestTypes.setItemAnimator(new DefaultItemAnimator());
        mPestAdapter.setClickListener(this);
    }

    private void updatePhotoList(String mType, String mPestType) {

        Utils.showCustomDialog(myFragmentView.getContext());

        arr_photoWithRemarks = MasterDbLists.getPhotoRemarksDetails(mType, mPestType, _appPrefs.getSERVICEID());
        if (arr_photoWithRemarks.size() > 0) {
            rv_photoremark.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            photoAdapter = new PhotoAdapter(getActivity(), arr_photoWithRemarks);
            rv_photoremark.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            rv_photoremark.setAdapter(photoAdapter);
            photoAdapter.setClickListener(this);
        } else {
            // no data
            rv_photoremark.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);

        }
        Utils.dismissDialog();

    }

    private void getRemarkList() {

        arr_remarks = MasterDbLists.RemarksList();
    }


    /**
     * Remarks Popup
     *
     * @param context
     */
    public void popup_selectRemarks2(Context context, final int mPosition, String mRemarks, final ArrayList<Integer> selectedArr, String mOthers) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_premarks, null);
        alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close = (CircleImageView) promptsView
                .findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);
        edt_remarks = (EditText) promptsView.findViewById(R.id.input_remarks);
        RecyclerView rv_remarks = (RecyclerView) promptsView.findViewById(R.id.recyclerview);
        final EditText edt_others = (EditText) promptsView.findViewById(R.id.input_others);

        edt_others.setText("" + mOthers);
        edt_remarks.setText("" + mRemarks);


        for (int i = 0; i < selectedArr.size(); i++) {

            int position = selectedArr.get(i);

            arr_remarks.get(position).setSelected(true);
        }


        docsDataDisplayAdapter = new DocsDataDisplayAdapter(getActivity(), arr_remarks);
        rv_remarks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_remarks.setAdapter(docsDataDisplayAdapter);
        docsDataDisplayAdapter.setClickListener(this);
        docsDataDisplayAdapter.notifyDataSetChanged();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                MasterDbLists.UpdatePhotoRemarks(mPosition, edt_remarks.getText().toString(), arr_SelectedList, edt_others.getText().toString());
                updatePhotoList("AFTER", str_pestType);
                photoAdapter.notifyDataSetChanged();

                for (int i = 0; i < arr_remarks.size(); i++) {
                    arr_remarks.get(i).setSelected(false);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, int ClickType) {
        AppLogger.info(TAG, "Photo " + photoAdapter.getItem(position).getId() + ", position " + position);

        switch (ClickType) {
            case AppConstants.TYPE_CLCIK_REMARKS:
                ArrayList<Integer> arr_selected = new ArrayList<>();
                for (int i = 0; i < photoAdapter.getItem(position).getSelectedPositions().size(); i++) {
                    arr_selected.add(photoAdapter.getItem(position).getSelectedPositions().get(i));
                }
                handleRemarks(photoAdapter.getItem(position).getId(), photoAdapter.getItem(position).getRemarks(), arr_selected, photoAdapter.getItem(position).getOthers());
                break;
            case AppConstants.TYPE_CLCIK_DELETE:
                handleDelete(photoAdapter.getItem(position).getId());
                break;
        }

    }

    /**
     * Delete Row
     *
     * @param position
     */
    private void handleDelete(int position) {
        //  Utils.showToast(getActivity(), " Delete - " + position);
        MasterDbLists.DeletefromPhotos(position);
        updatePhotoList("AFTER", str_pestType);
        photoAdapter.notifyDataSetChanged();
    }

    /**
     * Add's Remarks
     *
     * @param position
     */
    private void handleRemarks(int position, String mRemarks, ArrayList<Integer> selectedArr, String mOthers) {
        // Utils.showToast(getActivity(), " Remarks - " + position);
        // multiSelectRemarksPopup(position);
        popup_selectRemarks2(getActivity(), position, mRemarks, selectedArr, mOthers);
        // MasterDbLists.UpdatePhotoRemarks(position);

    }

    /**
     * @param view
     * @param position
     */
    @Override
    public void onPestItemClick(View view, int position) {

        AppLogger.info(TAG, "PestType " + mPestAdapter.getItem(position).getTitle() + ", position " + position);

        clearOldSelection();
        arr_pest_list.get(position).setSelectedPosition(position);
        mPestAdapter.notifyDataSetChanged();

        str_pestType = mPestAdapter.getItem(position).getTitle().toString().trim();
        updatePhotoList("AFTER", str_pestType);

    }

    // this method clears the previous selection list
    private void clearOldSelection() {
        for (PestModel model : arr_pest_list) {
            model.setSelectedPosition(-1);
        }
    }

   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

    }


  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    *//*    switch (requestCode) {
            case PermissionUtils.PERMISSION_REQUEST_CODE_CAMERA: {
                String permissionOne = permissions[0];
                int grantResultOne = grantResults[0];

                String permissionTwo = permissions[1];
                int grantResultTwo = grantResults[1];

                if (permissionOne.equals(Manifest.permission.CAMERA) && permissionTwo.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResultOne == PackageManager.PERMISSION_GRANTED && grantResultTwo == PackageManager.PERMISSION_GRANTED) {
                        launchCamera();
                    } else {
                        Toast.makeText(getActivity(), "Camera, File Permissions required to capture the Photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case PermissionUtils.PERMISSION_REQUEST_CODE_GALLERY: {
                String permissionOne = permissions[0];
                int grantResultOne = grantResults[0];

                String permissionTwo = permissions[1];
                int grantResultTwo = grantResults[1];

                if (permissionOne.equals(Manifest.permission.CAMERA) && permissionTwo.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResultOne == PackageManager.PERMISSION_GRANTED && grantResultTwo == PackageManager.PERMISSION_GRANTED) {
                        //launchGallery();
                    } else {
                        Toast.makeText(getActivity(), "Camera, File Permissions required to capture the Photo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;


        }*//*

        try {
            retropicker.onRequesPermissionResult(requestCode, permissions, grantResults);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/

    @Override
    public void selectedIndices(List<Integer> indices) {
    }

    @Override
    public void selectedStrings(List<String> strings) {
        // Toast.makeText(getActivity(), strings.toString(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void OnItemClick(View view, int position) {

        arr_SelectedList.add(position);

        RemarskModel remarskModel = arr_remarks.get(position);
        if (remarskModel.getSelected()) {
            remarskModel.setSelected(false);
        } else {
            remarskModel.setSelected(true);
        }

        ArrayList<String> arr_remark_tem = new ArrayList<>();
        arr_SelectedList.clear();
        for (RemarskModel remarskModel1 : arr_remarks) {

            if (remarskModel1.getSelected()) {
                arr_remark_tem.add(remarskModel1.getName());
                arr_SelectedList.add(remarskModel1.getId());
            }
        }

        edt_remarks.setText(android.text.TextUtils.join(",", arr_remark_tem));

    }


    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
       // Toast.makeText(getActivity(), "onPauseFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
        //Toast.makeText(getActivity(), "onResumeFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CAMERA_ACTIVITY_RESULT_CODE:

                    onActivityResultOfCameraCapture(data);

                    Log.v("IMAG Path -- ", picturePath);

                    break;

            }
        }
    }*/

    private void onActivityResultOfCameraCapture(Intent data) {
        Uri selectedImage = imageUri;

        File imgFile = new File(selectedImage.getPath());
        if(imgFile.exists()){

                Log.e("TAG", "File Path: " + selectedImage.getPath());

                MasterDbLists.saveImageDetails("AFTER",str_pestType,_appPrefs.getSERVICEID().toString(),selectedImage.getPath());
                updatePhotoList("AFTER",str_pestType);
                photoAdapter.notifyDataSetChanged();

            System.out.print("IMAGE LENGTH --:"+(int) imgFile.length());

        }else{
            Toast.makeText(getActivity(), "Camera image not found", Toast.LENGTH_SHORT).show();
        }

    }


}
