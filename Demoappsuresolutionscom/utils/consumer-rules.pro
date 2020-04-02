#-keep interface * {
#  <methods>;
#}

-optimizationpasses 5
-optimizations !code/simplification/arithmetic
-repackageclasses 筆理清車著治意然只得界見業
-allowaccessmodification


-keep class com.appsuresolutions.utils.entrypoint.* { *; }
-keep interface com.appsuresolutions.utils.entrypoint.* { *; }

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}