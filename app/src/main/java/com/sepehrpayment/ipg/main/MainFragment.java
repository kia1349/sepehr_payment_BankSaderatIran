package com.sepehrpayment.ipg.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sepehrpayment.ipg.R;
import com.sepehrpayment.ipg.constant.ConstantValues;

import static android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN;

public class MainFragment extends Fragment {

    //sepehrpay variables
    String SepehrAmount = "";
    String SepehrDescription = "";
    String SepehrEmail = "";
    String SepehrMobile = "";
    int checkoutTotal = 0;


    View rootView;
    ImageButton checkout_payment_btn;
    EditText checkout_subtotal, customer_phone;
    WebView payment_result;
    int semiTransparentGrey = Color.argb(175, 185, 185, 185);

    String styleSheet = "<style> " +
            "body{background:#ffffff; margin:0; padding:0} " +
            "p{color:#757575;} " +
            "img{display:inline; height:auto; max-width:100%;}" +
            "</style>";


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);

        checkout_subtotal = (EditText) rootView.findViewById(R.id.checkout_subtotal);
        checkout_subtotal.setText("111");
        checkout_subtotal.addTextChangedListener(watcher);

        customer_phone = (EditText) rootView.findViewById(R.id.customer_phone);
        customer_phone.setText("09122398658");
        customer_phone.addTextChangedListener(watcher);

        checkout_payment_btn = (ImageButton) rootView.findViewById(R.id.checkout_payment_btn);
        checkout_payment_btn.setEnabled(false);
        checkout_payment_btn.setColorFilter(Color.WHITE);

        payment_result = (WebView) rootView.findViewById(R.id.payment_result);
        payment_result.getSettings().setJavaScriptEnabled(true);
        payment_result.setWebViewClient(new WebViewClient());
        payment_result.setVisibility(View.GONE);


        if (!checkout_subtotal.getText().toString().isEmpty() && !customer_phone.getText().toString().isEmpty()
                && customer_phone.length() >10 && Integer.parseInt(checkout_subtotal.getText().toString())> 100) {
            checkout_payment_btn.setEnabled(true);
            checkout_payment_btn.setColorFilter(null);
        }



        checkout_subtotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        customer_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //get customer Email
        SepehrEmail = "youremail@youremail.com";

        // Handle the Click event of checkout_order_btn Button
        checkout_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Delay of 2 seconds
                hideKeyboard(view);
                payment_result.loadDataWithBaseURL(null, "<html dir=\"rtl\" lang=\"\"><body>" + styleSheet + " " + "</body></html>", "text/html", "utf-8", null);
                payment_result.setVisibility(View.GONE);

                //get amount
                try {
                    checkoutTotal = Integer.parseInt(checkout_subtotal.getText().toString());
                } catch (NumberFormatException e) {
                    // handle the exception
                    checkoutTotal = 0;
                }

                //get customer phone
                try {
                    SepehrMobile = customer_phone.getText().toString();
                } catch (NumberFormatException e) {
                    // handle the exception
                    SepehrMobile = "";
                }



                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (checkoutTotal > 100) { //for pay in sepehrpay , The amount should be more than 100 toman

                            SepehrAmount = String.valueOf(checkoutTotal);
                            SepehrDescription = "خرید از درگاه سپهر پرداخت";

                            Intent sepehrintent = new Intent(getContext(), SepehrPayPayment.class);

                            // send the same configuration for restart resiliency
                            sepehrintent.putExtra("Active", 1);
                            sepehrintent.putExtra("MerchantID", ConstantValues.SEPEHRPAY_PUBLISHABLE_KEY);
                            sepehrintent.putExtra("Amount", SepehrAmount);
                            sepehrintent.putExtra("Description", SepehrDescription);
                            sepehrintent.putExtra("Email", SepehrEmail);
                            sepehrintent.putExtra("Mobile", SepehrMobile);

                            //new way for onactivityresult
                            launchSepehrPaymentActivity.launch(sepehrintent);

                        } else {
                            Snackbar.make(view, getString(R.string.invalid_sepehrpay_Amount), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                }, 2000);

            }
        });

        return rootView;
    }

    // Create lanucher variable inside onAttach or onCreate or global
    ActivityResultLauncher<Intent> launchSepehrPaymentActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // your operation....

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                        Snackbar snackbar = Snackbar.make(rootView, ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY, Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        TextView snackTextView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                        if (ConstantValues.IS_USER_PAID_SEPEHRPAY) {
                            snackTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.light_blue_400));
                        } else {
                            snackTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.pink_200));

                        }
                        snackTextView.setMaxLines(7);
//                        snackbar.show();

                        payment_result.setVisibility(View.VISIBLE);
                        ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW.replace("\\", "");

                        if (ConstantValues.LANGUAGE_DIRECTION.equals("rtl")) {
                            payment_result.loadDataWithBaseURL(null, "<html dir=\"rtl\" lang=\"\"><body>" + styleSheet + ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW + "</body></html>", "text/html", "utf-8", null);
                        } else {
                            payment_result.loadDataWithBaseURL(null, styleSheet + ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW, "text/html", "utf-8", null);
                        }
                        checkout_payment_btn.setEnabled(false);
                        checkout_payment_btn.setColorFilter(Color.WHITE);
                        checkout_subtotal.setText("");
                        customer_phone.setText("");
                    }


                }
            });


    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
//            if (!checkout_subtotal.getText().toString().isEmpty() && !customer_phone.getText().toString().isEmpty() && !(customer_phone.length() < 11)) {
            if (!checkout_subtotal.getText().toString().isEmpty() && !customer_phone.getText().toString().isEmpty()) {
                checkout_payment_btn.setEnabled(true);
                checkout_payment_btn.setColorFilter(null);
            } else {
                checkout_payment_btn.setEnabled(false);
                Snackbar.make(rootView, getString(R.string.please_fill_all_data), Snackbar.LENGTH_SHORT).show();
                checkout_payment_btn.setColorFilter(semiTransparentGrey);

            }
        }
    };


    public void hideKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), RESULT_UNCHANGED_SHOWN);
    }

}

