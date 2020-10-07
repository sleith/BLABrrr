######### Keep our models ###########
-keepclassmembers class com.fatdino.blabrrr.api.model.** {
      *;
}

######### Firebase Database ############
-keepattributes Signature
######## Strip Logs ###################
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int e(...);
}
