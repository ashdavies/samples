package io.ashdavies.samples.booking.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import io.ashdavies.R;
import io.ashdavies.commons.adapter.BaseAdapter;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;

public class CustomerAdapter extends BaseAdapter<CustomerAdapter.ViewHolder, CustomerEntity> {
    private final CustomerSelectionListener listener;

    public CustomerAdapter(Context context, CustomerSelectionListener listener) {
        super(context);

        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_customer, parent, false));
    }

    public class ViewHolder extends BaseAdapter.ViewHolder<CustomerEntity> {
        @BindView(R.id.forename) TextView forename;
        @BindView(R.id.surname) TextView surname;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void bind(final CustomerEntity item) {
            forename.setText(item.getCustomerFirstName());
            surname.setText(item.getCustomerLastName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    listener.onSelect(item);
                }
            });
        }
    }

    public interface CustomerSelectionListener {
        void onSelect(CustomerEntity customer);
    }
}
