package com.lowwor.tuicool.ui.hottopics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lowwor.tuicool.R;
import com.lowwor.tuicool.data.db.TuicoolDatabaseRepository;
import com.lowwor.tuicool.data.model.HotTopicsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lowworker on 2015/9/20.
 */
public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.ViewHolder> {
    Context mContext;
    List<HotTopicsItem> mTopics = new ArrayList<>();
    HotTopicClickListener mHotTopicClickListener;

    private static enum SubscribeType {SUBSCRIBED, NOT_SUBSCRIBED}

    private SparseBooleanArray mSubscribeItems = new SparseBooleanArray();

    TuicoolDatabaseRepository mTuicoolDatabaseRepository;

    public HotTopicAdapter(List<HotTopicsItem> topics, Context context, HotTopicClickListener hotTopicClickListener, TuicoolDatabaseRepository tuicoolDatabaseRepository) {
        this.mTopics = topics;
        mContext = context;
        mHotTopicClickListener = hotTopicClickListener;
        this.mTuicoolDatabaseRepository = tuicoolDatabaseRepository;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hot_topic, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {

        HotTopicsItem hotTopic = mTopics.get(pos);

        viewHolder.tvName.setText(hotTopic.name);
        Glide.with(mContext).load(hotTopic.image).into(viewHolder.ivAvatar);

        if (mTuicoolDatabaseRepository.isSubscribe(hotTopic)) {
            mSubscribeItems.put(pos, true);
        } else {
            mSubscribeItems.delete(pos);
        }
        viewHolder.ivIsSubscribed.setVisibility(mSubscribeItems.get(pos, false) ? View.VISIBLE : View.GONE);
        viewHolder.itemView.setOnClickListener(v -> {
            if (isPositionSubscribed(viewHolder.getAdapterPosition())) {
                mSubscribeItems.delete(viewHolder.getAdapterPosition());
            } else {
                mSubscribeItems.put(viewHolder.getAdapterPosition(),true);
            }
            viewHolder.ivIsSubscribed.setVisibility(mSubscribeItems.get(pos, false) ? View.VISIBLE : View.GONE);
            mHotTopicClickListener.onHotTopicClick(viewHolder.getAdapterPosition());

        });
    }



    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    private boolean isPositionSubscribed(int position) {
        return mSubscribeItems.get(position, false);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.iv_is_subscribed)
        ImageView ivIsSubscribed;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public interface HotTopicClickListener {
        void onHotTopicClick(int position);
    }
}