<#
    Rename all the files of a directory. The new name is the concat of $baseName and an int, default to 1.
    Then move all the renamed files into a new folder.


    Before
            folderPathSource     |     folderPathDestination    
        ------------------------ | -----------------------------
        aGIFFile.gif             | <empty>
        aPNGFile.png             |
        aSecondPNGFile.png       |
        aTxtFile.txt             |


    The configuration is
        $baseName = "my_new_file_naming_convention"
        $firstID = 743


    After
            folderPathSource     |     folderPathDestination    
        ------------------------ | -----------------------------
        <empty>                  | my_new_file_naming_convention743.gif
                                 | my_new_file_naming_convention744.png
                                 | my_new_file_naming_convention745.png
                                 | my_new_file_naming_convention746.txt


    
    This is my first PowerShell script. I know that I can rename and move a file thank to the command Move-Item.
    In the same time, I want to play a bit with the syntax :).

    Last updated: 2024/10/09
#>


Function MoveAllElementsFromOneFolderToAnotherOne {
	param (
		[string]$theFolderPathSource, 
		[string]$theFolderPathDestination
	)
	$filesToMove = "{0}{1}" -f $theFolderPathSource, "\*"
	Move-Item -Path $filesToMove -Destination $theFolderPathDestination
}

Function RenameAllElementsOfOneFolder {
	param (
		[string]$theFolderPath, 
		[string]$theBaseName,
		[int]$theFirstID=1
	)
	$files = Get-ChildItem -Path $theFolderPath
	$count = $theFirstID

	foreach ($file in $files) {
		$newName = "{0}{1}{2}" -f $theBaseName, $count, $file.Extension
		Rename-Item -Path $file.FullName -NewName $newName
		$count++
	}
}



$folderPathSource = "C:\dev\sources\day-to-day-personal-scripts\PowerShell\RenameFiles\to_do"
$folderPathDestination = "C:\dev\sources\day-to-day-personal-scripts\PowerShell\RenameFiles\done"
$baseName = "my_new_file_naming_convention"
$firstID = 743

RenameAllElementsOfOneFolder -theBaseName $baseName -theFirstID $firstID -theFolderPath $folderPathSource 

MoveAllElementsFromOneFolderToAnotherOne $folderPathSource $folderPathDestination
