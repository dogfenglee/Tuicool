package com.lowwor.tuicool.ui.hottopics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lowwor.tuicool.R;
import com.lowwor.tuicool.db.TuicoolDatabaseRepository;
import com.lowwor.tuicool.model.HotTopicsItem;

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
    private static enum SubscribeType { SUBSCRIBED, NOT_SUBSCRIBED }

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
        return new ViewHolder(v, mHotTopicClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        HotTopicsItem hotTopic = mTopics.get(i);

        viewHolder.tvName.setText(hotTopic.name);
        Glide.with(mContext).load(hotTopic.image).into(viewHolder.ivAvatar);

        if (mTuicoolDatabaseRepository.isSubscribe(hotTopic)) {
            viewHolder.ivSubscribe.setTag(R.id.iv_subscribe, SubscribeType.SUBSCRIBED);
            viewHolder.ivSubscribe.setBackgroundResource(R.drawable.ic_action_done);
        } else {
            viewHolder.ivSubscribe.setTag(R.id.iv_subscribe, SubscribeType.NOT_SUBSCRIBED);
            viewHolder.ivSubscribe.setBackgroundResource(R.drawable.ic_action_add);
        }

    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.iv_subscribe)
        ImageView ivSubscribe;

        public ViewHolder(View view, HotTopicClickListener hotTopicClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            bindListener(view, hotTopicClickListener);

        }

        private void bindListener(View view, final HotTopicClickListener hotTopicClickListener) {

            ivSubscribe.setOnClickListener(
                    v ->
                    {
                        SubscribeType subscibedType = (SubscribeType)ivSubscribe.getTag(R.id.iv_subscribe);
                        if (subscibedType== SubscribeType.SUBSCRIBED) {
                            ivSubscribe.setBackgroundResource(R.drawable.ic_action_add);
                        } else if(subscibedType== SubscribeType.NOT_SUBSCRIBED){
                            ivSubscribe.setBackgroundResource(R.drawable.ic_action_done);
                        }
                        hotTopicClickListener.onHotTopicClick(getPosition());

                    }
            );
        }
    }

    public interface HotTopicClickListener {
        void onHotTopicClick(int position);
    }
}