/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.*;

/**
 *
 * @author Wasif
 */
public class FileSystemManager {

    /*
     * createDirectory : is for creating directory
     * @param : String directoryPathName, is path and name of directory to be created
     */
    public static boolean createDirectory(String directoryPathName) throws Exception {
        boolean status;

        try {
            File directory = new File(directoryPathName);

            /*
             * if directory already exists delete that
             */
            if (directory.exists()) {
                status = deleteDirectory(directory);
                if (status == false) {
                    throw new Exception("Could not delete existing directory.\n");
                }
            }

            status = directory.mkdir();

        } catch (Exception e) {
            throw new Exception("FileSystemManager : createDirectory : "
                    + e + " : " + e.getMessage());
        }
        return status;
    }

    /* 
     * Deletes all files and subdirectories under directoryToDelete
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     */
    public static boolean deleteDirectory(File directoryToDelete) {

        if (directoryToDelete.isDirectory()) {

            String[] children = directoryToDelete.list();
            for (int i = 0; i < children.length; i++) {

                boolean success = deleteDirectory(new File(directoryToDelete, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        /* The directory is now empty so delete it. */
        return directoryToDelete.delete();
    }

    /*
     * This method returns filename out of the complete file URL provided
     * without the file extension
     */
    public static String getFileNameWithoutExtension(String Input_File_URL) throws Exception {
        String fileName;

        try {
            fileName = Input_File_URL.substring(Input_File_URL.lastIndexOf(File.separatorChar) + 1, Input_File_URL.lastIndexOf("."));
        } catch (Exception e) {

            throw new Exception("FileSystemManager : getFileNameWithoutExtension  : "
                    + e + " : " + e.getMessage());
        }
        return fileName;
    }


    /*
     * This method returns filename out of the complete file URL provided,
     * with file extension
     */
    public static String getFileNameWithExtension(String Input_File_URL) throws Exception {
        String fileName;

        try {
            fileName = Input_File_URL.substring(Input_File_URL.lastIndexOf(File.separatorChar) + 1, Input_File_URL.length());
        } catch (Exception e) {

            throw new Exception("FileSystemManager : getFileNameWithExtension : "
                    + e + " : " + e.getMessage());
        }
        return fileName;
    }

    /*
     * This method returns Directory with Path and Name, for the inputted FileURL,
     * Slash is included at the end of directoryPathName
     */
    public static String getDirectoryPathNameForFileURL(String Input_File_URL) throws Exception {
        String directoryPathName;

        try {
            System.out.println("getDirectoryPathNameForFileURL : Input_File_URL : " + Input_File_URL);
            directoryPathName = Input_File_URL.substring(0, Input_File_URL.lastIndexOf(File.separatorChar));

        } catch (Exception e) {

            throw new Exception("FileSystemManager : getDirectoryPathNameForFileURL : "
                    + e + " : " + e.getMessage());
        }

        return directoryPathName;
    }

    /*
     * Adds Suffix to a URL
     */
    public static String addSuffixToFileURL(String input_File_URL, String suffixToAdd, String extensionToAdd) throws Exception {
        String directoryPathName,
                fileName,
                newFileURL;

        try {
            System.out.println("addSuffixToFileURL : input_File_URL : " + input_File_URL);
            System.out.println("addSuffixToFileURL : suffixToAdd : " + suffixToAdd);
            System.out.println("addSuffixToFileURL : extensionToAdd : " + extensionToAdd);

            /*
             * get directory path name
             */
            directoryPathName = getDirectoryPathNameForFileURL(input_File_URL);
            /*
             * get file Name
             */
            fileName = getFileNameWithoutExtension(input_File_URL);
            /*
             * Set proper local stop_Word_Removed_XML_File_URL
             */
            if (extensionToAdd == null || extensionToAdd.isEmpty()) {
                newFileURL = directoryPathName + File.separatorChar + fileName + suffixToAdd + ".xml";
            } else {
                newFileURL = directoryPathName + File.separatorChar + fileName + suffixToAdd + "." + extensionToAdd;
            }


        } catch (Exception e) {

            throw new Exception("FileSystemManager : addSuffixToFileURL : "
                    + e + " : " + e.getMessage());
        }

        return newFileURL;
    }

    public static boolean copyFile(String source_File_Path_Name, String destination_File_Path_Name) throws Exception {
        File inputFile,
                outputFile;

        InputStream in;
        OutputStream out;
        int len;
        byte[] buf;

        try {

            inputFile = new File(source_File_Path_Name);
            outputFile = new File(destination_File_Path_Name);

            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);

            buf = new byte[1024];

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            throw new Exception("FileSystemManager : copyFile : "
                    + e + " : " + e.getMessage());
        }
        return true;
    }

    public static boolean deleteFile(String file_Path_Name_To_Delete) throws Exception {
        File file_To_Delete;

        try {
            file_To_Delete = new File(file_Path_Name_To_Delete);

            if (file_To_Delete.delete() != true) {
                throw new Exception("deleteFile : file deletion has failed.");
            }
        } catch (Exception e) {
            throw new Exception("FileSystemManager : deleteFile : "
                    + e + " : " + e.getMessage());
        }
        return true;
    }

    public static BufferedWriter createFile(String file_Path_Name_To_Create) throws Exception {
        /*
         * Local Declarations
         */
        FileOutputStream fOutStream = null;
        DataOutputStream dOutStream = null;
        BufferedWriter bWriter = null;

        try {
            /*
             * Get the object of FileOutputStream
             */
            fOutStream = new FileOutputStream(file_Path_Name_To_Create);

            /*
             * Get the object of DataOutputStream
             */
            dOutStream = new DataOutputStream(fOutStream);

            /*
             * Get the object of BufferedWriter
             */
            bWriter = new BufferedWriter(new OutputStreamWriter(dOutStream));

            if(bWriter == null) {
                throw new Exception("createFile : file creation has failed.");
            }

        } catch (Exception e) {
            throw new Exception("FileSystemManager : createFile : "
                    + e + " : " + e.getMessage());
        }
        return bWriter;
    }
}
