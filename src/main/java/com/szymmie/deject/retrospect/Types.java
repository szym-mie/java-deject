package com.szymmie.deject.retrospect;

public class Types {
    public static String getTypeName(Class<?> type) {
        String packageName = Types.getPackageName(type);
        String simpleName = type.getSimpleName();
        String finalName = !simpleName.isEmpty() ? simpleName : "<anonymous>";
        return String.format("%s.%s", packageName, finalName);
    }

    public static String getPackageName(Class<?> type) {
        Package maybePackage = type.getPackage();
        return maybePackage != null ? maybePackage.getName() : "<no-package>";
    }
}
