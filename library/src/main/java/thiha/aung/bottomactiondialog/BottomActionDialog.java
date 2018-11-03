package thiha.aung.bottomactiondialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BottomActionDialog extends BottomSheetDialog implements View.OnClickListener {

    private static final int CANCEL_BUTTON_ID = 100;

    Drawable background;
    Drawable cancelButtonBackground;
    Drawable otherButtonTopBackground;
    Drawable otherButtonMiddleBackground;
    Drawable otherButtonBottomBackground;
    Drawable otherButtonSingleBackground;
    int cancelButtonTextColor;
    int otherButtonTextColor;
    int padding;
    int otherButtonSpacing;
    int cancelButtonMarginTop;
    float bottomActionDialogTextSize;

    String mCancelButtonTitle;
    private String[] mOtherButtonTitles;
    OnOtherButtonClickedListener mOnOtherButtonClickedListener;

    public BottomActionDialog(@NonNull Context context) {
        super(context);
    }

    public BottomActionDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomActionDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setCancelButtonTitle(String title) {
        mCancelButtonTitle = title;
    }

    public void setCancelButtonTitle(int strId) {
        setCancelButtonTitle(getContext().getString(strId));
    }

    private void setOtherButtonTitles(String... titles) {
        mOtherButtonTitles = titles;
    }

    public void setOnOtherButtonClickedListener(OnOtherButtonClickedListener onOtherButtonClickedListener) {
        this.mOnOtherButtonClickedListener = onOtherButtonClickedListener;
    }

    public void createView() {
        Context context = getContext();

        readAttribute(context);

        LinearLayout panel = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //params.gravity = Gravity.BOTTOM;
        panel.setLayoutParams(params);
        panel.setOrientation(LinearLayout.VERTICAL);

        if (mOtherButtonTitles != null) {
            for (int i = 0; i < mOtherButtonTitles.length; i++) {
                Button bt = new Button(context);
                bt.setId(CANCEL_BUTTON_ID + i + 1);
                bt.setOnClickListener(this);
                bt.setBackground(getOtherButtonBg(mOtherButtonTitles, i));
                bt.setText(mOtherButtonTitles[i]);
                bt.setTextColor(this.otherButtonTextColor);
                bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.bottomActionDialogTextSize);
                if (i > 0) {
                    LinearLayout.LayoutParams otherbuttonParams = createButtonLayoutParams();
                    otherbuttonParams.topMargin = this.otherButtonSpacing;
                    panel.addView(bt, otherbuttonParams);
                } else {
                    panel.addView(bt);
                }
            }
        }
        Button bt = new Button(context);
        bt.getPaint().setFakeBoldText(true);
        bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.bottomActionDialogTextSize);
        bt.setId(CANCEL_BUTTON_ID);
        bt.setBackground(this.cancelButtonBackground);
        bt.setText(mCancelButtonTitle);
        bt.setTextColor(this.cancelButtonTextColor);
        bt.setOnClickListener(this);
        LinearLayout.LayoutParams canelButtonParams = createButtonLayoutParams();
        canelButtonParams.topMargin = this.cancelButtonMarginTop;
        panel.addView(bt, canelButtonParams);

        panel.setBackground(this.background);
        panel.setPadding(this.padding, this.padding, this.padding,
                this.padding);

        setContentView(panel);
    }

    private void readAttribute(Context context) {
        TypedArray a = context.getTheme().obtainStyledAttributes(null,
                R.styleable.BottomActionDialog, R.attr.bottomActionDialogStyle, R.style.BottomActionDialogStyle);
        Drawable background = a
                .getDrawable(R.styleable.BottomActionDialog_bottomActionDialogBackground);
        if (background != null) {
            this.background = background;
        }
        Drawable cancelButtonBackground = a
                .getDrawable(R.styleable.BottomActionDialog_cancelButtonBackground);
        if (cancelButtonBackground != null) {
            this.cancelButtonBackground = cancelButtonBackground;
        }
        Drawable otherButtonTopBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonTopBackground);
        if (otherButtonTopBackground != null) {
            this.otherButtonTopBackground = otherButtonTopBackground;
        }
        Drawable otherButtonMiddleBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonMiddleBackground);
        if (otherButtonMiddleBackground != null) {
            this.otherButtonMiddleBackground = otherButtonMiddleBackground;
        }
        Drawable otherButtonBottomBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonBottomBackground);
        if (otherButtonBottomBackground != null) {
            this.otherButtonBottomBackground = otherButtonBottomBackground;
        }
        Drawable otherButtonSingleBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonSingleBackground);
        if (otherButtonSingleBackground != null) {
            this.otherButtonSingleBackground = otherButtonSingleBackground;
        }
        this.cancelButtonTextColor = a.getColor(
                R.styleable.BottomActionDialog_cancelButtonTextColor,
                this.cancelButtonTextColor);
        this.otherButtonTextColor = a.getColor(
                R.styleable.BottomActionDialog_otherButtonTextColor,
                this.otherButtonTextColor);
        this.padding = (int) a.getDimension(
                R.styleable.BottomActionDialog_bottomActionDialogPadding, this.padding);
        this.otherButtonSpacing = (int) a.getDimension(
                R.styleable.BottomActionDialog_otherButtonSpacing,
                this.otherButtonSpacing);
        this.cancelButtonMarginTop = (int) a.getDimension(
                R.styleable.BottomActionDialog_cancelButtonMarginTop,
                this.cancelButtonMarginTop);
        this.bottomActionDialogTextSize = a.getDimensionPixelSize(R.styleable.BottomActionDialog_bottomActionDialogTextSize, (int) this.bottomActionDialogTextSize);

        a.recycle();
    }

    private Drawable getOtherButtonBg(String[] titles, int i) {
        if (titles.length == 1) {
            return this.otherButtonSingleBackground;
        }
        if (titles.length == 2) {
            switch (i) {
                case 0:
                    return this.otherButtonTopBackground;
                case 1:
                    return this.otherButtonBottomBackground;
            }
        }
        if (titles.length > 2) {
            if (i == 0) {
                return this.otherButtonTopBackground;
            }
            if (i == (titles.length - 1)) {
                return this.otherButtonBottomBackground;
            }
            return this.getOtherButtonMiddleBackground();
        }
        return null;
    }

    private LinearLayout.LayoutParams createButtonLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }

    @Override
    public void onClick(final View v) {
        dismiss();
        if (v.getId() != CANCEL_BUTTON_ID && mOnOtherButtonClickedListener != null) {
            mOnOtherButtonClickedListener.onClick(this, v.getId() - CANCEL_BUTTON_ID
                    - 1);
        }
    }

    public Drawable getOtherButtonMiddleBackground() {
        if (otherButtonMiddleBackground instanceof StateListDrawable) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(null,
                    R.styleable.BottomActionDialog, R.attr.bottomActionDialogStyle, R.style.BottomActionDialogStyle);
            otherButtonMiddleBackground = a
                    .getDrawable(R.styleable.BottomActionDialog_otherButtonMiddleBackground);
            a.recycle();
        }
        return otherButtonMiddleBackground;
    }

    public static class Builder {

        private Context mContext;
        private String mCancelButtonTitle;
        private String[] mOtherButtonTitles;
        private boolean mCancelable;
        private OnOtherButtonClickedListener mOnOtherButtonClickedListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setCancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setCancelButtonTitle(String title) {
            mCancelButtonTitle = title;
            return this;
        }

        public Builder setCancelButtonTitle(int strId) {
            return setCancelButtonTitle(mContext.getString(strId));
        }

        public Builder setOtherButtonTitles(String... titles) {
            mOtherButtonTitles = titles;
            return this;
        }

        public Builder setOnOtherButtonClickedListener(OnOtherButtonClickedListener onOtherButtonClickedListener) {
            this.mOnOtherButtonClickedListener = onOtherButtonClickedListener;
            return this;
        }

        public BottomActionDialog create() {
            BottomActionDialog dialog = new BottomActionDialog(this.mContext, R.style.TransparentBottomSheetDialogTheme);
            if (this.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setCancelButtonTitle(this.mCancelButtonTitle);
            dialog.setOtherButtonTitles(this.mOtherButtonTitles);
            dialog.setOnOtherButtonClickedListener(this.mOnOtherButtonClickedListener);
            dialog.createView();
            return dialog;
        }

        public BottomActionDialog show() {
            BottomActionDialog dialog = this.create();
            dialog.show();
            return dialog;
        }

    }

    public interface OnOtherButtonClickedListener {

        void onClick(BottomActionDialog bottomActionDialog, int which);

    }

}
