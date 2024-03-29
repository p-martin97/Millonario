package com.example.millonario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.millonario.ListaPuntuacionesFragment.OnListFragmentInteractionListener;
import com.example.millonario.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListaPuntuacionesRecyclerViewAdapter extends RecyclerView.Adapter<MyListaPuntuacionesRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyListaPuntuacionesRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listapuntuaciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nick.setText(mValues.get(position).nick);
        holder.puntuacion.setText(mValues.get(position).puntuacion);
        holder.latitud.setText(mValues.get(position).latitud.substring(0,5));
        holder.longitud.setText(mValues.get(position).longitud.substring(0,5));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nick;
        public final TextView puntuacion;
        public final TextView latitud;
        public final TextView longitud;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nick = (TextView) view.findViewById(R.id.nickL);
            puntuacion = (TextView) view.findViewById(R.id.puntuacionL);
            latitud = (TextView) view.findViewById(R.id.latitudL);
            longitud = (TextView) view.findViewById(R.id.longitudL);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nick.getText() + "'";
        }
    }
}
