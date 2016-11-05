package io.ashdavies.samples.booking.domain.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.ashdavies.samples.booking.Booking;
import io.ashdavies.samples.booking.data.cache.TableCacheRepository;

public class TableCacheResetAlarm extends BroadcastReceiver {
    private static final long DELAY = TimeUnit.MINUTES.toMillis(10);

    @Inject
    TableCacheRepository repository;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager service = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock lock = service.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Quandoo");
        lock.acquire();

        Booking.from(context).getComponent().inject(this);
        repository.reset();

        lock.release();
    }

    public static void set(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, TableCacheResetAlarm.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), DELAY, pending);
    }

    public void cancel(Context context) {
        Intent intent = new Intent(context, TableCacheResetAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(sender);
    }
}
