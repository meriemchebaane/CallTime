package com.example.user.gridadapter;
/**
 * Created by Meriem Chebaane
 */
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    //permission for hang up
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;

    //Grid view for list contact
    private GridView gridView;
    private GridAdapter myAdapter;

    //Contact List & Calls list
    public static List<Contact> listcontact = new ArrayList<Contact>();
    public static List<Calls> listCall = new ArrayList<Calls>();

    //Float Action Button to synchronise data
    private FloatingActionButton mFloatingActionButton;

    //Firebase
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mContactsDatabaseReference;
    private static DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Gerer les permissions

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        //Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mContactsDatabaseReference = mFirebaseDatabase.getReference();
        //this will hold all the favoris numbers
        //get the favoris list from Random Forest Algorithm (Retire data from Firebase)
        final ArrayList<String> keys = new ArrayList<String>();
        final ArrayList<String> types = new ArrayList<String>();
        final ArrayList<String> occurences = new ArrayList<String>();
        final ArrayList<String> numero = new ArrayList<String>();

        final ArrayList<String> callers = new ArrayList<String>();
       // final ArrayList<String> hangup = new ArrayList<String>();

        String test = getChild();
        mContactsDatabaseReference.child(test).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //get all the children of this level
               Iterable<DataSnapshot>children= dataSnapshot.getChildren();

               //shak hand with all of them
                for(DataSnapshot child : children){
                    String key = child.getKey();
                    String type = "";
                    String occurence = "";
                    String num = "";
                    keys.add(key);
                  String s = child.getValue().toString();
                    if(s.contains(","))
                    {
                        String parts[] = s.split("\\,");
                        String p1 =parts[0];
                        if(p1.contains("=")){
                            String resultats[] = p1.split("\\=");
                             num = resultats[1];
                            numero.add(num);
                        }
                        String p2 =parts[1];
                        if(p2.contains("=")){
                            String resultats2[] = p2.split("\\=");
                            type= resultats2[1];
                            types.add(type);

                        }
                        String p3 =parts[3];
                        if(p2.contains("=")){
                            String resultats3[] = p3.split("\\=");
                            occurence= resultats3[1];
                            occurences.add(occurence);

                        }
                           if((type.equals("OUTGOING"))||(type.equals("INCOMING"))){
                       // display the favoris list
                              String message =child.getKey()+"/"+occurence;
                               callers.add(message);
                        }
                }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               Log.w("TAG", "Failed to read value.", error.toException());

            }
        });

        gridView = (GridView) findViewById(R.id.gridView);
        myAdapter = new GridAdapter(this, callers);
        gridView.setAdapter(myAdapter);


        //synchronize the data call
        getCallDetails();
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
                            mContactsDatabaseReference.child("calls").setValue(listCall);
                        }
                        //Show list
                        //  ContactArrayAdapter adapter =new ContactArrayAdapter(this,listcontact);


                    }
                }
            }


        });

        //get the current slot
        TextView period=(TextView)findViewById(R.id.period);
        period.setText(getCurrentTime());

    }

    //Function to create the slot
    private String getCurrentTime(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        SimpleDateFormat heure = new SimpleDateFormat("HH");
        Date d = new Date();

        int Heure = Integer.parseInt( heure.format(d));

        String[] slots = {"De 08 à 12","De 12 à 14","De 14 à 18","De 18 à 23","De 00 à 08"};
        String getSlot = "";
        if(Heure >= 8 && Heure <12){
            getSlot = slots[0];
        }else if(Heure >= 12 && Heure <14){
            getSlot = slots[1];
        }else if(Heure >= 14 && Heure <18){
            getSlot = slots[2];
        }else if (Heure >= 18 && Heure <=23){
            getSlot = slots[3];
        }else if (Heure >= 00 && Heure < 8){
            getSlot = slots[4];
        }
        return getSlot;

    }

    //Function to create the slot
    private String getChild(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        SimpleDateFormat heure = new SimpleDateFormat("HH");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        int Heure = Integer.parseInt( heure.format(d));
        String dayOfTheWeek = sdf.format(d);

        String[] slots = {"0812","1214","1418","1823","0008"};
        String getSlot = "";
        if(Heure >= 8 && Heure <12){
            getSlot = dayOfTheWeek+slots[0];
        }else if(Heure >= 12 && Heure <14){
            getSlot = dayOfTheWeek+slots[1];
        }else if(Heure >= 14 && Heure <18){
            getSlot = dayOfTheWeek+slots[2];
        }else if (Heure >= 18 && Heure <=23){
            getSlot = dayOfTheWeek+slots[3];
        }else if (Heure >= 00 && Heure < 8){
            getSlot = dayOfTheWeek+slots[4];
        }
        return getSlot;

    }

    // Request the permissions
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // Function to get the details of all calls and push it to Firebase
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
            int name = managedCursor.getColumnIndex( CallLog.Calls.CACHED_NAME);
            int isNew =  managedCursor.getColumnIndex( CallLog.Calls.NEW);

            sb.append( "Call Details :");
            while ( managedCursor.moveToNext() ) {

                Calls cl = new Calls();
                String phNumber = managedCursor.getString( number );
                String callType = managedCursor.getString( type );
                String callDate = managedCursor.getString( date );
                String sName = managedCursor.getString(name);
                String sNew = managedCursor.getString(isNew);
                //  Date callDayTime = new Date(Long.valueOf(callDate));
                SimpleDateFormat Day = new SimpleDateFormat("dd");
                SimpleDateFormat Month = new SimpleDateFormat("MM");
                SimpleDateFormat year = new SimpleDateFormat("yy");
                SimpleDateFormat Time = new SimpleDateFormat("HH:mm");
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

                String callDay = Day.format(new Date(Long.valueOf(callDate)));
                String callYear = Month.format(new Date(Long.valueOf(callDate)));
                String callMonh = year.format(new Date(Long.valueOf(callDate)));
                String callTime = Time.format(new Date(Long.valueOf(callDate)));
                String dayWeek = sdf.format(new Date(Long.valueOf(callDate)));

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
                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+dayWeek+" \nCall duration in sec :--- "+callDuration );
                sb.append("\n----------------------------------");
                cl.setCallNumber(phNumber);
                cl.setDuration(callDuration);
                cl.setIsCallNew(sNew);
                cl.setCallDay(callDay.toString());
                cl.setCallMonth(callMonh.toString());
                cl.setCallTime(callTime.toString());
                cl.setCallYear(callYear.toString());
                cl.setCallDayWeek(dayWeek.toString());
                cl.setCallType(dir);
                cl.setCallName(sName);
                listCall.add(cl);
            }
            managedCursor.close();
            //Log.d("log : ", sb.toString());
       //    Toast.makeText(this, sb.toString(),
          //         Toast.LENGTH_LONG).show();





        }
        else{

            Log.d("No permession","No permession");
        }
        return;
    }


}