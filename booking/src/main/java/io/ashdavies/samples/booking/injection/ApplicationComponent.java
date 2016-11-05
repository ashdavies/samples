package io.ashdavies.samples.booking.injection;

import javax.inject.Singleton;

import dagger.Component;
import io.ashdavies.samples.booking.domain.alarm.TableCacheResetAlarm;
import io.ashdavies.samples.booking.ui.activity.CustomerListActivity;
import io.ashdavies.samples.booking.ui.activity.TableGridActivity;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(CustomerListActivity activity);

    void inject(TableGridActivity activity);

    void inject(TableCacheResetAlarm alarm);
}
