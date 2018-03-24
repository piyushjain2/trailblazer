package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import trailblaze.issft06.android.com.trailblaze.adapter.ContributeAdapter;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.ContributeItem;
import trailblaze.issft06.android.com.trailblaze.R;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class Tab2Upload extends Fragment {

     FirebaseFirestore mFirebaseFirestore;
     FirebaseStorage mFirebaseStorage;

     private Button mUploadPhoto;
     private Button mUploadPdf;
     private Button mUploadVideo;
     private EditText mEditText;
     private StorageReference mPhotosStorageReference;
     private StorageReference mViedeoStorageReference;
     private StorageReference mDocumentSotrageReference;
     private RecyclerView mRecyclerView;
     private ContributeAdapter mContriubuteItemAdapter;


    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;
    private static final int RC_DOCUMENT_PICKER = 3;
    private static final int RC_VIDEO_PICKER = 4;
    private CollectionReference mCollectionRef;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_upload, container, false);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mPhotosStorageReference = mFirebaseStorage.getReference().child("images");
        mViedeoStorageReference = mFirebaseStorage.getReference().child("videos");
        mDocumentSotrageReference = mFirebaseStorage.getReference().child("documents");
        mRecyclerView = rootView.findViewById(R.id.contributionsListView);


        final Context context = this.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final ArrayList<ContributeItem> contributeItems = new ArrayList<ContributeItem>();
        mCollectionRef = mFirebaseFirestore.collection("contributions");
        mCollectionRef.whereEqualTo("userId",App.user.getId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                    if (document != null) {
                        ContributeItem contributeItem = document.toObject(ContributeItem.class);
                        contributeItems.add(contributeItem);
                    } else {
                        Log.w(TAG, "No item here");
                    }
                }
                mContriubuteItemAdapter = new ContributeAdapter(contributeItems);
                mRecyclerView.setAdapter(mContriubuteItemAdapter);
            }
        });







        mEditText = rootView.findViewById(R.id.descriptionText);
        mUploadPhoto = rootView.findViewById(R.id.photo_upload_button);
        mUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);



            }
        });

        mUploadPdf = rootView.findViewById(R.id.document_upload_button);
        mUploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_DOCUMENT_PICKER);



            }
        });


        mUploadVideo = rootView.findViewById(R.id.video_upload_button);
        mUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/mp4");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_VIDEO_PICKER);



            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Date now = new Date();

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mPhotosStorageReference.child(selectedImageUri.getLastPathSegment());


            // Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String contentType = taskSnapshot.getMetadata().getContentType();

                            // Set the download URL to the message box, so that the user can send it to the database
                            ContributeItem contributeItem = new ContributeItem();
                            contributeItem.setContentType(contentType);

                            contributeItem.setId(App.user.getId()+"_"+now);
                            contributeItem.setUserName(App.user.getName());
                            contributeItem.setUserId(App.user.getId());
                            contributeItem.setDesc(mEditText.getText().toString());
                            contributeItem.setTrailStationId(App.trailStation.getId());
                            contributeItem.setUrl(downloadUrl.toString());
                            contributeItem.setcTime(now);



                            mFirebaseFirestore.collection("contributions").add(contributeItem);

                        }
                    });
        }
        else if (requestCode == RC_DOCUMENT_PICKER && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mDocumentSotrageReference.child(uri.getLastPathSegment());


            // Upload file to Firebase Storage
            photoRef.putFile(uri)
                    .addOnSuccessListener(this.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String contentType = taskSnapshot.getMetadata().getContentType();

                            // Set the download URL to the message box, so that the user can send it to the database
                            ContributeItem contributeItem = new ContributeItem();
                            contributeItem.setContentType(contentType);
                            contributeItem.setId(App.user.getId()+"_"+now);

                            contributeItem.setUserName(App.user.getName());
                            contributeItem.setUserId(App.user.getId());
                            contributeItem.setDesc(mEditText.getText().toString());
                            contributeItem.setTrailStationId(App.trailStation.getId());
                            contributeItem.setUrl(downloadUrl.toString());
                            contributeItem.setcTime(now);

                            mFirebaseFirestore.collection("contributions").add(contributeItem);

                        }
                    });
        }
        else if (requestCode == RC_VIDEO_PICKER && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mViedeoStorageReference.child(uri.getLastPathSegment());


            // Upload file to Firebase Storage
            photoRef.putFile(uri)
                    .addOnSuccessListener(this.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String contentType = taskSnapshot.getMetadata().getContentType();

                            // Set the download URL to the message box, so that the user can send it to the database
                            ContributeItem contributeItem = new ContributeItem();
                            contributeItem.setContentType(contentType);
                            contributeItem.setId(App.user.getId()+"_"+now);

                            contributeItem.setUserName(App.user.getName());
                            contributeItem.setUserId(App.user.getId());
                            contributeItem.setDesc(mEditText.getText().toString());
                            contributeItem.setTrailStationId(App.trailStation.getId());
                            contributeItem.setUrl(downloadUrl.toString());
                            contributeItem.setcTime(now);

                            mFirebaseFirestore.collection("contributions").add(contributeItem);

                        }
                    });
        }
    }


}
