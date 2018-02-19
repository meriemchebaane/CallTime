package com.example.chebaane.calltime;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Contact List
    public static List<Contact> listcontact = new ArrayList<Contact>();

    // Pour la gestion des autorisations Android
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    //Float Action Button
    private FloatingActionButton mFloatingActionButton;

    //Firebase
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mContactsDatabaseReference;
    private static DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCallDetails();
        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mContactsDatabaseReference = mFirebaseDatabase.getReference();

        //FloatingActionButton sends list of contacts
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get all contact
                //the cursor is like a record set
                Cursor cur = null;

                //get the connection with data base of contact
                ContentResolver cr = getContentResolver();
                try {
                    //get all contact without filtering data
                    cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                            null, null, null, null);
                } catch (Exception exp) {
                    Log.e("ERROR ON CONTACTS", exp.getMessage());
                }
                //has contact??
                if (cur.getCount() > 0) {

                    while (cur.moveToNext()) {

                        //init contact
                        Contact contact = new Contact();

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        contact.id = id;
                        contact.name = name;

                        //get phone number
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            //Query phone here.  Covered next
                            if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                        new String[]{id}, null);

                                while (pCur.moveToNext()) {
                                    String Number = pCur.getString(pCur.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                                    contact.numero = Number;
                                }
                                pCur.close();

                            }
                            listcontact.add(contact);
                            //add a new contact in firebase
                            mContactsDatabaseReference.child("contacts").setValue(listcontact);
                        }
                        //Show list
                        //  ContactArrayAdapter adapter =new ContactArrayAdapter(this,listcontact);


                    }
                }
            }


        });
    }

    private void getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = null;
        //get the connection with data base of contact
        ContentResolver cr = getContentResolver();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("permession granted"," permession granted");
            managedCursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
            int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
            int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
            sb.append( "Call Details :");
            while ( managedCursor.moveToNext() ) {
                String phNumber = managedCursor.getString( number );
                String callType = managedCursor.getString( type );
                String callDate = managedCursor.getString( date );
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString( duration );
                String dir = null;
                int dircode = Integer.parseInt( callType );
                switch( dircode ) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
                sb.append("\n----------------------------------");
            }
            managedCursor.close();
            Log.d("log : ", sb.toString());
            Toast.makeText(this, sb.toString(),
                    Toast.LENGTH_LONG).show();

        }
        else{

            Log.d("No permession","No permession");
        }
        return;
    }

}
