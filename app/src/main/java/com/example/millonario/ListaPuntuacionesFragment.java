package com.example.millonario;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.millonario.dummy.DummyContent;
import com.example.millonario.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListaPuntuacionesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaPuntuacionesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListaPuntuacionesFragment newInstance(int columnCount) {
        ListaPuntuacionesFragment fragment = new ListaPuntuacionesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listapuntuaciones_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyListaPuntuacionesRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        List<DummyItem> puntuacionList=new ArrayList<DummyItem>();
        RecyclerView mRecyclerView = (RecyclerView) view;

        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                getContext().getDatabasePath("BaseDatos").getAbsolutePath(),
                null,
                SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);


        String usu = preferences.getString("usuario", "");

        String query = "select * from puntuaciones where nick =?";
        Cursor c = db.rawQuery(query, new String[] {usu});

        int aux1;
        String aux2;
        int aux3;
        double aux4;
        double aux5;


        if (c.moveToFirst()) {
            do {
                 aux1=c.getInt(0);
                 aux2=c.getString(1);
                 aux3=c.getInt(2);
                 aux4=c.getDouble(3);
                 aux5=c.getDouble(4);

                puntuacionList.add(new DummyItem(
                        Integer.toString(c.getInt(0)),
                        c.getString(1),
                        Integer.toString(c.getInt(2)),
                        String.valueOf(c.getDouble(3)),
                        String.valueOf(c.getDouble(4))
                ));
            } while (c.moveToNext());
            MyListaPuntuacionesRecyclerViewAdapter adapter = new MyListaPuntuacionesRecyclerViewAdapter(puntuacionList, mListener);
            mRecyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
