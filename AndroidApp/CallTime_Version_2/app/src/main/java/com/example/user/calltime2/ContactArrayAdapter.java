package com.example.user.calltime2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.user.calltime2.MainActivity.listcontact;

/**
 * Created by chebaane on 05/11/2017.
 */

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    public  ContactArrayAdapter(Context context, List<Contact> listcontact) {

        super(context, R.layout.contact_item,listcontact);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View itemView = convertView;
        if (itemView == null) {
//                itemView=getActivity().getLayoutInflater().inflate(R.layout.item_view,parent,false);
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.contact_item, parent, false);
        }
        // Find the contact to work with.

        Contact contact = listcontact.get(position);



        TextView Name = (TextView) itemView.findViewById(R.id.Name);
        Name.setText(contact.getName());
        TextView Number = (TextView) itemView.findViewById(R.id.Number);
        Number.setText(contact.getNumero());
        return itemView;
//            return super.getView(position,convertView,parent);
    }

}
