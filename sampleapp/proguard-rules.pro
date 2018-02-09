# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\0\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#-dontpreverify
#-verbose
#-optimizations
# keep 4大组件， application
-keepattributes Signature
-keepattributes EnclosingMethod
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclassmembers enum *{
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# Serializables类
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class com.memo.**{ *;}
-keep class com.memo.**$*{ *;}
-keep public class org.cybergarage.upnp.Device { *; }
-keep public class org.cybergarage.upnp.device.DeviceChangeListener { *; }
-keep public class com.memo.sdk.MemoTVCastSDK$ISetTvWifiListener { *; }
-keep public class com.memo.connection.WifiAdmin$WifiCipherType { *;}
-keep public class com.memo.connection.MemoWifiManager$IConnectWifiListener { *; }
-keep public class org.cybergarage.xml.parser.** { *; }
-keep public class org.cybergarage.util.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
**[] $VALUES;
public *;
}
 -keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule

 # Remove Logging
 -assumenosideeffects class android.util.Log {
     public static *** d(...);
     public static *** w(...);
     public static *** v(...);
     public static *** i(...);
 }

-dontwarn okio.**
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class org.mozilla.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class android.** {*;}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class com.memo.wang.avi.** { *; }
-keep class com.memo.wang.avi.indicators.** { *; }
-keep public class io.evercam.** { *; }
-dontwarn io.evercam.**
