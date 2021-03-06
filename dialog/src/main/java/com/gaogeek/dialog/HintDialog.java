package com.gaogeek.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


/**
 * Created by gaogeek on 2017/7/27.
 */

public class HintDialog extends Dialog {
    private OverLineTextView mMessage;
    private TextView mTitle;
    private OnConfirmListener mListener;

    private static HintDialog mDialog = null;

    private Context mContext;

    private HintDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_hint);
        setCanceledOnTouchOutside(false);
        mContext = context;

        mMessage = (OverLineTextView) findViewById(R.id.message_body);
        mTitle = (TextView) findViewById(R.id.dialog_title);

        findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.OnConfirm();
                    mListener = null;
                }
            }
        });
    }

    public static HintDialog makeText(Context context, String msg) {
        if (mDialog == null) {
            mDialog = new HintDialog(context);
        } else {
            if (!mDialog.mContext.getClass().equals(context.getClass())) {
                mDialog = null;
                mDialog = new HintDialog(context);
            }
        }
        if (!msg.isEmpty()) {
            mDialog.setMessage(msg);
        }
        return mDialog;
    }

    private void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setTitle(int textRes) {
        setTitle(getContext().getResources().getString(textRes));
    }

    private void setMessage(String text) {
        mMessage.setText(text, TextView.BufferType.NORMAL);
    }

    public void setMessage(int textRes) {
        setMessage(getContext().getResources().getString(textRes));
    }

    public HintDialog setOnConfirmListener(OnConfirmListener listener) {
        mListener = listener;
        return mDialog;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
