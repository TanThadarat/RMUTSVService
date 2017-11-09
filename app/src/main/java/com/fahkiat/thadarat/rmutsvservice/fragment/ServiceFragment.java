package com.fahkiat.thadarat.rmutsvservice.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fahkiat.thadarat.rmutsvservice.MyServiceActivity;
import com.fahkiat.thadarat.rmutsvservice.R;
import com.fahkiat.thadarat.rmutsvservice.utility.GetAllData;
import com.fahkiat.thadarat.rmutsvservice.utility.ListViewAdapter;
import com.fahkiat.thadarat.rmutsvservice.utility.Myconstant;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lenovo on 9/11/2560.
 */

public class ServiceFragment extends android.support.v4.app.Fragment{

    public static ServiceFragment serviceInstance(String[] strings) {
        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("Login",strings);
        serviceFragment.setArguments(bundle);
        return serviceFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] strings = getArguments().getStringArray("Login");
        Log.d("9novV1", "Login(1) on ServiceFragment ==> "+ strings[1]);
//        Create Toolbar

        createToolbar(strings[1]);

//        Create ListView
        createListView();


    }

    private void createListView() {
        ListView listView = getView().findViewById(R.id.livUser);
        Myconstant myconstant = new Myconstant();
        try {
            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myconstant.getUrlGetAllUser());
            String resultJSON = getAllData.get();
            Log.d("9novV1", "JSON ==> " + resultJSON);
            JSONArray jsonArray = new JSONArray(resultJSON);

            String[] nameStrings = new String[jsonArray.length()];
            String[] catStrings = new String[jsonArray.length()];
            String[] userStrings = new String[jsonArray.length()];
            String[] passwordStrings = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStrings[i] = jsonObject.getString("Name");
                catStrings[i] = jsonObject.getString("Category");
                userStrings[i] = jsonObject.getString("User");
                passwordStrings[i] = jsonObject.getString("Password");

            }   // fpr
                ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),
                        nameStrings, catStrings, userStrings, passwordStrings);
                listView.setAdapter(listViewAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createToolbar(String setTile) {
        Toolbar toolbar = getView().findViewById(R.id.toolberService);
        ((MyServiceActivity)getActivity()) .setSupportActionBar(toolbar);
        ((MyServiceActivity) getActivity()).getSupportActionBar().setTitle(setTile);
        ((MyServiceActivity) getActivity()).getSupportActionBar().setSubtitle("Who Loged");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;

    }
}   // Main Class
