package com.mr.zadanie2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.zadanie2.TaskFragment.OnListFragmentInteractionListener;
import com.mr.zadanie2.tasks.TaskListContent.Task;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Task task = mValues.get(position);
        holder.mItem = task;
        holder.mContentView.setText(task.title);
        final String picPath = task.picPath;
        Context context = holder.mView.getContext();
        if(picPath != null && !picPath.isEmpty()){
            if(picPath.contains("avatar")){
                Drawable taskDrawable;
                switch(picPath){
                    case "avatar 1":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_1);
                        break;
                    case "avatar 2":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_2);
                        break;
                    case "avatar 3":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_3);
                        break;
                    case "avatar 4":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_4);
                        break;
                    case "avatar 5":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_5);
                        break;
                    case "avatar 6":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_6);
                        break;
                    case "avatar 7":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_7);
                        break;
                    case "avatar 8":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_8);
                        break;
                    case "avatar 9":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_9);
                        break;
                    case "avatar 10":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_10);
                        break;
                    case "avatar 11":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_11);
                        break;
                    case "avatar 12":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_12);
                        break;
                    case "avatar 14":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_14);
                        break;
                    case "avatar 15":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_15);
                        break;
                    case "avatar 16":
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_16);
                        break;
                    default:
                        taskDrawable = context.getResources().getDrawable(R.drawable.avatar_3);
                }
                holder.mItemImageView.setImageDrawable(taskDrawable);
            }else{
                Bitmap cameraImage = PicUtils.decodePic(task.picPath, 128, 128);
                holder.mItemImageView.setImageBitmap(cameraImage);
            }
        }else{
            holder.mItemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_3));
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
                }
            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onDeleteClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mItemImageView;
        public final TextView mContentView;
        public final ImageButton mDeleteButton;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = (ImageView) view.findViewById(R.id.item_image);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDeleteButton = (ImageButton) view.findViewById(R.id.delete_but);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
