<p align="center"><img width="100" height="100" src="app/src/main/res/drawable/alarmlogo.png"></p>


# WakeUp ⏰
WakeUp is a user-friendly alarm app that helps you start your day on time. You can easily set daily or one-time alarms. When the alarm rings, you must solve a small challenge to stop it, ensuring that you're fully awake. WakeUp focuses on essential features to provide a reliable and easy experience for managing alarms.

## Features ✨
- Simple Alarm Setup: Quickly set one-time or recurring alarms with just a few taps.
- Wake-Up Challenge: Solve a small challenge a math problem to stop the alarm, ensuring you're fully awake before turning it off.
- Snooze Functionality: Easily snooze the alarm for a few extra minutes.
- Multiple Alarms: Set and manage multiple alarms for different tasks or events throughout the day.
- Alarm Notifications: Receive reminders and notifications before an alarm rings to prepare you in advance.
- Alarm Preview: See a quick preview of your next alarm on the home screen for easy reference.
- Custom Alarm Messages: Attach a personalized message to each alarm, so you receive a specific reminder along with the alarm, helping you remember important tasks or events.
- Flexible Alarm Scheduling: Set the same alarm to ring on different days of the week, allowing you to customize your schedule and avoid having to create multiple alarms for recurring events.
- Edit or Delete Alarms: Easily modify or remove existing alarms as needed. You can adjust alarm times, messages, or other settings, or delete alarms that are no longer needed.
- Battery Optimized: Designed to minimize battery usage while keeping alarms accurate and reliable.

## Preview 📱

https://github.com/user-attachments/assets/132fe1f9-a39c-427a-9b23-e08b129ec0f4






## Technical Details


### Architecture
Clean Architecture: The app is structured using Clean Architecture principles, which include the following layers:
- Data Layer: Manages data sources and repository implementations, interacting with the Room database to handle alarm data.
- Domain Layer: Contains business logic and use cases, providing a clear separation between the data and presentation layers.
- Core Layer: Includes shared utilities and common code used across the application.
- Presentation Layer: Manages UI components and interacts with the ViewModel to display alarm-related information.
- Dependency Injection (DI): Uses a DI framework Dagger-Hilt to manage dependencies across the app.
- Receiver: Handles broadcast events related to alarm triggers.
- Service: Manages background and foreground tasks related to alarms and notifications.

### Technical Handle
- Handle Multiple Alarms at the Same Time :  The app is designed to efficiently manage scenarios where multiple alarms are set to trigger simultaneously. Each alarm is processed individually, leveraging background services to ensure accurate and timely activation of all alarms without conflicts.
  
- Manage Alarm Days : Implements logic to automatically update the alarm schedule by removing the current day from the alarm's list of scheduled days once the alarm rings. If the current day is the last scheduled day for that alarm, the alarm is deactivated.
  
- Alarm Functionality with Screen Off : Ensures that alarms will ring even if the phone screen is turned off. This feature allows alarms to activate reliably regardless of the phone's display status, ensuring users are woken up or reminded as scheduled.
  
- Handle Multiple Pre-Alarms Notifications : Manages multiple pre-alarm notifications scheduled to trigger at the same time, ensuring each notification is displayed correctly without overlap or conflict.
  
- Handle Alarm Modifications and Deletions : Manages updates or removals of alarms, ensuring that any changes are promptly reflected in both the UI and background services. This includes adjusting or canceling alarms based on user modifications or deletions
  
- Handle Alarm Rescheduling : If an alarm is set for a time that has already passed earlier in the current day, the app automatically reschedules it to the same time on the same day of the following week, ensuring the alarm remains active and timely.
  
- Handle Alarm Restart on Phone Reboot : If the phone is restarted, the app automatically restarts all alarms to ensure they are rescheduled and activated as intended. This ensures that alarms continue to function correctly even after a device reboot
  
- Handle Random Questions on Alarm Screen : Every time the alarm screen is displayed, it presents a random question to engage the user. This feature adds an interactive element to the alarm experience, requiring users to answer the question before dismissing or snoozing the alarm.
  
- Remove App from Recent Apps List : After exiting the alarm screen, the app automatically removes itself from the recent apps list to prevent users from reopening the alarm screen once the alarm has been stopped. This ensures that the alarm screen cannot be accessed again unless a new alarm is triggered.
  

### Techniques 🛠️

- Android ( kotlin )
- XML
- Data Binding
- Service
- Broadcast Receiver
- Shared preferences
- Room Database
- Dagger hilt
- MVVM Architecture for clean and maintainable code.
