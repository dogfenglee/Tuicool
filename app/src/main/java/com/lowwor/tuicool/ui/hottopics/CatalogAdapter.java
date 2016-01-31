package com.lowwor.tuicool.ui.hottopics;

/**
 * Created by lowworker on 2015/9/20.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.data.model.HotTopicsCatalog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    Context mContext;
    List<HotTopicsCatalog> mCatalogs = new ArrayList<>();
    CatalogClickListener mRecyclerClickListener;
    public static int selectedPosition = 0;

    public CatalogAdapter(List<HotTopicsCatalog> catalogs, Context context, CatalogClickListener recyclerClickListener) {
        this.mCatalogs = catalogs;
        mContext = context;
        this.mRecyclerClickListener = recyclerClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_catalog, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {

        HotTopicsCatalog catalog = mCatalogs.get(pos);

        viewHolder.bindHotTopic(catalog);

        viewHolder.itemView.setOnClickListener(
                v -> {

                    Logger.i(isSelected(pos) + "");
                    selectedPosition = pos;
                    notifyDataSetChanged();
                    mRecyclerClickListener.onElementClick(pos);

                }
        );
        if (isSelected(pos)) {
            viewHolder.mCatalogWrapper.setBackgroundResource(android.R.color.white);
            viewHolder.mIndicator.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIndicator.setVisibility(View.GONE);
            viewHolder.mCatalogWrapper.setBackgroundResource(R.color.material_grey_300);
        }

    }


    private boolean isSelected(int pos) {
        return selectedPosition ==pos;
    }

    @Override
    public int getItemCount() {
        return mCatalogs.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_catalog)
        TextView tvCatalog;
        @Bind(R.id.catalog_wrapper)
        CardView mCatalogWrapper;
        @Bind(R.id.selected_indicator)
        View mIndicator;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindHotTopic(HotTopicsCatalog catalog) {

            tvCatalog.setText(catalog.name);
        }


    }

    public interface CatalogClickListener {
        void onElementClick(int position);
    }

}

