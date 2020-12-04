package com.jaa.likeastarappmpp.message

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.StringDesc

class MessageManager(
    private val context:Context
) {

    fun showErrorMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun getStringFromResource(resource: ResourceStringDesc): String {
        return resource.toString(context)
    }

    fun showAlert(title: StringDesc, description: StringDesc, buttonTitle: StringDesc) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title.toString(context))
        builder.setMessage(description.toString(context))
        builder.setPositiveButton(buttonTitle.toString(context), null)
        builder.show()
    }

}