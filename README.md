# RecursiveSearch in Groovy

## Overview

The `RecursiveSearch` class is a Groovy program designed where its purpose is to search and replace text in files within a given directory recursively. Additionally, it can create a backup to files modified and can generate an audit file containing the list of modified files.

## How it Works

The program takes four command-line arguments:

1. `directory`: The path of the directory to search.
2. `search_text`: The text to search within files.
3. `replace_text`: The text to replace the searched text with.
4. `output_path` (optional): The path where the log file will be generated. If not provided, it defaults to a file called `outputtedList`.

This is an example: 
```bash 
groovy RecursiveSearch <directory> <original_text> <replace_text> [output_list_path]
```

This an example you can use now!
```bash 
groovy RecursiveSearch .\DirectoryofFiles\ apple peach AuditFile
```

### Class Constructor

The constructor `RecursiveSearch(String dir, String search, String replace, String outputPath)` initializes the main logic of the program.

### Features

- Checks if the given path is a directory.
- Creates a backup directory for storing original files before modification.
- Recursively searches the directory and all its files to find and replace text.
- Audits the modified files.
- Measures the time taken for the operation to complete.

## How to Run

Before you run the program in the command line of your choice, you must make sure that Java as well as Groovy are installed and configured to your environment. 

Run the program using the following command:

```bash
groovy RecursiveSearch.groovy <directory> <search_text> <replace_text> [output_path]
```

## Error Handling
The program has  error handling that includes:
- Checks for invalid directory paths.
- Checks for the correct range of arguments.
- Checks for IO issues with the file.


## Logging
The program logs the start time, end time, and the time taken for the execution to complete. It also logs the actions taken during the core logic. 

## Code Structure
The program is divided into the following methods:

- `RecursiveSearch(String dir, String search, String replace, String outputPath)`: The constructor that initializes and controls the logic of the program.
- `main(String[] args)`: The entry point of the program that parses command-line arguments and handles command-line errors.
