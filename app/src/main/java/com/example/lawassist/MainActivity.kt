package com.example.lawassist
import androidx.compose.foundation.background
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lawassist.database.LawDatabase
import com.example.lawassist.repository.LawRepository
import com.example.lawassist.ui.theme.LawAssistTheme
import com.example.lawassist.viewmodel.LawViewModel
import com.example.lawassist.viewmodel.LawViewModelFactory
import kotlinx.coroutines.launch

// Message data class to store chat history
data class ChatMessage(
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class MainActivity : ComponentActivity() {

    private val lawRepository: LawRepository by lazy {
        val lawDao = LawDatabase.getDatabase(applicationContext).lawDao()
        LawRepository(lawDao)
    }

    private val lawViewModel: LawViewModel by viewModels {
        LawViewModelFactory(lawRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LawAssistTheme {
                ModernChatScreen(viewModel = lawViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernChatScreen(viewModel: LawViewModel) {
    // Maintain chat history
    val messages = remember { mutableStateListOf<ChatMessage>() }

    // Track AI response
    val aiResponse by viewModel.aiResponse
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Add initial greeting message if no messages exist
    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            messages.add(
                ChatMessage(
                    "I'm LawAssist, your AI assistant for laws, government schemes and accessibility services in India. I provide quick, clear, and easy-to-understand answers to help you navigate various government initiatives like PM Awas Yojana, Bharat and Sugamya Bharat Abhiyan. I'm here to assist you in accessing essential services and benefits.",
                    false
                )
            )
        }
    }

    // Track when a new AI response comes in
    LaunchedEffect(aiResponse) {
        if (aiResponse.isNotEmpty()) {
            // Find if this response is already in messages to avoid duplicates
            val responseExists = messages.any { !it.isFromUser && it.content == aiResponse }
            if (!responseExists) {
                messages.add(ChatMessage(aiResponse, false))
                coroutineScope.launch {
                    listState.animateScrollToItem(messages.size - 1)
                }
            }
        }
    }

    // Main chat interface layout
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top app bar
            TopAppBar(
                title = {
                    Text(
                        "Law Assist",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )

            // Chat message area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(messages) { message ->
                        ChatBubble(message)
                    }
                }
            }

            // Bottom message input area
            ChatInputBar(
                onMessageSent = { userMessage ->
                    if (userMessage.isNotEmpty()) {
                        messages.add(ChatMessage(userMessage, true))
                        viewModel.queryGroqLlama(userMessage)
                        coroutineScope.launch {
                            listState.animateScrollToItem(messages.size - 1)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isFromUser) 4.dp else 16.dp
                    )
                )
                .background(
                    if (message.isFromUser) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = message.content,
                color = if (message.isFromUser) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(2.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(onMessageSent: (String) -> Unit) {
    var userInput by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text input field
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Type your question...") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onMessageSent(userInput)
                        userInput = ""
                    }
                ),
                shape = RoundedCornerShape(24.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Send button
            Button(
                onClick = {
                    onMessageSent(userInput)
                    userInput = ""
                },
                enabled = userInput.isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            ) {
                Text("Send", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val lawDao = LawDatabase.getDatabase(context).lawDao()
    val lawRepository = LawRepository(lawDao)
    val viewModel = LawViewModel(lawRepository)

    LawAssistTheme {
        ModernChatScreen(viewModel = viewModel)
    }
}