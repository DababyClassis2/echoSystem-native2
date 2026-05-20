# System Diagrams

## 1. High-Level Architecture
```
[UI Layer] → [ViewModels] → [Repositories] → [Ktor Server]
                                   ↑
                        [Discovery Modules]
```

---

## 2. Discovery Flow
```
BLE Scan ─┐
NSD Scan ─┼──> DeviceRegistry → UI
WiFi Direct ─┤
UDP Broadcast ┘
```

---

## 3. File Transfer Flow
```
Sender → ChunkedUploader → Ktor Client → Receiver Ktor Server → FileRoutes.kt
```

---

## 4. Service Lifecycle
```
App Launch → Start Foreground Service → Start Ktor → Start Discovery → UI Updates
```
