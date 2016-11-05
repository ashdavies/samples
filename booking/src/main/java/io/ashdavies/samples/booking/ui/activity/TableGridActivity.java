package io.ashdavies.samples.booking.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import io.ashdavies.R;
import io.ashdavies.commons.adapter.BaseAdapter;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.ashdavies.samples.booking.ui.adapter.TableAdapter;
import io.ashdavies.samples.booking.ui.presenter.TableGridPresenter;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import io.ashdavies.commons.adapter.DividerItemDecoration;
import io.ashdavies.commons.util.IntentUtils;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.samples.booking.injection.ApplicationComponent;

public class TableGridActivity extends ApplicationListActivity<TableEntity> {
    private static final int DEFAULT_SPAN_COUNT = 5;

    @Inject
    TableGridPresenter presenter;

    public static Intent newIntent(Context context, CustomerEntity customer) {
        return IntentUtils.newIntent(context, TableGridActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    protected BaseAdapter<? extends BaseAdapter.ViewHolder<TableEntity>, TableEntity> getAdapter() {
        return new TableAdapter(getContext(), new TableAdapter.TableSelectionListener() {
            @Override public void onTableSelected(TableEntity table) {
                if (table.isAvailable()) {
                    presenter.book(table);
                    showSuccessDialog();
                    return;
                }

                showFailedDialog();
            }
        });
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.table_success)
                .setMessage(R.string.table_booking_success)
                .setPositiveButton(R.string.table_ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    private void showFailedDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.table_sorry)
                .setMessage(R.string.table_booking_failed)
                .setPositiveButton(R.string.table_ok, null)
                .show();
    }

    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
    }

    @Override
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.coordinator_recycler;
    }

    @IdRes
    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onActionBarSet(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tables);
    }

    @Override
    public void inject(ApplicationComponent component) {
        component.inject(this);
    }
}
