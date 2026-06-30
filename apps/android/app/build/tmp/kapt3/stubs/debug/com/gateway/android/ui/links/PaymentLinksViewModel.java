package com.gateway.android.ui.links;

import androidx.lifecycle.ViewModel;
import com.gateway.android.data.api.PaymentData;
import com.gateway.android.data.repo.MerchantRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2 = {"Lcom/gateway/android/ui/links/PaymentLinksViewModel;", "Landroidx/lifecycle/ViewModel;", "merchantRepo", "Lcom/gateway/android/data/repo/MerchantRepository;", "(Lcom/gateway/android/data/repo/MerchantRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gateway/android/ui/links/PaymentLinksUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "load", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PaymentLinksViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.MerchantRepository merchantRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gateway.android.ui.links.PaymentLinksUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.links.PaymentLinksUiState> state = null;
    
    @javax.inject.Inject()
    public PaymentLinksViewModel(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.MerchantRepository merchantRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.links.PaymentLinksUiState> getState() {
        return null;
    }
    
    public final void load() {
    }
}