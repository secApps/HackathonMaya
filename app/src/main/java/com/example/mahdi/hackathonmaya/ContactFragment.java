package com.example.mahdi.hackathonmaya;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.mahdi.hackathonmaya.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashSet;

import static android.location.Location.convert;


public class ContactFragment extends ListFragment {
    ArrayList<String> emlids;
    // TODO: Rename parameter arguments, choose names that match




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // TODO: Change Adapter to display your content
        emlids=getNameEmailDetails();
        String arr[]=(String[])emlids.toArray();
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,  arr));
    }







    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Will have to take this email id and send back to the Feedback Activity. and this will be used to send feedback


    }
    public ArrayList<String> getNameEmailDetails() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = getActivity();
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();
        return emlRecs;
    }



}
