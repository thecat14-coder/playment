package com.gateway.android.data.repo;

import com.gateway.android.data.api.GatewayApi;
import com.gateway.android.data.db.EvidenceDao;
import com.gateway.android.data.db.PendingEvidence;
import com.gateway.android.domain.parser.NotificationParser;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014J0\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\f2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u001cJ\u000e\u0010\u001d\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/gateway/android/data/repo/EvidenceRepository;", "", "api", "Lcom/gateway/android/data/api/GatewayApi;", "evidenceDao", "Lcom/gateway/android/data/db/EvidenceDao;", "parser", "Lcom/gateway/android/domain/parser/NotificationParser;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "(Lcom/gateway/android/data/api/GatewayApi;Lcom/gateway/android/data/db/EvidenceDao;Lcom/gateway/android/domain/parser/NotificationParser;Lcom/gateway/android/data/repo/AuthRepository;)V", "computeSignature", "", "parsed", "Lcom/gateway/android/domain/parser/NotificationParser$ParsedNotification;", "nonce", "deviceSecret", "timestamp", "", "getAllPending", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/gateway/android/data/db/PendingEvidence;", "processAndUpload", "", "notificationTitle", "notificationBody", "packageName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "retryFailed", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadEvidence", "evidence", "(Lcom/gateway/android/data/db/PendingEvidence;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class EvidenceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.api.GatewayApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.db.EvidenceDao evidenceDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.domain.parser.NotificationParser parser = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    
    @javax.inject.Inject()
    public EvidenceRepository(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.api.GatewayApi api, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.db.EvidenceDao evidenceDao, @org.jetbrains.annotations.NotNull()
    com.gateway.android.domain.parser.NotificationParser parser, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.gateway.android.data.db.PendingEvidence>> getAllPending() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object processAndUpload(@org.jetbrains.annotations.Nullable()
    java.lang.String notificationTitle, @org.jetbrains.annotations.NotNull()
    java.lang.String notificationBody, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadEvidence(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.db.PendingEvidence evidence, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object retryFailed(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String computeSignature(com.gateway.android.domain.parser.NotificationParser.ParsedNotification parsed, java.lang.String nonce, java.lang.String deviceSecret, long timestamp) {
        return null;
    }
}