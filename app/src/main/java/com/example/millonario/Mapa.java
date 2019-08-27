package com.example.millonario;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapa extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String usu;
    private double latMarker = 0;
    private double longMarker = 0;
    private String usuarioMarker;
    private String scoringMarker;



    private GoogleMap map;

    private OnFragmentInteractionListener mListener;

    public Mapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mapa.
     */
    // TODO: Rename and change types and number of parameters
    public static Mapa newInstance(String param1, String param2) {
        Mapa fragment = new Mapa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", 0);
        SharedPreferences.Editor editor = preferences.edit();
        usu = preferences.getString("usuario", "");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        new Network(map).execute();

    }
    class Network extends AsyncTask<Void, Void, Void> {


        public GoogleMap googleMap;

        public Network( GoogleMap d){

            this.googleMap = d;
        }
        private void anyade(final double lt, final double lg, final String u, final String p) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(lt, lg))
                            .title(u + "  " + p));
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {

            map = googleMap;
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.authority("wwtbamandroid.appspot.com");
            builder.appendPath("rest");
            builder.appendPath("highscores");
            builder.appendQueryParameter("name", usu);

            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "MSIE");
                connection.setDoInput(true);
// Get response
                BufferedReader reader = null;


                     reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


                StringBuilder sBuilder = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    sBuilder.append(s);
                }
                reader.close();
                JSONObject object = new JSONObject(sBuilder.toString());
                JSONArray jsonList = object.getJSONArray("scores");
                int i =0;
                while (jsonList.getJSONObject(i) != null) {
                    object = jsonList.getJSONObject(i);
                    if(object.getString("name").equals(usu)){
                        latMarker=Double.parseDouble(object.getString("latitude"));
                        longMarker= Double.parseDouble(object.getString("longitude"));
                        usuarioMarker=object.getString("name");
                        scoringMarker= object.getString("scoring");

                    }
                    anyade(Double.parseDouble(object.getString("latitude")),Double.parseDouble(object.getString("longitude")),
                            object.getString("name"),object.getString("scoring"));
                    i++;
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(new Runnable(){
                public void run() {
                    map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latMarker,longMarker) , 3.0f) );
                    //map.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
                }
            });

            return null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
