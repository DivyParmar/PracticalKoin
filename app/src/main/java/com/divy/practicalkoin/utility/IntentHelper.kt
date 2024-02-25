package com.divy.practicalkoin.utility

import android.content.Context
import android.content.Intent

class IntentHelper {

    companion object {
        fun getMainActivityIntent(context: Context, isClearFlag: Boolean? = false): Intent {
            return Intent(context, com.divy.practicalkoin.activities.MainActivity::class.java).also {
                if (isClearFlag != null && isClearFlag) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }


        fun getPickMediaActivityIntent(context: Context, isClearFlag: Boolean? = false): Intent {
            return Intent(context, com.divy.practicalkoin.activities.PickMediaActivity::class.java).also {
                if (isClearFlag != null && isClearFlag) {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
    }
}