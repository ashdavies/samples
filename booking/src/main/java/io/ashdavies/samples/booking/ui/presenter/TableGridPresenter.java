package io.ashdavies.samples.booking.ui.presenter;

import android.support.annotation.NonNull;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.presenter.BaseViewPresenter;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.ashdavies.samples.booking.domain.factory.TableRepositoryFactory;
import rx.functions.Action1;

public class TableGridPresenter extends BaseViewPresenter<ListView<TableEntity>> {
    @Inject TableRepositoryFactory factory;

    @Inject
    public TableGridPresenter() {
    }

    @Override
    protected void onViewAttached(@NonNull final ListView<TableEntity> view) {
        factory.get().subscribe(new Action1<Collection<TableEntity>>() {
            @Override public void call(Collection<TableEntity> tables) {
                view.add(tables);
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                view.onError(throwable);
            }
        });
    }

    public void book(TableEntity table) {
        factory.update(table.toBuilder()
                .available(false)
                .build());
    }
}
