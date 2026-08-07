package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.UserProfile
import com.example.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private val _myProfile = MutableStateFlow<UserProfile?>(null)
    val myProfile: StateFlow<UserProfile?> = _myProfile

    val isVerificationPending = MutableStateFlow(false)

    init {
        loadMyProfile()
    }

    private fun loadMyProfile() {
        viewModelScope.launch {
            val existing = profileRepository.getProfileByIdOnce("my_user_id")
            if (existing != null) {
                _myProfile.value = existing
            } else {
                val newProfile = UserProfile(
                    id = "my_user_id",
                    displayName = "New User",
                    age = 20,
                    gender = "Male",
                    lookingFor = "Female",
                    bio = "",
                    occupation = "",
                    education = "",
                    city = "",
                    isProfileComplete = false
                )
                profileRepository.saveProfile(newProfile)
                _myProfile.value = newProfile
            }
        }
    }

    fun updateProfile(profile: UserProfile) {
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
            _myProfile.value = profile
        }
    }

    fun submitVerificationPhoto(photoUrl: String) {
        isVerificationPending.value = true
        viewModelScope.launch {
            val current = _myProfile.value ?: return@launch
            val updated = current.copy(isVerified = true)
            profileRepository.saveProfile(updated)
            _myProfile.value = updated
            isVerificationPending.value = false
        }
    }
}
