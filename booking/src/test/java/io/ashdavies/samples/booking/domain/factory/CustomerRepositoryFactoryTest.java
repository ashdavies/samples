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
import io.ashdavies.samples.booking.data.cache.CustomerCacheRepository;
import io.ashdavies.samples.booking.data.network.CustomerNetworkRepository;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class CustomerRepositoryFactoryTest {
    private CustomerRepositoryFactory factory;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock CustomerNetworkRepository network;
    @Mock CustomerCacheRepository cache;

    @Before
    public void setUp() throws Exception {
        factory = new CustomerRepositoryFactory();
        factory.network = network;
        factory.cache = cache;

        factory.scheduler = Schedulers.immediate();
    }

    @Test
    public void assertCacheReturnedWhenNotEmpty() throws Exception {
        List<CustomerEntity> list = new ArrayList<>();
        list.add(mock(CustomerEntity.class));

        when(cache.get()).thenReturn(Observable.just(list));
        assertEquals(list, factory.get().toBlocking().first());
    }

    @Test
    public void assertNetworkRequestWhenCacheEmpty() throws Exception {
        when(cache.get()).thenReturn(Observable.<List<CustomerEntity>>empty());

        List<CustomerEntity> list = new ArrayList<>();
        list.add(mock(CustomerEntity.class));

        when(network.get()).thenReturn(Observable.just(list));
        assertEquals(list, factory.get().toBlocking().first());
    }

    @Test
    public void assertReturnsOnlyOneResult() throws Exception {
        final List<CustomerEntity> list = new ArrayList<>();
        list.add(mock(CustomerEntity.class));

        when(cache.get()).thenReturn(Observable.create(new Observable.OnSubscribe<List<CustomerEntity>>() {
            @Override public void call(Subscriber<? super List<CustomerEntity>> subscriber) {
                subscriber.onNext(list);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }));

        TestSubscriber<List<CustomerEntity>> subscriber = new TestSubscriber<>();
        factory.get().subscribe(subscriber);

        subscriber.assertValueCount(1);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }
}
