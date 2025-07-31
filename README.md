# 📰 News Catcher (Jetpack Compose + MVVM + Clean Architecture)

**News Catcher** is a sleek, Kotlin-based Android news application built with **Jetpack Compose**, **MVVM**, and **Clean Architecture**. It allows users to browse, bookmark, and share news articles, with support for language switching and a modern, responsive UI. The app uses **Kotlin Coroutines** for asynchronous operations and **Hilt** for dependency injection, ensuring a scalable and maintainable architecture.

---

## 📸 Screenshots

| Home Page | Article Details | Bookmarks | Language Selection |
|-----------|-----------------|-----------|---------------------|
| <img src="screenshots/home.png" width="200" height="400" /> | <img src="screenshots/details.png" width="200" height="400" /> | <img src="screenshots/bookmarks.png" width="200" height="400" /> | <img src="screenshots/language.png" width="200" height="400" /> |

---

## 📱 Key Screens & Features

- **Home Page**
  - Browse the latest news articles with horizontal carousel
  - Real-time search functionality
- **Article Details**
  - Bookmark articles for later reading  
  - Share articles via other apps  
  - Open full article in browser  
- **Bookmarks Page**
  - View and manage saved articles
- **Language Page**
  - Instantly switch between supported languages (AZ, EN)
- **Navigation**
  - Type-safe navigation using Jetpack Navigation
- **Responsive UI**
  - Entire UI built with Jetpack Compose

---

## 🧠 Technologies Used

| Technology | Purpose |
|------------|---------|
| **Kotlin** | Core programming language |
| **Jetpack Compose** | Declarative UI framework |
| **Kotlin Coroutines** | Asynchronous and concurrent operations |
| **MVVM** | Architectural pattern |
| **Clean Architecture** | Layer separation (UI, Domain, Data) |
| **Hilt** | Dependency injection |
| **Jetpack Navigation** | Type-safe screen navigation |
| **Coil** | Image loading |
| **Kotlinx Serialization** | Data serialization for navigation |
| **SharedPreferences** | Persist language settings |
| **Room** (assumed) | Local DB for bookmarks |
| **Retrofit** (assumed) | Network layer |
| **StateFlow** | Reactive state management |

---

## ⚙️ Core Features

✅ Browse and search news articles  
✅ Bookmark articles for offline viewing  
✅ Share articles via external apps  
✅ Language switching (AZ / EN) with persistence  
✅ Responsive UI using Jetpack Compose  
✅ Type-safe and flexible navigation  
✅ Proper error/loading state handling  
✅ Infinite scrolling support (via `LazyColumn`)

---

## 🧱 Project Architecture

### 🧩 Clean Architecture + MVVM

#### 1️⃣ UI Layer
- Composable Screens (`HomeScreen`, `DetailScreen`, etc.)
- ViewModels with `StateFlow` for reactive state
- Mappers for UI ↔ Domain models

#### 2️⃣ Domain Layer
- Use Cases (`GetNewsUseCase`, `SearchArticlesUseCase`, etc.)
- Pure Kotlin classes
- Business logic isolated from UI & data

#### 3️⃣ Data Layer
- Repositories for local and remote sources  
- Retrofit (for API), Room/SharedPreferences (for local)  
- Mappers for Data ↔ Domain models

---

## 🔁 Coroutine Flow

- **StateFlow** for UI state (Success, Error, Loading)
- **Suspend** functions for use cases & repositories
- **Flow** for local data streams (Room)
- **Retrofit** (suspend) for remote data calls

---

### 🔨 Installation

Clone the project:

```bash
git clone https://github.com/your-username/news-catcher.git
cd news-catcher
