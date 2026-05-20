# API Documentation

## Ktor Server Routes

### GET /device/info
Returns:
```json
{
  "deviceId": "...",
  "deviceName": "...",
  "localIp": "..."
}
```

### POST /pairing/verify
Body:
```json
{ "pin": "1234" }
```

### POST /transfer/upload
Multipart file upload endpoint.

---

## Discovery Events

### BLE
- Emits `DeviceCandidate(address, port, "ble")`

### NSD
- Emits `DeviceCandidate(host, port, "nsd")`

### WiFi Direct
- Emits `DeviceCandidate(mac, 8080, "wifi_direct")`

### UDP
- Emits local IPv4 addresses for broadcast scanning.

---

## ViewModel State

### HomeViewModel
- `localIp: StateFlow<String>`

### DeviceViewModel
- `selectedDevice: StateFlow<DeviceCandidate?>`

### FilesViewModel
- `selectedFiles: StateFlow<List<File>>`
