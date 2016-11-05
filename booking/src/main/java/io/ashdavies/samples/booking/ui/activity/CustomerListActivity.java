package io.ashdavies.samples.booking.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import io.ashdavies.R;
import io.ashdavies.commons.adapter.BaseAdapter;
import io.ashdavies.commons.adapter.DividerItemDecoration;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.samples.booking.ui.adapter.CustomerAdapter;
import io.ashdavies.samples.booking.ui.presenter.CustomerListPresenter;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import io.ashdavies.samples.booking.injection.ApplicationComponent;

public class CustomerListActivity extends ApplicationListActivity<CustomerEntity> {

    @Inject
    CustomerListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    protected BaseAdapter<? extends BaseAdapter.ViewHolder<CustomerEntity>, CustomerEntity> getAdapter() {
        return new CustomerAdapter(getContext(), new CustomerAdapter.CustomerSelectionListener() {
            @Override public void onSelect(CustomerEntity customer) {
                startActivity(TableGridActivity.newIntent(getContext(), customer));
            }
        });
    }

    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.coordinator_recycler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @IdRes
    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onActionBarSet(ActionBar actionBar) {
        actionBar.setTitle(R.string.customers);
    }

    @Override
    public void inject(ApplicationComponent component) {
        component.inject(this);
    }
}
