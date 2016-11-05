package io.ashdavies.samples.booking.ui.adapter;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import io.ashdavies.R;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.ashdavies.commons.adapter.BaseAdapter;

public class TableAdapter extends BaseAdapter<TableAdapter.ViewHolder, TableEntity> {
    private final TableSelectionListener listener;

    public TableAdapter(Context context, TableSelectionListener listener) {
        super(context);

        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), getInflater().inflate(R.layout.item_table, parent, false));
    }

    public class ViewHolder extends BaseAdapter.ContextViewHolder<TableEntity> {
        @BindView(R.id.table) CardView table;
        @BindView(R.id.number) TextView number;

        public ViewHolder(Context context, View view) {
            super(context, view);
        }

        @Override
        public void bind(final TableEntity item) {
            number.setText(String.valueOf(getAdapterPosition() + 1));
            number.setTextColor(getTextColor(item.isAvailable()));

            table.setCardBackgroundColor(getColor(item.isAvailable()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    listener.onTableSelected(item);
                }
            });
        }

        @ColorInt
        private int getColor(boolean isAvailable) {
            return ContextCompat.getColor(getContext(), isAvailable ? R.color.white : R.color.red);
        }

        @ColorInt
        private int getTextColor(boolean isAvailable) {
            return ContextCompat.getColor(getContext(), isAvailable ? R.color.primary_text : R.color.white);
        }
    }

    public interface TableSelectionListener {
        void onTableSelected(TableEntity table);
    }
}
