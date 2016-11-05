package io.ashdavies.samples.booking.data.network;

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
import io.ashdavies.samples.booking.data.service.QuandooService;
import rx.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class TableNetworkRepositoryTest {
    private TableNetworkRepository repository;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock QuandooService service;

    @Before
    public void setUp() throws Exception {
        repository = new TableNetworkRepository();
        repository.service = service;
    }

    @Test
    public void assertGet() throws Exception {
        List<Boolean> list = new ArrayList<>();
        when(service.getTableMap()).thenReturn(Observable.just(list));

        repository.get();
        verify(service, times(1)).getTableMap();
    }
}
