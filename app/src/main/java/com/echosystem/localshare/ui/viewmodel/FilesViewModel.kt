package com.echosystem.localshare.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.echosystem.localshare.model.SharedFile
import com.echosystem.localshare.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    val receivedFiles: StateFlow<List<SharedFile>> = fileRepository.filesFlow
    
    fun deleteFile(id: String) {
        fileRepository.deleteFile(id)
    }
}
