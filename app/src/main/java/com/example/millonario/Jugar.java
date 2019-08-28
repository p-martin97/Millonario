package com.example.millonario;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Jugar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Jugar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jugar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int npregunta;
    private int npuntos;
    private int npuntosGuardar;
    private int comodines;
    private String right;
    private String nick;
    TextView puntuacion;

    private int phone;
    private int fifty2;
    private int fifty1;
    private int audience;

    private double lat;
    private double lg;


    private int[] puntuaciones = {0, 100, 200, 300,
            500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Jugar() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", 0);
        SharedPreferences.Editor editor = preferences.edit();

        Button cincuBt = (Button) this.getActivity().findViewById(R.id.cincuBtn);
        ImageButton audienciaBt = (ImageButton) this.getActivity().findViewById(R.id.audienciaBtn);
        ImageButton llamadaBt = (ImageButton) this.getActivity().findViewById(R.id.llamadaBtn);


        TextView pr = (TextView) this.getActivity().findViewById(R.id.preguntaTxt);
        String prTxt = pr.getText().toString().trim();
        TextView pt = (TextView) this.getActivity().findViewById(R.id.puntuacionTxt);
        String ptTxt = pr.getText().toString().trim();
        Button r1 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        String r1Txt = r1.getText().toString().trim();
        Button r2 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        String r2Txt = r2.getText().toString().trim();
        Button r3 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        String r3Txt = r3.getText().toString().trim();
        Button r4 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        String r4Txt = r4.getText().toString().trim();




        //Save values from Edit Text Controls

        editor.putInt("nPuntos",npuntos);
        editor.putInt("nPregunta",npregunta);
        editor.putString("respuesta1",r1Txt);
        editor.putString("respuesta2",r2Txt);
        editor.putString("respuesta3",r3Txt);
        editor.putString("respuesta4",r4Txt);
        editor.putString("pregunta",prTxt);
        editor.putString("puntos",ptTxt);

        editor.putBoolean("comodin1",cincuBt.getVisibility() == View.VISIBLE);
        editor.putBoolean("comodin2",llamadaBt.getVisibility() == View.VISIBLE);
        editor.putBoolean("comodin3",audienciaBt.getVisibility() == View.VISIBLE);

        editor.commit();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
            SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", 0);
            SharedPreferences.Editor editor = preferences.edit();



            TextView pr = (TextView) this.getActivity().findViewById(R.id.preguntaTxt);
            String prTxt = pr.getText().toString().trim();
            TextView pt = (TextView) this.getActivity().findViewById(R.id.puntuacionTxt);
            String ptTxt = pr.getText().toString().trim();
            Button r1 = (Button) this.getActivity().findViewById(R.id.res1Btn);
            String r1Txt = r1.getText().toString().trim();
            Button r2 = (Button) this.getActivity().findViewById(R.id.res1Btn);
            String r2Txt = r2.getText().toString().trim();
            Button r3 = (Button) this.getActivity().findViewById(R.id.res1Btn);
            String r3Txt = r3.getText().toString().trim();
            Button r4 = (Button) this.getActivity().findViewById(R.id.res1Btn);
            String r4Txt = r4.getText().toString().trim();


            //Save values from Edit Text Controls

            editor.putInt("nPuntos",npuntos);
            editor.putInt("nPregunta",npregunta);
            editor.putString("respuesta1",r1Txt);
            editor.putString("respuesta2",r2Txt);
            editor.putString("respuesta3",r3Txt);
            editor.putString("respuesta4",r4Txt);
            editor.putString("pregunta",prTxt);
            editor.putString("puntos",ptTxt);

            editor.commit();
        super.onDestroyView();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Jugar.
     */
    // TODO: Rename and change types and number of parameters
    public static Jugar newInstance(String param1, String param2) {
        Jugar fragment = new Jugar();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jugar, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InputStream is;
        String pregunta;

        final TextView pr = (TextView) this.getActivity().findViewById(R.id.preguntaTxt);
        final TextView pt = (TextView) this.getActivity().findViewById(R.id.puntuacionTxt);
        Button r1 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        Button r2 = (Button) this.getActivity().findViewById(R.id.res2Btn);
        Button r3 = (Button) this.getActivity().findViewById(R.id.res3Btn);
        Button r4 = (Button) this.getActivity().findViewById(R.id.res4Btn);



        final Button cincuBt = (Button) this.getActivity().findViewById(R.id.cincuBtn);
        final ImageButton audienciaBt = (ImageButton) this.getActivity().findViewById(R.id.audienciaBtn);
        final ImageButton llamadaBt = (ImageButton) this.getActivity().findViewById(R.id.llamadaBtn);

        puntuacion = this.getActivity().findViewById(R.id.puntuacionTxt);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        npregunta = preferences.getInt("nPregunta", 1);
        npuntos = preferences.getInt("nPuntos", 0);
        puntuacion.setText(Integer.toString(npuntos));
        comodines = preferences.getInt("comodines", 1);
        nick = preferences.getString("usuario", "");
        if (preferences.getString("latitud", null)==null)
            lat = 0.00;
        else
            lat = Double.parseDouble(preferences.getString("latitud", null));
        if (preferences.getString("longitud", null)==null)
            lg = 0.00;
        else
            lg = Double.parseDouble(preferences.getString("longitud", null));

        Boolean jk1 = preferences.getBoolean("comodin1", false);;
        Boolean jk2 = preferences.getBoolean("comodin2", false);;
        Boolean jk3 = preferences.getBoolean("comodin3", false);;


        if (npregunta==1){
            if (comodines>0)
                cincuBt.setVisibility(View.VISIBLE);
            else
                cincuBt.setVisibility(View.INVISIBLE);
            if (comodines>1)
                llamadaBt.setVisibility(View.VISIBLE);
            else
                llamadaBt.setVisibility(View.INVISIBLE);
            if (comodines>2)
                audienciaBt.setVisibility(View.VISIBLE);
            else
                audienciaBt.setVisibility(View.INVISIBLE);
        }
        else {

            if (jk1)
                cincuBt.setVisibility(View.VISIBLE);
            else
                cincuBt.setVisibility(View.INVISIBLE);
            if (jk2)
                llamadaBt.setVisibility(View.VISIBLE);
            else
                llamadaBt.setVisibility(View.INVISIBLE);
            if (jk3)
                audienciaBt.setVisibility(View.VISIBLE);
            else
                audienciaBt.setVisibility(View.INVISIBLE);
        }



        cargaPreguntas();



        final Button res1Bt = (Button) view.findViewById(R.id.res1Btn);
        final Button res2Bt = (Button) view.findViewById(R.id.res2Btn);
        final Button res3Bt = (Button) view.findViewById(R.id.res3Btn);
        final Button res4Bt = (Button) view.findViewById(R.id.res4Btn);
        final Button rendir = (Button) view.findViewById(R.id.rendirBtn);

        final ImageView victoria = view.findViewById(R.id.victoriaImg);
        final ImageView derrota = view.findViewById(R.id.derrotaImg);

        final TextView jugando = (TextView) view.findViewById(R.id.jugandoTxt);
        final TextView fin = (TextView) view.findViewById(R.id.finalTxt);

        victoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                victoria.setVisibility(View.INVISIBLE);
                fin.setVisibility(View.INVISIBLE);
                res1Bt.setVisibility(View.VISIBLE);
                res2Bt.setVisibility(View.VISIBLE);
                res3Bt.setVisibility(View.VISIBLE);
                res4Bt.setVisibility(View.VISIBLE);
                res1Bt.setBackgroundColor(Color.WHITE);
                res2Bt.setBackgroundColor(Color.WHITE);
                res3Bt.setBackgroundColor(Color.WHITE);
                res4Bt.setBackgroundColor(Color.WHITE);
                jugando.setVisibility(View.VISIBLE);
                pr.setVisibility(View.VISIBLE);
                pt.setVisibility(View.VISIBLE);
                rendir.setVisibility(View.VISIBLE);

                cincuBt.setEnabled(true);
                llamadaBt.setEnabled(true);
                audienciaBt.setEnabled(true);

                if (comodines>0)
                    cincuBt.setVisibility(View.VISIBLE);
                else
                    cincuBt.setVisibility(View.INVISIBLE);
                if (comodines>1)
                    llamadaBt.setVisibility(View.VISIBLE);
                else
                    llamadaBt.setVisibility(View.INVISIBLE);
                if (comodines>2)
                    audienciaBt.setVisibility(View.VISIBLE);
                else
                    audienciaBt.setVisibility(View.INVISIBLE);
                guardaPuntos();
            }

        });
        derrota.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                derrota.setVisibility(View.INVISIBLE);
                fin.setVisibility(View.INVISIBLE);
                res1Bt.setVisibility(View.VISIBLE);
                res2Bt.setVisibility(View.VISIBLE);
                res3Bt.setVisibility(View.VISIBLE);
                res4Bt.setVisibility(View.VISIBLE);
                res1Bt.setBackgroundColor(Color.WHITE);
                res2Bt.setBackgroundColor(Color.WHITE);
                res3Bt.setBackgroundColor(Color.WHITE);
                res4Bt.setBackgroundColor(Color.WHITE);

                pt.setVisibility(View.VISIBLE);
                jugando.setVisibility(View.VISIBLE);
                pr.setVisibility(View.VISIBLE);
                rendir.setVisibility(View.VISIBLE);
                cincuBt.setEnabled(true);
                llamadaBt.setEnabled(true);
                audienciaBt.setEnabled(true);

                if (comodines>0)
                    cincuBt.setVisibility(View.VISIBLE);
                else
                    cincuBt.setVisibility(View.INVISIBLE);
                if (comodines>1)
                    llamadaBt.setVisibility(View.VISIBLE);
                else
                    llamadaBt.setVisibility(View.INVISIBLE);
                if (comodines>2)
                    audienciaBt.setVisibility(View.VISIBLE);
                else
                    audienciaBt.setVisibility(View.INVISIBLE);
                guardaPuntos();
            }

        });


        res1Bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                npuntosGuardar = npuntos;
                int i = npuntos;
            if (npregunta < 15 && right.equals("1")) {
                npregunta++;
                sumaPuntos();
                res1Bt.setBackgroundColor(Color.WHITE);
                res2Bt.setBackgroundColor(Color.WHITE);
                res3Bt.setBackgroundColor(Color.WHITE);
                res4Bt.setBackgroundColor(Color.WHITE);
                cargaPreguntas();
            }
            else {
                npregunta = 1;
                npuntos=0;
                cargaPreguntas();
                rendir.setVisibility(View.INVISIBLE);
                res1Bt.setVisibility(View.INVISIBLE);
                res2Bt.setVisibility(View.INVISIBLE);
                res3Bt.setVisibility(View.INVISIBLE);
                res4Bt.setVisibility(View.INVISIBLE);
                pr.setVisibility(View.INVISIBLE);
                pt.setVisibility(View.INVISIBLE);
                jugando.setVisibility(View.INVISIBLE);
                fin.setVisibility(View.VISIBLE);
                fin.setText(getResources().getString(R.string.gameOver)+Integer.toString(i));
                derrota.setVisibility(View.VISIBLE);

                cincuBt.setEnabled(false);
                llamadaBt.setEnabled(false);
                audienciaBt.setEnabled(false);

            }


                puntuacion.setText(Integer.toString(npuntos));
            }

        });
        res2Bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                npuntosGuardar = npuntos;
                int i = npuntos;
                if (npregunta < 15 && right.equals("2")) {
                    npregunta++;
                    sumaPuntos();
                    res1Bt.setBackgroundColor(Color.WHITE);
                    res2Bt.setBackgroundColor(Color.WHITE);
                    res3Bt.setBackgroundColor(Color.WHITE);
                    res4Bt.setBackgroundColor(Color.WHITE);
                    cargaPreguntas();
                }
                else {
                    npregunta = 1;
                    npuntos=0;
                    cargaPreguntas();
                    rendir.setVisibility(View.INVISIBLE);
                    res1Bt.setVisibility(View.INVISIBLE);
                    res2Bt.setVisibility(View.INVISIBLE);
                    res3Bt.setVisibility(View.INVISIBLE);
                    res4Bt.setVisibility(View.INVISIBLE);
                    pt.setVisibility(View.INVISIBLE);
                    jugando.setVisibility(View.INVISIBLE);
                    pr.setVisibility(View.INVISIBLE);
                    fin.setVisibility(View.VISIBLE);
                    fin.setText(getResources().getString(R.string.gameOver)+Integer.toString(i));
                    derrota.setVisibility(View.VISIBLE);

                    cincuBt.setEnabled(false);
                    llamadaBt.setEnabled(false);
                    audienciaBt.setEnabled(false);


                }

                puntuacion.setText(Integer.toString(npuntos));
            }


        });
        res3Bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                npuntosGuardar = npuntos;
                int i = npuntos;
                if (npregunta < 15 && right.equals("3")) {
                    npregunta++;
                    sumaPuntos();
                    res1Bt.setBackgroundColor(Color.WHITE);
                    res2Bt.setBackgroundColor(Color.WHITE);
                    res3Bt.setBackgroundColor(Color.WHITE);
                    res4Bt.setBackgroundColor(Color.WHITE);
                    cargaPreguntas();
                }
                else {
                    npregunta = 1;
                    npuntos=0;
                    cargaPreguntas();
                    rendir.setVisibility(View.INVISIBLE);
                    res1Bt.setVisibility(View.INVISIBLE);
                    res2Bt.setVisibility(View.INVISIBLE);
                    res3Bt.setVisibility(View.INVISIBLE);
                    res4Bt.setVisibility(View.INVISIBLE);
                    pt.setVisibility(View.INVISIBLE);
                    jugando.setVisibility(View.INVISIBLE);
                    pr.setVisibility(View.INVISIBLE);
                    derrota.setVisibility(View.VISIBLE);
                    fin.setVisibility(View.VISIBLE);
                    fin.setText(getResources().getString(R.string.gameOver)+Integer.toString(i));

                    cincuBt.setEnabled(false);
                    llamadaBt.setEnabled(false);
                    audienciaBt.setEnabled(false);
                }

                puntuacion.setText(Integer.toString(npuntos));
            }

        });
        res4Bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                npuntosGuardar = npuntos;
                int i = npuntos;
                if (npregunta < 15 && right.equals("4")) {
                    npregunta++;
                    sumaPuntos();
                    res1Bt.setBackgroundColor(Color.WHITE);
                    res2Bt.setBackgroundColor(Color.WHITE);
                    res3Bt.setBackgroundColor(Color.WHITE);
                    res4Bt.setBackgroundColor(Color.WHITE);
                    cargaPreguntas();
                }
                else {
                    npregunta = 1;
                    npuntos=0;
                    cargaPreguntas();
                    rendir.setVisibility(View.INVISIBLE);
                    res1Bt.setVisibility(View.INVISIBLE);
                    res2Bt.setVisibility(View.INVISIBLE);
                    res3Bt.setVisibility(View.INVISIBLE);
                    res4Bt.setVisibility(View.INVISIBLE);
                    pt.setVisibility(View.INVISIBLE);
                    jugando.setVisibility(View.INVISIBLE);
                    pr.setVisibility(View.INVISIBLE);
                    derrota.setVisibility(View.VISIBLE);
                    fin.setVisibility(View.VISIBLE);
                    fin.setText(getResources().getString(R.string.gameOver)+Integer.toString(i));

                    cincuBt.setEnabled(false);
                    llamadaBt.setEnabled(false);
                    audienciaBt.setEnabled(false);
                }

                puntuacion.setText(Integer.toString(npuntos));
            }

        });

        cincuBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fifty1==1 || fifty2==1)
                    res1Bt.setVisibility(View.INVISIBLE);
                if (fifty1==2 || fifty2==2)
                    res2Bt.setVisibility(View.INVISIBLE);
                if (fifty1==3 || fifty2==3)
                    res3Bt.setVisibility(View.INVISIBLE);
                if (fifty1==4 || fifty2==4)
                    res4Bt.setVisibility(View.INVISIBLE);
                cincuBt.setVisibility(View.INVISIBLE);
            }

        });
        audienciaBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (audience==1)
                    res1Bt.setBackgroundColor(Color.GREEN);
                if (audience==2)
                    res2Bt.setBackgroundColor(Color.GREEN);
                if (audience==3)
                    res3Bt.setBackgroundColor(Color.GREEN);
                if (audience==4)
                    res4Bt.setBackgroundColor(Color.GREEN);
                audienciaBt.setVisibility(View.INVISIBLE);
            }

        });
        llamadaBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (phone==1)
                    res1Bt.setBackgroundColor(Color.GREEN);
                if (phone==2)
                    res2Bt.setBackgroundColor(Color.GREEN);
                if (phone==3)
                    res3Bt.setBackgroundColor(Color.GREEN);
                if (phone==4)
                    res4Bt.setBackgroundColor(Color.GREEN);
                llamadaBt.setVisibility(View.INVISIBLE);
            }

        });
        rendir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                npuntosGuardar = npuntos;
                int i = npuntos;
                npregunta = 1;
                npuntos=0;
                cargaPreguntas();
                res1Bt.setVisibility(View.INVISIBLE);
                res2Bt.setVisibility(View.INVISIBLE);
                res3Bt.setVisibility(View.INVISIBLE);
                res4Bt.setVisibility(View.INVISIBLE);
                pr.setVisibility(View.INVISIBLE);
                pt.setVisibility(View.INVISIBLE);
                jugando.setVisibility(View.INVISIBLE);
                fin.setVisibility(View.VISIBLE);
                fin.setText(getResources().getString(R.string.gameOver)+Integer.toString(i));
                derrota.setVisibility(View.VISIBLE);

                cincuBt.setEnabled(false);
                llamadaBt.setEnabled(false);
                audienciaBt.setEnabled(false);
            }

        });



    }
    private void guardaPuntos(){
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(
                    getContext().getDatabasePath("BaseDatos").getAbsolutePath(),
                    null,
                    SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY);
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                    + "puntuaciones" + "'", null);
            if (cursor.getCount()<=0){
                db.execSQL("CREATE TABLE puntuaciones (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nick TEXT NOT NULL, puntos INTEGER NOT NULL" +
                        ", latitud REAL NOT NULL" +
                        ", longitud REAL NOT NULL);");
            }
            SQLiteStatement stmt = db.compileStatement("INSERT INTO puntuaciones (nick,puntos,latitud,longitud) values (?,?,?,?)");
            // Careful! The index begins at 1, not 0 !!
            stmt.bindString(1, nick);
            stmt.bindLong(2, npuntosGuardar);
            stmt.bindDouble(3, lat);
            stmt.bindDouble(4, lg);
            stmt.execute();
            stmt.close();
            //db.execSQL("INSERT INTO puntuaciones (nick,puntos,latitud,longitud) VALUES ( '"+nick+"','"+Integer.toString(npuntos)+"','"+lat+"','"+lg+"') ");

            db.beginTransaction();
            try {
            // Perform action query operations
                db.setTransactionSuccessful();
                new Network(nick,Integer.toString(npuntosGuardar),Double.toString(lat),Double.toString(lg)).execute();
            }
            catch (SQLiteException e) {
                e.printStackTrace();
            }
            finally {
                db.endTransaction();
                String query = "select * from puntuaciones ";
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                String a= c.getString(0);
                String b = c.getString(1);
                String d= c.getString(2);
                String e = c.getString(3);
                db.close();
            }


        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
    };


    private void sumaPuntos(){

        npuntos = puntuaciones[npregunta-1];
        puntuacion.setText(Integer.toString(npuntos));


    }

    private void cargaPreguntas(){
        InputStream is;
        String pregunta;

        TextView pr = (TextView) this.getActivity().findViewById(R.id.preguntaTxt);
        Button r1 = (Button) this.getActivity().findViewById(R.id.res1Btn);
        Button r2 = (Button) this.getActivity().findViewById(R.id.res2Btn);
        Button r3 = (Button) this.getActivity().findViewById(R.id.res3Btn);
        Button r4 = (Button) this.getActivity().findViewById(R.id.res4Btn);

        Button res1Bt = (Button) this.getActivity().findViewById(R.id.res1Btn);
        Button res2Bt = (Button) this.getActivity().findViewById(R.id.res2Btn);
        Button res3Bt = (Button) this.getActivity().findViewById(R.id.res3Btn);
        Button res4Bt = (Button) this.getActivity().findViewById(R.id.res4Btn);



        try {
            Resources r = getActivity().getResources();
            if (Locale.getDefault().getLanguage() == "es"){
                is = r.openRawResource(R.raw.questionses);
            }
            else {
                is = r.openRawResource(R.raw.questions);
            }
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is,null);
            int eventType = parser.getEventType();
            EditText target = null;
            int i = 1;
            while ((XmlPullParser.END_DOCUMENT != eventType )&& i<=npregunta) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("question".equals(parser.getName())) {
                            pr.setText(parser.getAttributeValue(null, "text"));
                            r1.setText(parser.getAttributeValue(null, "answer1"));
                            r2.setText(parser.getAttributeValue(null, "answer2"));
                            r3.setText(parser.getAttributeValue(null, "answer3"));
                            r4.setText(parser.getAttributeValue(null, "answer4"));
                            right = parser.getAttributeValue(null, "right");

                            phone=Integer.parseInt(parser.getAttributeValue(null, "phone"));
                            fifty2=Integer.parseInt(parser.getAttributeValue(null, "fifty2"));
                            fifty1=Integer.parseInt(parser.getAttributeValue(null, "fifty1"));
                            audience=Integer.parseInt(parser.getAttributeValue(null, "audience"));
                            i++;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        // target.setText(parser.getText());
                        break;
                }
                parser.next();
                eventType = parser.getEventType();
            }
            is.close();
        }
        catch (XmlPullParserException e) {e.printStackTrace();}
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}


            res1Bt.setVisibility(View.VISIBLE);
            res2Bt.setVisibility(View.VISIBLE);
            res3Bt.setVisibility(View.VISIBLE);
            res4Bt.setVisibility(View.VISIBLE);


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

        public String name;
        public String score;
        public String longitude;
        public String latitude;

        public Network(String a, String b,String c, String d){
            this.name = a;
            this.score = b;
            this.longitude = c;
            this.latitude = d;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.authority("wwtbamandroid.appspot.com");
            builder.appendPath("rest");
            builder.appendPath("highscores");

            String body = "name="+name+"&score="+score+"&longitude="+longitude+"&latitude="+latitude;
            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
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
