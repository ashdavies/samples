package io.ashdavies.samples.booking.domain.factory;

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
import io.ashdavies.samples.booking.data.cache.TableCacheRepository;
import io.ashdavies.samples.booking.data.network.TableNetworkRepository;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class TableRepositoryFactoryTest {
    private TableRepositoryFactory factory;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock TableNetworkRepository network;
    @Mock TableCacheRepository cache;

    @Before
    public void setUp() throws Exception {
        factory = new TableRepositoryFactory();
        factory.network = network;
        factory.cache = cache;

        factory.scheduler = Schedulers.immediate();
    }

    @Test
    public void assertCacheReturnedWhenNotEmpty() throws Exception {
        List<TableEntity> list = new ArrayList<>();
        list.add(mock(TableEntity.class));

        when(cache.get()).thenReturn(Observable.just(list));
        assertEquals(list, factory.get().toBlocking().first());
    }

    @Test
    public void assertNetworkRequestWhenCacheEmpty() throws Exception {
        when(cache.get()).thenReturn(Observable.<List<TableEntity>>empty());

        List<TableEntity> list = new ArrayList<>();
        list.add(mock(TableEntity.class));

        when(network.get()).thenReturn(Observable.just(list));
        assertEquals(list, factory.get().toBlocking().first());
    }

    @Test
    public void assertReturnsOnlyOneResult() throws Exception {
        final List<TableEntity> list = new ArrayList<>();
        list.add(mock(TableEntity.class));

        when(cache.get()).thenReturn(Observable.create(new Observable.OnSubscribe<List<TableEntity>>() {
            @Override public void call(Subscriber<? super List<TableEntity>> subscriber) {
                subscriber.onNext(list);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }));

        TestSubscriber<List<TableEntity>> subscriber = new TestSubscriber<>();
        factory.get().subscribe(subscriber);

        subscriber.assertValueCount(1);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }
}
