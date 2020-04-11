package com.gianlu.commonutils.dialogs;

import android.app.Dialog;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.gianlu.commonutils.ui.NightlyActivity;
import com.gianlu.commonutils.ui.Toaster;

public abstract class ActivityWithDialog extends NightlyActivity implements DialogUtils.ShowStuffInterface {
    private Dialog mDialog;

    @Override
    public final void showDialog(@NonNull Dialog dialog) {
        mDialog = dialog;
        DialogUtils.showDialogValid(this, mDialog);
    }

    @Override
    public final void showDialog(@NonNull AlertDialog.Builder dialog) {
        DialogUtils.showDialogValid(this, dialog, dialog1 -> mDialog = dialog1);
    }

    @Override
    public final void showDialog(@NonNull DialogFragment dialog) {
        showDialog(dialog, null);
    }

    @Override
    public final void showDialog(@NonNull DialogFragment dialog, @Nullable String tag) {
        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, tag);
        mDialog = dialog.getDialog();
    }

    @Override
    public final void showToast(@NonNull Toaster toaster) {
        toaster.show(this);
    }

    @Override
    public final void dismissDialog() {
        if (mDialog != null) mDialog.dismiss();
        mDialog = null;
    }

    @Override
    public final void showProgress(@StringRes int res) {
        showDialog(DialogUtils.progressDialog(this, res));
    }

    public final boolean hasVisibleDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }
}
