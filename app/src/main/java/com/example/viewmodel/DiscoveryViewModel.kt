package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Match
import com.example.data.model.UserProfile
import com.example.data.repository.MatchRepository
import com.example.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DiscoveryFilterState(
    val maxDistanceKm: Int = 50,
    val minAge: Int = 18,
    val maxAge: Int = 45,
    val verifiedOnly: Boolean = false,
    val selectedInterests: Set<String> = emptySet(),
    val genderPreference: String = "All"
)

class DiscoveryViewModel(
    private val profileRepository: ProfileRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _filterState = MutableStateFlow(DiscoveryFilterState())
    val filterState: StateFlow<DiscoveryFilterState> = _filterState

    val rawProfiles: StateFlow<List<UserProfile>> = profileRepository.getDiscoveryProfiles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredProfiles: StateFlow<List<UserProfile>> = combine(rawProfiles, _filterState) { list, filter ->
        list.filter { profile ->
            profile.age in filter.minAge..filter.maxAge &&
                    profile.distanceKm <= filter.maxDistanceKm &&
                    (!filter.verifiedOnly || profile.isVerified)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _matchOverlay = MutableStateFlow<Match?>(null)
    val matchOverlay: StateFlow<Match?> = _matchOverlay

    private val lastSwipedId = MutableStateFlow<String?>(null)

    fun swipeRight(profile: UserProfile) {
        viewModelScope.launch {
            profileRepository.likeProfile(profile.id)
            lastSwipedId.value = profile.id

            // Create mutual match for rich user feedback
            val newMatch = Match(
                matchId = "match_${profile.id}",
                userId = profile.id,
                userName = profile.displayName,
                userAge = profile.age,
                userPhotoUrl = profile.photosJson,
                userOccupation = profile.occupation,
                matchInsight = "96% Compatibility • Both interested in Travel & Food"
            )
            matchRepository.insertMatch(newMatch)
            _matchOverlay.value = newMatch
        }
    }

    fun swipeLeft(profile: UserProfile) {
        viewModelScope.launch {
            profileRepository.passProfile(profile.id)
            lastSwipedId.value = profile.id
        }
    }

    fun superLike(profile: UserProfile) {
        viewModelScope.launch {
            profileRepository.superLikeProfile(profile.id)
            lastSwipedId.value = profile.id

            val newMatch = Match(
                matchId = "match_${profile.id}",
                userId = profile.id,
                userName = profile.displayName,
                userAge = profile.age,
                userPhotoUrl = profile.photosJson,
                userOccupation = profile.occupation,
                matchInsight = "⭐ Super Liked! 99% Compatibility"
            )
            matchRepository.insertMatch(newMatch)
            _matchOverlay.value = newMatch
        }
    }

    fun rewind() {
        val lastId = lastSwipedId.value
        if (lastId != null) {
            viewModelScope.launch {
                profileRepository.rewindDecision(lastId)
                lastSwipedId.value = null
            }
        }
    }

    fun updateFilters(newFilters: DiscoveryFilterState) {
        _filterState.value = newFilters
    }

    fun dismissMatchOverlay() {
        _matchOverlay.value = null
    }

    fun addSampleUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
        }
    }
}
