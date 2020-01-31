package com.example.gpsactivity;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gpsactivity.DataRowFragment.OnListFragmentInteractionListener;
import com.example.gpsactivity.model.SensorsData;
import com.example.gpsactivity.model.SensorsDataAdapter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SensorsData} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyDataRowRecyclerViewAdapter extends RecyclerView.Adapter<MyDataRowRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDataRowRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_datarow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mRow = mValues.get(position);
        holder.mContentTextView.setText(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    SensorsData sensorsData = SensorsDataAdapter.unpack(holder.mRow);
                    mListener.onListFragmentInteraction(sensorsData);
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
        public final TextView mContentTextView;
        public String mRow;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentTextView = view.findViewById(R.id.content_text_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentTextView.getText() + "'";
        }
    }
}
