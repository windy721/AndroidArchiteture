package com.jim.androidarchiteture.common;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by JimGong on 2015/7/9.
 */
public final class PhoneUtils {

    public static void dail(Context pContext, String pPhoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pPhoneNumber));
        if (intent.resolveActivity(pContext.getPackageManager()) != null) {
            pContext.startActivity(intent);
        }
    }

    public static void call(Context pContext, String pPhoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + pPhoneNumber));
        if (ActivityCompat.checkSelfPermission(pContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pContext.startActivity(intent);
    }

    public static String getDisplayPhoneNO(String pPhoneNO) {
        final int PHONE_NO_LENGTH = 11;
        if (StringUtils.isBlank(pPhoneNO) || pPhoneNO.length() < PHONE_NO_LENGTH) {
            return pPhoneNO;
        }
        return pPhoneNO.substring(0, 3) + "****" + pPhoneNO.substring(7);
    }

    // 获取库Phone表字段
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    // 联系人显示名称
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    // 电话号码
    private static final int PHONES_NUMBER_INDEX = 1;
    public static ArrayList<Contact> getPhoneContacts(Context pContext) {
        ArrayList<Contact> contacts = new ArrayList<>();
        ContentResolver resolver = pContext.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // validate
                if (StringUtils.isEmpty(phoneNumber)) {
                    continue;
                }

                Contact contact = new Contact();
                contact.mContactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                contact.mContactNumber = phoneNumber.replace(" ", "");
                contacts.add(contact);
            }

            phoneCursor.close();
        }

        return contacts;
    }

    public static class Contact {
        public String mContactName;
        public String mContactNumber;
    }
}
