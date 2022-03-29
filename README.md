# TaskMaster

Coded by Robb Alexander, 2022

## Change Log

### 03/21/22 (Lab 26: Beginning TaskMaster)

* Initial project built
  
* Created initial activities per wireframe provided

  * Main activity renamed to **MyTasks**

  * **AddTask** activity added

  * **MyTasks** activity added

* Added non-functional UI elements

  * Added event listener to submit button on AddTaskActivity

* Screenshots:

  * ![Lab 01 Homepage](screenshots\Screenshot_20220321_204003.png)

* APK created; copy located in project root

### 03/22/22 (Lab 27: Data in TaskMaster)

* Added activities:

  * **TaskDetail**

  * **Settings**

* Added intent *from* MyTasks *to* TaskDetail

  * Wired into buttons (TaskDetail title conditioned on particular button clicked)

* Added intent *from* MyTasks *to* Settings

  * Wired into button

* Screenshots:

  * ![Updated Main screen](screenshots/Screenshot_20220322_210119.png)

  * ![Conditional Task Tile](screenshots/Screenshot_20220322_210139.png)

* Updated APK in root

### 03/23/22 (Lab 28: RecyclerViews for Displaying Lists of Data)

* Added RecyclerView functionality:

    * **MyTasksRecyclerViewAdapter** class

    * **fragment_task* fragment for

    * **Task** class created

* Debug:

    * Fixed main title username display only updating on app restart

* Screenshots:

    * ![Home Screen With RecyclerView](screenshots/Screenshot_20220323_165650.png)

* Updated APK in root

### 3/24/22 (Lab 29: Room)

* Added dependencies and annotations for [Room](https://developer.android.com/jetpack/androidx/releases/room) database support

    - Refactored **Task** class as @Entity for database usage

* TaskDetail activity pulls

* Screenshots:
    * ![TaskDetail screen with db-sourced text](screenshots/Screenshot_20220324_231647.png)

### 3/28/22 (Lab 31: Espresso & Polish)

* Fixed crash on task selection after Room integration

* Fixed newly added Tasks only populating main page after app restart

* Screenshots:

* Added Espresso tests (note-- 1 Task is hard-coded to allow for some Intention testing after Room feature integration)
  * 

  *

