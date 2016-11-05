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

import io.ashdavies.BuildConfig;
import io.ashdavies.samples.booking.data.service.QuandooService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class CustomerNetworkRepositoryTest {
    private CustomerNetworkRepository repository;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock QuandooService service;

    @Before
    public void setUp() throws Exception {
        repository = new CustomerNetworkRepository();
        repository.service = service;
    }

    @Test
    public void assertGet() throws Exception {
        repository.get();
        verify(service, times(1)).getCustomerList();
    }
}
