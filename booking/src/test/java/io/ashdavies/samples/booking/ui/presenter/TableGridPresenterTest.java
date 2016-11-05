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
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.ashdavies.samples.booking.domain.factory.TableRepositoryFactory;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class TableGridPresenterTest {
    private TableGridPresenter presenter;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock TableRepositoryFactory factory;
    @Mock ListView<TableEntity> view;

    @Before
    public void setUp() throws Exception {
        presenter = new TableGridPresenter();
        presenter.factory = factory;
    }

    @Test
    public void assertAddOnViewAttached() throws Exception {
        List<TableEntity> list = new ArrayList<>();
        list.add(mock(TableEntity.class));

        when(factory.get()).thenReturn(Observable.just(list));
        presenter.onAttach(view);

        verify(view, times(1)).add(list);
    }

    @Test
    public void assertOnErrorCalled() throws Exception {
        Observable<List<TableEntity>> observable = Observable.error(new RuntimeException());

        when(factory.get()).thenReturn(observable);
        presenter.onAttach(view);

        verify(view, times(1)).onError(any(RuntimeException.class));
    }
}
