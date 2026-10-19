package com.example.bai_tap_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bai_tap_1.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bai_tap_1Theme {
                AppNavigation()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Bai_tap_1Theme {
            AppNavigation()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UIComponentsListPreview() {
        Bai_tap_1Theme {
            UIComponentsListScreen(navController = rememberNavController())
        }
    }
}

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object ComponentList : Screen("component_list")
    data object Detail : Screen("detail/{componentName}") {
        fun createRoute(componentName: String) = "detail/$componentName"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.ComponentList.route) {
            UIComponentsListScreen(navController = navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val componentName = backStackEntry.arguments?.getString("componentName") ?: "Detail"
            DetailScreen(navController = navController, componentName = componentName)
        }
    }
}

// Màn hình: Chào mừng
@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(0.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.images),
                contentDescription = "logo",
                modifier = Modifier.size(250.dp).offset(y = (-85).dp)
            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(text = "Jetpack Compose", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Jetpack Compose is a modern toolkit for building native Android applications using a declarative programming approach.",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Button(
            onClick = { navController.navigate(Screen.ComponentList.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "I'm ready", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// Data cho danh sách
data class ComponentItem(val title: String, val subtitle: String)
data class Category(val title: String, val items: List<ComponentItem>)

val categories = listOf(
    Category("Display", listOf(
        ComponentItem("Text", "Displays text"),
        ComponentItem("Images", "Displays an image"),
        ComponentItem("Icon", "Displays vector icons"),
        ComponentItem("Badge", "Notification or count badge")
    )),
    Category("Input", listOf(
        ComponentItem("TextField", "Input field for text"),
        ComponentItem("PasswordField", "Input field for passwords"),
        ComponentItem("Toggle", "On/off switch"),
        ComponentItem("Slider", "Selects a value from a range"),
        ComponentItem("DatePicker", "Selects a date and time"),
        ComponentItem("TimePicker", "Selects time of day"),
        ComponentItem("Chip", "Compact element for input/filter")
    )),
    Category("Layout", listOf(
        ComponentItem("Column", "Arranges elements vertically"),
        ComponentItem("Row Layout", "Arranges elements horizontally"),
        ComponentItem("Box", "Stacks elements on top of each other"),
        ComponentItem("Spacer", "Adds empty space between items"),
        ComponentItem("LazyRow", "Horizontally scrolling list")
    )),
    Category("Selection", listOf(
        ComponentItem("Button", "Triggers an action when tapped"),
        ComponentItem("Checkbox", "Selects one or multiple options"),
        ComponentItem("RadioButton", "Selects a single option"),
        ComponentItem("Picker", "Selects a value from a list"),
        ComponentItem("FloatingActionButton", "Primary floating action button")
    )),
    Category("Navigation", listOf(
        ComponentItem("NavigationView", "Navigation container with title and back button"),
        ComponentItem("TabBar", "Switches between main sections"),
        ComponentItem("Menu", "Shows a menu with multiple actions"),
        ComponentItem("NavigationBar", "Bottom navigation bar")
    )),
    Category("Content", listOf(
        ComponentItem("List", "Displays a list of items"),
        ComponentItem("Card", "Container with content and style"),
        ComponentItem("Divider", "Separates content into sections"),
        ComponentItem("Surface", "Material surface background container")
    )),
    Category("Feedback", listOf(
        ComponentItem("ProgressIndicator", "Indicates loading or progress"),
        ComponentItem("Snackbar", "Short notification message at bottom"),
        ComponentItem("AlertDialog", "Modal dialog popup for confirmation"),
        ComponentItem("BottomSheet", "Sheet sliding up from screen bottom")
    ))
)

// Màn hình 2: Danh sách đầy đủ các nhóm thành phần UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIComponentsListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "UI Components List",
                        color = PrimaryBlue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBackground),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 12.dp,
                bottom = paddingValues.calculateBottomPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                item(key = "header_${category.title}") {
                    Text(
                        text = category.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
                items(category.items, key = { "${category.title}_${it.title}" }) { item ->
                    ComponentCard(title = item.title, subtitle = item.subtitle, navController = navController)
                }
            }
        }
    }
}

@Composable
fun ComponentCard(title: String, subtitle: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CardBackground)
            .clickable { navController.navigate(Screen.Detail.createRoute(title)) }
            .padding(12.dp)
    ) {
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}

// Màn hình 3: Chi tiết khi bấm vào từng ô
@Composable
fun DetailScreen(navController: NavController, componentName: String) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "‹",
                            fontSize = 28.sp,
                            color = PrimaryBlue
                        )
                    }
                }
                val displayTitle = if (componentName == "Text") "Text Detail" else componentName
                Text(
                    text = displayTitle,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            ComponentDetailContent(componentName = componentName)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentDetailContent(componentName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (componentName) {
            "Text" -> TextDetailDemo()
            "Images", "Image" -> ImageDetailDemo()
            "Icon" -> IconDetailDemo()
            "Badge" -> BadgeDetailDemo()
            "TextField" -> TextFieldDetailDemo()
            "PasswordField" -> PasswordFieldDetailDemo()
            "Toggle" -> ToggleDetailDemo()
            "Slider" -> SliderDetailDemo()
            "DatePicker" -> DatePickerDetailDemo()
            "TimePicker" -> TimePickerDetailDemo()
            "Chip" -> ChipDetailDemo()
            "Column" -> ColumnDetailDemo()
            "Row Layout", "Row" -> RowDetailDemo()
            "Box" -> BoxDetailDemo()
            "Spacer" -> SpacerDetailDemo()
            "LazyRow" -> LazyRowDetailDemo()
            "Button" -> ButtonDetailDemo()
            "Checkbox" -> CheckboxDetailDemo()
            "RadioButton" -> RadioButtonDetailDemo()
            "Picker" -> PickerDetailDemo()
            "FloatingActionButton" -> FabDetailDemo()
            "NavigationView" -> NavigationViewDetailDemo()
            "TabBar" -> TabBarDetailDemo()
            "Menu" -> MenuDetailDemo()
            "NavigationBar" -> NavigationBarDetailDemo()
            "List" -> ListDetailDemo()
            "Card" -> CardDetailDemo()
            "Divider" -> DividerDetailDemo()
            "Surface" -> SurfaceDetailDemo()
            "ProgressIndicator" -> ProgressIndicatorDetailDemo()
            "Snackbar" -> SnackbarDetailDemo()
            "AlertDialog" -> AlertDialogDetailDemo()
            "BottomSheet" -> BottomSheetDetailDemo()
            else -> {
                Text(
                    text = "Chi tiết thành phần: $componentName",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TextDetailDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        val annotatedText = buildAnnotatedString {
            append("The ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                append("quick ")
            }
            withStyle(
                style = SpanStyle(
                    color = Color(0xFFC06014),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Brown\n")
            }
            append("fox ")
            withStyle(style = SpanStyle(letterSpacing = 4.sp)) {
                append("j u m p s ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            ) {
                append("over\n")
            }
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("the ")
            }
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                append("lazy ")
            }
            withStyle(style = SpanStyle(fontFamily = FontFamily.Serif)) {
                append("dog.")
            }
        }

        Text(
            text = annotatedText,
            fontSize = 24.sp,
            lineHeight = 36.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
    }
}

@Composable
fun ImageDetailDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Online Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = "https://giaothongvantaihcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png",
                fontSize = 11.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "In App Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = "In app",
                fontSize = 13.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TextFieldDetailDemo() {
    var textValue by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = { Text("Thông tin nhập", color = Color.Gray) },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Text(
            text = textValue.ifEmpty { "Tự động cập nhật dữ liệu theo textfield" },
            color = Color.Red,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PasswordFieldDetailDemo() {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Password Input Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Text(if (isPasswordVisible) "Hide" else "Show", color = PrimaryBlue)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ToggleDetailDemo() {
    var isChecked by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Enable Notifications", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(if (isChecked) "Status: ON" else "Status: OFF", color = if (isChecked) PrimaryBlue else Color.Gray)
            }
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
        }
    }
}

@Composable
fun SliderDetailDemo() {
    var sliderPosition by remember { mutableFloatStateOf(40f) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Select Range Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Value: ${sliderPosition.toInt()}", fontSize = 18.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..100f
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDetailDemo() {
    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }
    val selectedDate = datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
    } ?: "Chưa chọn ngày"

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Date Picker Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Selected Date: $selectedDate", fontSize = 16.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Select Date")
            }
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = PrimaryBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun ColumnDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Vertical Column Layout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(PrimaryBlue, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                    Text("Column Item 1", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(CardBackground, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                    Text("Column Item 2", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(PrimaryBlue, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                    Text("Column Item 3", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RowDetailDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp)
                        .background(CardBackground, RoundedCornerShape(10.dp))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp)
                        .background(PrimaryBlue, RoundedCornerShape(10.dp))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp)
                        .background(CardBackground, RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Composable
fun ButtonDetailDemo() {
    var clickCount by remember { mutableIntStateOf(0) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Button Styles & Action", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Click count: $clickCount", fontSize = 16.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
            Button(
                onClick = { clickCount++ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Filled Button")
            }
            ElevatedButton(
                onClick = { clickCount++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Elevated Button", color = PrimaryBlue)
            }
            OutlinedButton(
                onClick = { clickCount++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Outlined Button", color = PrimaryBlue)
            }
            TextButton(
                onClick = { clickCount++ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Text Button", color = PrimaryBlue)
            }
        }
    }
}

@Composable
fun CheckboxDetailDemo() {
    var check1 by remember { mutableStateOf(true) }
    var check2 by remember { mutableStateOf(false) }
    var check3 by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Select Multiple Options", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = check1, onCheckedChange = { check1 = it })
                Text("Option A")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = check2, onCheckedChange = { check2 = it })
                Text("Option B")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = check3, onCheckedChange = { check3 = it })
                Text("Option C")
            }
        }
    }
}

@Composable
fun RadioButtonDetailDemo() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Select Single Option", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable { selectedOption = option }
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = { selectedOption = option }
                    )
                    Text(option)
                }
            }
            Text("Selected: $selectedOption", color = PrimaryBlue, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerDetailDemo() {
    val options = listOf("Kotlin", "Java", "Python", "Swift", "Dart")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Select Value From List", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true).fillMaxWidth(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    label = { Text("Language") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationViewDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Navigation Bar Container Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(PrimaryBlue, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("‹", fontSize = 28.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Header Title", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun TabBarDetailDemo() {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Tab Bar Switcher Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            PrimaryTabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = { Text(title) }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Content of ${titles[state]}", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MenuDetailDemo() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Dropdown Menu Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Show Options Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(text = { Text("Edit") }, onClick = { expanded = false })
                    DropdownMenuItem(text = { Text("Share") }, onClick = { expanded = false })
                    HorizontalDivider()
                    DropdownMenuItem(text = { Text("Delete", color = Color.Red) }, onClick = { expanded = false })
                }
            }
        }
    }
}

@Composable
fun ListDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Scrollable List Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                (1..4).forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardBackground, RoundedCornerShape(4.dp))
                            .padding(12.dp)
                    ) {
                        Text("List Item #$item", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
fun CardDetailDemo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Card Container Styles", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Text("Filled Card Container", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
        }
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Elevated Card Container", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
        }
        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Outlined Card Container", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DividerDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Horizontal Divider Separator", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Section 1 Content")
            HorizontalDivider(color = PrimaryBlue, thickness = 2.dp)
            Text("Section 2 Content")
            HorizontalDivider(color = Color.LightGray)
            Text("Section 3 Content")
        }
    }
}

@Composable
fun IconDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Vector Icons Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.images), contentDescription = null, modifier = Modifier.size(48.dp), tint = PrimaryBlue)
                Text("Icon component supports tinting and scaling.", fontSize = 14.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Notification Badges Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
                BadgedBox(badge = { Badge { Text("5") } }) {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)) {
                        Text("Notifications")
                    }
                }
                BadgedBox(badge = { Badge() }) {
                    Text("Messages", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDetailDemo() {
    val timePickerState = rememberTimePickerState(initialHour = 10, initialMinute = 30)
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val formattedHour = timePickerState.hour.toString().padStart(2, '0')
            val formattedMinute = timePickerState.minute.toString().padStart(2, '0')
            Text("Time Picker Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Selected Time: $formattedHour:$formattedMinute", fontSize = 16.sp, color = PrimaryBlue, fontWeight = FontWeight.Bold)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Select Time")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("OK", color = PrimaryBlue) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel", color = Color.Gray) }
            },
            text = { TimePicker(state = timePickerState) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipDetailDemo() {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Filter & Input Chips Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = selected1,
                    onClick = { selected1 = !selected1 },
                    label = { Text("Android") }
                )
                FilterChip(
                    selected = selected2,
                    onClick = { selected2 = !selected2 },
                    label = { Text("Jetpack Compose") }
                )
                AssistChip(
                    onClick = {},
                    label = { Text("Share") }
                )
            }
        }
    }
}

@Composable
fun BoxDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Stacked Box Layout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(100.dp).background(CardBackground, RoundedCornerShape(8.dp)))
                Box(modifier = Modifier.size(60.dp).background(PrimaryBlue, RoundedCornerShape(8.dp)))
                Text("Stacked", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SpacerDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Spacer Element Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(PrimaryBlue, RoundedCornerShape(4.dp)))
            Text("Blank space of 30.dp added below:", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(CardBackground, RoundedCornerShape(4.dp)))
        }
    }
}

@Composable
fun LazyRowDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Horizontal Scrolling List Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(6) { index ->
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(if (index % 2 == 0) PrimaryBlue else CardBackground, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Item ${index + 1}", color = if (index % 2 == 0) Color.White else Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun FabDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Floating Action Button Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                FloatingActionButton(
                    onClick = {},
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                ) {
                    Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                ExtendedFloatingActionButton(
                    onClick = {},
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                ) {
                    Text("Add Item")
                }
            }
        }
    }
}

@Composable
fun NavigationBarDetailDemo() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Search", "Profile")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bottom Navigation Bar Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            NavigationBar(containerColor = Color.White) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        label = { Text(item) },
                        icon = { Text("${index + 1}", fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }
    }
}

@Composable
fun SurfaceDetailDemo() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Surface Container Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = PrimaryBlue,
                shadowElevation = 4.dp
            ) {
                Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("Surface with Elevation and Background Color", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProgressIndicatorDetailDemo() {
    var progress by remember { mutableFloatStateOf(0.7f) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Progress Indicators Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryBlue
            )
            CircularProgressIndicator(
                color = PrimaryBlue
            )
        }
    }
}

@Composable
fun SnackbarDetailDemo() {
    var showSnackbar by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Snackbar Notification Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(
                onClick = { showSnackbar = !showSnackbar },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(if (showSnackbar) "Hide Snackbar" else "Show Snackbar")
            }
            if (showSnackbar) {
                Snackbar(
                    action = {
                        TextButton(onClick = { showSnackbar = false }) { Text("DISMISS", color = Color.Yellow) }
                    }
                ) {
                    Text("This is a Snackbar notification!")
                }
            }
        }
    }
}

@Composable
fun AlertDialogDetailDemo() {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Alert Dialog Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Open Alert Dialog")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Action") },
            text = { Text("Are you sure you want to proceed with this operation?") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("Confirm", color = PrimaryBlue) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel", color = Color.Gray) }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDetailDemo() {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ScreenBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Modal Bottom Sheet Demo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(
                onClick = { showSheet = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Open Bottom Sheet")
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Bottom Sheet Content", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Swipe down or click outside to dismiss.")
                Button(
                    onClick = { showSheet = false },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Close")
                }
            }
        }
    }
}
