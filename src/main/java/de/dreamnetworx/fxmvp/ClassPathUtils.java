package de.dreamnetworx.fxmvp;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.*;

public class ClassPathUtils {

    public String getClassForSimpleName(String simpleName) {
        final Collection<String> packages = getPackages();

        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(ClassLoader.getSystemClassLoader());
        final Resource resource = resourcePatternResolver.getResource("*." + simpleName);
        if(resource != null) {
            try {
                return resource.getURI().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String aPackage : packages) {
            try {
                String fqn = aPackage + "." + simpleName;

                Class.forName(fqn);
                return fqn;
            } catch (Exception e) {
                // Ignore
            }
        }
        return null;
    }

    private Collection<String> getPackages() {
        Set<String> packages = new HashSet<>();
        for (Package aPackage : Package.getPackages()) {
            packages.add(aPackage.getName());
        }
        return packages;
    }

}
