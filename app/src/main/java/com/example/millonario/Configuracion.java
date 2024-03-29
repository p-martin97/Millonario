package com.example.millonario;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Configuracion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Configuracion#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Configuracion extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private LocationManager locationManager;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Context mContext;

    private TextInputEditText u = null;
    private TextInputEditText f = null;

    private TextView ltb = null;
    private TextView lgb = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Configuracion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Configuracion.
     */
    // TODO: Rename and change types and number of parameters
    public static Configuracion newInstance(String param1, String param2) {
        Configuracion fragment = new Configuracion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveSharedPref();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView lon = (TextView) view.findViewById(R.id.LongitudTxt);
        TextView lat = (TextView) view.findViewById(R.id.LatitudTxt);
        TextInputEditText usu = (TextInputEditText) view.findViewById(R.id.playerName);
        Spinner jk = (Spinner) view.findViewById(R.id.nJokers);

        u = (TextInputEditText) view.findViewById(R.id.playerName);
        f = (TextInputEditText) view.findViewById(R.id.friendName);

        ltb = (TextView) view.findViewById(R.id.LatitudTxt);
        lgb = (TextView) view.findViewById(R.id.LongitudTxt);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);


        String lonTxt = preferences.getString("longitud", "");
        String latTxt = preferences.getString("latitud", "");
        Integer njk = preferences.getInt("comodines", 1);
        String usutxt = preferences.getString("usuario", "");

        lon.setText(lonTxt);
        lat.setText(latTxt);
        jk.setSelection(njk-1);
        usu.setText(usutxt);


        Button button = (Button) view.findViewById(R.id.addFriendBtn);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                enviar();

            }
        });
        Button button1 = (Button) view.findViewById(R.id.getLctBtn);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                locate();


            }
        });

    }

    public void locate() {

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        ltb.setText(String.valueOf(latitude));
        lgb.setText(String.valueOf(longitude));

    }


    public void enviar(){
        new Network(u.getText().toString(),f.getText().toString()).execute();


    }


    private void saveSharedPref() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", 0);
        SharedPreferences.Editor editor = preferences.edit();

        TextInputEditText usu = (TextInputEditText) this.getActivity().findViewById(R.id.playerName);
        String usutxt = usu.getText().toString().trim();
        Spinner jk = (Spinner) this.getActivity().findViewById(R.id.nJokers);
        Integer njk = Integer.parseInt(jk.getSelectedItem().toString());
        TextView lat = (TextView) this.getActivity().findViewById(R.id.LatitudTxt);
        String latTxt = lat.getText().toString().trim();
        TextView lon = (TextView) this.getActivity().findViewById(R.id.LongitudTxt);
        String lonTxt = lat.getText().toString().trim();


        //Save values from Edit Text Controls

        editor.putString("usuario",usutxt);
        editor.putInt("comodines",njk);
        editor.putString("longitud",lonTxt);
        editor.putString("latitud",latTxt);
        editor.commit();

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
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        ltb.setText(String.valueOf(latitude));
        lgb.setText(String.valueOf(longitude));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

    class Network extends AsyncTask<Void, Void, Void> {

        public String a;
        public String b;

        public Network(String a, String b){
            this.a = a;
            this.b = b;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.authority("wwtbamandroid.appspot.com");
            builder.appendPath("rest");
            builder.appendPath("friends");

            String body = "friend_name="+b+"&name="+a;
            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();
// Get response
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
// Retrieve and process the response
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }




}
