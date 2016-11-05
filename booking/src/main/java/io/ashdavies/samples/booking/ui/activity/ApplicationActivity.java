package io.ashdavies.samples.booking.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import butterknife.BindView;
import io.ashdavies.samples.booking.Booking;
import io.ashdavies.R;
import io.ashdavies.commons.activity.BaseToolbarActivity;
import io.ashdavies.samples.booking.injection.InjectionTarget;
import io.ashdavies.samples.booking.injection.ApplicationComponent;

public abstract class ApplicationActivity extends BaseToolbarActivity implements InjectionTarget<ApplicationComponent> {
    @BindView(R.id.coordinator) CoordinatorLayout coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(Booking.from(this).getComponent());
    }

    @Override
    public void onError(Throwable throwable) {
        Snackbar.make(coordinator, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
        throwable.printStackTrace();
    }
}
