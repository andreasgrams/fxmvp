package de.dreamnetworx.fxmvp.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleControl extends ResourceBundle.Control {

    public static final String FORMAT_JAVA_PROPERTIES = "java.properties";
    public static final String FORMAT_JAVA_CLASS = "java.class";
    /**
     * Encoding which will be used to read resource bundle, by defaults it's UTF-8
     */
    private String encoding = "UTF-8";

    /**
     * Just empty default constructor
     */
    public ResourceBundleControl() {
    }

    /**
     * This constructor allows to set encoding that will be used while reading resource bundle
     *
     * @param encoding encoding to use
     */
    public ResourceBundleControl(String encoding) {
        this.encoding = encoding;
    }

    /**
     * This code is just copy-paste with usage {@link java.io.Reader} instead of {@link InputStream} to read
     * properties.<br>
     * <br>
     * <p>
     * {@inheritDoc}
     */
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException,
            InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        ResourceBundle bundle = null;

        if(format.equals(FORMAT_JAVA_CLASS)) {
            try {
                @SuppressWarnings(
                        {"unchecked"})
                Class<? extends ResourceBundle> bundleClass = (Class<? extends ResourceBundle>) loader.loadClass(bundleName);

                // If the class isn't a ResourceBundle subclass, throw a
                // ClassCastException.
                if(ResourceBundle.class.isAssignableFrom(bundleClass)) {
                    bundle = bundleClass.newInstance();
                } else {
                    throw new ClassCastException(bundleClass.getName() + " cannot be cast to ResourceBundle");
                }
            } catch (ClassNotFoundException ignored) {
            }
        } else if(format.equals(FORMAT_JAVA_PROPERTIES)) {
            final String resourceName = toResourceName(bundleName, "properties");
            final ClassLoader classLoader = loader;
            final boolean reloadFlag = reload;
            InputStreamReader isr = null;
            InputStream stream;
            try {
                stream = AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
                    @Override
                    public InputStream run() throws IOException {
                        InputStream is = null;
                        if(reloadFlag) {
                            URL url = classLoader.getResource(resourceName);
                            if(url != null) {
                                URLConnection connection = url.openConnection();
                                if(connection != null) {
                                    // Disable caches to get fresh data for
                                    // reloading.
                                    connection.setUseCaches(false);
                                    is = connection.getInputStream();
                                }
                            }
                        } else {
                            is = classLoader.getResourceAsStream(resourceName);
                        }
                        return is;
                    }
                });
                if(stream != null) {
                    isr = new InputStreamReader(stream, encoding);
                }
            } catch (PrivilegedActionException e) {
                throw (IOException) e.getException();
            }
            if(isr != null) {
                try {
                    bundle = new PropertyResourceBundle(isr);
                } finally {
                    isr.close();
                }
            }
        } else {
            throw new IllegalArgumentException("unknown format: " + format);
        }
        return bundle;
    }

    /**
     * Returns encoding that will be used to read .properties resource bundles
     *
     * @return encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the encoding that will be used to read properties resource bundles
     *
     * @param encoding encoding that will be used for properties
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
