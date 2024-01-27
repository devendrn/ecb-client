package devendrn.ecb.client.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MultipleStop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.R
import devendrn.ecb.client.ui.components.EcAppIcon
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcLoginScreen(
    isChecking: Boolean,
    username: String,
    password: String,
    invalidUsername: Boolean,
    invalidPassword: Boolean,
    onUsernameUpdate: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPassClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                EcAppIcon()
                Icon(
                    imageVector = Icons.Filled.MultipleStop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp)
                )
                Image(
                    painter = painterResource(R.drawable.etlab_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameUpdate,
                label = { Text(stringResource(R.string.username)) },
                singleLine = true,
                isError = invalidUsername,
                supportingText = {
                    if (invalidUsername) Text(stringResource(R.string.invalid_username))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordUpdate,
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                isError = invalidPassword,
                supportingText = {
                    if (invalidPassword) Text(stringResource(R.string.invalid_password))
                },
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            contentDescription = if (showPassword) {
                                stringResource(R.string.hide_pass)
                            } else {
                                stringResource(R.string.show_pass)
                            }
                        )
                    }
                },
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 30.dp)
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = onForgotPassClick
                ) {
                    Text(stringResource(R.string.forgot_pass))
                }
                SignInButton(
                    enabled = (username.length > 3 && password.length > 3),
                    isChecking = isChecking,
                    onSignInClick = onSignInClick,
                )
            }
        }
    }
}

@Composable
fun SignInButton(
    enabled: Boolean,
    isChecking: Boolean,
    onSignInClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onSignInClick
    ) {
        if (isChecking) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 3.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(stringResource(R.string.sign_in))
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    EcTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EcLoginScreen(
                isChecking = false,
                username = "",
                password = "",
                invalidUsername = false,
                invalidPassword = false,
                onUsernameUpdate = {},
                onPasswordUpdate = {},
                onSignInClick = {},
                onForgotPassClick = {}
            )
        }
    }
}
