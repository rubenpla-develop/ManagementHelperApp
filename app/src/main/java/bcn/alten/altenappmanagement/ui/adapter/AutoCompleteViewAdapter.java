package bcn.alten.altenappmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import bcn.alten.altenappmanagement.R;

public class AutoCompleteViewAdapter extends BaseAdapter implements Filterable {

    private String[] stringList = {"One", "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen"};

    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> resultsList = new ArrayList<>();

    private Context mContext;

    public AutoCompleteViewAdapter(Context context) {
        mContext = context;
        itemList.addAll(Arrays.asList(stringList));
    }

    @Override
    public int getCount() {
        return resultsList.size();
    }

    @Override
    public String getItem(int index) {
        return resultsList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ListViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.autocomplete_item, parent, false);
            /*AbsListView.LayoutParams params = new AbsListView.LayoutParams(100, 35);

            convertView.setLayoutParams(params);*/

            holder = new ListViewHolder();
            holder.label = (TextView) convertView.findViewById(R.id.autocomplete_item_tv);

            convertView.setTag(holder);
        } else {
            view = convertView;
            holder = (ListViewHolder) view.getTag();
        }

        holder.label.setText(getItem(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    resultsList = findMatches(constraint.toString());

                    filterResults.values = resultsList;
                    filterResults.count = resultsList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {

                    resultsList = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    private ArrayList<String> findMatches(String constraint) {
        ArrayList<String> list = new ArrayList<>();

        for (String item : itemList) {

            if (constraint.toString().equalsIgnoreCase(item) ||
                    item.toLowerCase().contains(constraint.toLowerCase())) {
                list.add(item);
            }
        }

        return list;
    }

    private class ListViewHolder  {
        TextView label;
    }
}
