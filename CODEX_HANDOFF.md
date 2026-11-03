# SHUE Trainer — Codex handoff

## Goal
Build a realistic, offline-first Android educational simulator of a ШУЭ (шкаф управления электролизом) for instrumentation/automation trainees. User operates from a phone now and will later use a PC. Source repository: ggumno/shue-trainer.

## Current status (2026-11-03)
- Kotlin / Jetpack Compose Android prototype committed on main.
- GitHub Actions workflow .github/workflows/android-apk.yml exists, but first run failed. **Inspect the actual GitHub Actions job logs before assuming cause.** Fix build, rerun and verify an installable debug APK artifact.
- Current code is only a preliminary scaffold with a 15-code fault list and a deliberately *fictional/conditional* KA1 demonstration. It is not a faithful electrical simulator yet.
- An app icon image showing the front of a blue-framed industrial cabinet was generated in chat, but **not committed as launcher icon**. Request image upload or source asset if needed. Do not assume the asset is in the repository.
- Repository was observed as **public** although user intended private. Ask user to switch visibility to private before uploading internal photographs or detailed schematics.

## Visual and interaction requirements
- One cabinet model only. Use the **first user-provided front-panel photo**: blue-framed NEUI-PIC300 front with displays, switches, buttons. Other photos show an identical cabinet interior behind a different faceplate; use them for component placement, not as a second selectable cabinet.
- Photorealistic interactive cabinet, doors opening, touch targets on actual components; synchronized interactive electrical schematic and virtual multimeter with two movable probes at real terminals; controls, indicator displays, fault response.
- Instructor selects a displayed fault code and an underlying physical failure; student diagnoses through simulated measurements, earns assessment.
- Do not fabricate exact wire routing, coil pinouts, signal polarity or voltages from partially readable photographs. Clearly distinguish verified facts from provisional simulation assumptions.

## Electrical identifiers visible in supplied photos
PW1: ±15V and 5V; PW2: 12V for I/O; PW3: 5V for V/F. Motherboard PV+/PV−, relays KA1–KA4 and KS1–KS7, contactors 1JC–4JC. Fault list uses KM1–KM4; whether these correspond to 1JC–4JC must be verified, not assumed.

## Authoritative 15 fault codes (second-phase list from latest clearer photograph)
1. Нет контакта KM1, KM2
2. Залипание KM1, KM2
3. Залипание KM3, KM4
4. Неисправность таймера
5. Неисправность узла возд. перекл-ля (руч/авт)
6. Обрыв питания (3-х фаз)
7. Обрыв фазы двигателя
8. Неисправность преобразователя V/F
9. Неисправность кнопки перемещения анода вверх
10. Неисправность кнопки перемещения анода вниз
11. Превышенное время по таймеру
12. Нет контакта KM3
13. Нет контакта KM4
14. Залипание кнопки перетяжки
15. Отказ сброса таймера

The 15 codes are already present in app/src/main/assets/faults.json. Prior mistaken catalog omitted #12 and shifted #13/#14; **do not reintroduce that mistake**.

## Development and delivery
- Kotlin + Jetpack Compose.
- Offline-first; optional JSON lesson/scenario content updates from GitHub with versioning and validation; APK distribution via GitHub Actions artifacts or Releases. Do not promise silent APK installation.
- Aim for reproducible GitHub Actions debug APK first, then icon, interface, scenarios, circuit model, assessment.
- Keep photo/schematic assets out of public repositories.
- User speaks Russian; respond in Russian.
- User asked to move chat into Codex: this file is a summary, not a literal full chat transcript. Ask for original images when implementation requires them.
