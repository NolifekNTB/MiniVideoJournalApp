# 📸 MiniVideoJournalApp

MiniVideoJournalApp is a simple Android app to **record**, **save**, and **view** short video journals.  
It uses modern Android technologies like **Jetpack Compose**, **CameraX**, **SQLDelight**, and **Koin** for a clean, scalable architecture.

---

## 🛠 Tech Stack

- **Jetpack Compose** – Modern declarative UI toolkit
- **CameraX** – Access device cameras easily
- **SQLDelight** – Typesafe local SQLite database
- **Koin** – Lightweight Dependency Injection
- **Kotlin Coroutines** – Asynchronous programming
- **Accompanist Permissions** – Manage runtime permissions easily

---

## 📋 Main Features

- Record videos using the device's camera and microphone
- Save videos with optional descriptions
- View a list of recorded videos
- Play saved video journals
- Request runtime permissions dynamically
- Simple bottom navigation between recording and video list

---

## 📂 Project Structure


---

## 🚀 Getting Started

### Prerequisites
- Android Studio Giraffe or newer
- Android device/emulator running API 29+ (Android 10+)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/MiniVideoJournalApp.git
    ```
2. Open the project in Android Studio.
3. Run the app on an emulator or physical device.

### Permissions Required
- **Camera** access (to record videos)
- **Microphone** access (to capture audio)
- **Storage** access (to save and load video files)

---

## 📄 License

This project is licensed under the MIT License.  
(Feel free to replace this with your own license.)

---

## 📈 Future Improvements

- Add ability to delete videos
- Support video categories or tagging
- Cloud backup support (e.g., Firebase, Drive)
- Add full error handling for failed recordings
- Enhance UI with animations and better UX

---

## 📢 Notes for Reviewers / Interviewers

- Follows **MVVM architecture**.
- **Dependency Injection** with Koin.
- **SQLDelight** ensures type-safe database access.
- Proper **dynamic permissions** handling for Android 10, 11, 12, 13+.
- Designed with **scalability** and **testability** in mind.

---

# 🔥 Thank you for checking out MiniVideoJournalApp!
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
