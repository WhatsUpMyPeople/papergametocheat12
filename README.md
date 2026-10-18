# PaperGameRig

Client-side Fabric mod for **Minecraft 26.2** (Fabric Loader 0.19.5, Fabric API 0.160.0+26.2, Java 25, Mojang Mappings). Rigs PaperGame-style gambling minigames (PaperGame item swap, 50/50, Blackjack) in single-player / offline testing. Liquid-glass UI, keybinds, per-tab config.

**NOT FOR USE ON SERVERS YOU DO NOT OWN.** Server-side anti-cheat will catch item swaps; use only in your own worlds or on servers where rigging is permitted.

## Features

- **PaperGame tab** — Rig toggle, Side A / Side B item IDs (with "From hand" grab), toggle key, switch-side key
- **50/50 tab** — Force heads/tails/alternate/random, win-streak target
- **Blackjack tab** — Force dealer bust, force player 21, card-count assist overlay
- **Settings tab** — Overlay on/off, overlay key, open-settings key
- **Liquid glass UI** — blurred backdrop, frosted panels, soft inner glow
- Config persisted to `config/papergamerig.json`

## Build

```bash
./gradlew build
```

Jar lands in `build/libs/papergamerig-1.0.0.jar`. Drop it in `.minecraft/mods/` alongside Fabric API.

## GitHub Actions

Pushing to `main` (or any tag `v*`) builds the jar and uploads it as an artifact. Tagged pushes also create a Release with the jar attached. See `.github/workflows/build.yml`.

## Keybinds (default)

| Action | Key |
|---|---|
| Open settings | P |
| Toggle overlay | O |
| Toggle rig | 6 |
| Switch rig side | 7 |

All rebindable in Minecraft's Controls menu or inside the mod's settings panel.
