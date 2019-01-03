-injars dist\DBFMigrationTool.jar
-outjars dist\o_DBFMigrationTool.jar

-libraryjars <java.home>/lib/rt.jar
-libraryjars <java.home>/lib/jce.jar
-libraryjars <java.home>/lib/ext/jfxrt.jar
-libraryjars lib\commons-codec-1.2.jar
-libraryjars lib\javadbf-0.4.0-sources.jar
-libraryjars lib\json-20160810.jar

-dontshrink
-dontoptimize
-flattenpackagehierarchy ''
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
-adaptresourcefilecontents **.fxml,**.properties,META-INF/MANIFEST.MF

-keepclassmembernames class * {
    @javafx.fxml.FXML *;
}


# Keep - Applications. Keep all application classes, along with their 'main'
# methods.
-keepclasseswithmembers public class com.javafx.main.Main, dbfmigrationtool.DBFMigrationTool {
    public static void main(java.lang.String[]);
}