package com.lowwor.tuicool.ui.topic;

/**
 * Created by lowworker on 2015/9/13.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lowwor.tuicool.R;
import com.lowwor.tuicool.model.Article;
import com.lowwor.tuicool.ui.article.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yw on 2015/4/28.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    Context mContext;
    List<Article> mArticles = new ArrayList<>();

    public TopicAdapter(List<Article> topics, Context context) {
        this.mArticles = topics;
        mContext = context;
    }

    public TopicAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Article article = mArticles.get(i);
//        final Member member = topic.member;
//
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2015/9/13 intent to TopicActivity and isRead
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra("model", article);
//                if (!mDataSource.isTopicRead(topic.id))
//                    new SetReadTask(topic, TopicsAdapter.this).execute();
                mContext.startActivity(intent);
            }
        });


        if (!article.img.isEmpty()) {
            viewHolder.ivArticle.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(article.img).centerCrop().into(viewHolder.ivArticle);
        } else {
            viewHolder.ivArticle.setVisibility(View.GONE);
        }
        viewHolder.tvFeedTitle.setText(article.feedTitle);
        viewHolder.tvTitle.setText(article.title);
        viewHolder.tvTime.setText(article.time);
//
//        // TODO: 2015/9/13 setIsRead
//        //这里设置已读未读颜色
////        boolean read = Application.getDataSource().isTopicRead(topic.id);
////        viewHolder.tvTitle.setTextColor(read ?
////                mContext.getResources().getColor(R.color.list_item_read) :
////                mContext.getResources().getColor(R.color.list_item_unread));
//
//
//        final Node node = topic.node;
//        viewHolder.tvNodeTitle.setText(node.title);
//        viewHolder.tvNodeTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO: 2015/9/13 intent to NodeActivity
////                if (node == null) return;
////                Intent intent = new Intent(mContext, NodeActivity.class);
////                intent.putExtra("model", (Parcelable) node);
////                mContext.startActivity(intent);
//            }
//        });
//
//
//        // TODO: 2015/9/13 setreplies
////        if (topic.replies > 0) {
////            int count_color = read ?
////                    mContext.getResources().getColor(R.color.topic_count_read) :
////                    mContext.getResources().getColor(R.color.topic_count_unread);
////            viewHolder.replies.setVisibility(View.VISIBLE);
////            viewHolder.replies.setText(String.valueOf(topic.replies));
////
////
////            viewHolder.replies.setBackgroundDrawable(drawable);
////        } else {
////            viewHolder.replies.setVisibility(View.INVISIBLE);
////        }


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public void initialize(List<Article> data) {

        mArticles = data;
        notifyDataSetChanged();

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_container)
        CardView card;
        @Bind(R.id.iv_article)
        ImageView ivArticle;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_feed_title)
        TextView tvFeedTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
