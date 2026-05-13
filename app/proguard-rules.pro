# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Room
-keep class com.nammamistri.app.data.model.** { *; }
-keep class com.nammamistri.app.data.db.** { *; }
-keep class com.nammamistri.app.data.repository.** { *; }

# ViewModel
-keep class com.nammamistri.app.viewmodel.** { *; }

# Data classes
-keep class com.nammamistri.app.data.model.* {
    <fields>;
    <methods>;
}

# DAO interfaces
-keep interface com.nammamistri.app.data.db.*Dao {
    <methods>;
}

# Keep constructors for Room entities
-keepclassmembers class com.nammamistri.app.data.model.* {
    <init>(...);
}

# Keep Room database
-keep class com.nammamistri.app.data.db.AppDatabase {
    <methods>;
}

# Keep converters
-keep class com.nammamistri.app.data.db.Converters {
    <methods>;
}
