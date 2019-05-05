package com.mr.zadanie2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mr.zadanie2.tasks.TaskListContent;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TaskFragment extends Fragment {

    // TODO: Customize parameters

    private MyTaskRecyclerViewAdapter mRecyclerViewAdapter;

    private OnListFragmentInteractionListener mListener;

    public void notifyDataChange(){
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public TaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerViewAdapter = new MyTaskRecyclerViewAdapter(TaskListContent.ITEMS, mListener);
            recyclerView.setAdapter(mRecyclerViewAdapter);
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

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentClickInteraction(TaskListContent.Task task, int position);
        void onDeleteClick(int position);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Activity.RESULT_OK){
            if(data != null){
                boolean changeDataSet = data.getBooleanExtra(TaskInfoActivity.DATA_CHANGED_KEY, false);
                if(changeDataSet)
                    notifyDataChange();
            }
        }
    }
}
