package ru.foolstack.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.FieldsDefaultBorderColor
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import java.io.ByteArrayOutputStream


@Composable
fun Avatar(avatar: String, modifier: Modifier, onClick: (ByteArray?) -> Unit = {}){
    Log.d("аватара", "is ${avatar.length}")

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            bitmap.value?.let { btm ->
                val stream = ByteArrayOutputStream()
                btm.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                onClick(byteArray)
            }
        }
    }
    Box(modifier = modifier
        .padding(top = 72.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center){
    Box(modifier = Modifier
        .size(120.dp)
        .clip(shape = CircleShape)
        .background(MaterialTheme.colorScheme.FieldsDefaultBorderColor)
        .clickable {
            launcher.launch("image/*")
        }){
        if(avatar.isNotEmpty()){
            avatar.decodeBase64ToBitmap()?.let {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    bitmap = it,
                    contentDescription = "Avatar"
                )
            }
        }
        else{
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = "Avatar",
            )
        }
    }
    }
}