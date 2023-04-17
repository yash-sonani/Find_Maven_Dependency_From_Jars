import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MavenDependencyPathFinder {
    public static void main(String[] args) {
        File folder = new File("path/to/folder/with/jars");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jarFile = new JarFile(file)) {
                    Manifest manifest = jarFile.getManifest();
                    Attributes mainAttributes = manifest.getMainAttributes();
                    String groupId = mainAttributes.getValue("Implementation-Vendor-Id");
                    String artifactId = mainAttributes.getValue("Implementation-Title");
                    String version = mainAttributes.getValue("Implementation-Version");
                    if (groupId != null && artifactId != null && version != null) {
                        System.out.println("<dependency>");
                        System.out.println("    <groupId>" + groupId + "</groupId>");
                        System.out.println("    <artifactId>" + artifactId + "</artifactId>");
                        System.out.println("    <version>" + version + "</version>");
                        System.out.println("</dependency>");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
