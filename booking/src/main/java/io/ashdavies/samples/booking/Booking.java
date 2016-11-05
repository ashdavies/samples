package io.ashdavies.samples.booking;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import io.ashdavies.samples.booking.domain.alarm.TableCacheResetAlarm;
import io.ashdavies.samples.booking.injection.ApplicationComponent;
import io.ashdavies.samples.booking.injection.ApplicationModule;
import io.ashdavies.samples.booking.injection.DaggerApplicationComponent;
import io.ashdavies.samples.booking.injection.HasComponent;

public class Booking extends Application implements HasComponent<ApplicationComponent> {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = createComponent();
        TableCacheResetAlarm.set(this);
    }

    private ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return component;
    }

    public static Booking from(@NonNull Context context) {
        return (Booking) context.getApplicationContext();
    }
}
