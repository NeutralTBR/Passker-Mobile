package com.neutraltbr.passker

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import java.util.Base64
import kotlin.random.Random

fun encodeBase64(input: String): String {
    val encodedString = Base64.getEncoder().encodeToString(input.toByteArray())
    return encodedString
}

fun shuffleString(input: String): String {
    val chars = input.toCharArray()
    chars.shuffle(Random.Default)
    return String(chars)
}

fun swapCase(input: String): String {
    return input.map { char ->
        when{
            char.isUpperCase() -> char.lowercaseChar()
            char.isLowerCase() -> char.uppercaseChar()
            else -> char
        }
    }.joinToString("")
}

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun processString(input: String): String{
    val encoded = encodeBase64(input)
    val shuffled = shuffleString(encoded)
    val swapped = swapCase(shuffled)

    if (input.isEmpty()) return input

    return swapped
}

fun repeatProcess(input: String, times: Int): String {
    var result = input

    if (times <= 0) return processString(input)

    result = processString(input)

    repeat(times) {
        result = processString(result)
    }

    return result
}