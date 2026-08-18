package com.mdc.serial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stethoscope
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MDCSerialApp()
                }
            }
        }
    }
}

@Composable
fun MDCSerialApp() {

    var currentPage by remember { mutableStateOf("dashboard") }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "MDC Serial",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "মুন ডায়াগনস্টিক সেন্টার",
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    BoxMenu(
                        expanded = menuExpanded,
                        onExpandedChange = {
                            menuExpanded = !menuExpanded
                        },
                        onControlPanel = {
                            menuExpanded = false
                            currentPage = "control"
                        },
                        onLogout = {
                            menuExpanded = false
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Pull-to-refresh ব্যবহার করা হবে।
                            // ২০ সেকেন্ডের Auto Refresh নেই।
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { padding ->

        when (currentPage) {

            "dashboard" -> DashboardPage(
                modifier = Modifier.padding(padding),
                onNavigate = { currentPage = it }
            )

            "total" -> TotalSerialPage(
                modifier = Modifier.padding(padding)
            )

            "add_serial" -> AddSerialPage(
                modifier = Modifier.padding(padding)
            )

            "add_doctor" -> AddDoctorPage(
                modifier = Modifier.padding(padding)
            )

            "add_care" -> AddCareOfPage(
                modifier = Modifier.padding(padding)
            )

            "control" -> ControlPanelPage(
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun BoxMenu(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onControlPanel: () -> Unit,
    onLogout: () -> Unit
) {
    Box {

        IconButton(onClick = onExpandedChange) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onExpandedChange
        ) {

            DropdownMenuItem(
                text = {
                    Text("Control Panel")
                },
                onClick = onControlPanel
            )

            DropdownMenuItem(
                text = {
                    Text("Logout")
                },
                onClick = onLogout
            )
        }
    }
}

@Composable
fun DashboardPage(
    modifier: Modifier,
    onNavigate: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "ড্যাশবোর্ড",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "আজকের সিরিয়াল ব্যবস্থাপনা",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "দ্রুত অ্যাকশন",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ActionCard(
                title = "সব সিরিয়াল",
                icon = Icons.Default.Search,
                onClick = {
                    onNavigate("total")
                }
            )

            ActionCard(
                title = "এড সিরিয়াল",
                icon = Icons.Default.Add,
                onClick = {
                    onNavigate("add_serial")
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ActionCard(
                title = "এড ডাক্তার",
                icon = Icons.Default.Stethoscope,
                onClick = {
                    onNavigate("add_doctor")
                }
            )

            ActionCard(
                title = "এড কেয়ার অফ",
                icon = Icons.Default.Groups,
                onClick = {
                    onNavigate("add_care")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "আজকের সারাংশ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    SummaryItem(
                        title = "মোট",
                        value = "0"
                    )

                    SummaryItem(
                        title = "অপেক্ষমাণ",
                        value = "0"
                    )

                    SummaryItem(
                        title = "সম্পন্ন",
                        value = "0"
                    )
                }
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(165.dp)
            .height(105.dp),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SummaryItem(
    title: String,
    value: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = title,
            fontSize = 12.sp
        )
    }
}

@Composable
fun TotalSerialPage(
    modifier: Modifier
) {

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "সব সিরিয়াল",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        TabRow(
            selectedTabIndex = selectedTab
        ) {

            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                },
                text = {
                    Text("ডাক্তার ওয়াইজ")
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                },
                text = {
                    Text("কেয়ার অফ ওয়াইজ")
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (selectedTab == 0) {
            DoctorWiseSerial()
        } else {
            CareOfWiseSerial()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorWiseSerial() {

    var date by remember { mutableStateOf("") }
    var doctor by remember { mutableStateOf("") }

    Column {

        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("তারিখ")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = doctor,
            onValueChange = {
                doctor = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("ডাক্তার নির্বাচন করুন")
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("সিরিয়াল দেখুন")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ডাক্তার নির্বাচন করলে সিরিয়াল এখানে দেখা যাবে।",
            fontSize = 14.sp
        )
    }
}

@Composable
fun CareOfWiseSerial() {

    var date by remember { mutableStateOf("") }
    var careOf by remember { mutableStateOf("") }

    Column {

        OutlinedTextField(
            value = date,
            onValueChange = {
                date = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("তারিখ")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = careOf,
            onValueChange = {
                careOf = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("কেয়ার অফ নির্বাচন করুন")
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("সিরিয়াল দেখুন")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "কেয়ার অফ নির্বাচন করলে সিরিয়াল এখানে দেখা যাবে।",
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSerialPage(
    modifier: Modifier
) {

    var patientName by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    var careOfName by remember { mutableStateOf("") }
    var serialDate by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "এড সিরিয়াল",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = patientName,
            onValueChange = {
                patientName = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("রোগীর নাম")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = doctorName,
            onValueChange = {
                doctorName = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("ডাক্তার নাম নির্বাচন করুন")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = careOfName,
            onValueChange = {
                careOfName = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("কেয়ার অফ নির্বাচন করুন")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = serialDate,
            onValueChange = {
                serialDate = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("সিরিয়ালের তারিখ")
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("সিরিয়াল যোগ করুন")
        }
    }
}

@Composable
fun AddDoctorPage(
    modifier: Modifier
) {

    var name by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "এড ডাক্তার",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("ডাক্তারের নাম লিখুন")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = designation,
            onValueChange = {
                designation = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("ডাক্তারের পদবী লিখুন")
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ডাক্তার যোগ করুন")
        }
    }
}

@Composable
fun AddCareOfPage(
    modifier: Modifier
) {

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "এড কেয়ার অফ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("কেয়ার অফের নাম লিখুন")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("ঠিকানা লিখুন")
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("কেয়ার অফ যোগ করুন")
        }
    }
}

@Composable
fun ControlPanelPage(
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Admin Control Panel",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "ব্যবহারকারী ও অপারেটর নিয়ন্ত্রণ",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Admin এখান থেকে User ও Operator-এর অ্যাক্সেস নিয়ন্ত্রণ করবে।"
                )
            }
        }
    }
}
