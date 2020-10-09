package com.fatdino.blabrrr.rules

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulersOverrideRule : TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler: Scheduler? -> Schedulers.trampoline() }
                base.evaluate()
                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}