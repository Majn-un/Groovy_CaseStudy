import java.nio.file.* 
import java.nio.charset.Charset
import groovy.io.FileType

class RecursiveSearch {

    // RecursiveSearch() is the function that takes care of the main logic of recursively looping through a directory, 
    // searching files for key word, changing word, create a backup directory, save the original file in the backup directory,
    // and writing the files changed in the outputted list file.
    RecursiveSearch(String dir, String search, String replace, String outputPath) {
        // First dir is converted into a file object to allow function to make further adjustments
        println "Checking to see if directory supplied is valid..."     
        File directory = new File(dir)
        // Check to see if directory file object is actually a directory by using isDirectory method
        if (directory.isDirectory()) {
            println "Directory is valid. Validating backup directory..."

            // Create a backups directory if it doesn't exist
            File backupsDir = new File("Backups")
            if (!backupsDir.exists()) {
                println "Backup directory was created. Starting search..."
                backupsDir.mkdirs()
            } else {
                println "Validated backups directory. Starting search..."
            }

            // Track all files that were changed
            def outPuttedFileList = []
            boolean found = false
            int count = 0
            // Recursively searching all files within a given directory using the .eachFileRecurse method (from FileType library)
            directory.eachFileRecurse (FileType.FILES) { file ->
                
                println "Seaching file: ${file.name}"
                
                // Retrieve contents from the file to find a keyword, replace a keyboard, and audit file changed
                def fileContent = file.getText('UTF-8')
                if (fileContent.contains(search)) {
                    count += 1
                    found = true
                    // Backup the original file before replacing the text by copying the current copy into a backup file
                    File backupFile = new File("${backupsDir.absolutePath}/${file.name}-backup")
                    println "Creating backup for file: ${file.name}"
                    Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    println "Search text found in ${file.name}. Replacing..."
                    
                    // Replace the text and write back to the file
                    fileContent = fileContent.replaceAll(search, replace)
                    file.setText(fileContent, 'UTF-8')
                    
                    // Audit where change was made
                    outPuttedFileList << file.absolutePath
                } 
            }

            // If no file was found with the searched phrase, print statement indicating so
            if (found == false) {
                println "No file had the phrase(s) \"${search}\" in the directory \"${dir}\""
            } else {
                // Print how many files were changed 
                println "The number of files changed: ${count}"
                // Write auditted log to specified/default text file
                println "Writing output log to ${outputPath}"
                new File(outputPath).write(outPuttedFileList.join("\n"))
            }

        } else {
            println "Invalid directory path."
        }
    }


    // The main function serves as the starter point for this class. Its main logic is too take in the arguments from the command line, and forward it to RecursiveSearch logic 
    static void main(String[] args) {
        // Create our first logger statement as we want to track when the function first starts
        Date start = new Date();
        println "Start time: ${start}"

        // the try-catch block is too catch any errors and communicate with the user the most accurate error response
        try {
            // check to make sure its no more than 4 and no less than 3 arguments as per requirements            
            if (args.length < 3 || args.length > 4) {
                println "RecursiveSearch Command Line Format: RecursiveSearch <directory> <original_text> <replace_text> [output_list_path]"
                return
            }

            // if the outPath is not given, the variable is still created but will store a null
            String outputPath
            if (args.length < 4) {
                outputPath = "outputtedList"
            } else {
                outputPath = args[3]
            }

            // we call our function that will do all the logic by giving all parameters that it needs
            new RecursiveSearch(args[0], args[1], args[2], outputPath)

            // we track what the date is to show the end time as well as how long it took by finding the difference between the end and start time
            Date end = new Date();
            println "End time: ${end}"
            println "Time taken: ${end.getTime()-start.getTime()} seconds"

        // We start catching errors that I think the user will make
        // The most common error in this case will be the inaccurate argument error. This will tell the use a more informational response to let the user know what to do to fix it.
        } catch (ArrayIndexOutOfBoundsException e) {
            println "Error: The number of command-line arguments are inaccurate ex. RecursiveSearch <directory> <original_text> <replace_text> [output_list_path] "
        } catch (IOException e) { // A exception that catches IO exceptions with the files that it is maniplulating
            println "File operation error: ${e.getMessage()}"
        } catch (Exception e) { // A generic exception is placed to track when any other error occurs. It will output the error that the exceptions returns
            println "General error: ${e.getMessage()}"
        }
    }
}
