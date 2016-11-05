package io.ashdavies.samples.booking.ui.presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.ashdavies.BuildConfig;
import io.ashdavies.cumin.view.ListView;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import io.ashdavies.samples.booking.domain.factory.CustomerRepositoryFactory;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class CustomerListPresenterTest {
    private CustomerListPresenter presenter;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock CustomerRepositoryFactory factory;
    @Mock ListView<CustomerEntity> view;

    @Before
    public void setUp() throws Exception {
        presenter = new CustomerListPresenter();
        presenter.factory = factory;
    }

    @Test
    public void assertAddOnViewAttached() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        list.add(mock(CustomerEntity.class));

        when(factory.get()).thenReturn(Observable.just(list));
        presenter.onAttach(view);

        verify(view, times(1)).add(list);
    }

    @Test
    public void assertOnErrorCalled() throws Exception {
        Observable<List<CustomerEntity>> observable = Observable.error(new RuntimeException());

        when(factory.get()).thenReturn(observable);
        presenter.onAttach(view);

        verify(view, times(1)).onError(any(RuntimeException.class));
    }
}
