package io.ashdavies.samples.booking.ui.presenter;

import android.support.annotation.NonNull;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.presenter.BaseViewPresenter;
import io.ashdavies.commons.view.ListView;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import io.ashdavies.samples.booking.domain.factory.CustomerRepositoryFactory;
import rx.functions.Action1;

public class CustomerListPresenter extends BaseViewPresenter<ListView<CustomerEntity>> {
    @Inject CustomerRepositoryFactory factory;

    @Inject
    public CustomerListPresenter() {
    }

    @Override
    protected void onViewAttached(@NonNull final ListView<CustomerEntity> view) {
        factory.get().subscribe(new Action1<Collection<CustomerEntity>>() {
            @Override public void call(Collection<CustomerEntity> customers) {
                view.add(customers);
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                view.onError(throwable);
            }
        });
    }
}
