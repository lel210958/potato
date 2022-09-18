package com.lel.potato.common.tool;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static void main(String[] args) {
//        groupByFileName("E:\\temp", "E:\\TwFiles");
        renameSimpleName("E:\\TwFiles");
//        deleteEmptyDir("E:\\temp");
    }

    /**
     * 根据给出的path，扫描对应路径的文件获取文件前缀，
     * 然后指定targetPath有没有对应文件前缀的文件夹，没有则创建
     * 然后当前文件移动至targetPath对应前缀文件夹中，path中对应的文件删除
     * */
    public static void groupByFileName(String path, String targetPath){
        List<File> deleteFiles = new ArrayList<>();
        File src = new File(path);
        File[] files = src.listFiles();
        if(files == null || files.length <=0){
            return;
        }
        Arrays.stream(files).forEach(file -> {
            if(file.isFile()){
                if(!isSuffix(file, "zip")){
                    //非zip文件
                    removeAndReName(targetPath, file);
                    deleteFiles.add(file);
                }
            }else if(file.isDirectory()){
                //文件夹
                groupByFileName(file.getPath(), targetPath);
            }
        });

        //删除
        deleteFiles.forEach(File::delete);
    }

    private static void removeAndReName(String targetPath, File file) {
        String[] nameArr = file.getName().split("-");
        System.err.println(nameArr[0]);
        File temp = new File(targetPath + File.separator + nameArr[0] + File.separator);
        if(!temp.exists()){
            temp.mkdirs();
        }
        String newName;
        if(nameArr.length == 4){
            newName = temp.getAbsolutePath() + File.separator + nameArr[0] + "-" + nameArr[2] + "." + FileUtil.getSuffix(file);
        }else{
            newName = temp.getAbsolutePath() + File.separator + file.getName();
        }
        System.err.println("newName: " + newName);
        file.renameTo(new File(newName));
    }

    public static boolean isSuffix(File file, String suffix){
        String suf = FileUtil.getSuffix(file);
        return suffix.equalsIgnoreCase(suf);
    }

    public static void renameSimpleName(String path){
        File src = new File(path);
        File[] files = src.listFiles();
        Arrays.stream(files).forEach(file -> {
            if(file.isDirectory()){
                //文件夹重命名
                String[] nameArr = file.getName().split("-");
                if(nameArr.length > 1) {
                    String newDirName = nameArr[0];
                    String newPath = file.getParentFile().getAbsolutePath() + File.separator + newDirName;
                    file.renameTo(new File(newPath));
                    renameSimpleName(newPath);
                    return;
                }else{
                    renameSimpleName(file.getPath());
                    return;
                }
            }
            if(!isSuffix(file, "zip")){
                String[] nameArr = file.getName().split("-");
                //Ivressee1-1386230515733204993-20210425_160804-vid1.mp4 更改为Ivressee1-20210425_160804-vid1.mp4
                //flashshark1997-1415296982839562246-20210714_210750-img2.jpg
                if(nameArr.length == 4 && nameArr[1].length() > 15){
                    String newName = file.getParentFile().getAbsolutePath() + File.separator + nameArr[0] + "-" + nameArr[2] + "." + FileUtil.getSuffix(file);
                    System.err.println(newName);
                    file.renameTo(new File(newName));
                    //删除原文件
                    file.delete();
                }
            }
        });
    }

    public static void deleteEmptyDir(String path){
        File src = new File(path);
        File[] files = src.listFiles();
        if(files == null || files.length == 0){
            src.delete();
            return;
        }
        Arrays.stream(files).forEach(file -> {
            if(file.isDirectory()){
                deleteEmptyDir(file.getPath());
            }
        });
    }

}
