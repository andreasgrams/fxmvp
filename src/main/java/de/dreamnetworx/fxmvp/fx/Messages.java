package de.dreamnetworx.fxmvp.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class Messages {

    public static final String FILE_ENCODING = "UTF-8";
    public static String DEFAULT_LOCALE_BUNDLE_NAME = "messages/messages";
    private static Locale resourceBundleLocale = null; // has to be null initially
    private static ResourceBundle resourceBundle = null;
    private static Locale locale = Locale.GERMANY;

    private static synchronized final ResourceBundle getLocaleBundle(String bundlename) {

        Locale currentLocale = getLocale();
        if(!currentLocale.equals(resourceBundleLocale)) {
            resourceBundleLocale = currentLocale;

            try {
                resourceBundle = new EncodedControl(FILE_ENCODING).newBundle(bundlename, resourceBundleLocale, "java.properties", Messages.class.getClassLoader(), false);
            } catch (IllegalAccessException | InstantiationException | IOException e) {
                e.printStackTrace();
            }
        }
        return resourceBundle;

    }

    /**
     * Returns a string localized using currently set locale
     *
     * @param key resource bundle key
     * @return localized text or formatted key if not found
     */
    public static final String getString(final String key) {
        try {
            return getLocaleBundle(DEFAULT_LOCALE_BUNDLE_NAME).getString(key);
        } catch (MissingResourceException ex) {
            return String.format("<%s>", key);
        }
    }

    public static final String getKey(String value) {
        Set<String> keySet = getLocaleBundle(DEFAULT_LOCALE_BUNDLE_NAME).keySet();
        for (String key : keySet) {
            if(getLocaleBundle(DEFAULT_LOCALE_BUNDLE_NAME).getString(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

    public static ResourceBundle getResourceBundle(String bundleName) {
        return getLocaleBundle(bundleName);
    }

    /**
     * Returns the Locale object that is associated with ControlsFX.
     *
     * @return the global ControlsFX locale
     */
    public static final Locale getLocale() {
        // following allows us to have a "dynamic" locale based on OS/JDK
        return locale == null ? Locale.getDefault() : locale;
    }

    public static ResourceBundle getResourceBundle() {
        return getLocaleBundle(DEFAULT_LOCALE_BUNDLE_NAME);
    }
}
