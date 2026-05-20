# Failure Modes Report

## 1. Discovery Failures
- WiFi Direct may fail if permissions are missing.
- NSD may fail on networks with multicast disabled.

## 2. Transfer Failures
- Large files may cause memory pressure.
- Interrupted WiFi may corrupt sessions.

## 3. Service Failures
- Foreground service may be killed on low-end devices.

## 4. Ktor Failures
- Port conflicts
- Slow file writes on older devices

## 5. UI Failures
- Missing state handling for long transfers
