package trailblaze.issft06.android.com.trailblaze.view;



        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.design.widget.TextInputLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.AppCompatButton;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.FacebookSdk;
        import com.facebook.GraphRequest;
        import com.facebook.GraphResponse;
        import com.facebook.login.LoginManager;
        import com.facebook.login.LoginResult;
        import com.facebook.login.widget.LoginButton;
        import com.google.android.gms.auth.api.Auth;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.auth.api.signin.GoogleSignInResult;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.Scopes;
        import com.google.android.gms.common.SignInButton;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.Scope;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.InputStream;
        import java.security.KeyStore;
        import java.security.cert.Certificate;
        import java.security.cert.CertificateFactory;
        import java.util.ArrayList;

        import javax.net.ssl.SSLContext;
        import javax.net.ssl.TrustManagerFactory;



public class Login extends AppCompatActivity implements
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    String email = "";
    String password;
    String name;
    EditText nameSignUp;
    EditText userNameSignUp;
    EditText emailSignUp;
    EditText passwordSignUp;
    EditText repeatPasswordSignUp;
    CallbackManager callbackManager;
    String mobile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String gcm_id ;
    boolean fbDone = false;
    ViewGroup mContainer;
    boolean otpVisible = false;
    LinearLayout trueOTPView;
    TextView switchToOtp;
    LinearLayout loginContainer, signupContainer;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    private static GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private boolean doubleBackToExitPressedOnce = false;
    private String isVerified;
    private boolean isMobile = false;

    private Button signin;
    private boolean otpSent = false;

    ArrayList<String> contacts = new ArrayList<String>();
    ArrayList<ArrayList<String>> phone = new ArrayList<>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> contacted = new ArrayList<String>();
    private static final int CONTACTS_LOADER_ID = 1;

    JSONArray allContacts = new JSONArray();
    private String customerId;
    private TextInputLayout passwordSignUpView;
    private TextInputLayout userNameSignUpView;
    private TextView resendOTPView;
    //    private TrueClient mTrueClient;
    private boolean trueDone = false;
    private boolean otpSentAlt = false;
    private boolean trueOTP = false;
    private int LOGIN_TRUECALLER = 190;
    private int LOGIN_GPLUS = 191;
    private int LOGIN_FB = 64206;
    private AppCompatButton facebookButton;
    private AppCompatButton googlePlusButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Set the facebook app id and initialize sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color

            window.setStatusBarColor(getResources().getColor(R.color.status_color));
        }

        try {
            init(this,this);
            trueButton = findViewById(R.id.com_truecaller_android_sdk_truebutton);
            customTrueButton = findViewById(R.id.trueButton);
            customTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trueButton.onClick(trueButton);
                }
            });
//            mTrueClient = new TrueClient(this, this);
//            ((TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton)).setTrueClient(mTrueClient);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "True error", Toast.LENGTH_SHORT).show();
        }
        mContainer = (ViewGroup) findViewById(R.id.main_container);
        loginContainer = (LinearLayout) findViewById(R.id.signin_container);
//        signupContainer = (LinearLayout) findViewById(R.id.signup_container);


        //Inject the views in the respective containers
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //include views based on user settings
        loginContainer.addView(layoutInflater.inflate(R.layout.fragment_custom_login, mContainer, false));
//        signupContainer.addView(layoutInflater.inflate(R.layout.fragment_signup, mContainer, false));

        preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        nameSignUp = (EditText) findViewById(R.id.nameEditText);
        userNameSignUp = (EditText) findViewById(R.id.userNameEditText);
        userNameSignUpView = (TextInputLayout) findViewById(R.id.usernameWrapper);
        emailSignUp = (EditText) findViewById(R.id.emailEditText);
        passwordSignUpView = (TextInputLayout) findViewById(R.id.passwordWrapper);
        repeatPasswordSignUp = (EditText) findViewById(R.id.repeatPasswordSignUp);
        gcm_id = preferences.getString("gcm_token","");
        otpView = (CodeInput) findViewById(R.id.pinviewLogin);
        resendOTPView = (TextView) findViewById(R.id.resendOTPButton);
        signin = (Button) findViewById(R.id.custom_signin_button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCustomSignin();
            }
        });
//        switchToOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchViewToOtp();
//            }
//        });





        facebookButton =  findViewById(R.id.login_fb_button);
//        facebookButton.setReadPermissions("email");
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            facebookButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_vector, 0, 0, 0);
//        } else {
//            facebookButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facebook_white_36dp, 0, 0, 0);
//        }
        facebookButton.setOnClickListener(this);

        googlePlusButton =  findViewById(R.id.login_google_button);
//

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId()
                .build();



        googlePlusButton.setOnClickListener(this);
//        findViewById(R.id.user_signup_button).setOnClickListener(this);
//        findViewById(R.id.switch_to_signup).setOnClickListener(this);
//        findViewById(R.id.user_signin_button).setOnClickListener(this);
//        findViewById(R.id.custom_signin_button).setOnClickListener(this);
//        signupContainer.setVisibility(View.GONE);
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(new Scope(Scopes.PLUS_LOGIN))
//                .addScope(new Scope(Scopes.EMAIL))
//                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        try {

            //Facebook login callback
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    try {

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (object != null) {
                                    try {
                                        if (object.has("name"))
                                            name = object.getString("name");
                                        else
                                            name = "";

                                        if (object.has("email"))
                                            email = object.getString("email");
                                        else
                                            email = "";
                                        if (object.has("id"))
                                            social_id = object.getString("id");

                                        String profile_pic = "";
                                        if (object.has("picture")) {
                                            JSONObject profile_pic_data = new JSONObject(object.get("picture").toString());
                                            JSONObject profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
                                            profile_pic = profile_pic_url.getString("url");
                                        }
                                        editor.putString("profilePicURL", String.valueOf(profile_pic));


                                        password = "GoogleSignIned";
                                        mobile = "";
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.putBoolean("isSocial", true);
                                        editor.putString("whichSocial", "fb");
                                        editor.putString("socialId", social_id);
                                        editor.apply();
                                        if (social_id != null) {
                                            String[] names = {"facebookId", "GCMId", "name", "email", "profileData", "profileImage"};
                                            String[] value = {social_id, gcm_id, name, email, String.valueOf(object), String.valueOf(profile_pic)};
                                            String url = getResources().getString(R.string.base_url) + "customerLoginFacebook";
                                            if (!fbDone) {
                                                new HttpApiCall(Login.this, url, names, value, "fbLogin");
                                                fbDone = true;
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(Login.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,email,location,picture.width(120).height(120)");
                        request.setParameters(parameters);
                        request.executeAsync();

                        request.executeAsync();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
                    if (mProgressDialog!=null) {
                        mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        mProgressDialog.setTitleText("Login Cancelled!");
                        mProgressDialog.setContentText("Login permission denied or login cancelled by user");
                        mProgressDialog.setConfirmText("Ok");
                        mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (mProgressDialog!=null) {
                                    mProgressDialog.dismissWithAnimation();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onError(FacebookException e) {
                    if(mProgressDialog!=null) {
                        mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        mProgressDialog.setTitleText("Error!");
                        mProgressDialog.setContentText(e.toString());
                        mProgressDialog.setConfirmText("Ok");
                        mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (mProgressDialog!=null) {
                                    mProgressDialog.dismissWithAnimation();
                                }
                            }
                        });
                    }
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void switchViewToOtp() {
        mContainer.setVisibility(View.VISIBLE);
        facebookButton.setVisibility(View.GONE);
        googlePlusButton.setVisibility(View.GONE);
        findViewById(R.id.trueButton).setVisibility(View.GONE);
        signin.setText("Send OTP");
        userNameSignUp.setError(null);
    }


    public boolean isNetworkOn(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                super.onActivityResult(requestCode, resultCode, data);
                Log.d("true data",data.toString());
                TrueSDK.getInstance().onActivityResultObtained(this,resultCode, data);
//        try {
//            if (mTrueClient != null && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
                callbackManager.onActivityResult(requestCode, resultCode, data);
                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                }
            } else {
                if (mProgressDialog != null) {

                    mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    mProgressDialog.setConfirmText("OK");
                    mProgressDialog.setTitleText("Could not connect to internet");
                    mProgressDialog.setContentText("Please check your internet connection and try again.");
                    mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //intentionally left blank.
                            if (mProgressDialog != null) {
                                mProgressDialog.dismissWithAnimation();
                            }
                        }
                    });
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            if (mProgressDialog != null) {

                mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mProgressDialog.setConfirmText("OK");
                mProgressDialog.setTitleText("Could not connect to internet");
                mProgressDialog.setContentText("Please check your internet connection and try again.");
                mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //intentionally left blank.
                        if (mProgressDialog != null) {
                            mProgressDialog.dismissWithAnimation();
                        }
                    }
                });
            }
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            if(mProgressDialog!=null) {

                mProgressDialog.setTitleText(getString(R.string.getting_data));
            }
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            email = acct.getEmail();
            name = acct.getDisplayName();
            String picture = String.valueOf(acct.getPhotoUrl());
            social_id = acct.getId();
            editor.putString("profilePicURL", picture);
            editor.apply();
            password = "GoogleSignIned";

            mobile = "";
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putBoolean("loggedIn", true);
            editor.putBoolean("isSocial", true);
            editor.putBoolean("isVerified",false);
            editor.putString("whichSocial", "gplus");
            editor.putString("socialId",social_id);
            editor.apply();
            String[] names = {"gPlusId", "GCMId","name","email","profileData","profileImage"};
            String[] value = {social_id,gcm_id, name,email, String.valueOf(acct),picture};
            String url = getResources().getString(R.string.base_url) + "customerLogingPlus";
            new HttpApiCall(Login.this, url, names, value, "gplus");

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(isNetworkOn(Login.this)) {
            if (id == R.id.login_fb_button) {
                //do facebook login
                doFacebookLogin(); //In Progress
            } else if (id == R.id.login_google_button) {
                //do google login

                signIn();
            }
        }else{
            Toast.makeText(Login.this, "Network error. Check your network connection and try again", Toast.LENGTH_SHORT).show();
        }
//        if (id == R.id.user_signin_button) {
//            signupContainer.setVisibility(View.GONE);
//            loginContainer.setVisibility(View.VISIBLE);
//        } else if (id == R.id.switch_to_signup) {
//            loginContainer.setVisibility(View.GONE);
//            signupContainer.setVisibility(View.VISIBLE);
//        }

    }

    void doFacebookLogin(){
        mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.com_facebook_blue));
        mProgressDialog.setTitleText("Facebook login");
        mProgressDialog.setContentText(getString(R.string.logging_holder));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");

        LoginManager.getInstance().logInWithReadPermissions(Login.this, permissions);


    }


    void doCustomSignin(){
        try {
            userNameSignUp = (EditText) findViewById(R.id.userNameEditText);
            mobile = String.valueOf(userNameSignUp.getText());
            if (mobile.length() == 10) {
                editor.putString("mobile", mobile);
                editor.commit();

                if (otpSent) {
                    if (otpView.getCode().length == 6) {
                        Character[] chars = otpView.getCode();
                        password = "";
                        for (int i = 0; i < chars.length; i++) {
                            password = password + chars[i];
                        }
                        mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
                        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        mProgressDialog.setTitleText(getString(R.string.logging_holder));
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        if (otpSentAlt) {
                            EditText trueFirstName = findViewById(R.id.firstName);
                            EditText trueSecondName = findViewById(R.id.secondName);
                            EditText trueEmail = findViewById(R.id.emailTrue);
                            if (trueFirstName.getText().length() > 1) {
                                if (trueSecondName.getText().length() > 1) {
                                    name = trueFirstName.getText() + " " + trueSecondName.getText();
                                    email = "";
                                    TrueProfile.Builder builder = new TrueProfile.Builder(String.valueOf(trueFirstName.getText()), String.valueOf(trueSecondName.getText()));
                                    if (trueEmail.getText().length() > 4) {
                                        email = trueEmail.getText() + "";
                                        builder.setEmail(String.valueOf(trueEmail.getText()));
                                    }
                                    TrueProfile pro = builder.build();
                                    TrueSDK.getInstance().verifyOtp(pro, password, Login.this);
                                } else {
                                    trueSecondName.setError("Enter Valid Last Name");
                                    trueSecondName.requestFocus();
                                }
                            } else {
                                trueFirstName.setError("Enter Valid First Name");
                                trueFirstName.requestFocus();
                            }
                        } else {
                            String[] splited = name.split("\\s+");
                            TrueProfile.Builder builder;
                            if (splited.length > 1) {
                                builder = new TrueProfile.Builder(splited[0], splited[1]);
                            } else {
                                builder = new TrueProfile.Builder(splited[0], splited[0]);
                            }
                            builder.setEmail(email);
                            TrueProfile pro = builder.build();
                            TrueSDK.getInstance().verifyOtp(pro, password, Login.this);
                        }
//                    String[] splited = name.split("\\s+");
//                    TrueProfile.Builder builder = new TrueProfile.Builder(splited[0], splited[1]);
//                    builder.setEmail(email);
//                    TrueProfile pro = builder.build();
//                    TrueSDK.getInstance().verifyOtp(pro,password,Login.this);

                    } else {
                        Toast.makeText(Login.this, "Please enter 6-digit OTP", Toast.LENGTH_SHORT).show();
                    }
                } else  {
                    sendOTP();
                }
            } else {
                userNameSignUp.setError("Please enter valid mobile number.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void sendOTP(){
        mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
        mProgressDialog.setTitleText("Sending OTP...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        if (mobile.length()==10) {
            if (isSocial){
                if (mProgressDialog!=null) {
                    mProgressDialog.dismiss();
                }
                otpView.setVisibility(View.VISIBLE);
                resendOTPView.setVisibility(View.VISIBLE);
                resendOTPView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resendOTP();
                    }
                });
                userNameSignUp.setVisibility(View.GONE);
                userNameSignUpView.setVisibility(View.GONE);
                signin.setText("Verify OTP");
                otpSent = true;
                otpSentAlt = false;
                TrueSDK.getInstance().requestVerification("IN",mobile,Login.this);
            }else {
                String[] names = {"mobile", "GCMId"};
                String[] value = {mobile, ""};
                String url = getResources().getString(R.string.base_url) + "customerDetails";
                new HttpApiCall(Login.this, url, names, value, "customerDetails");
            }
        }else{
            Toast.makeText(this, "Please Enter a valid 10 digit mobile number", Toast.LENGTH_SHORT).show();
        }
    }

    void resendOTP(){
        if (isSocial){
            String deviceName = Build.DEVICE;
            InstaCash.getInstance().trackEvent("Login", "Social-TrueCaller OTP verified", deviceName);
            String yourURL = getResources().getString(R.string.base_url) + "socialMobileConnect";
            String[] names= new String[6];
            String[] values = new String[6];
            names[0] = "socialMediaType";
            names[1] = "socialMediaId";
            names[2] = "mobile";
            names[3] = "GCMId";
            names[4] = "internalOTP";
            names[5] = "OTPLength";

            if(isGplus){
                values[0] = "gplus";
            }
            else{
                values[0] = "facebook";
            }
            values[1] = social_id;
            values[2] = mobile;
            values[3] = gcm_id;
            values[4] = "1";
            values[5] = "6";
            new HttpApiCall(this, yourURL, names, values, "socialMobileConnect");
        }else {
            String deviceName = Build.DEVICE;
            InstaCash.getInstance().trackEvent("Login", "TrueCaller OTP verified", deviceName);
            String profile_pic = "";
            String id = "";

            editor.putString("name", name);
            editor.putString("email", email);
            editor.putBoolean("loggedIn", true);
            editor.putBoolean("isVerified", true);
            editor.apply();
            mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
            mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.com_facebook_blue));
            mProgressDialog.setTitleText("TrueCaller Login");
            mProgressDialog.setContentText(getString(R.string.logging_holder));
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            String[] names = {"trueId", "GCMId", "name", "email", "phone", "profileImage","OTPLength"};
            String[] value = {id, gcm_id, name, email, mobile, profile_pic,"6"};
            String url = getResources().getString(R.string.base_url) + "sendOTP";
            if (!trueDone) {
                new HttpApiCall(Login.this, url, names, value, "trueLogin");
                trueDone = true;
            }
        }
    }

//    void sendOTPOld(){
//        mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
//        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
//        mProgressDialog.setTitleText("Sending OTP...");
//        mProgressDialog.setCancelable(true);
//        mProgressDialog.show();
//        String[] names = {"mobile"};
//        String[] value = {mobile};
//        String url = getResources().getString(R.string.base_url) + "sendOTP";
//        new HttpApiCall(Login.this, url, names, value, "sendOtp");
//    }

    void doCustomSignup(){
        nameSignUp = (EditText) findViewById(R.id.nameSignUp);
        userNameSignUp = (EditText) findViewById(R.id.userNameSignUp);
        emailSignUp = (EditText) findViewById(R.id.emailSignUp);
        passwordSignUp = (EditText) findViewById(R.id.passwordSignUp);
        repeatPasswordSignUp = (EditText) findViewById(R.id.repeatPasswordSignUp);

        name = String.valueOf(nameSignUp.getText());
        mobile = String.valueOf(userNameSignUp.getText());
        email = String.valueOf(emailSignUp.getText());
        password = String.valueOf(passwordSignUp.getText());
        String confirmPass = String.valueOf(repeatPasswordSignUp.getText());
        if (!(name.length()>0) ){
            nameSignUp.setError("Please enter Name.");
            //errorText.setVisibility(View.VISIBLE);
        }
        else if(mobile.length()!=10){
            userNameSignUp.setError("Please enter valid Mobile Number.");
            // errorText.setVisibility(View.VISIBLE);
        }
        else if (!(password.length()>4) ){
            passwordSignUp.setError("The password must be atleast 5 characters long.");
            //errorText.setVisibility(View.VISIBLE);
        }
        else if (!password.equals(confirmPass)){
            repeatPasswordSignUp.setError("Password and Confirm password do not match.");
            // errorText.setVisibility(View.VISIBLE);
        }
        else{
            mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
            mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
            mProgressDialog.setTitleText("Creating Account");
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            String url = getResources().getString(R.string.base_url) + "customerRegister";
            new HttpPostCall().execute(url, "signup");
        }
    }

    @Override
    public void onStart() {
        try {
            InstaCash.getInstance().trackScreenView("Login");
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void signIn() {
        showProgressDialog();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        try {
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.google_button_color));
        mProgressDialog.setTitleText(getString(R.string.logging_holder));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }


    @Override
    public void webCallResponse(String response , String flag) {

        if(response!=null) {
            try {

                JSONObject object = new JSONObject(response);
                String status = object.getString("status").toLowerCase();

                if(flag.equals("sendOtp")){
                    if (mProgressDialog!=null) {
                        mProgressDialog.dismiss();
                    }
                    otpView.setVisibility(View.VISIBLE);
                    resendOTPView.setVisibility(View.VISIBLE);
                    resendOTPView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resendOTP();
                        }
                    });
                    userNameSignUp.setVisibility(View.GONE);
                    userNameSignUpView.setVisibility(View.GONE);
                    signin.setText("Verify OTP");
                    otpSent = true;
                }else if(flag.equals("customerDetails")){
                    if (status.equals("success")) {
                        if (mProgressDialog!=null) {
                            mProgressDialog.dismiss();
                        }
                        otpView.setVisibility(View.VISIBLE);
                        resendOTPView.setVisibility(View.VISIBLE);
                        resendOTPView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resendOTP();
                            }
                        });
                        userNameSignUp.setVisibility(View.GONE);
                        userNameSignUpView.setVisibility(View.GONE);
                        signin.setText("Verify OTP");
                        otpSent = true;
                        otpSentAlt = false;
                        name = object.getJSONObject("msg").getString("name");
                        email = object.getJSONObject("msg").getString("email");
                        TrueSDK.getInstance().requestVerification("IN",mobile,Login.this);
                    } else{
                        if (mProgressDialog!=null) {
                            mProgressDialog.dismiss();
                        }
                        otpView.setVisibility(View.VISIBLE);
                        findViewById(R.id.trueOTPView).setVisibility(View.VISIBLE);

                        resendOTPView.setVisibility(View.VISIBLE);
                        resendOTPView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resendOTP();
                            }
                        });
                        userNameSignUp.setVisibility(View.GONE);
                        userNameSignUpView.setVisibility(View.GONE);
                        signin.setText("Verify OTP");
                        otpSent = true;
                        otpSentAlt = true;
                        TrueSDK.getInstance().requestVerification("IN",mobile,Login.this);
                    }
                } else if (flag.equals("socialMobileConnect")){
                    if (status.equals("success")) {
                        if (mProgressDialog!=null) {
                            name = object.getJSONObject("msg").getString("name");
                            email = object.getJSONObject("msg").getString("email");
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putBoolean("loggedIn", true);
                            editor.putBoolean("isVerified", true);
                            editor.commit();
                            mProgressDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            mProgressDialog.setConfirmText("OK");
                            mProgressDialog.setTitleText("Logged in");
                            mProgressDialog.setContentText("You are now logged in as " + name);
                            mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (mProgressDialog!=null) {
                                        mProgressDialog.dismissWithAnimation();
                                    }
                                    finish();
                                }
                            });
                        }
                    }

                }else {
                    if (status.equals("success")) {
                        boolean hasMobile = false;
                        customerId = object.getJSONObject("msg").getString("userId");
                        String phone = "";
                        isSocial = true;
                        if (object.getJSONObject("msg").has("mobile")) {
                            phone = object.getJSONObject("msg").getString("mobile");
                            hasMobile = true;
                        }
                        boolean isVerified = false;
                        if (object.getJSONObject("msg").has("status")) {
                            isVerified = (object.getJSONObject("msg").getString("status").toLowerCase().equals("verified"));
                        }
                        editor.putString("customerId", customerId);
                        editor.putBoolean("isVerified", isVerified);
                        editor.putString("mobile", phone);
                        editor.commit();
                        if (isVerified) {

                            if (mProgressDialog!=null) {
                                mProgressDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                mProgressDialog.setConfirmText("OK");
                                mProgressDialog.setTitleText("Logged in");
                                mProgressDialog.setContentText("You are now logged in as " + name);
                                mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        if (mProgressDialog!=null) {
                                            mProgressDialog.dismissWithAnimation();
                                        }
                                        finish();
                                    }
                                });
                            }
                        } else {
//                            final Intent intent = new Intent(Login.this, PhoneVerification.class);
//                            intent.putExtra("isSocial", true);
//                            intent.putExtra("fromSignin", true);
//                            if (flag.equals("gplus")) {
//                                intent.putExtra("gplus", true);
//                            } else {
//                                intent.putExtra("fb", true);
//                            }
//                            if (mProgressDialog!=null) {
//                                mProgressDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                                mProgressDialog.setConfirmText("OK");
//                                mProgressDialog.setTitleText("Logged in");
//                                mProgressDialog.setContentText("You are now logged in as " + name);
//                                mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        if (mProgressDialog!=null) {
//                                            mProgressDialog.dismissWithAnimation();
//                                        }
//                                        startActivity(intent);
//
//                                        finish();
//                                    }
//                                });
//                            }
                            if (flag.equals("gplus")) {
                                isGplus = true;
                            }
                            if (mProgressDialog!=null) {
                                mProgressDialog.dismiss();
                            }
                            mContainer.setVisibility(View.VISIBLE);
                            facebookButton.setVisibility(View.GONE);
                            googlePlusButton.setVisibility(View.GONE);
                            findViewById(R.id.trueButton).setVisibility(View.GONE);
                            signin.setText("Send OTP");
                            if (hasMobile && phone!=null && !phone.equals("null")){
                                userNameSignUp = (EditText) findViewById(R.id.userNameEditText);
                                userNameSignUp.setText(phone);
                                signin.callOnClick();
                            }

                        }
                    } else {
                        if (mProgressDialog!=null) {

                            mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            mProgressDialog.setConfirmText("OK");
                            mProgressDialog.setTitleText("Error");
                            mProgressDialog.setContentText("Please make sure You've input correct details");
                            mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    //intentionally left blank.
                                    if (mProgressDialog!=null) {
                                        mProgressDialog.dismissWithAnimation();
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (mProgressDialog!=null) {

                    mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    mProgressDialog.setConfirmText("OK");
                    mProgressDialog.setTitleText("Error");
                    mProgressDialog.setContentText("Please check your internet connection and try again after sometime");
                    mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //intentionally left blank.
                            if (mProgressDialog!=null) {
                                mProgressDialog.dismissWithAnimation();
                            }
                        }
                    });
                }
            }
//
        }
        else{
            if (mProgressDialog!=null) {

                mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mProgressDialog.setConfirmText("OK");
                mProgressDialog.setTitleText("Could not connect to internet");
                mProgressDialog.setContentText("Please check your internet connection and try again.");
                mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //intentionally left blank.
                        if (mProgressDialog!=null) {
                            mProgressDialog.dismissWithAnimation();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(mProgressDialog==null) {


            mProgressDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            mProgressDialog.setConfirmText("OK");
            mProgressDialog.setTitleText("Could not connect to internet");
            mProgressDialog.setContentText("Please check your internet connection and try again after sometime");
            mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    //intentionally left blank.
                    if (mProgressDialog!=null) {
                        mProgressDialog.dismissWithAnimation();
                    }
                }
            });
        }
    }

    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
        Log.d("success Profile Shared","true");
        try {
            Log.d("trueProfile", String.valueOf(trueProfile.firstName) + trueProfile.lastName + ": "+ trueProfile.phoneNumber);
            String profile_pic = trueProfile.avatarUrl;
            if (trueProfile.phoneNumber != null) {
                mobile = trueProfile.phoneNumber;
            }
            editor.putString("profilePicURL", String.valueOf(profile_pic));
            email = trueProfile.email;
            String id = trueProfile.url;
            name = trueProfile.firstName + " " + trueProfile.lastName;
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putBoolean("loggedIn", true);
            editor.putBoolean("isVerified", true);
            editor.apply();
            mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
            mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.com_facebook_blue));
            mProgressDialog.setTitleText("TrueCaller Login");
            mProgressDialog.setContentText(getString(R.string.logging_holder));
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            String[] names = {"trueId", "GCMId", "name", "email", "phone", "profileImage"};
            String[] value = {id, gcm_id, name, email, mobile, profile_pic};
            String url = getResources().getString(R.string.base_url) + "customerLoginTrueCaller";
            if (!trueDone) {
                new HttpApiCall(Login.this, url, names, value, "trueLogin");
                trueDone = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
//        Log.d("truecaller profile",trueError.getErrorType()+"");
    }

    @Override
    public void onOtpRequired() {

        Toast.makeText(this, "Please Enter mobile number to Continue", Toast.LENGTH_LONG).show();
        switchViewToOtp();
    }

    @Override
    public void onOtpSuccess(int i,String s) {
        if (i==MODE_VERIFIED){
            if (isSocial){
                String deviceName = Build.DEVICE;
                InstaCash.getInstance().trackEvent("Login", "Social-TrueCaller OTP verified", deviceName);
                String yourURL = getResources().getString(R.string.base_url) + "socialMobileConnect";
                String[] names= new String[5];
                String[] values = new String[5];
                names[0] = "socialMediaType";
                names[1] = "socialMediaId";
                names[2] = "mobile";
                names[3] = "GCMId";
                names[4] = "internalOTP";

                if(isGplus){
                    values[0] = "gplus";
                }
                else{
                    values[0] = "facebook";
                }
                values[1] = social_id;
                values[2] = mobile;
                values[3] = gcm_id;
                values[4] = "0";
                new HttpApiCall(this, yourURL, names, values, "socialMobileConnect");
            }else {
                String deviceName = Build.DEVICE;
                InstaCash.getInstance().trackEvent("Login", "TrueCaller OTP verified", deviceName);
                String profile_pic = "";
                String id = "";

                editor.putString("name", name);
                editor.putString("email", email);
                editor.putBoolean("loggedIn", true);
                editor.putBoolean("isVerified", true);
                editor.apply();
                mProgressDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
                mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.com_facebook_blue));
                mProgressDialog.setTitleText("TrueCaller Login");
                mProgressDialog.setContentText(getString(R.string.logging_holder));
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                String[] names = {"trueId", "GCMId", "name", "email", "phone", "profileImage"};
                String[] value = {id, gcm_id, name, email, mobile, profile_pic};
                String url = getResources().getString(R.string.base_url) + "customerLoginTrueCaller";
                if (!trueDone) {
                    new HttpApiCall(Login.this, url, names, value, "trueLogin");
                    trueDone = true;
                }
            }
        } else if (i == MODE_OTP_SENT){
            String deviceName = Build.DEVICE;
            InstaCash.getInstance().trackEvent("Login", "TrueCaller OTP sent", deviceName);
            Toast.makeText(this, "OTP Sent : ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOtpFailure(int i, @NonNull TrueException e) {
//        Log.d("error",e.getExceptionMessage());
        Toast.makeText(this, e.getExceptionMessage(), Toast.LENGTH_SHORT).show();
    }

    public class HttpPostCall extends AsyncTask<String,String,String> {
        HttpResponse response;
        String type = "";

        @Override
        protected String doInBackground(String... params) {

            String responseText = "";
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
                InputStream cert = getResources().openRawResource(R.raw.my_cert_live);
                Certificate ca;
                try {
                    ca = cf.generateCertificate(cert);
                } finally { cert.close(); }

                // creating a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                // creating a TrustManager that trusts the CAs in our KeyStore
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // creating an SSLSocketFactory that uses our TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, tmf.getTrustManagers(), null);
// Tell the okhttp to use a SocketFactory from our SSLContext
                OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory()).build();


                HttpUrl.Builder urlBuilder = HttpUrl.parse(params[0]).newBuilder();
                String url = urlBuilder.build().toString();


                String postBody = "userName="+getString(R.string.userName) + "&apiKey="+getString(R.string.apiKey)+"&GCMId="+gcm_id;

                if (params[1].equals("signup")) {
                    postBody = postBody+"&name="+name;
                    postBody = postBody+"&mobile="+mobile;
                    postBody = postBody+"&email="+email;
                    postBody = postBody+"&password="+password;
                } else if(params[1].equals("signin")) {
                    if (otpSent){
                        postBody = postBody+"&otp="+password;
                    }else{
                        postBody = postBody+"&password="+password;
                    }
                    postBody = postBody+"&mobile="+mobile;
                }

//                Log.d(params[0],postBody);

                RequestBody body = RequestBody.create(JSON, postBody);


                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();


                Response response = client.newCall(request).execute();
                responseText = response.body().string();
//                Log.d("response",responseText);

            } catch (Exception e) {
                e.printStackTrace();
            }

//            HttpClient client = new DefaultHttpClient();
//            String address = params[0];
//            String responseText = null;
//            type = params[1];
//            //deviceName = DeviceName.getDeviceName();
//            org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(address);
//            try {
//
//                // Add your data
//                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
//                nameValuePairs.add(new BasicNameValuePair("userName", getString(R.string.userName)));
//                nameValuePairs.add(new BasicNameValuePair("apiKey", getString(R.string.apiKey)));
//                nameValuePairs.add(new BasicNameValuePair("GCMId", gcm_id));
//
//                if (params[1].equals("signup")) {
//                    nameValuePairs.add(new BasicNameValuePair("name", name));
//                    nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
//                    nameValuePairs.add(new BasicNameValuePair("email", email));
//                    nameValuePairs.add(new BasicNameValuePair("password", password));
//                } else if(params[1].equals("signin")) {
//                    if (otpSent){
//                        nameValuePairs.add(new BasicNameValuePair("otp", password));
//                    }else{
//                        nameValuePairs.add(new BasicNameValuePair("password", password));
//                    }
//                    nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
//
//                }
//                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                // Execute HTTP Post Request
//                response = client.execute(post);
//                responseText = EntityUtils.toString(response.getEntity());
//
//            } catch (IOException e) {
//            }

            return responseText;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {


                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equals("Success")) {
                        if(type.equals("signin")){
//                            Log.d("response",s);

                            name = jsonObject.getJSONObject("msg").getString("name");
                            email = jsonObject.getJSONObject("msg").getString("email");
                            isVerified = jsonObject.getJSONObject("msg").getString("status");
                            customerId = jsonObject.getJSONObject("msg").getString("userId");
                            if (mProgressDialog!=null) {

                                mProgressDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                mProgressDialog.setTitleText("Sign in Successful");

                                if (name!=null) {
                                    if (name.toLowerCase().equals("null")) {
                                        mProgressDialog.setContentText("Welcome Back!");
                                    } else {
                                        mProgressDialog.setContentText("Welcome Back " + name + "!");
                                    }
                                }
                                else
                                    mProgressDialog.setContentText("Welcome Back!");
                            }
                            editor.putString("customerId", customerId);
                            editor.putString("name",name);
                            editor.putString("email",email);
                            editor.commit();
                        }
                        else {

                            customerId = jsonObject.getJSONObject("msg").getString("userId");
                            isVerified = jsonObject.getJSONObject("msg").getString("status");
                            name = jsonObject.getJSONObject("msg").getString("name");
                            editor.putString("customerId", customerId);
                            editor.putString("name",name);
                            editor.putString("mobile",mobile);
                            editor.putString("email",email);
                            editor.commit();
                            if (mProgressDialog!=null) {

                                mProgressDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                mProgressDialog.setTitleText("Sign up Successful");
                                mProgressDialog.setContentText("Welcome " + name + "!");
                            }
                        }
                        try {
                            String deviceName = Build.DEVICE;
                            InstaCash.getInstance().trackEvent("Login", "Success", deviceName);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(isVerified.equals("verified"))
                        {
                            editor.putBoolean("isVerified", true);
                        }
                        else{
                            editor.putBoolean("isVerified", false);
                            if(mobile.length()==10){
                                isMobile = true;
                            }
                        }

                        editor.putBoolean("loggedIn", true);
                        if(mobile.length()==10){
                            editor.putString("mobile", mobile);
                        }
                        editor.commit();
                        if (mProgressDialog!=null) {

                            mProgressDialog.setConfirmText("Ok");
                            mProgressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (mProgressDialog!=null) {
                                        mProgressDialog.dismissWithAnimation();
                                    }
                                    if (isMobile && !(isVerified.toLowerCase().equals("verified"))) {
                                        Intent intent = new Intent(Login.this, PhoneVerification.class);
                                        intent.putExtra("mobile", mobile);
                                        intent.putExtra("fromSignin", true);
                                        startActivity(intent);
                                    }
                                    if ((isVerified.toLowerCase().equals("verified"))) {
                                        editor.putBoolean("isVerified", true);
                                        editor.commit();
                                    }
                                    finish();
                                }
                            });
                        }
                    }
                    else if(type.equals("signin")){
                        userNameSignUp.setError("username or password is invalid");
                        passwordSignUp.setError("username or password is invalid");
                        if (mProgressDialog!=null) {

                            mProgressDialog.dismissWithAnimation();
                        }
                    }
                    else{
                        Toast.makeText(Login.this, "Please make sure You entered correct details.", Toast.LENGTH_SHORT).show();
                        if (mProgressDialog!=null) {

                            mProgressDialog.dismissWithAnimation();
                        }
                    }
                }
                else{
                    String deviceName = Build.DEVICE;
                    InstaCash.getInstance().trackEvent("Login", "No Internet", deviceName);
                    Toast.makeText(Login.this, "Internet seems to be disconnected please make sure it is on", Toast.LENGTH_SHORT).show();
                    if (mProgressDialog!=null) {

                        mProgressDialog.dismissWithAnimation();
                    }
                }
            }catch (Exception e){
                if (mProgressDialog!=null) {
                    mProgressDialog.dismissWithAnimation();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent i = new Intent();
            setResult(4, i);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to Exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
