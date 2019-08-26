package com.msil.sharedmobility.subscribe.di.module.activity;

import android.app.Activity;
import android.content.Context;
import com.msil.sharedmobility.subscribe.di.qualifier.ActivityContext;
import com.msil.sharedmobility.subscribe.di.qualifier.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityContextModule {

    private final Context context;

    ActivityContextModule(Activity context) {
        this.context = context;
    }

    @ActivityContext
    @PerActivity
    @Provides
    public Context context() {
        return context;
    }
}
