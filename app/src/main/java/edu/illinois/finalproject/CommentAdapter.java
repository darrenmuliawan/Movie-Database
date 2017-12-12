package edu.illinois.finalproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by darrenalexander on 12/10/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> listOfComments;

    public CommentAdapter(List<Comment> comments) {
        listOfComments = comments;
    }

    public void addComment(Comment comment) {
        listOfComments.add(comment);
    }

    public void deleteAllComment() {
        listOfComments.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View commentItem = LayoutInflater.from(parent.getContext()).
                inflate(viewType, parent, false);
        return new ViewHolder(commentItem);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.comment_list;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        final Comment comment = listOfComments.get(position);
        holder.comment.setText(comment.getComment());
        holder.commentName.setText(comment.getName());
        holder.commentTime.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return listOfComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView commentName;
        public TextView commentTime;
        public TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.commentName = (TextView) itemView.findViewById(R.id.commentName);
            this.commentTime = (TextView) itemView.findViewById(R.id.commentTime);
            this.comment = (TextView) itemView.findViewById(R.id.comment);
        }
    }
}
