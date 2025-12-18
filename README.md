# PocketArcade – Android Games App

PocketArcade is a native Android application that contains a collection of small, classic games built with **Kotlin**.
The project focuses on **clean architecture, modern Android practices, and testability**, using XML-based UI and a modular structure.

The main purpose of this project is to **practice and strengthen Android development skills**, including **MVVM, dependency injection, API integration, and unit testing**, while building fun and well-structured games.

---

## 📦 Technologies

| Layer                | Technologies                                   |
| -------------------- | ---------------------------------------------- |
| Language             | Kotlin 2.x                                     |
| UI                   | XML layouts, ViewBinding                       |
| Architecture         | MVVM                                           |
| Async                | Kotlin Coroutines                              |
| Dependency Injection | Dagger Hilt                                    |
| Networking           | Retrofit, OkHttp                               |
| Testing              | JUnit 4, MockK, Coroutines Test, LiveData Test |
| Build                | Gradle Version Catalog (libs.versions.toml)    |
| Android              | Android SDK, Jetpack Libraries                 |

---

## 🎮 Games

<details>
<summary><strong>❌⭕ Tic-Tac-Toe</strong></summary>

**Implemented:**
- Classic **3×3 grid** turn-based gameplay with X and O
- Support for **two players (PvP)** on the same device
- Win detection for:
  - Horizontal rows
  - Vertical columns
  - Diagonal lines
- Draw detection when the board is full and no player has won
- Reset / restart functionality for starting a new game
- Simple and clear UI using a button grid
- Clean separation between UI and game logic

**Architecture & Design:**
- Game rules implemented in a **pure Kotlin game class**
- UI state handled via **ViewModel (MVVM)**
- No UI logic inside the game engine, making it easy to test and extend
- Designed to be reusable for future difficulty levels or AI logic

**Testing:**
- Unit tests for core Tic-Tac-Toe logic:
  - Correct win detection
  - Draw/tie scenarios
  - Turn switching between players
  - Board state updates
</details>

<details>
<summary><strong>🪢 🧍‍♂️ Hangman</strong></summary>

**Implemented:**
* Random word fetching via external API
* Fallback to local default words if API fails
* Letter-by-letter guessing with visual hangman progress
* Game states: loading, playing, win, and game over
* Reset game support
* Swedish alphabet support (Å Ä Ö)
* Clean separation of:
  * Game logic (`HangmanGame`)
  * State model (`HangmanGameState`)
  * UI logic (`HangmanViewModel`)

**Testing:**

* `HangmanGameTest`
  * Correct, incorrect, and duplicate guesses
  * Win and game over conditions
    
* `HangmanViewModelTest`
  * Loading state handling
  * Word fetching via repository
  * State updates after guesses
</details>

---

## 🚀 Features (current / planned)

### **Implemented**

* MVVM architecture across all games
* Dagger Hilt for dependency injection
* External API integration for game content
* Clear separation of UI, domain, and data layers
* Unit tests for core game logic and ViewModels
* SafeArgs navigation between screens
* Reusable game components and utilities

### **Planned / Future Enhancements**

* More games:
  * Memory
  * Word-based mini games
* Improved and animated UI
* Sound effects and haptic feedback
* Persistent score tracking
* Difficulty selection for all games
* Offline-first improvements and caching
* Expanded test coverage (UI tests)

---

## 🧪 Testing Philosophy

PocketArcade emphasizes **testable game logic**:

* Game rules are kept outside UI components
* ViewModels are tested with mocked repositories
* Coroutines and LiveData are tested using test dispatchers
* External dependencies are isolated for reliable unit tests

---

## 📌 Project Goals

* Practice **real-world Android architecture**
* Improve **code readability and maintainability**
* Build a strong **Android-focused portfolio project**
* Gradually expand functionality without sacrificing structure
