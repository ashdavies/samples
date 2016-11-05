package io.ashdavies.samples.booking.injection;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.ashdavies.samples.booking.data.service.BookingService;
import io.ashdavies.samples.booking.domain.entity.Models;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.ConfigurationBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Context context() {
        return application;
    }

    @Provides
    SharedPreferences preferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    Scheduler scheduler() {
        return Schedulers.io();
    }

    @Provides
    Configuration configuration(Context context) {
        return new ConfigurationBuilder(new DatabaseSource(context, Models.DEFAULT, 1), Models.DEFAULT)
                .build();
    }

    @Provides
    @Named("endpoint")
    String endpoint() {
        return "http://ashdavies.io/samples/booking";
    }

    @Provides
    Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                .create();
    }

    @Provides
    Retrofit retrofit(@Named("endpoint") String endpoint, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    BookingService service(Retrofit retrofit) {
        return retrofit.create(BookingService.class);
    }
}
