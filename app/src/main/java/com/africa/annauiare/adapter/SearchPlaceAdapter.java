package com.africa.annauiare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikk on 9/3/17.
 */

public class SearchPlaceAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    ArrayList<String>mResultList;
    private LayoutInflater inflater;
    public SearchPlaceAdapter(Context mContext, ArrayList<String> resultList){
        this.mContext = mContext;
        this.mResultList = resultList;
        inflater = LayoutInflater.from(mContext);
    }

    public void addItem(String item) {
            mResultList.add(item);
            notifyDataSetChanged();
    }

    public void removeDuplicate() {
        Set<String> hs = new HashSet<>();
        hs.addAll(mResultList);
        mResultList.clear();
        mResultList.addAll(hs);
        notifyDataSetChanged();
    }

    public void clearItem() {
        mResultList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mResultList.size()!=0)
            return mResultList.size();
        else
            return 0;
    }

    @Override
    public String getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String item = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(item);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if(constraint!=null && constraint.length()>0){
                    final String category = constraint.toString().substring(0,1).toUpperCase() + constraint.toString().substring(1).toLowerCase();

                    ArrayList<String> filterList=new ArrayList<String>();
                    for(int i=0;i<mResultList.size();i++){
//                        if(mResultList.get(i).toString().contains(constraint.toString().toUpperCase())) {
//                            filterList.add(mResultList.get(i));
//                        }
//                        if(mResultList.get(i).toString().contains(category)){
//                            filterList.add(mResultList.get(i));
//                        }
//                        if(mResultList.get(i).toString().contains(constraint)){
//                            filterList.add(mResultList.get(i));
//                        }
                        filterList.add(mResultList.get(i));
                    }

//                    Set<String> hs = new HashSet<>();
//                    hs.addAll(filterList);
//                    filterList.clear();
//                    filterList.addAll(hs);
                    results.count=filterList.size();
                    results.values=filterList;
                }else{
                    results.count=mResultList.size();
                    results.values=mResultList;
                }
                return  results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    mResultList=(ArrayList<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }

            }
        };
    }



    private static class ViewHolder {
        TextView name;
    }
}
