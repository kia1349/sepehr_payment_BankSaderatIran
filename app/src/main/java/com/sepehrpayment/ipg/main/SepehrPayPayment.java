package com.sepehrpayment.ipg.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


import com.sepehrpayment.ipg.R;
import com.sepehrpayment.ipg.constant.ConstantValues;

import org.json.JSONObject;


public class SepehrPayPayment extends AppCompatActivity {
    String LinkSend = ConstantValues.ECOMMERCE_URL + "SepehrPaySend.php";

    String MerchantID = "";
    String Amount = "";
    String Description = "";
    String Email = "";
    String Mobile = "";
    int Active = 0;

    String Result;
    int level;
    String success;
    int value;
    String message;
    String tracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Active = 0;
        ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
        ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "";
        ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "";

        if (getIntent().hasExtra("Active")) {
            Active = getIntent().getExtras().getInt("Active");//When the intent has "Active" in its parameters, it means that we should take the purchase information and direct the customer to the payment gateway.
        } else {
            Active = 0;//When the intent hasn't "Active" in its parameters, it means that we should take the result of purchase proccess, and Return the customer to the Android application again.
        }


        if (Active == 1) {
            MerchantID = getIntent().getExtras().getString("MerchantID");
            Amount = getIntent().getExtras().getString("Amount");
            Description = getIntent().getExtras().getString("Description");
            Email = getIntent().getExtras().getString("Email");
            Mobile = getIntent().getExtras().getString("Mobile");
            ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "انصراف از پرداخت";
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "انصراف از پرداخت";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(LinkSend + "?EcommerceUrl=" + ConstantValues.ECOMMERCE_URL + "&MerchantID=" + MerchantID +
                            "&Amount=" + Amount + "&Description=" + Description + "&Email=" + Email + "&Mobile=" + Mobile))
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);

//          if (browserIntent.resolveActivity(getPackageManager()) != null) {
            if (getPackageManager().resolveActivity(browserIntent, 0) != null) {
                startActivity(browserIntent);
            }
            ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "انصراف از پرداخت";
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "انصراف از پرداخت";
            finish();

            RTLToast.Config.getInstance()
                    .setTextColor(ContextCompat.getColor(this, R.color.sepehr))
//                .setToastTypeface(Typeface.createFromAsset(getAssets(), "IRANSans.ttf"))
//                    .setToastTypeface(ResourcesCompat.getFont(this, R.font.iransansmobile))
                    .apply();
            RTLToast.custom(this, R.string.custom_message, ContextCompat.getDrawable(this, R.drawable.sepehrpay),
                    ContextCompat.getColor(this, R.color.blue_800), Toast.LENGTH_SHORT, true, true).show();
            RTLToast.Config.reset();
            ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "انصراف از پرداخت";
            ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "انصراف از پرداخت";
        } else {
            if (getIntent().getData() != null && getIntent().getData().getScheme().equalsIgnoreCase("aryaclub")) {

                ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "";
                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "";

                Result = getIntent().getData().getQueryParameter("result");
                level = Integer.parseInt(getIntent().getData().getQueryParameter("level"));
                try {

                    JSONObject obj = new JSONObject(Result);
                    success = obj.getString("success");
                    value = obj.getInt("value");
                    message = obj.getString("message");
                    tracking = obj.getString("tracking");

                    if (level == 1 && value == 1) {
                        switch (success) {
                            case "OK1":
                                ConstantValues.IS_USER_PAID_SEPEHRPAY = true;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = message + "\n\n" + tracking;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = message + "<br/>" + tracking;
                                break;

                            case "OK2":
                            case "ERROR":
                            case "CANCELL":
                                ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = message + "\n\n" + tracking;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = message + "<br/>" + tracking;
                                break;

                            default:
                                ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "خطای ناشناس";
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY_WEB_VIEW = "خطای ناشناس";
                                break;

                        }

                    } else if (level == 0 && value == 0) {
                        switch (success) {
                            case "ERROR1":
                            case "ERROR2":
                            case "ERROR3":
                                ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = message + "\n\n" + tracking;
                                break;

                            default:
                                ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                                ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "خطای ناشناس";
                                break;

                        }

                    }

                } catch (Throwable tx) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + Result + "\"");
                    ConstantValues.IS_USER_PAID_SEPEHRPAY = false;
                    ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY = "خطای ناشناس";
                }

                if (ConstantValues.IS_USER_PAID_SEPEHRPAY) {
                    setReturnToMain();
                } else {
                }
                this.onBackPressed();
            }
        }
    }

    public void setReturnToMain() {
        Intent checkoutintent = new Intent();
        checkoutintent.putExtra("IS_USER_PAID_SEPEHRPAY", ConstantValues.IS_USER_PAID_SEPEHRPAY);
        checkoutintent.putExtra("MESSAGE_USER_PAID_SEPEHRPAY", ConstantValues.MESSAGE_USER_PAID_SEPEHRPAY);
        setResult(Activity.RESULT_OK, checkoutintent);
        finish();
    }

}
