Original App Design Project - README Template
===

# CollabDeck

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description
Our app lets registered users create sets of terms and definitions customized for their own needs. Users can work together in the same set in real time and leave comments on specific term.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Education
- **Mobile:** This app can be used as a mobile app or on a computer, with similiar features. However, students are more likely to use the mobile app when they are on the go.
- **Story:** Users are able to study together with their friends online and respond to each others questions, as well as motivate eachother through friendly competition.
- **Market:** Essentially anyone can use the app and customize to their needs. Students will be able to collaborate and study together. Teachers will be able to create study sets for their students. Parents will be able to create study sets for their kids.
- **Habit:** This app is likely to be used often by students, or simply anyone who likes to learn. Studying will also be gamified in this app by allowing collaborators to have a level of competition through adding a self-reported leaderboard for students to quiz themselves.
- **Scope:** First, we would start with letting users study together and test themselves with flashcards. We will add a way for users to mark the number of cards that they got right or wrong when quizzing themselves and populate a high score board, so all collaborators on a set of cards have a way of seeing eachothers' performance and will try to compete.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can sign up to create a new account using Parse authentication
* User can log in and log out of his or her account
* The current signed in user is persisted accross app restarts
* Upon login, user can view all sets
* User can create study set and add flashcards
* User can add collaborators to study set
* User can edit flashcards
* User can test themselves with term, definition, or both
* User can mark whether they got a correct or incorrect term/definition when quizzing themself
* Correct answer reports from the user will update a tally as they run through the set of flashcards.
* Tallies of correct answers (scores) as well as the associated user will be present on a leaderboard page which can be accessed from the flashcard set.
* User can comment on flashcard
* User can view all comments in study set/flashcards
* Profile page with options to edit profile picture, and logout
* Settings (notification, logout, delete account)
* User can update their profile image from their photo gallery or by taking a picture with their camera

**Optional Nice-to-have Stories**

* After the user creates a study set, show an indeterminate progress bar while the set is being uploaded to Parse.
* User can create class, add classmates who are automatically added to study sets created in class
* User can create folder, add study sets to folder
* Settings (offline studying)

### 2. Screen Archetypes

* Register
   * User can sign up to create a new account using Parse authentication
* Login
   * User can log in and log out of his or her account
   * The current signed in user is persisted accross app restarts
* Stream
   * Upon login, user can view all sets
   * User can view each collaborator's top score in study set
   * User can view all comments in flashcard/study set
* Detail
   * User can add collaborators to study set
   * User can edit flashcards
   * User can test themselves with term, definition, or both
* Creation
   * User can create study set and add flashcards
   * User can comment on flashcard
* Profile
   * Profile page to see user's profile picture, edit profile, or logout
   
* Settings
   * Settings (notification, logout, delete account)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Create
* Profile
* Settings

**Flow Navigation** (Screen to Screen)

* Landing Page -> Click on Register/Login button -> Home screen
* Home -> Click on flashcard set -> Set View screen
* Home -> Click on Logout Button -> Logout Screen
* Home -> Click on New Set button -> Create Blank Set screen
* Home -> Click on Profile button -> Profile screen
* Home -> Click on Settings button -> Settings screen
* Settings -> Click on Delete Account button -> Delete Account screen
* Profile -> Click on Edit Profile button -> Edit Profile screen
* Edit Profile -> Click Open Camera/Open Gallery button -> Opens the phone's Camera/Photo Gallery
* Set View -> Click on flashcard/Test Mode button -> Front of Card screen
* Set View -> Click on Leaderboard (star) button -> Leaderboard screen
* Set View -> Click on Add Card button -> Edit/Compose card screen
* Set View -> Click on Collaborators button -> Invite Collaborators screen
* Front of card -> Click on Flip Card button -> Back of Card screen
* Front of card -> Click on Comments button -> Flashcard Comments screen
* Front of card -> Click on Edit Card button -> Edit/Compose Card screen
* ('Front of Card' and 'Back of Card' screens share the same functionalities)
* Comments screen -> Click on Add Comment button -> Compose Comment screen

## Wireframes
![handdrawn designs](https://user-images.githubusercontent.com/85243009/200191100-a55a11ce-d7ff-4ce7-b5d6-c9fcf2f74483.jpg)


### [BONUS] Digital Wireframes & Mockups
![lowfi screens](https://user-images.githubusercontent.com/85243009/200190505-373b1bf9-47b8-4790-a999-555eb27b315b.png)
![hi-fi_design](https://github.com/I-m-Out-Of-Ideas/ImOutOfIdeas/blob/master/design%20files/hifi_design.png)

### [BONUS] Interactive Prototype
![navigation](https://user-images.githubusercontent.com/85243009/200195941-706c3704-6caa-472d-b580-28d3ce190187.gif)

## Schema 
[This section will be completed in Unit 9]
### Models
StudySet
|Property|Type|Description|
|-|-|-|
|setId|String|unique id for the study set (default)|
|setName|String|set name|
|username|Array of Pointer to User|study set collaborators|

FlashCard
|Property|Type|Description|
|-|-|-|
|cardId|String|unique id for the flashcard (default)|
|setId|Pointer to StudySet|StudySet setId|
|frontCard|String|term|
|backCard|String|definition|

Comments
|Property|Type|Description|
|-|-|-|
|commentId|String|unique id for the comment (default)|
|cardId|Pointer to FlashCard|FlashCard cardId|
|username|Pointer to user|User id|
|comment|String|comment|

LeaderBoard
|Property|Type|Description|
|-|-|-|
|leaderBoardId|String|unique id for the leaderboard (default)|
|setId|Pointer to StudySet|StudySet setId|
|userName|Pointer to User|User id|
|score|int|score user got while self testing|

### Networking
List of network requests by screen
- Home
  - (Read/GET) Query all study sets associated with user
  ```kotlin
  val query : ParseQuery<StudySet> = ParseQuery.getQuery(StudySet::class.java)
  query.include(StudySet.KEY_USER)
  query.addDescendingOrder("createdAt")
  query.findInBackground(object : FindCallback<StudySet> {
      override fun done(studySetList: MutableList<StudySet>?, e: ParseException?) {
          if (e != null) {
              Log.e("ACTIVITY" , "queryPosts failure ${e}")
          } else {
              if (studySetList != null) {
                  if (studySetList.size != 0) {
                      Log.e("ACTIVITY", "queryPosts success")
                  }
              }
          }
      }
  })
  ```
  - (Create/STUDYSET) Create a new study set
- Set View
  - (Create/FLASHCARD) Create a new flashcard
  - (Delete) Delete existing flashcard
-Front of Card
  - (Read/GET) Query all comments associated with flashcard
  ```kotlin
  val query : ParseQuery<Comment> = ParseQuery.getQuery(Comment::class.java)
        query.include(Comment.KEY_COMMENTER)
        query.include(Comment.KEY_STUDYSET)
        query.whereEqualTo(Comment.KEY_STUDYSET, studyset);
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Comment> {
            override fun done(commentList: MutableList<Comment>?, e: ParseException?) {
                if (e != null) {
                    Log.e("ACTIVITY" , "queryComments failure ${e}")
                } else {
                    if (commentList != null) {
                        if (commentList.size != 0) {
                            Log.e("RITIKA", "queryComments success")
                        }
                    }
                }
            }

        })
  ```
  - (Create/COMMENT) Create a new comment
  - (Delete) Delete existing comment
- Profile
  - (Read/GET) Query logged in user object
  - (Update/PUT) Update user profile image
