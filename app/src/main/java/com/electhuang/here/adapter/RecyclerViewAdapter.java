package com.electhuang.here.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electhuang.here.R;

import java.util.List;

/**
 * Created by elecdog on 2016/11/20.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

	private List<String> dataList;

	public RecyclerViewAdapter(List<String> dataList) {
		this.dataList = dataList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
						.recyclerview_item, parent,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tv_title.setText(dataList.get(position).split(",")[0]);
		holder.tv_reg_address.setText(dataList.get(position).split(",")[1]);
	}

	@Override
	public int getItemCount() {
		return dataList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_title, tv_reg_time, tv_reg_address;

		public ViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_reg_time = (TextView) itemView.findViewById(R.id.tv_reg_time);
			tv_reg_address = (TextView) itemView.findViewById(R.id.tv_reg_address);
		}
	}
}
