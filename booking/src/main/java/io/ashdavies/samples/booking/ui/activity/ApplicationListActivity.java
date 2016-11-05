package io.ashdavies.samples.booking.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

import butterknife.BindView;
import io.ashdavies.R;
import io.ashdavies.commons.adapter.BaseAdapter;
import io.ashdavies.commons.view.ListView;

public abstract class ApplicationListActivity<T> extends ApplicationActivity implements ListView<T> {
    private BaseAdapter<? extends BaseAdapter.ViewHolder<T>, T> adapter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = getAdapter();

        recycler.addItemDecoration(getDividerItemDecoration());
        recycler.setLayoutManager(getLayoutManager());
        recycler.setAdapter(adapter);
    }

    protected abstract BaseAdapter<? extends BaseAdapter.ViewHolder<T>, T> getAdapter();

    protected abstract RecyclerView.ItemDecoration getDividerItemDecoration();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void add(T item) {
        adapter.addItem(item);
    }

    @Override
    public void add(Collection<T> items) {
        adapter.addItems(items);
    }
}
