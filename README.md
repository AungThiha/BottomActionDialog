# BottomActionDialog
[ ![Download](https://api.bintray.com/packages/aungthiha/maven/BottomActionDialog/images/download.svg)](https://bintray.com/aungthiha/maven/BottomActionDialog/_latestVersion)

BottomSheetDialog with iOS UIActionSheet design. Inspiration taken from ![ActionSheet](https://github.com/baoyongzhang/android-ActionSheet). The difference is when the ActionSheet is invoked from a FragmentDialog, it's shown beneath the FragmentDialog and won't be visible to users. This library solves that problem by using BottomSheetDialog from support design library.

<img src="https://raw.githubusercontent.com/aungthiha/BottomActionDialog/master/demo.gif" width="320" alt="Demo Gif"/>

# Usage

### Add dependency

```groovy
dependencies {
    compile 'thiha.aung.bottomactiondialog:bottomactiondialog:1.0.1'
}
```

### Create BottomActionDialog and show

```java
new BottomActionDialog.Builder(this)
        .setCancelButtonTitle("Cancel")
        .setOtherButtonTitles("Item1", "Item2", "Item3", "Item4")
        .setOnOtherButtonClickedListener(new BottomActionDialog.OnOtherButtonClickedListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
            }
        })
        .setCancelable(true)
        .show();
```

### Methods

* `setCancelButtonTitle()` Cancel button title, (CharSequence)
* `setOtherButtonTitles()` Item buttons title，(CharSequence[])
* `setCancelable()` Touch outside or tap back key to close, (boolean)
* `setOnOtherButtonClickedListener()` set a Listener to listen button click event
* `show()` Show BottomActionDialog, return `BottomActionDialog` Object

### Listen event

implementing `OnOtherButtonClickedListener` interface.
* `onClick()` Click item event，`dialog` is the DialogInterface and `position` is item index.

```java

	@Override
    public void onClick(DialogInterface dialog, int position) {
    	Toast.makeText(getApplicationContext(), "click item index = " + position,
				0).show();
    }
```

### Style

```xml
<!-- Application theme. -->
    <style name="AppTheme" parent="AppBaseTheme">
        <item name="bottomActionDialogStyle">@style/DemoBottomActionDialogStyle</item>
    </style>
```

```xml
 <!-- iOS7 Style -->
 <style name="DemoBottomActionDialogStyle" parent="BottomActionDialogStyle">
        <item name="bottomActionDialogBackground">@android:color/transparent</item>
        <item name="cancelButtonBackground">@drawable/slt_as_ios7_cancel_bt</item>
        <item name="otherButtonTopBackground">@drawable/slt_as_ios7_other_bt_top</item>
        <item name="otherButtonMiddleBackground">@drawable/slt_as_ios7_other_bt_middle</item>
        <item name="otherButtonBottomBackground">@drawable/slt_as_ios7_other_bt_bottom</item>
        <item name="otherButtonSingleBackground">@drawable/slt_as_ios7_other_bt_single</item>
        <item name="cancelButtonTextColor">#1E82FF</item>
        <item name="otherButtonTextColor">#1E82FF</item>
        <item name="bottomActionDialogPadding">10dp</item>
        <item name="otherButtonSpacing">0dp</item>
        <item name="cancelButtonMarginTop">10dp</item>
        <item name="bottomActionDialogTextSize">16sp</item>
    </style>
```

### Style attributes
* `bottomActionDialogBackground`
* `cancelButtonBackground`
* `otherButtonTopBackground`
* `otherButtonMiddleBackground`
* `otherButtonBottomBackground`
* `otherButtonSingleBackground`
* `cancelButtonTextColor`
* `otherButtonTextColor`
* `bottomActionDialogPadding`
* `otherButtonSpacing`
* `cancelButtonMarginTop`
* `bottomActionDialogTextSize`


