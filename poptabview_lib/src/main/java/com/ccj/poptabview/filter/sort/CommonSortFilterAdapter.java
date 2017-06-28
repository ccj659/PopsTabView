package com.ccj.poptabview.filter.sort;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.bean.FilterBean;
import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.listener.OnHolderClickListener;
import com.ccj.poptabview.R;

import java.util.List;

/**
 * 筛选器adapter
 * @update ccj sj
 */
public class CommonSortFilterAdapter extends RecyclerView.Adapter implements OnHolderClickListener {

    public static final int INITIAL_COUNT = 6;//初始状态显示6个项目

    private ComFilterTagClickListener mListener;

    private List<FilterBean.CategoryMall> mData;
    private int mType;
    private boolean isExpand = false;//是否已展开
    private String checkedId;//选中的项的id

    public CommonSortFilterAdapter(ComFilterTagClickListener listener, int type) {
        mListener = listener;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(mData.get(position).getName());
            if (mData.get(position).getId().equals(checkedId)) {
                viewHolder.tv_filter.setChecked(true);
            } else {
                viewHolder.tv_filter.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else if (mData.size() > 6 && !isExpand) {
            return INITIAL_COUNT;
        }
        return mData.size();
    }

    public void setData(List<FilterBean.CategoryMall> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            checkedId = null;
            isExpand = false;
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            checkedId = null;
            notifyDataSetChanged();
        }
    }


    public void clearSelected() {
            checkedId = null;
            notifyDataSetChanged();
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        notifyDataSetChanged();
    }

    public void setCheckedId(String checkedId) {
        this.checkedId = checkedId;
        notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        if (position >= 0 && position < mData.size()) {
            FilterBean.CategoryMall data = mData.get(position);
            //商城筛选需要记住当前选中的项目
            if (data.getId().equals(checkedId)) {
                checkedId = null;
            } else if (mType == FilterConfig.FILTER_TYPE_SINGLE) {//单选
//                List<FilterBean.CategoryMall> temp = new ArrayList<>();
//                temp.add(data);
//                mData = temp;
                this.checkedId = data.getId();
                notifyDataSetChanged();
            }else if (mType == FilterConfig.FILTER_TYPE_MUTIFY){//多选
                //// TODO: 17/6/22  多选

            }else {
                this.checkedId = data.getId();
                notifyDataSetChanged();
            }

            mListener.onComFilterTagClick(position,data.getId(), data.getName(), null);//此处的mType无用~
        }
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckedTextView tv_filter;
        OnHolderClickListener mListener;

        public FilterViewHolder(View itemView, OnHolderClickListener listener) {
            super(itemView);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
            tv_filter.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof CheckedTextView) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
