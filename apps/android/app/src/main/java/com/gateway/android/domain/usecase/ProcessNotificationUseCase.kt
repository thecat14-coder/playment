package com.gateway.android.domain.usecase

import com.gateway.android.data.repo.EvidenceRepository
import javax.inject.Inject

class ProcessNotificationUseCase @Inject constructor(
    private val evidenceRepo: EvidenceRepository,
) {
    suspend operator fun invoke(
        title: String?,
        body: String,
        packageName: String,
        timestamp: Long,
    ) {
        evidenceRepo.processAndUpload(title, body, packageName, timestamp)
    }
}
