package com.marossolutions.skeletonmultiplatform.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import com.marossolutions.skeletonmultiplatform.model.Airport
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.marossolutions.skeletonmultiplatform.theme.AppTheme
import skeletonmultiplatform.composeapp.generated.resources.Res
import skeletonmultiplatform.composeapp.generated.resources.airport_building_icon

@Composable
fun AirportItem(airport: Airport, onAirportClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable {
                onAirportClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(Res.drawable.airport_building_icon),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = airport.name,
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = airport.city + " - " + airport.icao,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun AirportItemPreview() {
    AppTheme {
        AirportItem(
            airport = Airport(
                icao = "fakeId",
                name = "Name",
                city = "Lorem ipsum dolor sit amet",
                latitude = "0.0",
                longitude = "0.0",
                timeZone = TimeZone.UTC,
            ),
            onAirportClick = {}
        )
    }
}
