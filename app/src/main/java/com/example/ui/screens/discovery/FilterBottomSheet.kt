package com.example.ui.screens.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodel.DiscoveryFilterState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilter: DiscoveryFilterState,
    onApplyFilters: (DiscoveryFilterState) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    var distanceKm by remember { mutableStateOf(currentFilter.maxDistanceKm.toFloat()) }
    var ageRange by remember { mutableStateOf(currentFilter.minAge.toFloat()..currentFilter.maxAge.toFloat()) }
    var verifiedOnly by remember { mutableStateOf(currentFilter.verifiedOnly) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.testTag("filter_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Discovery Preferences",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Maximum Distance
            Text(
                text = "Maximum Distance: ${distanceKm.toInt()} km",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Slider(
                value = distanceKm,
                onValueChange = { distanceKm = it },
                valueRange = 5f..150f,
                modifier = Modifier.testTag("distance_slider")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Age Range
            Text(
                text = "Age Range: ${ageRange.start.toInt()} - ${ageRange.endInclusive.toInt()} years",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            RangeSlider(
                value = ageRange,
                onValueChange = { ageRange = it },
                valueRange = 18f..70f,
                modifier = Modifier.testTag("age_range_slider")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Verified Only
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Verified Profiles Only",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Only show profiles with confirmed photo verification",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = verifiedOnly,
                    onCheckedChange = { verifiedOnly = it },
                    modifier = Modifier.testTag("verified_switch")
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onApplyFilters(
                        DiscoveryFilterState(
                            maxDistanceKm = distanceKm.toInt(),
                            minAge = ageRange.start.toInt(),
                            maxAge = ageRange.endInclusive.toInt(),
                            verifiedOnly = verifiedOnly
                        )
                    )
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("apply_filters_button"),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text(text = "Apply Preferences", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
