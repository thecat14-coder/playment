package com.gateway.android.domain.usecase;

import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.service.NotificationListenerService;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086B\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\f"}, d2 = {"Lcom/gateway/android/domain/usecase/SendHeartbeatUseCase;", "", "deviceRepo", "Lcom/gateway/android/data/repo/DeviceRepository;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "(Lcom/gateway/android/data/repo/DeviceRepository;Lcom/gateway/android/data/repo/AuthRepository;)V", "invoke", "Lkotlin/Result;", "", "invoke-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SendHeartbeatUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.DeviceRepository deviceRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    
    @javax.inject.Inject()
    public SendHeartbeatUseCase(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.DeviceRepository deviceRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo) {
        super();
    }
}