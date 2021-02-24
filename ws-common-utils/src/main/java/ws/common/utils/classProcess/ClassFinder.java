package ws.common.utils.classProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.common.utils.exception.ClassFinderException;

public class ClassFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassFinder.class);
    private static final char PACKAGE_DIVISION = '.';
    private static final char PATH_DIVISION_REVERSE = '/';
    private static final char PATH_DIVISION_POSITIVE = '\\';
    private static final String JAR_FILE_TYPE_STR = "jar";
    private static final String CLASS_TYPE_STR = ".class";

    /**
     * 递归获取指定包下面的所有指定class的子类或者接口实现类， 输出不包含父类或者接口本身
     * 
     * @param parentClass
     *            被检索的 父类 或者 接口类 的Class对象
     * @param targetPackageClass
     *            server的启动类 的Class对象
     * @return
     */
    public static <T> List<Class<? extends T>> getAllAssignedClass(Class<T> parentClass, Class<?> targetPackageClass) {
        return getAllAssignedClass(parentClass, true, targetPackageClass);
    }

    /**
     * 获取指定包下面的所有指定class的子类或者接口实现类 ，输出不包含父类或者接口本身
     * 
     * @param parentClass
     *            被检索的 父类 或者 接口类 的Class对象
     * @param isSearchChildPackage
     *            true 递归检索子包
     * @param targetPackageClass
     *            server的启动类 的Class对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Class<? extends T>> getAllAssignedClass(Class<T> parentClass, boolean isSearchChildPackage, Class<?> targetPackageClass) {
        try {
            LOGGER.debug("以类={} 的包目录为跟目录搜索！", targetPackageClass.toString());
            List<Class<? extends T>> classes = new ArrayList<Class<? extends T>>();
            List<String> classNames = getClassNameList(targetPackageClass, isSearchChildPackage);
            for (String className : classNames) {
                Class<?> clsTmp = ClassFinder.class.getClassLoader().loadClass(className);
                if (parentClass.isAssignableFrom(clsTmp) && !parentClass.equals(clsTmp)) {
                    classes.add((Class<? extends T>) clsTmp);
                }
            }
            return classes;
        } catch (Exception e) {
            throw new ClassFinderException("", e);
        }
    }

    /**
     * 从指定文件目录中查找所有.class文件了，并且以带包名的形式输出文件名
     * 
     * @param packageName
     *            包名
     * @param isSearchChildPackage
     *            是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameList(Class<?> targetPackageClass, boolean isSearchChildPackage) throws Exception {
        List<String> classPackageNames = new ArrayList<>();
        String targetPackageClassPath = targetPackageClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        LOGGER.debug("查找targetPackageClass={}的路径={}", targetPackageClass, targetPackageClassPath);
        if (targetPackageClassPath.endsWith(JAR_FILE_TYPE_STR)) {
            classPackageNames = getClassNameListByJar(targetPackageClassPath, targetPackageClass.getPackage().getName(), isSearchChildPackage);
        } else {
            getClassNameListByPackage(targetPackageClass.getPackage().getName(), targetPackageClassPath, classPackageNames, isSearchChildPackage);
        }
        return classPackageNames;
    }

    /**
     * 从指定文件目录中查找所有.class文件了，并且以带包名的形式输出文件名
     * 
     * @param packageName
     *            指定的包名
     * @param rootPath
     *            指定包的路径
     * @param classPackageNames
     *            查找到的结果放入 classPackageNames
     * @param isSearchChildPackage
     *            是否递归子目录
     * @return
     */
    private static void getClassNameListByPackage(String packageName, String rootPath, List<String> classPackageNames, boolean isSearchChildPackage) {
        File file = new File(rootPath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (isSearchChildPackage) {
                    getClassNameListByPackage(packageName, childFile.getPath(), classPackageNames, isSearchChildPackage);
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(CLASS_TYPE_STR)) {
                    // 所有 / \ 都转为 . 转完后为 ： .home.zhangweiwei.work.eclipseWork.network.Y6-ParticularFunctionServer.bin.game.y6.particularFunctionServer.system.xxx.A.class
                    childFilePath = childFilePath.replace(PATH_DIVISION_POSITIVE, PACKAGE_DIVISION);
                    childFilePath = childFilePath.replace(PATH_DIVISION_REVERSE, PACKAGE_DIVISION);
                    // 匹配的 package 如 ： .game.y6.particularFunctionServer.system.xxx.
                    String packageNameFull = "" + PACKAGE_DIVISION + packageName + PACKAGE_DIVISION;
                    if (childFilePath.contains(packageNameFull)) {
                        // game.y6.particularFunctionServer.system.xxx.A.class
                        classPackageNames.add(childFilePath.substring(childFilePath.indexOf(packageNameFull) + 1).replaceAll(".class", ""));
                    }
                }
            }
        }
    }

    /**
     * 从jar获取某包下所有类
     * 
     * @param jarFilePath
     *            jar文件的全路径
     * @param packageName
     *            package路径
     * @param isSearchChildPackage
     * @return
     * @throws Exception
     */
    private static List<String> getClassNameListByJar(String jarFilePath, String packageName, boolean isSearchChildPackage) throws Exception {
        List<String> myClassName = new ArrayList<String>();
        LOGGER.debug("遍历jarFilePath={} 文件查找 package={} !", jarFilePath, packageName);
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(CLASS_TYPE_STR)) {
                    entryName = entryName.replace(PATH_DIVISION_POSITIVE, PACKAGE_DIVISION);
                    entryName = entryName.replace(PATH_DIVISION_REVERSE, PACKAGE_DIVISION);
                    if (entryName.contains(packageName)) {
                        myClassName.add(entryName.substring(entryName.indexOf(packageName)).replaceAll(".class", ""));
                    }
                }
            }
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (Exception e) {
                    LOGGER.error("jarFile {} close error!", jarFile, e);
                }
            }
        }
        return myClassName;
    }
}