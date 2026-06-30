package com.gateway.android.ui.payments;

import androidx.lifecycle.ViewModel;
import com.gateway.android.data.api.PaymentData;
import com.gateway.android.data.repo.MerchantRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0010R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0013"}, d2 = {"Lcom/gateway/android/ui/payments/PaymentsViewModel;", "Landroidx/lifecycle/ViewModel;", "merchantRepo", "Lcom/gateway/android/data/repo/MerchantRepository;", "(Lcom/gateway/android/data/repo/MerchantRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gateway/android/ui/payments/PaymentsUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "load", "", "setFilter", "status", "", "setSearch", "query", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PaymentsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.MerchantRepository merchantRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gateway.android.ui.payments.PaymentsUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.payments.PaymentsUiState> state = null;
    
    @javax.inject.Inject()
    public PaymentsViewModel(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.MerchantRepository merchantRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.payments.PaymentsUiState> getState() {
        return null;
    }
    
    public final void setFilter(@org.jetbrains.annotations.Nullable()
    java.lang.String status) {
    }
    
    public final void setSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void load() {
    }
}