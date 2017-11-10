package pwcdma.asystentgierplanszowych.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.model.Result;

import static android.content.ContentValues.TAG;

public  class ResultListItemAdapter extends ArrayAdapter<Result> {

    private class ViewHolder {
        private TextView nameView;
        private TextView resultView;
        private TextView numberView;
        private RelativeLayout relativeLayout;
    }

    public ResultListItemAdapter(Context context, List<Result> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Result resultListItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_dice_history_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.numberView = convertView.findViewById(R.id.historyDiceNumber);
            viewHolder.nameView = convertView.findViewById(R.id.historyDiceName);
            viewHolder.resultView = convertView.findViewById(R.id.historyDiceResult);
            viewHolder.relativeLayout = convertView.findViewById(R.id.diceHistoryItemListRL);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.numberView.setText(Integer.toString(position+1));
        viewHolder.nameView.setText(resultListItem.getName());
        viewHolder.resultView.setText(Integer.toString(resultListItem.getResult()));

        return convertView;
    }
}