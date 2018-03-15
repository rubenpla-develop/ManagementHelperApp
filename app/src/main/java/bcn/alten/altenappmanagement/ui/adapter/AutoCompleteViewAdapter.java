package bcn.alten.altenappmanagement.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.model.BaseItem;
import bcn.alten.altenappmanagement.model.Client;

public class AutoCompleteViewAdapter<T extends BaseItem> extends BaseAdapter implements Filterable {

    private List<T> itemList = new ArrayList<>();
    private List<String> resultsList = new ArrayList<>();

    private Context mContext;

    public AutoCompleteViewAdapter(Context context) {
        mContext = context;
    }

    public AutoCompleteViewAdapter(Context context,@NonNull List<T> list) {
        mContext = context;
        this.itemList = list;
    }

    @Override
    public int getCount() {
        return resultsList.size();
    }

    @Override
    public String getItem(int index) {
        return resultsList.get(index).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ListViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.autocomplete_item, parent, false);

            holder = new ListViewHolder();
            holder.label = convertView.findViewById(R.id.autocomplete_item_tv);

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

                    resultsList = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    private List<String> findMatches(String constraint) {
        List<String> list = new ArrayList<>();

        //TODO try to improve, workaround with Client.class cast
        for (T item : itemList) {
            if (constraint.equalsIgnoreCase(((Client)item).getName()) ||
                    ((Client)item).getName().toLowerCase().contains(constraint.toLowerCase())) {

                list.add(((Client)item).getName());
            }
        }

        return list;
    }

    private class ListViewHolder  {
        TextView label;
    }
}
