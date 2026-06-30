package com.gateway.android.domain.parser;

import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\"\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0002\u00a8\u0006\u0013"}, d2 = {"Lcom/gateway/android/domain/parser/NotificationParser;", "", "()V", "extractAmount", "", "text", "", "(Ljava/lang/String;)Ljava/lang/Integer;", "extractBank", "extractSenderName", "extractUtr", "extractVpa", "parse", "Lcom/gateway/android/domain/parser/NotificationParser$ParsedNotification;", "title", "body", "packageName", "resolveAppName", "ParsedNotification", "app_debug"})
public final class NotificationParser {
    
    @javax.inject.Inject()
    public NotificationParser() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gateway.android.domain.parser.NotificationParser.ParsedNotification parse(@org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return null;
    }
    
    private final java.lang.Integer extractAmount(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String extractUtr(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String extractVpa(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String extractSenderName(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String extractBank(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String resolveAppName(java.lang.String packageName) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JW\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\u0005H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006\""}, d2 = {"Lcom/gateway/android/domain/parser/NotificationParser$ParsedNotification;", "", "rawText", "", "amount", "", "utr", "senderVpa", "senderName", "upiApp", "bank", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAmount", "()I", "getBank", "()Ljava/lang/String;", "getRawText", "getSenderName", "getSenderVpa", "getUpiApp", "getUtr", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class ParsedNotification {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String rawText = null;
        private final int amount = 0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String utr = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String senderVpa = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String senderName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String upiApp = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String bank = null;
        
        public ParsedNotification(@org.jetbrains.annotations.NotNull()
        java.lang.String rawText, int amount, @org.jetbrains.annotations.Nullable()
        java.lang.String utr, @org.jetbrains.annotations.Nullable()
        java.lang.String senderVpa, @org.jetbrains.annotations.Nullable()
        java.lang.String senderName, @org.jetbrains.annotations.NotNull()
        java.lang.String upiApp, @org.jetbrains.annotations.Nullable()
        java.lang.String bank) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getRawText() {
            return null;
        }
        
        public final int getAmount() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getUtr() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSenderVpa() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSenderName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getUpiApp() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getBank() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.gateway.android.domain.parser.NotificationParser.ParsedNotification copy(@org.jetbrains.annotations.NotNull()
        java.lang.String rawText, int amount, @org.jetbrains.annotations.Nullable()
        java.lang.String utr, @org.jetbrains.annotations.Nullable()
        java.lang.String senderVpa, @org.jetbrains.annotations.Nullable()
        java.lang.String senderName, @org.jetbrains.annotations.NotNull()
        java.lang.String upiApp, @org.jetbrains.annotations.Nullable()
        java.lang.String bank) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}