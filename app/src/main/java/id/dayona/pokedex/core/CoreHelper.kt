/*
 * Copyright (c) 2023 (  Dayona )
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package id.dayona.pokedex.core

import android.annotation.SuppressLint
import android.database.CursorWindow
import kotlinx.coroutines.Job
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Field
import java.math.RoundingMode
import java.text.DecimalFormat

sealed interface CoreHelper<out T>
class CoreSuccess<T>(val data: T) : CoreHelper<T>
class CoreError<T : Any>(val code: String, val message: String?) : CoreHelper<T>
class CoreException<T : Any>(val e: String) : CoreHelper<T>
object CoreLoading : CoreHelper<Nothing>

@SuppressLint("PrivateApi")
fun increaseDatabaseCapacity() {
    try {
        val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
        field.isAccessible = true
        field.set(null, 100 * 1024 * 1024)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Job.status(): String = when {
    isActive -> "Active/Completing"
    isCompleted && isCancelled -> "Cancelled"
    isCancelled -> "Cancelling"
    isCompleted -> "Completed"
    else -> "New"
}


val HTTP_LOGGER = "HTTP_LOGGER"
val ANOTHER_LOGGER = "ANOTHER_LOGGER"


fun sizeOf(obj: Any?): String? {
    if (obj == null)
        return "0"
    // Special output stream use to write the content
    // of an output stream to an internal byte array.
    val byteArrayOutputStream = ByteArrayOutputStream()
    // Output stream that can write object
    val objectOutputStream = ObjectOutputStream(byteArrayOutputStream);
    // Write object and close the output stream
    objectOutputStream.writeObject(obj)
    objectOutputStream.flush()
    objectOutputStream.close()
    // Get the byte array
    val byteArray = byteArrayOutputStream.toByteArray()
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    val dbSize = 1000 * 1024 * 1024
    return df.format(dbSize / 1024 / 1024)
}

