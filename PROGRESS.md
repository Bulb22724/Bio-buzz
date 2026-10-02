# FTC Vision Progress Tracker
| Day  | Date | Core | Stretch | Notes |
|------|------|------|---------|-------|
| I    | 10/1 |      |         |       |
| II   |      |      |         |       |
| III  |      |      |         |       |
| IV   |      |      |         |       |
| V    |      |      |         |       |
| VI   |      |      |         |       |
| VII  |      |      |         |       |
| VIII |      |      |         |       |
| IX   |      |      |         |       |
| X    |      |      |         |       |
# Measured Values
- Camera, resolution: Logitech C720 "Webcam 1", 640x480
- Camera height (in), pitch (deg), offset from robot center (X/Y) in: 
- Horizontal FOV (deg), fx (pixels):
- Tag detection range with 4/2/1 3.6in tags (in):
- Wheel motor names: CH: rf 0; rb 1; claw 2; elevator 3 EH: lf 0; lb 1
## Problems and fixes
- (date) problem --> fix 
# Quick GIT reference

| Scenario                                   | Command                                          |
|--------------------------------------------|--------------------------------------------------|
| File broken, last version needed           | git restore path/to/file.java                    |
| Weekly progress check                      | git log --oneline --graph --all                  |
| When did a tuned value change              | git log -p path/to/constants.java                |
| Undo a commit already pushed               | git revert <commit-id>                           |
| Put unfinished work aside to switch branch | git stash; later git stash pop                   |
| see code as it was at the end of day x     | git switch --detach dayX-done; git switch master |
| which days are done                        | git tag                                          |
