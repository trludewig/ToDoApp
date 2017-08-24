# Pre-work - *Simple ToDo*

**Simple ToDo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Tenley Ludewig**

Time spent: **15** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] List anything else that you can get done to improve the app functionality!

I used cupboard to interface with Sqlite in a clean, simple way.  I used a CursorAdapter for the first time.  I moved my strings into a string.xml resource file and created a Constants and Utils class.  I used a common activity class for both Add and Edit behavior.  I made a clean line item in the list view with priority and due date on the right vertically stacked.  I forced the orientation to remain in portrait mode such since state is not currently maintained across configuration changes.  Organized classes in packages.  Finally, I used a DatePickerDialog fragment per suggested best practices.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I love it!  In just a few minutes, one can feel a sense of accomplishment and have a little app that works!  Android themes, layouts, and styling remind me of CSS in my past web development.  Both use styling inheritance, leverage different layout patterns such as grid, linear, etc., and either relative or absolute positioning.  Compared to web development, the layouts in Android are much more decoupled from the business logic.  That said, I have not done any serious development in four years, so things may have changed.  

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The function of the ArrayAdapter is to serve as the glue between the Array (or data structure holding the data) and the view.  It is important because it decouples the business logic of the app with the UI.  The convertView is a view of an array item that is being recycled and passed into getView to be reused.

## Notes

Describe any challenges encountered while building the app.

When "parcelizing" my TodoItem, cupboard didn’t understand that on edit, there is a cupboard _id value and on add, there is not.  writeToParcel crashed the app on add because _id was null.  Therefore, I moved the _id field to the last field parsed so that my other fields didn’t get read in as longs and I added a null check.  See line 60 in TodoItem.

I also was not sure if it is expensive or correct to get a new cursor every time the db is changed and switch it out in the adapter.  This worked but I was not sure if this was the correct methodology.

Out of the box Android Studio gave me a ConstraintLayout and it made a very brittle layout of the items I dragged onto the screen.  I do not trust the layout designer in the Studio and prefer to use the text perspective.  Often, the design view shows the components laid out one way while in the emulator, the components display in a different way.  After a little troubleshooting, I found that the button component had to be defined prior to the other layout components that reference it. 

## License

    Copyright [2017] [Tenley Ludewig]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.