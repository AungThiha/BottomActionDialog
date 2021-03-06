package thiha.aung.bottomactiondialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialog;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BottomActionDialog extends BottomSheetDialog implements View.OnClickListener {

    private static final int CANCEL_BUTTON_ID = 100;

    private CharSequence mCancelButtonTitle;
    private CharSequence[] mOtherButtonTitles;
    private OnOtherButtonClickedListener mListener;

    public BottomActionDialog(@NonNull Context context) {
        super(context);
    }

    public BottomActionDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomActionDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setCancelButtonTitle(CharSequence title) {
        mCancelButtonTitle = title;
    }

    private void setCancelButtonTitle(int strId) {
        setCancelButtonTitle(getContext().getString(strId));
    }

    private void setOtherButtonTitles(CharSequence... titles) {
        mOtherButtonTitles = titles;
    }

    private void setOnOtherButtonClickedListener(OnOtherButtonClickedListener listener) {
        this.mListener = listener;
    }

    private void createView() {
        Context context = getContext();

        Attributes attrs = readAttribute(context);

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
                bt.setBackground(getOtherButtonBg(attrs, mOtherButtonTitles, i));
                bt.setText(mOtherButtonTitles[i]);
                bt.setTextColor(attrs.otherButtonTextColor);
                bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrs.bottomActionDialogTextSize);
                if (i > 0) {
                    LinearLayout.LayoutParams otherButtonParams = createButtonLayoutParams();
                    otherButtonParams.topMargin = attrs.otherButtonSpacing;
                    panel.addView(bt, otherButtonParams);
                } else {
                    panel.addView(bt);
                }
            }
        }
        Button bt = new Button(context);
        bt.getPaint().setFakeBoldText(true);
        bt.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrs.bottomActionDialogTextSize);
        bt.setId(CANCEL_BUTTON_ID);
        bt.setBackground(attrs.cancelButtonBackground);
        bt.setText(mCancelButtonTitle);
        bt.setTextColor(attrs.cancelButtonTextColor);
        bt.setOnClickListener(this);
        LinearLayout.LayoutParams cancelButtonParams = createButtonLayoutParams();
        cancelButtonParams.topMargin = attrs.cancelButtonMarginTop;
        panel.addView(bt, cancelButtonParams);

        panel.setBackground(attrs.background);
        panel.setPadding(attrs.padding, attrs.padding, attrs.padding,
                attrs.padding);

        setContentView(panel);
    }

    private Attributes readAttribute(Context context) {
        Attributes attrs = new Attributes(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(null,
                R.styleable.BottomActionDialog, R.attr.bottomActionDialogStyle, 0);
        Drawable background = a
                .getDrawable(R.styleable.BottomActionDialog_bottomActionDialogBackground);
        if (background != null) {
            attrs.background = background;
        }
        Drawable cancelButtonBackground = a
                .getDrawable(R.styleable.BottomActionDialog_cancelButtonBackground);
        if (cancelButtonBackground != null) {
            attrs.cancelButtonBackground = cancelButtonBackground;
        }
        Drawable otherButtonTopBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonTopBackground);
        if (otherButtonTopBackground != null) {
            attrs.otherButtonTopBackground = otherButtonTopBackground;
        }
        Drawable otherButtonMiddleBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonMiddleBackground);
        if (otherButtonMiddleBackground != null) {
            attrs.otherButtonMiddleBackground = otherButtonMiddleBackground;
        }
        Drawable otherButtonBottomBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonBottomBackground);
        if (otherButtonBottomBackground != null) {
            attrs.otherButtonBottomBackground = otherButtonBottomBackground;
        }
        Drawable otherButtonSingleBackground = a
                .getDrawable(R.styleable.BottomActionDialog_otherButtonSingleBackground);
        if (otherButtonSingleBackground != null) {
            attrs.otherButtonSingleBackground = otherButtonSingleBackground;
        }
        attrs.cancelButtonTextColor = a.getColor(
                R.styleable.BottomActionDialog_cancelButtonTextColor,
                attrs.cancelButtonTextColor);
        attrs.otherButtonTextColor = a.getColor(
                R.styleable.BottomActionDialog_otherButtonTextColor,
                attrs.otherButtonTextColor);
        attrs.padding = (int) a.getDimension(
                R.styleable.BottomActionDialog_bottomActionDialogPadding, attrs.padding);
        attrs.otherButtonSpacing = (int) a.getDimension(
                R.styleable.BottomActionDialog_otherButtonSpacing,
                attrs.otherButtonSpacing);
        attrs.cancelButtonMarginTop = (int) a.getDimension(
                R.styleable.BottomActionDialog_cancelButtonMarginTop,
                attrs.cancelButtonMarginTop);
        attrs.bottomActionDialogTextSize = a.getDimensionPixelSize(R.styleable.BottomActionDialog_bottomActionDialogTextSize, (int) attrs.bottomActionDialogTextSize);

        a.recycle();
        return attrs;
    }

    private Drawable getOtherButtonBg(Attributes attrs, CharSequence[] titles, int i) {
        if (titles.length == 1) {
            return attrs.otherButtonSingleBackground;
        }
        if (titles.length == 2) {
            switch (i) {
                case 0:
                    return attrs.otherButtonTopBackground;
                case 1:
                    return attrs.otherButtonBottomBackground;
            }
        }
        if (titles.length > 2) {
            if (i == 0) {
                return attrs.otherButtonTopBackground;
            }
            if (i == (titles.length - 1)) {
                return attrs.otherButtonBottomBackground;
            }
            return attrs.getOtherButtonMiddleBackground();
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
        if (v.getId() != CANCEL_BUTTON_ID && mListener != null) {
            mListener.onClick(this, v.getId() - CANCEL_BUTTON_ID
                    - 1);
        }
    }

    private static class Attributes {
        private Context mContext;

        public Attributes(Context context) {
            mContext = context;
            this.background = new ColorDrawable(Color.TRANSPARENT);
            this.cancelButtonBackground = new ColorDrawable(Color.BLACK);
            ColorDrawable gray = new ColorDrawable(Color.GRAY);
            this.otherButtonTopBackground = gray;
            this.otherButtonMiddleBackground = gray;
            this.otherButtonBottomBackground = gray;
            this.otherButtonSingleBackground = gray;
            this.cancelButtonTextColor = Color.WHITE;
            this.otherButtonTextColor = Color.BLACK;
            this.padding = dp2px(20);
            this.otherButtonSpacing = dp2px(2);
            this.cancelButtonMarginTop = dp2px(10);
            this.bottomActionDialogTextSize = dp2px(16);
        }

        private int dp2px(int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dp, mContext.getResources().getDisplayMetrics());
        }

        public Drawable getOtherButtonMiddleBackground() {
            if (otherButtonMiddleBackground instanceof StateListDrawable) {
                TypedArray a = mContext.getTheme().obtainStyledAttributes(null,
                        R.styleable.BottomActionDialog, R.attr.bottomActionDialogStyle, 0);
                otherButtonMiddleBackground = a
                        .getDrawable(R.styleable.BottomActionDialog_otherButtonMiddleBackground);
                a.recycle();
            }
            return otherButtonMiddleBackground;
        }

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
    }

    public static class Builder {

        private Context mContext;
        private CharSequence mCancelButtonTitle;
        private CharSequence[] mOtherButtonTitles;
        private boolean mCancelable;
        private OnOtherButtonClickedListener mListener;

        /**
         * Creates a builder for an Bottom Action Dialog
         *
         *
         * @param context the parent context
         */
        public Builder(Context context) {
            mContext = context;
        }

        /**
         * Sets whether this dialog is cancelable with the
         * {@link KeyEvent#KEYCODE_BACK BACK} key or
         * with a touch outside the window's bounds
         *
         * @param cancelable Whether the dialog should be canceled when touched outside
         *        the window or the {@link KeyEvent#KEYCODE_BACK BACK} key is clicked
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the tile of the cancel button
         *
         * @param title the title of the cancel button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelButtonTitle(CharSequence title) {
            mCancelButtonTitle = title;
            return this;
        }

        /**
         * Sets the tile of the cancel button using the given resource id.
         *
         * @param titleId Whether the title of the cancel button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelButtonTitle(@StringRes int titleId) {
            return setCancelButtonTitle(mContext.getString(titleId));
        }

        /**
         * Set a list of buttons to be displayed in the dialog as the content, you will be notified of
         * the clicked button via the supplied listener. This should be an array type i.e.
         * R.array.foo Clicking on a button in the list will dismiss the dialog
         *
         * @param titlesId the resource id of an array i.e. R.array.foo
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOtherButtonTitles(@ArrayRes int titlesId) {
            mOtherButtonTitles = mContext.getResources().getTextArray(titlesId);
            return this;
        }


        /**
         * Set a list of buttons to be displayed in the dialog as the content, you will be notified of
         * the clicked button via the supplied listener.
         *
         * @param titles Arguments for the titles of buttons
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOtherButtonTitles(CharSequence... titles) {
            mOtherButtonTitles = titles;
            return this;
        }

        /**
         * Sets a listener to be invoked when a button in the list is selected.
         *
         * @param listener the listener to be invoked
         * @return this Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnOtherButtonClickedListener(OnOtherButtonClickedListener listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * Creates an {@link BottomActionDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         *
         * @return created BottomActionDialog
         */
        public BottomActionDialog create() {
            BottomActionDialog dialog = new BottomActionDialog(this.mContext, R.style.TransparentBottomSheetDialogTheme);
            if (this.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setCancelButtonTitle(this.mCancelButtonTitle);
            dialog.setOtherButtonTitles(this.mOtherButtonTitles);
            dialog.setOnOtherButtonClickedListener(this.mListener);
            dialog.createView();
            return dialog;
        }


        /**
         * Creates an {@link BottomActionDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     BottomActionDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         *
         * @return shown BottomActionDialog
         */
        public BottomActionDialog show() {
            BottomActionDialog dialog = this.create();
            dialog.show();
            return dialog;
        }

    }

    /**
     * Interface definition for a callback to be invoked when
     * a button in this view has been clicked.
     */
    public interface OnOtherButtonClickedListener {
        /**
         * <p>Callback method to be invoked when a button in this dialog has been
         * clicked.
         *
         * @param dialog the dialog that contains the button that received the click
         * @param position The position of the button in the dialog
         */
        void onClick(DialogInterface dialog, int position);

    }

}
