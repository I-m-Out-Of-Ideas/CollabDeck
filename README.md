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
* User can view all comments in study set
* User can view all comments in flashcard
* Profile page to see user's sets, edit profile
* Settings (notification, logout, delete account)
* User can update their profile image from their camera roll

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
   * User can view all comments in study set
   * User can view each collaborator's top score in study set
   * User can view all comments in flashcard
* Detail
   * User can add collaborators to study set
   * User can edit flashcards
   * User can test themselves with term, definition, or both
* Creation
   * User can create study set and add flashcards
   * User can comment on flashcard
* Profile
   * Profile page to see user's sets, edit profile
   
* Settings
   * Settings (notification, logout, delete account)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Create
* Profile

**Flow Navigation** (Screen to Screen)

* Landing Page -> Register/Login -> Home
* Home -> Click on set -> Set Detail page -> Click on flashcard -> Edit Flashcard page
* Home -> Click on set -> Set Detail page -> Click on leaderboard button -> Leaderboard page
* Create -> Create set page -> Set Detail page
* Profile -> Edit Profile page
* Setting -> Settings Page

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
