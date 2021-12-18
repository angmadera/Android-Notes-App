# Android-Notes
An android developed app that allows the creation and maintenance of personal notes. Any number of notes are allowed (including no notes at all). Notes are made up of a title, a note text, and a last-update time.

## Details
The notes are saved and loaded from the internal file system in JSON format.  If no file is found upon loading, the application will  start with no existing notes and no errors and a new JSON file would be created.

JSON file loading happens in the onCreate method. Saving happens whenever a new note is added or a note is deleted.

A Note class (with title, note text, and last save date) is created to represent each individual note in the application.

The application is made up of 3 activities: Main Activity, Edit Activity, and About Activity.

## Main Activity
Main Activity allows the user to create a new note via an Add options-menu item. Tapping this menu icon will open the Edit Activity with empty Title and Note Text areas. 

The main activity allows the user to edit an existing note by tapping on an existing note in the displayed list of notes. Doing so  opens the Edit Activity, displaying the note’s Title and Note Text – both available for editing.

The main activity also has an Info options-menu item. Tapping this menu icon will open the About Activity  when pressed. The About Activity indicates the application’s name, the date & author, and the version number.

Note list-entries contain the note title (bold), the last-save-time, and the first 80 characters of the note text. Notes titles or note text with more than 80 characters display only the first 80 characters with “…” at the end (to indicate there is more content available).

The app title “Android Notes” ad the current number of notes is displayed in the title-bar.

## Edit Activity
The Edit Activity contains editable fields for the note title and note text. The last-save time automatically generated and saved when the note is saved.
The note title is a single-line text field (EditText) where the user can enter or edit a title for the current note (no size limit).

The note text is a multi-line text area (EditText) with no size limit. This has scrolling capability for when notes exceed the size of the activity.

The Edit Activity allows the note title & text to be saved by either:
a) Pressing the Save options-menu item. This will return the new note to the MainActivity, and then exit the Edit Activity. MainActivity adds the note to the MainActivity’s list of notes. Note that if no changes have been made to the current note, the Edit Activity simply exits.
b) Pressing the Back arrow to exit the activity. This will first display a confirmation dialog where the user can opt to save the note (if changes have been made) before exiting the activity. If saved, the new note is returned to the MainActivity, and then exit the Edit Activity. MainActivity then adds the note to the MainActivity’s list of notes. Note that if no changes have been made to the current note, the Edit Activity simply exits.

Note this “Save” in steps “a” and “b” does not imply saving to file – it implies updating an existing note object in the Main Activity’s note list or adding a new note to the Main Activity’s note list.

A note without a title is not allowed to be saved (even if there is note text). If such an attempt is made, it shows an Ok/Cancel alert dialog telling the user the note will not be saved without a title. 

Selecting Ok should close the dialog and EditActivity without saving

Selecting Cancel should close the dialog and remain in the EditActivity.

The activity background should be set to a color other than white (the same color used in the Main Activity). The title and text fields should be a lighter version of that same color (the same color used in the Main Activity).

## About Activity
The About Activity contains a full-screen image background.

Over the background image, key information on the application is clearly displayed. This includes the application title, a copyright date and your name, and the version number
(1.0).

There is no functionality present on this activity. The only action a user can take is to press the Back arrow to exit the activity.
